package com.example.firstmaps.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MarkerDao {

    @Insert
    void insert(MyMarker myMarker);

    @Update
    void update(MyMarker myMarker);

    @Delete
    void delete(MyMarker myMarker);

    @Query("SELECT * FROM Markers WHERE LATITUDE LIKE :latitude AND LONGITUDE LIKE :longitude LIMIT 1")
    MyMarker findByParams(Double latitude, Double longitude);

    @Query("SELECT * FROM Markers")
    List<MyMarker> getAll();


}
