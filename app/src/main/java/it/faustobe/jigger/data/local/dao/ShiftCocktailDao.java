package it.faustobe.jigger.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.faustobe.jigger.data.local.entities.ShiftCocktail;
import it.faustobe.jigger.data.models.CocktailWithCount;

@Dao
public interface ShiftCocktailDao {

    @Insert
    void insert(ShiftCocktail shiftCocktail);

    @Query("SELECT * FROM shift_cocktails WHERE shiftId = :shiftId ORDER BY timestamp DESC")
    List<ShiftCocktail> getCocktailsByShift(String shiftId);

    @Query("SELECT * FROM shift_cocktails WHERE shiftId = :shiftId ORDER BY timestamp DESC")
    LiveData<List<ShiftCocktail>> getCocktailsByShiftLive(String shiftId);

    @Query("SELECT c.*, COUNT(sc.id) as count FROM cocktails c " +
           "INNER JOIN shift_cocktails sc ON c.id = sc.cocktailId " +
           "WHERE sc.shiftId = :shiftId " +
           "GROUP BY c.id " +
           "ORDER BY count DESC")
    List<CocktailWithCount> getCocktailCountsForShift(String shiftId);

    @Query("SELECT c.*, COUNT(sc.id) as count FROM cocktails c " +
           "INNER JOIN shift_cocktails sc ON c.id = sc.cocktailId " +
           "WHERE sc.shiftId = :shiftId " +
           "GROUP BY c.id " +
           "ORDER BY count DESC")
    LiveData<List<CocktailWithCount>> getCocktailCountsForShiftLive(String shiftId);

    @Query("SELECT COUNT(*) FROM shift_cocktails WHERE shiftId = :shiftId")
    int getTotalCocktailsForShift(String shiftId);

    @Query("SELECT COUNT(*) FROM shift_cocktails WHERE shiftId = :shiftId")
    LiveData<Integer> getTotalCocktailsForShiftLive(String shiftId);

    @Query("DELETE FROM shift_cocktails WHERE id = :shiftCocktailId")
    void deleteById(String shiftCocktailId);

    @Query("DELETE FROM shift_cocktails WHERE shiftId = :shiftId AND cocktailId = :cocktailId " +
           "AND id = (SELECT id FROM shift_cocktails WHERE shiftId = :shiftId AND cocktailId = :cocktailId " +
           "ORDER BY timestamp DESC LIMIT 1)")
    void deleteLastCocktailEntry(String shiftId, String cocktailId);
}
