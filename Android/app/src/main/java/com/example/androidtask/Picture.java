package com.example.androidtask;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "picture_table")
public class Picture {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fileName;

    private String description;

    private Double latitude;

    private Double longitude;

    public Picture(String fileName, String description, Double latitude, Double longitude) {
        this.fileName = fileName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDescription() {
        return description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
