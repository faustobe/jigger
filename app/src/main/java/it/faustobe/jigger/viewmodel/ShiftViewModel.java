package it.faustobe.jigger.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.faustobe.jigger.JiggerApplication;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.models.CocktailWithCount;
import it.faustobe.jigger.data.repository.CocktailRepository;
import it.faustobe.jigger.data.repository.ShiftRepository;
import it.faustobe.jigger.domain.usecases.EndShiftUseCase;
import it.faustobe.jigger.domain.usecases.GetActiveShiftUseCase;
import it.faustobe.jigger.domain.usecases.RecordCocktailUseCase;
import it.faustobe.jigger.domain.usecases.StartShiftUseCase;
import it.faustobe.jigger.domain.usecases.UseCaseCallback;

public class ShiftViewModel extends AndroidViewModel {

    private final MutableLiveData<Shift> currentShift = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();

    private final ShiftRepository shiftRepository;
    private final CocktailRepository cocktailRepository;

    private final StartShiftUseCase startShiftUseCase;
    private final EndShiftUseCase endShiftUseCase;
    private final RecordCocktailUseCase recordCocktailUseCase;
    private final GetActiveShiftUseCase getActiveShiftUseCase;

    private LiveData<List<CocktailWithCount>> cocktailCounts;
    private LiveData<Integer> totalCocktails;
    private final MediatorLiveData<List<CocktailWithCount>> cocktailCountsMediator = new MediatorLiveData<>();
    private final MediatorLiveData<Integer> totalCocktailsMediator = new MediatorLiveData<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public ShiftViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = ((JiggerApplication) application).getDatabase();
        shiftRepository = new ShiftRepository(database);
        cocktailRepository = new CocktailRepository(database);

        startShiftUseCase = new StartShiftUseCase(shiftRepository);
        endShiftUseCase = new EndShiftUseCase(shiftRepository);
        recordCocktailUseCase = new RecordCocktailUseCase(shiftRepository);
        getActiveShiftUseCase = new GetActiveShiftUseCase(shiftRepository);

        loadActiveShift();
    }

    public void loadActiveShift() {
        isLoading.setValue(true);
        getActiveShiftUseCase.execute(new UseCaseCallback<Shift>() {
            @Override
            public void onSuccess(Shift shift) {
                currentShift.postValue(shift);
                isLoading.postValue(false);
                if (shift != null) {
                    updateCocktailObservers(shift.getId());
                }
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Error loading shift: " + e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    private void updateCocktailObservers(String shiftId) {
        // Must run on main thread
        mainHandler.post(() -> {
            // Remove old sources
            if (cocktailCounts != null) {
                cocktailCountsMediator.removeSource(cocktailCounts);
            }
            if (totalCocktails != null) {
                totalCocktailsMediator.removeSource(totalCocktails);
            }

            // Add new sources
            cocktailCounts = shiftRepository.getCocktailCountsForShiftLive(shiftId);
            totalCocktails = shiftRepository.getTotalCocktailsForShiftLive(shiftId);

            cocktailCountsMediator.addSource(cocktailCounts, cocktailCountsMediator::setValue);
            totalCocktailsMediator.addSource(totalCocktails, totalCocktailsMediator::setValue);
        });
    }

    public void startShift() {
        startShift("default");
    }

    public void startShift(String bartenderId) {
        isLoading.setValue(true);
        startShiftUseCase.execute(bartenderId, new UseCaseCallback<Shift>() {
            @Override
            public void onSuccess(Shift shift) {
                currentShift.postValue(shift);
                isLoading.postValue(false);
                successMessage.postValue("Turno iniziato");
                updateCocktailObservers(shift.getId());
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void endShift() {
        Shift shift = currentShift.getValue();
        if (shift == null) {
            errorMessage.setValue("Nessun turno attivo");
            return;
        }

        isLoading.setValue(true);
        endShiftUseCase.execute(shift.getId(), new UseCaseCallback<Shift>() {
            @Override
            public void onSuccess(Shift closedShift) {
                currentShift.postValue(null);
                isLoading.postValue(false);
                successMessage.postValue("Turno terminato");
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Error ending shift: " + e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void recordCocktail(String cocktailId) {
        Shift shift = currentShift.getValue();
        if (shift == null) {
            errorMessage.setValue("Devi prima iniziare un turno");
            return;
        }

        recordCocktailUseCase.execute(shift.getId(), cocktailId, new UseCaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // LiveData will automatically update
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Error recording cocktail: " + e.getMessage());
            }
        });
    }

    public void removeCocktail(String cocktailId) {
        Shift shift = currentShift.getValue();
        if (shift == null) return;

        shiftRepository.removeCocktail(shift.getId(), cocktailId, new it.faustobe.jigger.data.repository.RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // LiveData will automatically update
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Error removing cocktail: " + e.getMessage());
            }
        });
    }

    // Getters for LiveData
    public LiveData<Shift> getCurrentShift() {
        return currentShift;
    }

    public LiveData<List<CocktailWithCount>> getCocktailCounts() {
        return cocktailCountsMediator;
    }

    public LiveData<Integer> getTotalCocktails() {
        return totalCocktailsMediator;
    }

    public LiveData<List<Cocktail>> getAllCocktails() {
        return cocktailRepository.getAllCocktailsLive();
    }

    public LiveData<List<Cocktail>> getFavoriteCocktails() {
        return cocktailRepository.getFavoriteCocktailsLive();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }

    public void clearSuccessMessage() {
        successMessage.setValue(null);
    }

    public String getActiveShiftId() {
        Shift shift = currentShift.getValue();
        return shift != null ? shift.getId() : null;
    }

    public void addCustomCocktail(Cocktail cocktail) {
        cocktailRepository.insertCocktail(cocktail);
        successMessage.postValue("Cocktail aggiunto!");
    }
}
