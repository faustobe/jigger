package it.faustobe.jigger.domain.usecases;

public interface UseCaseCallback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}
