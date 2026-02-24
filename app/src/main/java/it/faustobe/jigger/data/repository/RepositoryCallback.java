package it.faustobe.jigger.data.repository;

public interface RepositoryCallback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}
