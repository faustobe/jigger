package it.faustobe.jigger.ui.shift;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.ui.common.CocktailSearchAdapter;

public class SearchCocktailBottomSheet extends BottomSheetDialogFragment {

    private TextInputEditText searchEditText;
    private RecyclerView resultsRecyclerView;
    private TextView emptyResultsText;
    private MaterialButton addCustomButton;
    private CocktailSearchAdapter adapter;

    private List<Cocktail> allCocktails = new ArrayList<>();
    private OnCocktailSelectedListener listener;
    private OnCustomCocktailRequestListener customCocktailListener;

    public interface OnCocktailSelectedListener {
        void onCocktailSelected(Cocktail cocktail);
    }

    public interface OnCustomCocktailRequestListener {
        void onCustomCocktailRequested();
    }

    public static SearchCocktailBottomSheet newInstance() {
        return new SearchCocktailBottomSheet();
    }

    public void setCocktails(List<Cocktail> cocktails) {
        this.allCocktails = cocktails != null ? new ArrayList<>(cocktails) : new ArrayList<>();
        if (adapter != null) {
            filterCocktails("");
        }
    }

    public void setOnCocktailSelectedListener(OnCocktailSelectedListener listener) {
        this.listener = listener;
    }

    public void setOnCustomCocktailRequestListener(OnCustomCocktailRequestListener listener) {
        this.customCocktailListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_search_cocktail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchEditText = view.findViewById(R.id.searchEditText);
        resultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        emptyResultsText = view.findViewById(R.id.emptyResultsText);
        addCustomButton = view.findViewById(R.id.addCustomButton);

        setupAdapter();
        setupSearch();
        setupAddCustomButton();

        // Mostra tutti i cocktail inizialmente
        filterCocktails("");

        // Focus sulla casella di ricerca
        searchEditText.requestFocus();
    }

    private void setupAddCustomButton() {
        addCustomButton.setOnClickListener(v -> {
            if (customCocktailListener != null) {
                customCocktailListener.onCustomCocktailRequested();
            }
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Espandi il bottom sheet
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setSkipCollapsed(true);

            // Imposta altezza minima
            parent.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }

    private void setupAdapter() {
        adapter = new CocktailSearchAdapter();
        adapter.setListener(cocktail -> {
            if (listener != null) {
                listener.onCocktailSelected(cocktail);
            }
            dismiss();
        });
        resultsRecyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCocktails(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterCocktails(String query) {
        List<Cocktail> filtered = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();

        for (Cocktail cocktail : allCocktails) {
            if (lowerQuery.isEmpty() ||
                cocktail.getName().toLowerCase().contains(lowerQuery) ||
                cocktail.getCategory().toLowerCase().contains(lowerQuery)) {
                filtered.add(cocktail);
            }
        }

        // Ordina: preferiti prima, poi alfabetico
        filtered.sort((a, b) -> {
            if (a.isFavorite() != b.isFavorite()) {
                return a.isFavorite() ? -1 : 1;
            }
            return a.getName().compareToIgnoreCase(b.getName());
        });

        adapter.setCocktails(filtered);

        // Mostra/nascondi messaggio vuoto
        if (filtered.isEmpty() && !lowerQuery.isEmpty()) {
            emptyResultsText.setVisibility(View.VISIBLE);
            resultsRecyclerView.setVisibility(View.GONE);
        } else {
            emptyResultsText.setVisibility(View.GONE);
            resultsRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
