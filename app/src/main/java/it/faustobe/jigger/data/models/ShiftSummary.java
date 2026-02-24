package it.faustobe.jigger.data.models;

import java.util.List;

import it.faustobe.jigger.data.local.entities.Shift;

public class ShiftSummary {

    private Shift shift;
    private int totalCocktails;
    private long durationMillis;
    private List<CocktailWithCount> cocktailBreakdown;

    public ShiftSummary() {
    }

    public ShiftSummary(Shift shift, int totalCocktails, long durationMillis,
                        List<CocktailWithCount> cocktailBreakdown) {
        this.shift = shift;
        this.totalCocktails = totalCocktails;
        this.durationMillis = durationMillis;
        this.cocktailBreakdown = cocktailBreakdown;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public int getTotalCocktails() {
        return totalCocktails;
    }

    public void setTotalCocktails(int totalCocktails) {
        this.totalCocktails = totalCocktails;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public List<CocktailWithCount> getCocktailBreakdown() {
        return cocktailBreakdown;
    }

    public void setCocktailBreakdown(List<CocktailWithCount> cocktailBreakdown) {
        this.cocktailBreakdown = cocktailBreakdown;
    }

    public String getFormattedDuration() {
        long hours = durationMillis / (1000 * 60 * 60);
        long minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60);
        return hours + "h " + minutes + "m";
    }
}
