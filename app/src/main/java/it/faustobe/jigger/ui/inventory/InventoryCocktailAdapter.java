package it.faustobe.jigger.ui.inventory;

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

public class InventoryCocktailAdapter extends RecyclerView.Adapter<InventoryCocktailAdapter.ViewHolder> {

    private List<Cocktail> cocktails = new ArrayList<>();
    private OnCocktailInteractionListener listener;

    public interface OnCocktailInteractionListener {
        void onFavoriteClick(Cocktail cocktail);
        void onCocktailLongClick(Cocktail cocktail);
    }

    public void setListener(OnCocktailInteractionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory_cocktail, parent, false);
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
                       oldItem.isFavorite() == newItem.isFavorite() &&
                       oldItem.getCategory().equals(newItem.getCategory()) &&
                       oldItem.getGlassType().equals(newItem.getGlassType());
            }
        });

        cocktails = new ArrayList<>(newCocktails);
        diffResult.dispatchUpdatesTo(this);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView detailsText;
        private final ImageView favoriteIcon;
        private final ImageView customBadge;

        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cocktailName);
            detailsText = itemView.findViewById(R.id.cocktailDetails);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
            customBadge = itemView.findViewById(R.id.customBadge);
        }

        void bind(Cocktail cocktail, OnCocktailInteractionListener listener) {
            nameText.setText(cocktail.getName());
            detailsText.setText(cocktail.getCategory() + " • " + cocktail.getGlassType());

            // Show custom badge if it's a custom cocktail
            customBadge.setVisibility(cocktail.isCustom() ? View.VISIBLE : View.GONE);

            // Update favorite icon
            if (cocktail.isFavorite()) {
                favoriteIcon.setImageResource(R.drawable.ic_favorite);
            } else {
                favoriteIcon.setImageResource(R.drawable.ic_favorite_border);
            }

            // Favorite click
            favoriteIcon.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFavoriteClick(cocktail);
                }
            });

            // Long click for edit/delete (custom cocktails only)
            itemView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onCocktailLongClick(cocktail);
                    return true;
                }
                return false;
            });
        }
    }
}
