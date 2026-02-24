package it.faustobe.jigger.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.faustobe.jigger.data.local.entities.Ingredient;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ingredient> ingredients);

    @Update
    void update(Ingredient ingredient);

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    LiveData<List<Ingredient>> getAllIngredientsLive();

    @Query("SELECT * FROM ingredients WHERE currentStock <= minThreshold ORDER BY name ASC")
    List<Ingredient> getLowStockIngredients();

    @Query("SELECT * FROM ingredients WHERE currentStock <= minThreshold ORDER BY name ASC")
    LiveData<List<Ingredient>> getLowStockIngredientsLive();

    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    Ingredient getIngredientById(String ingredientId);

    @Query("SELECT * FROM ingredients WHERE type = :type ORDER BY name ASC")
    List<Ingredient> getIngredientsByType(String type);

    @Query("SELECT COUNT(*) FROM ingredients")
    int getIngredientCount();

    @Query("DELETE FROM ingredients WHERE id = :ingredientId")
    void deleteById(String ingredientId);
}
