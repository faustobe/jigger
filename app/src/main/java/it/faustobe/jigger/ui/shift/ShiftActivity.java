package it.faustobe.jigger.ui.shift;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.databinding.ActivityShiftBinding;
import it.faustobe.jigger.ui.common.CocktailCountAdapter;
import it.faustobe.jigger.ui.common.CocktailSimpleAdapter;
import it.faustobe.jigger.ui.history.HistoryActivity;
import it.faustobe.jigger.ui.inventory.InventoryActivity;
import it.faustobe.jigger.viewmodel.ShiftViewModel;

public class ShiftActivity extends AppCompatActivity {

    private ActivityShiftBinding binding;
    private ShiftViewModel viewModel;

    private CocktailCountAdapter cocktailCountAdapter;
    private CocktailSimpleAdapter allCocktailsAdapter;

    private Handler durationHandler;
    private Runnable durationRunnable;
    private List<Cocktail> allCocktailsList = new ArrayList<>();
    private MenuItem searchMenuItem;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        setupAdapters();
        setupClickListeners();
        setupBottomNavigation();
        observeViewModel();
    }

    private void setupAdapters() {
        cocktailCountAdapter = new CocktailCountAdapter();
        cocktailCountAdapter.setListener(new CocktailCountAdapter.OnCocktailClickListener() {
            @Override
            public void onCocktailClick(Cocktail cocktail) {
                recordCocktail(cocktail);
            }

            @Override
            public void onCocktailLongClick(Cocktail cocktail) {
                showRemoveCocktailDialog(cocktail);
            }
        });
        binding.cocktailGrid.setAdapter(cocktailCountAdapter);

        allCocktailsAdapter = new CocktailSimpleAdapter();
        allCocktailsAdapter.setListener(this::recordCocktail);
        binding.allCocktailsGrid.setAdapter(allCocktailsAdapter);
    }

    private void setupClickListeners() {
        binding.btnStartShift.setOnClickListener(v -> viewModel.startShift());

        binding.btnEndShift.setOnClickListener(v -> showEndShiftDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shift, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        updateSearchMenuVisibility();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            showSearchDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSearchMenuVisibility() {
        if (searchMenuItem != null) {
            Shift shift = viewModel.getCurrentShift().getValue();
            searchMenuItem.setVisible(shift != null && shift.isActive());
        }
    }

    private void showSearchDialog() {
        SearchCocktailBottomSheet bottomSheet = SearchCocktailBottomSheet.newInstance();
        bottomSheet.setCocktails(allCocktailsList);
        bottomSheet.setOnCocktailSelectedListener(this::recordCocktail);
        bottomSheet.setOnCustomCocktailRequestListener(this::showAddCustomCocktailDialog);
        bottomSheet.show(getSupportFragmentManager(), "search");
    }

    private void showAddCustomCocktailDialog() {
        AddCustomCocktailDialog dialog = AddCustomCocktailDialog.newInstance();
        dialog.setOnCocktailCreatedListener(cocktail -> {
            viewModel.addCustomCocktail(cocktail);
            // Also record it immediately for the current shift
            recordCocktail(cocktail);
        });
        dialog.show(getSupportFragmentManager(), "add_custom");
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setSelectedItemId(R.id.nav_shift);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_shift) {
                return true;
            } else if (itemId == R.id.nav_inventory) {
                startActivity(new Intent(this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            }
            return false;
        });
    }

    private void observeViewModel() {
        viewModel.getCurrentShift().observe(this, this::updateShiftUI);

        viewModel.getCocktailCounts().observe(this, cocktails -> {
            if (cocktails != null && !cocktails.isEmpty()) {
                cocktailCountAdapter.setCocktails(cocktails);
                binding.cocktailGrid.setVisibility(View.VISIBLE);
                binding.quickAccessTitle.setVisibility(View.VISIBLE);
            } else {
                binding.cocktailGrid.setVisibility(View.GONE);
                binding.quickAccessTitle.setVisibility(View.GONE);
            }
        });

        viewModel.getAllCocktails().observe(this, cocktails -> {
            if (cocktails != null) {
                allCocktailsAdapter.setCocktails(cocktails);
                allCocktailsList = cocktails;
            }
        });

        viewModel.getTotalCocktails().observe(this, total -> {
            if (total != null) {
                binding.totalCocktails.setText(getString(R.string.total_cocktails_format, total));
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
                viewModel.clearErrorMessage();
            }
        });

        viewModel.getSuccessMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                viewModel.clearSuccessMessage();
            }
        });
    }

    private void updateShiftUI(Shift shift) {
        if (shift != null && shift.isActive()) {
            // Active shift
            binding.noShiftCard.setVisibility(View.GONE);
            binding.shiftInfoCard.setVisibility(View.VISIBLE);
            binding.allCocktailsTitle.setVisibility(View.VISIBLE);
            binding.allCocktailsGrid.setVisibility(View.VISIBLE);

            binding.shiftStartTime.setText(getString(R.string.shift_started_format,
                    timeFormat.format(new Date(shift.getStartTime()))));

            startDurationUpdate(shift.getStartTime());
        } else {
            // No active shift
            binding.noShiftCard.setVisibility(View.VISIBLE);
            binding.shiftInfoCard.setVisibility(View.GONE);
            binding.quickAccessTitle.setVisibility(View.GONE);
            binding.cocktailGrid.setVisibility(View.GONE);
            binding.allCocktailsTitle.setVisibility(View.GONE);
            binding.allCocktailsGrid.setVisibility(View.GONE);

            stopDurationUpdate();
        }
        updateSearchMenuVisibility();
    }

    private void startDurationUpdate(long startTime) {
        if (durationHandler == null) {
            durationHandler = new Handler(Looper.getMainLooper());
        }

        durationRunnable = new Runnable() {
            @Override
            public void run() {
                long duration = System.currentTimeMillis() - startTime;
                long hours = duration / (1000 * 60 * 60);
                long minutes = (duration % (1000 * 60 * 60)) / (1000 * 60);
                binding.shiftDuration.setText(getString(R.string.duration_format, hours, minutes));
                durationHandler.postDelayed(this, 60000); // Update every minute
            }
        };

        durationHandler.post(durationRunnable);
    }

    private void stopDurationUpdate() {
        if (durationHandler != null && durationRunnable != null) {
            durationHandler.removeCallbacks(durationRunnable);
        }
    }

    private void recordCocktail(Cocktail cocktail) {
        binding.getRoot().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        viewModel.recordCocktail(cocktail.getId());
    }

    private void showRemoveCocktailDialog(Cocktail cocktail) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.remove_cocktail_title)
                .setMessage(getString(R.string.remove_cocktail_message, cocktail.getName()))
                .setPositiveButton(R.string.remove, (dialog, which) -> {
                    viewModel.removeCocktail(cocktail.getId());
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showEndShiftDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.end_shift_title)
                .setMessage(R.string.end_shift_message)
                .setPositiveButton(R.string.end_shift, (dialog, which) -> {
                    String shiftId = viewModel.getActiveShiftId();
                    viewModel.endShift();

                    if (shiftId != null) {
                        Intent intent = new Intent(this, ShiftSummaryActivity.class);
                        intent.putExtra(ShiftSummaryActivity.EXTRA_SHIFT_ID, shiftId);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bottomNav.setSelectedItemId(R.id.nav_shift);
        viewModel.loadActiveShift();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDurationUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDurationUpdate();
        binding = null;
    }
}
