package it.faustobe.jigger.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import it.faustobe.jigger.JiggerApplication;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.models.ShiftSummary;
import it.faustobe.jigger.data.repository.ShiftRepository;
import it.faustobe.jigger.domain.usecases.GetShiftSummaryUseCase;
import it.faustobe.jigger.domain.usecases.UseCaseCallback;

public class SummaryViewModel extends AndroidViewModel {

    private final MutableLiveData<ShiftSummary> shiftSummary = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final GetShiftSummaryUseCase getShiftSummaryUseCase;

    public SummaryViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = ((JiggerApplication) application).getDatabase();
        ShiftRepository shiftRepository = new ShiftRepository(database);

        getShiftSummaryUseCase = new GetShiftSummaryUseCase(shiftRepository);
    }

    public void loadShiftSummary(String shiftId) {
        if (shiftId == null || shiftId.isEmpty()) {
            errorMessage.setValue("Invalid shift ID");
            return;
        }

        isLoading.setValue(true);
        getShiftSummaryUseCase.execute(shiftId, new UseCaseCallback<ShiftSummary>() {
            @Override
            public void onSuccess(ShiftSummary summary) {
                shiftSummary.postValue(summary);
                isLoading.postValue(false);
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Error loading summary: " + e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public LiveData<ShiftSummary> getShiftSummary() {
        return shiftSummary;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }
}
