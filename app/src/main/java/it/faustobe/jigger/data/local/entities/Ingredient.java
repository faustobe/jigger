package it.faustobe.jigger.data.local.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "ingredients")
public class Ingredient {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String type; // "SPIRIT", "LIQUEUR", "MIXER", "GARNISH", "SYRUP", "JUICE", "OTHER"

    @NonNull
    private String unit; // "ml", "g", "piece", "bunch"

    private float currentStock;

    private float minThreshold;

    @Nullable
    private Float costPerUnit;

    public Ingredient() {
        this.id = UUID.randomUUID().toString();
        this.name = "";
        this.type = "OTHER";
        this.unit = "ml";
        this.currentStock = 0;
        this.minThreshold = 0;
    }

    public Ingredient(@NonNull String id, @NonNull String name, @NonNull String type,
                      @NonNull String unit, float currentStock, float minThreshold,
                      @Nullable Float costPerUnit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.currentStock = currentStock;
        this.minThreshold = minThreshold;
        this.costPerUnit = costPerUnit;
    }

    // Getters
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public float getCurrentStock() {
        return currentStock;
    }

    public float getMinThreshold() {
        return minThreshold;
    }

    @Nullable
    public Float getCostPerUnit() {
        return costPerUnit;
    }

    // Setters
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    public void setCurrentStock(float currentStock) {
        this.currentStock = currentStock;
    }

    public void setMinThreshold(float minThreshold) {
        this.minThreshold = minThreshold;
    }

    public void setCostPerUnit(@Nullable Float costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public boolean isLowStock() {
        return currentStock <= minThreshold;
    }
}
