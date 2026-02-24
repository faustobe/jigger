package it.faustobe.jigger.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.faustobe.jigger.data.local.dao.CocktailDao;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.local.entities.Cocktail;

public class CocktailRepository {

    private final CocktailDao cocktailDao;

    public CocktailRepository(AppDatabase database) {
        this.cocktailDao = database.cocktailDao();
    }

    public LiveData<List<Cocktail>> getAllCocktailsLive() {
        return cocktailDao.getAllCocktailsLive();
    }

    public LiveData<List<Cocktail>> getFavoriteCocktailsLive() {
        return cocktailDao.getFavoriteCocktailsLive();
    }

    public void getAllCocktails(RepositoryCallback<List<Cocktail>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Cocktail> cocktails = cocktailDao.getAllCocktails();
                callback.onSuccess(cocktails);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void getFavoriteCocktails(RepositoryCallback<List<Cocktail>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Cocktail> cocktails = cocktailDao.getFavoriteCocktails();
                callback.onSuccess(cocktails);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void searchCocktails(String query, RepositoryCallback<List<Cocktail>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Cocktail> cocktails = cocktailDao.searchCocktails(query);
                callback.onSuccess(cocktails);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void getCocktailById(String cocktailId, RepositoryCallback<Cocktail> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Cocktail cocktail = cocktailDao.getCocktailById(cocktailId);
                callback.onSuccess(cocktail);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void insertCocktail(Cocktail cocktail) {
        AppDatabase.databaseWriteExecutor.execute(() -> cocktailDao.insert(cocktail));
    }

    public void updateCocktail(Cocktail cocktail) {
        AppDatabase.databaseWriteExecutor.execute(() -> cocktailDao.update(cocktail));
    }

    public void toggleFavorite(String cocktailId, RepositoryCallback<Cocktail> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Cocktail cocktail = cocktailDao.getCocktailById(cocktailId);
                if (cocktail != null) {
                    cocktail.setFavorite(!cocktail.isFavorite());
                    cocktailDao.update(cocktail);
                    callback.onSuccess(cocktail);
                } else {
                    callback.onError(new IllegalStateException("Cocktail not found"));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void deleteCocktail(String cocktailId) {
        AppDatabase.databaseWriteExecutor.execute(() -> cocktailDao.deleteById(cocktailId));
    }
}
