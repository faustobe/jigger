package it.faustobe.jigger.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import it.faustobe.jigger.R;
import it.faustobe.jigger.databinding.ActivityHistoryBinding;
import it.faustobe.jigger.ui.inventory.InventoryActivity;
import it.faustobe.jigger.ui.shift.ShiftActivity;
import it.faustobe.jigger.ui.shift.ShiftSummaryActivity;
import it.faustobe.jigger.viewmodel.HistoryViewModel;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryViewModel viewModel;
    private ShiftHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        setupAdapter();
        setupBottomNavigation();
        observeViewModel();
    }

    private void setupAdapter() {
        adapter = new ShiftHistoryAdapter();
        adapter.setListener(shiftWithCount -> {
            // Only allow viewing completed shifts
            if (!shiftWithCount.getShift().isActive()) {
                Intent intent = new Intent(this, ShiftSummaryActivity.class);
                intent.putExtra(ShiftSummaryActivity.EXTRA_SHIFT_ID, shiftWithCount.getShift().getId());
                startActivity(intent);
            }
        });
        binding.shiftsRecyclerView.setAdapter(adapter);
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setSelectedItemId(R.id.nav_history);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_shift) {
                startActivity(new Intent(this, ShiftActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_inventory) {
                startActivity(new Intent(this, InventoryActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_history) {
                return true;
            }
            return false;
        });
    }

    private void observeViewModel() {
        viewModel.getShiftsWithCounts().observe(this, shifts -> {
            if (shifts != null) {
                adapter.setShifts(shifts);
                binding.emptyText.setVisibility(shifts.isEmpty() ? View.VISIBLE : View.GONE);
                binding.shiftsRecyclerView.setVisibility(shifts.isEmpty() ? View.GONE : View.VISIBLE);
                binding.statsCard.setVisibility(shifts.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getTotalShifts().observe(this, total -> {
            binding.totalShiftsValue.setText(String.valueOf(total != null ? total : 0));
        });

        viewModel.getTotalCocktails().observe(this, total -> {
            binding.totalCocktailsValue.setText(String.valueOf(total != null ? total : 0));
        });

        viewModel.getAvgPerShift().observe(this, avg -> {
            binding.avgPerShiftValue.setText(String.valueOf(avg != null ? avg : 0));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bottomNav.setSelectedItemId(R.id.nav_history);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
