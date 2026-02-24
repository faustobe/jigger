package it.faustobe.jigger.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import it.faustobe.jigger.JiggerApplication;
import it.faustobe.jigger.data.local.dao.ShiftCocktailDao;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.models.ShiftWithCocktailCount;
import it.faustobe.jigger.data.repository.ShiftRepository;

public class HistoryViewModel extends AndroidViewModel {

    private final ShiftRepository shiftRepository;
    private final ShiftCocktailDao shiftCocktailDao;

    private final LiveData<List<Shift>> allShifts;
    private final MediatorLiveData<List<ShiftWithCocktailCount>> shiftsWithCounts = new MediatorLiveData<>();

    private final MutableLiveData<Integer> totalShifts = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> totalCocktails = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> avgPerShift = new MutableLiveData<>(0);

    public HistoryViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = ((JiggerApplication) application).getDatabase();
        shiftRepository = new ShiftRepository(database);
        shiftCocktailDao = database.shiftCocktailDao();

        allShifts = shiftRepository.getAllShiftsLive();

        shiftsWithCounts.addSource(allShifts, shifts -> {
            if (shifts != null) {
                loadCocktailCounts(shifts);
            }
        });
    }

    private void loadCocktailCounts(List<Shift> shifts) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<ShiftWithCocktailCount> result = new ArrayList<>();
            int totalCocktailsCount = 0;
            int completedShifts = 0;

            for (Shift shift : shifts) {
                int count = shiftCocktailDao.getTotalCocktailsForShift(shift.getId());
                result.add(new ShiftWithCocktailCount(shift, count));
                totalCocktailsCount += count;

                if (!shift.isActive()) {
                    completedShifts++;
                }
            }

            final int finalTotal = totalCocktailsCount;
            final int finalCompleted = completedShifts;
            final int avg = completedShifts > 0 ? totalCocktailsCount / completedShifts : 0;

            shiftsWithCounts.postValue(result);
            totalShifts.postValue(shifts.size());
            totalCocktails.postValue(finalTotal);
            avgPerShift.postValue(avg);
        });
    }

    public LiveData<List<ShiftWithCocktailCount>> getShiftsWithCounts() {
        return shiftsWithCounts;
    }

    public LiveData<Integer> getTotalShifts() {
        return totalShifts;
    }

    public LiveData<Integer> getTotalCocktails() {
        return totalCocktails;
    }

    public LiveData<Integer> getAvgPerShift() {
        return avgPerShift;
    }
}
