package it.faustobe.jigger.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "bartenders")
public class Bartender {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    private long createdAt;

    public Bartender() {
        this.id = UUID.randomUUID().toString();
        this.name = "";
        this.createdAt = System.currentTimeMillis();
    }

    public Bartender(@NonNull String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdAt = System.currentTimeMillis();
    }

    public Bartender(@NonNull String id, @NonNull String name, long createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    public long getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
