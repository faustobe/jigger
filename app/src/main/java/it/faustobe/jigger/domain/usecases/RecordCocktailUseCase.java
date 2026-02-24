package it.faustobe.jigger.domain.usecases;

import it.faustobe.jigger.data.repository.RepositoryCallback;
import it.faustobe.jigger.data.repository.ShiftRepository;

public class RecordCocktailUseCase {

    private final ShiftRepository shiftRepository;

    public RecordCocktailUseCase(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public void execute(String shiftId, String cocktailId, UseCaseCallback<Void> callback) {
        shiftRepository.recordCocktail(shiftId, cocktailId, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                callback.onSuccess(null);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
