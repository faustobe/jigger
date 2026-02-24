package it.faustobe.jigger.data.models;

import androidx.room.Embedded;

import it.faustobe.jigger.data.local.entities.Cocktail;

public class CocktailWithCount {

    @Embedded
    private Cocktail cocktail;

    private int count;

    public CocktailWithCount() {
    }

    public CocktailWithCount(Cocktail cocktail, int count) {
        this.cocktail = cocktail;
        this.count = count;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
