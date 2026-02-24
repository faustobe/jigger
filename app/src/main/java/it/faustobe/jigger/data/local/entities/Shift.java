package it.faustobe.jigger.data.local.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "shifts")
public class Shift {

    @PrimaryKey
    @NonNull
    private String id;

    @Nullable
    private String bartenderId;

    private long startTime;

    @Nullable
    private Long endTime;

    @ColumnInfo(name = "status")
    @NonNull
    private String status;

    @Nullable
    private String notes;

    public Shift() {
        this.id = UUID.randomUUID().toString();
        this.startTime = System.currentTimeMillis();
        this.status = "ACTIVE";
    }

    // Getters
    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getBartenderId() {
        return bartenderId;
    }

    public long getStartTime() {
        return startTime;
    }

    @Nullable
    public Long getEndTime() {
        return endTime;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getNotes() {
        return notes;
    }

    // Setters
    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setBartenderId(@Nullable String bartenderId) {
        this.bartenderId = bartenderId;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(@Nullable Long endTime) {
        this.endTime = endTime;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public void close() {
        this.endTime = System.currentTimeMillis();
        this.status = "CLOSED";
    }
}
