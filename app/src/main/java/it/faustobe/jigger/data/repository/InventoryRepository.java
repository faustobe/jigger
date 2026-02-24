package it.faustobe.jigger.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.faustobe.jigger.data.local.dao.IngredientDao;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.local.entities.Ingredient;

public class InventoryRepository {

    private final IngredientDao ingredientDao;

    public InventoryRepository(AppDatabase database) {
        this.ingredientDao = database.ingredientDao();
    }

    public LiveData<List<Ingredient>> getAllIngredientsLive() {
        return ingredientDao.getAllIngredientsLive();
    }

    public LiveData<List<Ingredient>> getLowStockIngredientsLive() {
        return ingredientDao.getLowStockIngredientsLive();
    }

    public void getAllIngredients(RepositoryCallback<List<Ingredient>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Ingredient> ingredients = ingredientDao.getAllIngredients();
                callback.onSuccess(ingredients);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void getLowStockIngredients(RepositoryCallback<List<Ingredient>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Ingredient> ingredients = ingredientDao.getLowStockIngredients();
                callback.onSuccess(ingredients);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void getIngredientById(String ingredientId, RepositoryCallback<Ingredient> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Ingredient ingredient = ingredientDao.getIngredientById(ingredientId);
                callback.onSuccess(ingredient);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void insertIngredient(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> ingredientDao.insert(ingredient));
    }

    public void updateIngredient(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> ingredientDao.update(ingredient));
    }

    public void updateStock(String ingredientId, float newStock, RepositoryCallback<Ingredient> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Ingredient ingredient = ingredientDao.getIngredientById(ingredientId);
                if (ingredient != null) {
                    ingredient.setCurrentStock(newStock);
                    ingredientDao.update(ingredient);
                    callback.onSuccess(ingredient);
                } else {
                    callback.onError(new IllegalStateException("Ingredient not found"));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void decrementStock(String ingredientId, float amount, RepositoryCallback<Ingredient> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Ingredient ingredient = ingredientDao.getIngredientById(ingredientId);
                if (ingredient != null) {
                    float newStock = Math.max(0, ingredient.getCurrentStock() - amount);
                    ingredient.setCurrentStock(newStock);
                    ingredientDao.update(ingredient);
                    callback.onSuccess(ingredient);
                } else {
                    callback.onError(new IllegalStateException("Ingredient not found"));
                }
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void deleteIngredient(String ingredientId) {
        AppDatabase.databaseWriteExecutor.execute(() -> ingredientDao.deleteById(ingredientId));
    }
}
