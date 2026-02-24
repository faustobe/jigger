package it.faustobe.jigger.ui.shift;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.models.ShiftSummary;
import it.faustobe.jigger.databinding.ActivityShiftSummaryBinding;
import it.faustobe.jigger.ui.common.BreakdownAdapter;
import it.faustobe.jigger.viewmodel.SummaryViewModel;

public class ShiftSummaryActivity extends AppCompatActivity {

    public static final String EXTRA_SHIFT_ID = "extra_shift_id";

    private ActivityShiftSummaryBinding binding;
    private SummaryViewModel viewModel;
    private BreakdownAdapter breakdownAdapter;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShiftSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(SummaryViewModel.class);

        setupAdapter();
        setupClickListeners();
        observeViewModel();

        String shiftId = getIntent().getStringExtra(EXTRA_SHIFT_ID);
        if (shiftId != null) {
            viewModel.loadShiftSummary(shiftId);
        } else {
            finish();
        }
    }

    private void setupAdapter() {
        breakdownAdapter = new BreakdownAdapter();
        binding.breakdownRecyclerView.setAdapter(breakdownAdapter);
    }

    private void setupClickListeners() {
        binding.btnDone.setOnClickListener(v -> finish());
    }

    private void observeViewModel() {
        viewModel.getShiftSummary().observe(this, this::updateUI);

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
                viewModel.clearErrorMessage();
            }
        });
    }

    private void updateUI(ShiftSummary summary) {
        if (summary == null) return;

        // Start time
        binding.startTimeValue.setText(timeFormat.format(new Date(summary.getShift().getStartTime())));

        // End time
        Long endTime = summary.getShift().getEndTime();
        if (endTime != null) {
            binding.endTimeValue.setText(timeFormat.format(new Date(endTime)));
        } else {
            binding.endTimeValue.setText("--:--");
        }

        // Duration
        binding.durationValue.setText(summary.getFormattedDuration());

        // Total cocktails
        binding.totalCocktailsValue.setText(String.valueOf(summary.getTotalCocktails()));

        // Breakdown
        if (summary.getCocktailBreakdown() != null) {
            breakdownAdapter.setCocktails(summary.getCocktailBreakdown());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
