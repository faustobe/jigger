package it.faustobe.jigger.domain.usecases;

import it.faustobe.jigger.data.models.ShiftSummary;
import it.faustobe.jigger.data.repository.RepositoryCallback;
import it.faustobe.jigger.data.repository.ShiftRepository;

public class GetShiftSummaryUseCase {

    private final ShiftRepository shiftRepository;

    public GetShiftSummaryUseCase(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public void execute(String shiftId, UseCaseCallback<ShiftSummary> callback) {
        shiftRepository.getShiftSummary(shiftId, new RepositoryCallback<ShiftSummary>() {
            @Override
            public void onSuccess(ShiftSummary summary) {
                callback.onSuccess(summary);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
