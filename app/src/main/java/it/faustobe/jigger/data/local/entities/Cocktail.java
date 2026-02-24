package it.faustobe.jigger.data.local.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "cocktails")
public class Cocktail {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String category; // "IBA", "CLASSIC", "SIGNATURE", "CUSTOM"

    @NonNull
    private String glassType; // "HIGHBALL", "COUPE", "ROCKS", "MARTINI", etc.

    @Nullable
    private String imageUrl;

    private boolean isCustom;

    private boolean isFavorite;

    public Cocktail() {
        this.id = UUID.randomUUID().toString();
        this.name = "";
        this.category = "CLASSIC";
        this.glassType = "ROCKS";
        this.isCustom = false;
        this.isFavorite = false;
    }

    public Cocktail(@NonNull String id, @NonNull String name, @NonNull String category,
                    @NonNull String glassType, @Nullable String imageUrl,
                    boolean isCustom, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.glassType = glassType;
        this.imageUrl = imageUrl;
        this.isCustom = isCustom;
        this.isFavorite = isFavorite;
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
    public String getCategory() {
        return category;
    }

    @NonNull
    public String getGlassType() {
        return glassType;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    // Setters
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public void setGlassType(@NonNull String glassType) {
        this.glassType = glassType;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
