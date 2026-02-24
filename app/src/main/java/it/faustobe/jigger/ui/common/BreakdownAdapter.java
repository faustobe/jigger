package it.faustobe.jigger.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.models.CocktailWithCount;

public class BreakdownAdapter extends RecyclerView.Adapter<BreakdownAdapter.ViewHolder> {

    private List<CocktailWithCount> cocktails = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cocktail_breakdown, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CocktailWithCount item = cocktails.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    public void setCocktails(List<CocktailWithCount> newCocktails) {
        cocktails = new ArrayList<>(newCocktails);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView countText;

        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cocktailName);
            countText = itemView.findViewById(R.id.cocktailCount);
        }

        void bind(CocktailWithCount item) {
            nameText.setText(item.getCocktail().getName());
            countText.setText(String.valueOf(item.getCount()));
        }
    }
}
