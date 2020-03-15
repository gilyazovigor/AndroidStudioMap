package com.example.firstmaps;

//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

//@Entity
public class Marker {

 //   @PrimaryKey
    public Long id;

 //   @ColumnInfo(name = "LATITUDE")
    public Double latitude;

 //   @ColumnInfo(name = "LONGITUDE")
    public Double longitude;

 //   @ColumnInfo(name = "COMMENT")
    public String comment;

 //   @ColumnInfo(name = "FILE_PATH")
    public String filePath;

    public Marker(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
