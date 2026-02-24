package it.faustobe.jigger.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.faustobe.jigger.R;
import it.faustobe.jigger.data.local.entities.Shift;
import it.faustobe.jigger.data.models.ShiftWithCocktailCount;

public class ShiftHistoryAdapter extends RecyclerView.Adapter<ShiftHistoryAdapter.ViewHolder> {

    private List<ShiftWithCocktailCount> shifts = new ArrayList<>();
    private OnShiftClickListener listener;

    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public interface OnShiftClickListener {
        void onShiftClick(ShiftWithCocktailCount shift);
    }

    public void setListener(OnShiftClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shift_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShiftWithCocktailCount shiftWithCount = shifts.get(position);
        holder.bind(shiftWithCount, listener, dayFormat, monthFormat, timeFormat);
    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    public void setShifts(List<ShiftWithCocktailCount> newShifts) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return shifts.size();
            }

            @Override
            public int getNewListSize() {
                return newShifts.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return shifts.get(oldItemPosition).getShift().getId()
                        .equals(newShifts.get(newItemPosition).getShift().getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                ShiftWithCocktailCount oldItem = shifts.get(oldItemPosition);
                ShiftWithCocktailCount newItem = newShifts.get(newItemPosition);
                return oldItem.getCocktailCount() == newItem.getCocktailCount() &&
                       oldItem.getShift().getStatus().equals(newItem.getShift().getStatus());
            }
        });

        shifts = new ArrayList<>(newShifts);
        diffResult.dispatchUpdatesTo(this);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayText;
        private final TextView monthText;
        private final TextView timeRangeText;
        private final TextView durationText;
        private final TextView cocktailCountText;

        ViewHolder(View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            monthText = itemView.findViewById(R.id.monthText);
            timeRangeText = itemView.findViewById(R.id.timeRangeText);
            durationText = itemView.findViewById(R.id.durationText);
            cocktailCountText = itemView.findViewById(R.id.cocktailCountText);
        }

        void bind(ShiftWithCocktailCount shiftWithCount, OnShiftClickListener listener,
                  SimpleDateFormat dayFormat, SimpleDateFormat monthFormat, SimpleDateFormat timeFormat) {
            Shift shift = shiftWithCount.getShift();
            Date startDate = new Date(shift.getStartTime());

            // Date circle
            dayText.setText(dayFormat.format(startDate));
            monthText.setText(monthFormat.format(startDate).toUpperCase());

            // Time range
            String startTime = timeFormat.format(startDate);
            if (shift.getEndTime() != null) {
                String endTime = timeFormat.format(new Date(shift.getEndTime()));
                timeRangeText.setText(startTime + " - " + endTime);
            } else {
                timeRangeText.setText(startTime + " - " + itemView.getContext().getString(R.string.shift_in_progress));
            }

            // Duration
            long durationMillis = shiftWithCount.getDurationMillis();
            long hours = durationMillis / (1000 * 60 * 60);
            long minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60);
            durationText.setText(itemView.getContext().getString(R.string.duration_format, hours, minutes));

            // Cocktail count
            cocktailCountText.setText(String.valueOf(shiftWithCount.getCocktailCount()));

            // Click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onShiftClick(shiftWithCount);
                }
            });
        }
    }
}
