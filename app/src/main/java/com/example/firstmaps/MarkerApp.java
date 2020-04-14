package com.example.firstmaps;

import android.app.Application;

import androidx.room.Room;

import com.google.android.gms.maps.model.Marker;

public class MarkerApp extends Application {

    private static MarkerApp instance;
    private MarkerDB database;

    public static MarkerApp getInstance() {
        return instance;
    }
    public MarkerDB getDatabase() {

        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, MarkerDB.class, "Markers")
                .allowMainThreadQueries()
                .build();
    }

}
