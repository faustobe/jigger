package it.faustobe.jigger.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(
    tableName = "shift_cocktails",
    foreignKeys = {
        @ForeignKey(
            entity = Shift.class,
            parentColumns = "id",
            childColumns = "shiftId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Cocktail.class,
            parentColumns = "id",
            childColumns = "cocktailId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("shiftId"),
        @Index("cocktailId")
    }
)
public class ShiftCocktail {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String shiftId;

    @NonNull
    private String cocktailId;

    private long timestamp;

    private int quantity;

    public ShiftCocktail() {
        this.id = UUID.randomUUID().toString();
        this.shiftId = "";
        this.cocktailId = "";
        this.timestamp = System.currentTimeMillis();
        this.quantity = 1;
    }

    public ShiftCocktail(@NonNull String shiftId, @NonNull String cocktailId) {
        this.id = UUID.randomUUID().toString();
        this.shiftId = shiftId;
        this.cocktailId = cocktailId;
        this.timestamp = System.currentTimeMillis();
        this.quantity = 1;
    }

    // Getters
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getShiftId() {
        return shiftId;
    }

    @NonNull
    public String getCocktailId() {
        return cocktailId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setShiftId(@NonNull String shiftId) {
        this.shiftId = shiftId;
    }

    public void setCocktailId(@NonNull String cocktailId) {
        this.cocktailId = cocktailId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
