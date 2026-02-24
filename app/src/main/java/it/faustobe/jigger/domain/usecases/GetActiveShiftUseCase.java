package it.faustobe.jigger.domain.usecases;

import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.repository.RepositoryCallback;
import it.faustobe.jigger.data.repository.ShiftRepository;

public class GetActiveShiftUseCase {

    private final ShiftRepository shiftRepository;

    public GetActiveShiftUseCase(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public void execute(UseCaseCallback<Shift> callback) {
        shiftRepository.getActiveShift(new RepositoryCallback<Shift>() {
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
