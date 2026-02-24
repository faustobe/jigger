package it.faustobe.jigger.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.faustobe.jigger.data.local.entities.CocktailIngredient;
import it.faustobe.jigger.data.local.entities.Ingredient;

@Dao
public interface CocktailIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CocktailIngredient cocktailIngredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CocktailIngredient> cocktailIngredients);

    @Query("SELECT i.* FROM ingredients i " +
           "INNER JOIN cocktail_ingredients ci ON i.id = ci.ingredientId " +
           "WHERE ci.cocktailId = :cocktailId")
    List<Ingredient> getIngredientsForCocktail(String cocktailId);

    @Query("SELECT * FROM cocktail_ingredients WHERE cocktailId = :cocktailId")
    List<CocktailIngredient> getCocktailIngredients(String cocktailId);

    @Query("DELETE FROM cocktail_ingredients WHERE cocktailId = :cocktailId")
    void deleteByCocktailId(String cocktailId);
}
