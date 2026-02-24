package it.faustobe.jigger.domain.usecases;

import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.repository.RepositoryCallback;
import it.faustobe.jigger.data.repository.ShiftRepository;

public class StartShiftUseCase {

    private final ShiftRepository shiftRepository;

    public StartShiftUseCase(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public void execute(String bartenderId, UseCaseCallback<Shift> callback) {
        shiftRepository.startShift(bartenderId, new RepositoryCallback<Shift>() {
            @Override
            public void onSuccess(Shift shift) {
                callback.onSuccess(shift);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
