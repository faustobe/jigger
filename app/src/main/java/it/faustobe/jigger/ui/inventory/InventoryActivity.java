package it.faustobe.jigger.ui.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.databinding.ActivityInventoryBinding;
import it.faustobe.jigger.ui.history.HistoryActivity;
import it.faustobe.jigger.ui.shift.AddCustomCocktailDialog;
import it.faustobe.jigger.ui.shift.ShiftActivity;
import it.faustobe.jigger.viewmodel.InventoryViewModel;

public class InventoryActivity extends AppCompatActivity {

    private ActivityInventoryBinding binding;
    private InventoryViewModel viewModel;
    private InventoryCocktailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        setupAdapter();
        setupSearch();
        setupFilters();
        setupFab();
        setupBottomNavigation();
        observeViewModel();
    }

    private void setupAdapter() {
        adapter = new InventoryCocktailAdapter();
        adapter.setListener(new InventoryCocktailAdapter.OnCocktailInteractionListener() {
            @Override
            public void onFavoriteClick(Cocktail cocktail) {
                viewModel.toggleFavorite(cocktail);
            }

            @Override
            public void onCocktailLongClick(Cocktail cocktail) {
                if (cocktail.isCustom()) {
                    showCocktailOptionsDialog(cocktail);
                }
            }
        });
        binding.cocktailsRecyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilters() {
        binding.categoryChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;

            int checkedId = checkedIds.get(0);
            InventoryViewModel.FilterType filter;

            if (checkedId == R.id.chipAll) {
                filter = InventoryViewModel.FilterType.ALL;
            } else if (checkedId == R.id.chipFavorites) {
                filter = InventoryViewModel.FilterType.FAVORITES;
            } else if (checkedId == R.id.chipIba) {
                filter = InventoryViewModel.FilterType.IBA;
            } else if (checkedId == R.id.chipClassic) {
                filter = InventoryViewModel.FilterType.CLASSIC;
            } else if (checkedId == R.id.chipCustom) {
                filter = InventoryViewModel.FilterType.CUSTOM;
            } else {
                filter = InventoryViewModel.FilterType.ALL;
            }

            viewModel.setFilter(filter);
        });
    }

    private void setupFab() {
        binding.fabAdd.setOnClickListener(v -> showAddCocktailDialog());
    }

    private void showAddCocktailDialog() {
        AddCustomCocktailDialog dialog = AddCustomCocktailDialog.newInstance();
        dialog.setOnCocktailCreatedListener(cocktail -> viewModel.addCocktail(cocktail));
        dialog.show(getSupportFragmentManager(), "add_custom");
    }

    private void showCocktailOptionsDialog(Cocktail cocktail) {
        String[] options = {
                getString(R.string.edit_cocktail),
                getString(R.string.delete_cocktail)
        };

        new AlertDialog.Builder(this)
                .setTitle(cocktail.getName())
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showEditCocktailDialog(cocktail);
                    } else if (which == 1) {
                        showDeleteConfirmationDialog(cocktail);
                    }
                })
                .show();
    }

    private void showEditCocktailDialog(Cocktail cocktail) {
        EditCocktailDialog dialog = EditCocktailDialog.newInstance(cocktail);
        dialog.setOnCocktailUpdatedListener(updatedCocktail -> viewModel.updateCocktail(updatedCocktail));
        dialog.show(getSupportFragmentManager(), "edit_cocktail");
    }

    private void showDeleteConfirmationDialog(Cocktail cocktail) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_cocktail)
                .setMessage(getString(R.string.delete_cocktail_message, cocktail.getName()))
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    viewModel.deleteCocktail(cocktail);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setSelectedItemId(R.id.nav_inventory);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_shift) {
                startActivity(new Intent(this, ShiftActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_inventory) {
                return true;
            } else if (itemId == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void observeViewModel() {
        viewModel.getFilteredCocktails().observe(this, cocktails -> {
            if (cocktails != null) {
                adapter.setCocktails(cocktails);
                binding.emptyText.setVisibility(cocktails.isEmpty() ? View.VISIBLE : View.GONE);
                binding.cocktailsRecyclerView.setVisibility(cocktails.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getSuccessMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                viewModel.clearSuccessMessage();
            }
        });

        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
                viewModel.clearErrorMessage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bottomNav.setSelectedItemId(R.id.nav_inventory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
