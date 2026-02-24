package it.faustobe.jigger.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.faustobe.jigger.data.local.dao.ShiftCocktailDao;
import it.faustobe.jigger.data.local.dao.ShiftDao;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.local.entities.ShiftCocktail;
import it.faustobe.jigger.data.models.CocktailWithCount;
import it.faustobe.jigger.data.models.ShiftSummary;

public class ShiftRepository {

    private final ShiftDao shiftDao;
    private final ShiftCocktailDao shiftCocktailDao;

    public ShiftRepository(AppDatabase database) {
        this.shiftDao = database.shiftDao();
        this.shiftCocktailDao = database.shiftCocktailDao();
    }

    // Shift operations
    public void insertShift(Shift shift) {
        AppDatabase.databaseWriteExecutor.execute(() -> shiftDao.insert(shift));
    }

    public void updateShift(Shift shift) {
        AppDatabase.databaseWriteExecutor.execute(() -> shiftDao.update(shift));
    }

    public void getActiveShift(RepositoryCallback<Shift> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Shift shift = shiftDao.getActiveShift();
                callback.onSuccess(shift);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public LiveData<Shift> getActiveShiftLive() {
        return shiftDao.getActiveShiftLive();
    }

    public void getShiftById(String shiftId, RepositoryCallback<Shift> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Shift shift = shiftDao.getShiftById(shiftId);
                callback.onSuccess(shift);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void getRecentShifts(int limit, RepositoryCallback<List<Shift>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Shift> shifts = shiftDao.getRecentShifts(limit);
                callback.onSuccess(shifts);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public LiveData<List<Shift>> getAllShiftsLive() {
        return shiftDao.getAllShiftsLive();
    }

    // ShiftCocktail operations
    public void recordCocktail(ShiftCocktail shiftCocktail) {
        AppDatabase.databaseWriteExecutor.execute(() -> shiftCocktailDao.insert(shiftCocktail));
    }

    public void recordCocktail(String shiftId, String cocktailId, RepositoryCallback<Void> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                ShiftCocktail shiftCocktail = new ShiftCocktail(shiftId, cocktailId);
                shiftCocktailDao.insert(shiftCocktail);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public LiveData<List<CocktailWithCount>> getCocktailCountsForShiftLive(String shiftId) {
        return shiftCocktailDao.getCocktailCountsForShiftLive(shiftId);
    }

    public LiveData<Integer> getTotalCocktailsForShiftLive(String shiftId) {
        return shiftCocktailDao.getTotalCocktailsForShiftLive(shiftId);
    }

    public void removeCocktail(String shiftId, String cocktailId, RepositoryCallback<Void> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                shiftCocktailDao.deleteLastCocktailEntry(shiftId, cocktailId);
                callback.onSuccess(null);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    // Summary
    public void getShiftSummary(String shiftId, RepositoryCallback<ShiftSummary> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Shift shift = shiftDao.getShiftById(shiftId);
                if (shift == null) {
                    callback.onError(new IllegalStateException("Shift not found"));
                    return;
                }

                int totalCocktails = shiftCocktailDao.getTotalCocktailsForShift(shiftId);
                List<CocktailWithCount> breakdown = shiftCocktailDao.getCocktailCountsForShift(shiftId);

                long endTime = shift.getEndTime() != null ? shift.getEndTime() : System.currentTimeMillis();
                long duration = endTime - shift.getStartTime();

                ShiftSummary summary = new ShiftSummary(shift, totalCocktails, duration, breakdown);
                callback.onSuccess(summary);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    // Start shift
    public void startShift(String bartenderId, RepositoryCallback<Shift> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                // Check for existing active shift
                Shift existingShift = shiftDao.getActiveShift();
                if (existingShift != null) {
                    callback.onError(new IllegalStateException("An active shift already exists"));
                    return;
                }

                // Create new shift
                Shift newShift = new Shift();
                newShift.setBartenderId(bartenderId);
                shiftDao.insert(newShift);

                callback.onSuccess(newShift);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    // End shift
    public void endShift(String shiftId, RepositoryCallback<Shift> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Shift shift = shiftDao.getShiftById(shiftId);
                if (shift == null) {
                    callback.onError(new IllegalStateException("Shift not found"));
                    return;
                }

                shift.close();
                shiftDao.update(shift);

                callback.onSuccess(shift);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }
}
