package it.faustobe.jigger.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.faustobe.jigger.data.local.entities.Bartender;

@Dao
public interface BartenderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Bartender bartender);

    @Update
    void update(Bartender bartender);

    @Query("SELECT * FROM bartenders ORDER BY name ASC")
    List<Bartender> getAllBartenders();

    @Query("SELECT * FROM bartenders ORDER BY name ASC")
    LiveData<List<Bartender>> getAllBartendersLive();

    @Query("SELECT * FROM bartenders WHERE id = :bartenderId")
    Bartender getBartenderById(String bartenderId);

    @Query("SELECT COUNT(*) FROM bartenders")
    int getBartenderCount();

    @Query("DELETE FROM bartenders WHERE id = :bartenderId")
    void deleteById(String bartenderId);
}
