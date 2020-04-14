package com.example.firstmaps.Dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Markers")
public class MyMarker {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Long id;

    @ColumnInfo(name = "LATITUDE")
    private Double latitude;

    @ColumnInfo(name = "LONGITUDE")
    private Double longitude;

    @ColumnInfo(name = "FILE_PATH")
    private String filePath;

    public MyMarker(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
