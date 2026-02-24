package it.faustobe.jigger.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import it.faustobe.jigger.JiggerApplication;
import it.faustobe.jigger.data.local.database.AppDatabase;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.data.repository.CocktailRepository;
import it.faustobe.jigger.data.repository.RepositoryCallback;

public class InventoryViewModel extends AndroidViewModel {

    public enum FilterType {
        ALL,
        FAVORITES,
        IBA,
        CLASSIC,
        CUSTOM
    }

    private final CocktailRepository cocktailRepository;

    private final LiveData<List<Cocktail>> allCocktails;
    private final MediatorLiveData<List<Cocktail>> filteredCocktails = new MediatorLiveData<>();

    private final MutableLiveData<FilterType> currentFilter = new MutableLiveData<>(FilterType.ALL);
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public InventoryViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = ((JiggerApplication) application).getDatabase();
        cocktailRepository = new CocktailRepository(database);

        allCocktails = cocktailRepository.getAllCocktailsLive();

        // React to changes in cocktails, filter, or search query
        filteredCocktails.addSource(allCocktails, cocktails -> applyFilters());
        filteredCocktails.addSource(currentFilter, filter -> applyFilters());
        filteredCocktails.addSource(searchQuery, query -> applyFilters());
    }

    private void applyFilters() {
        List<Cocktail> cocktails = allCocktails.getValue();
        if (cocktails == null) {
            filteredCocktails.setValue(new ArrayList<>());
            return;
        }

        FilterType filter = currentFilter.getValue();
        String query = searchQuery.getValue();

        if (filter == null) filter = FilterType.ALL;
        if (query == null) query = "";

        String lowerQuery = query.toLowerCase().trim();
        List<Cocktail> result = new ArrayList<>();

        for (Cocktail cocktail : cocktails) {
            // Apply category filter
            boolean matchesFilter;
            switch (filter) {
                case FAVORITES:
                    matchesFilter = cocktail.isFavorite();
                    break;
                case IBA:
                    matchesFilter = cocktail.getCategory().startsWith("IBA");
                    break;
                case CLASSIC:
                    matchesFilter = cocktail.getCategory().equals("Classic") ||
                                   cocktail.getCategory().equals("Tiki & Tropical");
                    break;
                case CUSTOM:
                    matchesFilter = cocktail.isCustom();
                    break;
                case ALL:
                default:
                    matchesFilter = true;
                    break;
            }

            // Apply search query
            boolean matchesQuery = lowerQuery.isEmpty() ||
                    cocktail.getName().toLowerCase().contains(lowerQuery) ||
                    cocktail.getCategory().toLowerCase().contains(lowerQuery) ||
                    cocktail.getGlassType().toLowerCase().contains(lowerQuery);

            if (matchesFilter && matchesQuery) {
                result.add(cocktail);
            }
        }

        // Sort: favorites first, then alphabetically
        result.sort((a, b) -> {
            if (a.isFavorite() != b.isFavorite()) {
                return a.isFavorite() ? -1 : 1;
            }
            return a.getName().compareToIgnoreCase(b.getName());
        });

        filteredCocktails.setValue(result);
    }

    public void setFilter(FilterType filter) {
        currentFilter.setValue(filter);
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public void toggleFavorite(Cocktail cocktail) {
        cocktailRepository.toggleFavorite(cocktail.getId(), new RepositoryCallback<Cocktail>() {
            @Override
            public void onSuccess(Cocktail result) {
                if (result.isFavorite()) {
                    successMessage.postValue("Aggiunto ai preferiti");
                } else {
                    successMessage.postValue("Rimosso dai preferiti");
                }
            }

            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Errore: " + e.getMessage());
            }
        });
    }

    public void addCocktail(Cocktail cocktail) {
        cocktailRepository.insertCocktail(cocktail);
        successMessage.postValue("Cocktail aggiunto!");
    }

    public void updateCocktail(Cocktail cocktail) {
        cocktailRepository.updateCocktail(cocktail);
        successMessage.postValue("Cocktail aggiornato");
    }

    public void deleteCocktail(Cocktail cocktail) {
        cocktailRepository.deleteCocktail(cocktail.getId());
        successMessage.postValue("Cocktail eliminato");
    }

    // Getters
    public LiveData<List<Cocktail>> getFilteredCocktails() {
        return filteredCocktails;
    }

    public LiveData<FilterType> getCurrentFilter() {
        return currentFilter;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void clearSuccessMessage() {
        successMessage.setValue(null);
    }

    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }
}
