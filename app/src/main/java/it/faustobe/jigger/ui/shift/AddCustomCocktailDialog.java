package it.faustobe.jigger.ui.shift;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Cocktail;

public class AddCustomCocktailDialog extends DialogFragment {

    private TextInputLayout nameInputLayout;
    private TextInputEditText nameEditText;
    private AutoCompleteTextView categoryDropdown;
    private AutoCompleteTextView glassDropdown;

    private OnCocktailCreatedListener listener;

    public interface OnCocktailCreatedListener {
        void onCocktailCreated(Cocktail cocktail);
    }

    public static AddCustomCocktailDialog newInstance() {
        return new AddCustomCocktailDialog();
    }

    public void setOnCocktailCreatedListener(OnCocktailCreatedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_custom_cocktail, null);

        nameInputLayout = view.findViewById(R.id.nameInputLayout);
        nameEditText = view.findViewById(R.id.nameEditText);
        categoryDropdown = view.findViewById(R.id.categoryDropdown);
        glassDropdown = view.findViewById(R.id.glassDropdown);
        MaterialButton cancelButton = view.findViewById(R.id.cancelButton);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);

        setupDropdowns();

        cancelButton.setOnClickListener(v -> dismiss());
        saveButton.setOnClickListener(v -> validateAndSave());

        return new MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .create();
    }

    private void setupDropdowns() {
        String[] categories = {
                getString(R.string.category_custom),
                getString(R.string.category_signature),
                getString(R.string.category_classic),
                getString(R.string.category_tiki),
                getString(R.string.category_aperitivo)
        };

        String[] glassTypes = {
                getString(R.string.glass_rocks),
                getString(R.string.glass_highball),
                getString(R.string.glass_coupe),
                getString(R.string.glass_martini),
                getString(R.string.glass_flute),
                getString(R.string.glass_collins),
                getString(R.string.glass_hurricane),
                getString(R.string.glass_tiki),
                getString(R.string.glass_copper_mug),
                getString(R.string.glass_nick_nora),
                getString(R.string.glass_wine)
        };

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                categories
        );
        categoryDropdown.setAdapter(categoryAdapter);

        ArrayAdapter<String> glassAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                glassTypes
        );
        glassDropdown.setAdapter(glassAdapter);
    }

    private void validateAndSave() {
        String name = nameEditText.getText() != null ?
                nameEditText.getText().toString().trim() : "";

        if (name.isEmpty()) {
            nameInputLayout.setError(getString(R.string.error_name_required));
            return;
        }

        nameInputLayout.setError(null);

        String category = categoryDropdown.getText().toString();
        String glassType = glassDropdown.getText().toString();

        Cocktail cocktail = new Cocktail();
        cocktail.setName(name);
        cocktail.setCategory(category);
        cocktail.setGlassType(glassType);
        cocktail.setCustom(true);
        cocktail.setFavorite(false);

        if (listener != null) {
            listener.onCocktailCreated(cocktail);
        }

        dismiss();
    }
}
