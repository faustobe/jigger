package it.faustobe.jigger.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Cocktail;

public class CocktailSimpleAdapter extends RecyclerView.Adapter<CocktailSimpleAdapter.ViewHolder> {

    private List<Cocktail> cocktails = new ArrayList<>();
    private OnCocktailClickListener listener;

    public interface OnCocktailClickListener {
        void onCocktailClick(Cocktail cocktail);
    }

    public void setListener(OnCocktailClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cocktail_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cocktail item = cocktails.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    public void setCocktails(List<Cocktail> newCocktails) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return cocktails.size();
            }

            @Override
            public int getNewListSize() {
                return newCocktails.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return cocktails.get(oldItemPosition).getId()
                        .equals(newCocktails.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return cocktails.get(oldItemPosition).getName()
                        .equals(newCocktails.get(newItemPosition).getName());
            }
        });

        cocktails = new ArrayList<>(newCocktails);
        diffResult.dispatchUpdatesTo(this);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final MaterialCardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cocktailName);
            cardView = itemView.findViewById(R.id.cocktailCard);
        }

        void bind(Cocktail item, OnCocktailClickListener listener) {
            nameText.setText(item.getName());

            cardView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCocktailClick(item);
                }
            });
        }
    }
}
