package it.faustobe.jigger.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.faustobe.jigger.data.local.entities.Cocktail;

@Dao
public interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cocktail cocktail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Cocktail> cocktails);

    @Update
    void update(Cocktail cocktail);

    @Query("SELECT * FROM cocktails ORDER BY name ASC")
    List<Cocktail> getAllCocktails();

    @Query("SELECT * FROM cocktails ORDER BY name ASC")
    LiveData<List<Cocktail>> getAllCocktailsLive();

    @Query("SELECT * FROM cocktails WHERE isFavorite = 1 ORDER BY name ASC")
    List<Cocktail> getFavoriteCocktails();

    @Query("SELECT * FROM cocktails WHERE isFavorite = 1 ORDER BY name ASC")
    LiveData<List<Cocktail>> getFavoriteCocktailsLive();

    @Query("SELECT * FROM cocktails WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    List<Cocktail> searchCocktails(String query);

    @Query("SELECT * FROM cocktails WHERE id = :cocktailId")
    Cocktail getCocktailById(String cocktailId);

    @Query("SELECT * FROM cocktails WHERE category = :category ORDER BY name ASC")
    List<Cocktail> getCocktailsByCategory(String category);

    @Query("SELECT COUNT(*) FROM cocktails")
    int getCocktailCount();

    @Query("DELETE FROM cocktails WHERE id = :cocktailId")
    void deleteById(String cocktailId);
}
