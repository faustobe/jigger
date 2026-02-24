package it.faustobe.jigger.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.faustobe.jigger.data.local.entities.Shift;

@Dao
public interface ShiftDao {

    @Insert
    void insert(Shift shift);

    @Update
    void update(Shift shift);

    @Query("SELECT * FROM shifts WHERE status = 'ACTIVE' LIMIT 1")
    Shift getActiveShift();

    @Query("SELECT * FROM shifts WHERE status = 'ACTIVE' LIMIT 1")
    LiveData<Shift> getActiveShiftLive();

    @Query("SELECT * FROM shifts WHERE id = :shiftId")
    Shift getShiftById(String shiftId);

    @Query("SELECT * FROM shifts ORDER BY startTime DESC LIMIT :limit")
    List<Shift> getRecentShifts(int limit);

    @Query("SELECT * FROM shifts ORDER BY startTime DESC")
    LiveData<List<Shift>> getAllShiftsLive();

    @Query("SELECT * FROM shifts WHERE startTime >= :startDate AND startTime <= :endDate ORDER BY startTime DESC")
    List<Shift> getShiftsByDateRange(long startDate, long endDate);

    @Query("SELECT COUNT(*) FROM shifts")
    int getShiftCount();

    @Query("DELETE FROM shifts WHERE id = :shiftId")
    void deleteById(String shiftId);
}
