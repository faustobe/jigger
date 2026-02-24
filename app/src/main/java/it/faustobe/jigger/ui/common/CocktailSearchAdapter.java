package it.faustobe.jigger.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Cocktail;

public class CocktailSearchAdapter extends RecyclerView.Adapter<CocktailSearchAdapter.ViewHolder> {

    private List<Cocktail> cocktails = new ArrayList<>();
    private OnCocktailSelectedListener listener;

    public interface OnCocktailSelectedListener {
        void onCocktailSelected(Cocktail cocktail);
    }

    public void setListener(OnCocktailSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cocktail_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cocktail cocktail = cocktails.get(position);
        holder.bind(cocktail, listener);
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
                Cocktail oldItem = cocktails.get(oldItemPosition);
                Cocktail newItem = newCocktails.get(newItemPosition);
                return oldItem.getName().equals(newItem.getName()) &&
                       oldItem.isFavorite() == newItem.isFavorite();
            }
        });

        cocktails = new ArrayList<>(newCocktails);
        diffResult.dispatchUpdatesTo(this);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView categoryText;
        private final ImageView favoriteIcon;

        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cocktailName);
            categoryText = itemView.findViewById(R.id.cocktailCategory);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }

        void bind(Cocktail cocktail, OnCocktailSelectedListener listener) {
            nameText.setText(cocktail.getName());
            categoryText.setText(cocktail.getCategory() + " • " + cocktail.getGlassType());
            favoriteIcon.setVisibility(cocktail.isFavorite() ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCocktailSelected(cocktail);
                }
            });
        }
    }
}
