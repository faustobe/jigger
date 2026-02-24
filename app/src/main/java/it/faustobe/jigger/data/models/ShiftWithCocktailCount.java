package it.faustobe.jigger.data.models;

import it.faustobe.jigger.data.local.entities.Shift;

public class ShiftWithCocktailCount {
    private final Shift shift;
    private final int cocktailCount;

    public ShiftWithCocktailCount(Shift shift, int cocktailCount) {
        this.shift = shift;
        this.cocktailCount = cocktailCount;
    }

    public Shift getShift() {
        return shift;
    }

    public int getCocktailCount() {
        return cocktailCount;
    }

    public long getDurationMillis() {
        if (shift.getEndTime() != null) {
            return shift.getEndTime() - shift.getStartTime();
        }
        return System.currentTimeMillis() - shift.getStartTime();
    }
}
