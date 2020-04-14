package com.example.firstmaps;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.firstmaps.Dao.MarkerDao;
import com.example.firstmaps.Dao.MyMarker;

@Database(entities = {MyMarker.class}, version = 1)
public abstract class MarkerDB extends RoomDatabase {
    public abstract MarkerDao markerDao();
}
