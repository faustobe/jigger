package it.faustobe.jigger.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
    tableName = "cocktail_ingredients",
    primaryKeys = {"cocktailId", "ingredientId"},
    foreignKeys = {
        @ForeignKey(
            entity = Cocktail.class,
            parentColumns = "id",
            childColumns = "cocktailId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Ingredient.class,
            parentColumns = "id",
            childColumns = "ingredientId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("cocktailId"),
        @Index("ingredientId")
    }
)
public class CocktailIngredient {

    @NonNull
    private String cocktailId;

    @NonNull
    private String ingredientId;

    private float quantity;

    @NonNull
    private String unit;

    public CocktailIngredient() {
        this.cocktailId = "";
        this.ingredientId = "";
        this.quantity = 0;
        this.unit = "ml";
    }

    public CocktailIngredient(@NonNull String cocktailId, @NonNull String ingredientId,
                              float quantity, @NonNull String unit) {
        this.cocktailId = cocktailId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters
    @NonNull
    public String getCocktailId() {
        return cocktailId;
    }

    @NonNull
    public String getIngredientId() {
        return ingredientId;
    }

    public float getQuantity() {
        return quantity;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    // Setters
    public void setCocktailId(@NonNull String cocktailId) {
        this.cocktailId = cocktailId;
    }

    public void setIngredientId(@NonNull String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }
}
