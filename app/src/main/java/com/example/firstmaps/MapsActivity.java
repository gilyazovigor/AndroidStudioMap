package com.example.firstmaps;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.firstmaps.Dao.MyMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener{

    private static final int IMAGE_ACTIVITY = 23;
    private GoogleMap mMap;
    private Marker currentMarker;
    private MarkerDB markerDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markerDB = MarkerApp.getInstance().getDatabase();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // (just for moving Camera)
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        RefreshMarkers(googleMap);
    }

    private void RefreshMarkers(GoogleMap googleMap){
        List<MyMarker> mapMarkers = markerDB.markerDao().getAll();
        mapMarkers.forEach(mapMarker -> {
            currentMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mapMarker.getLatitude(),mapMarker.getLongitude()))
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions mo = new MarkerOptions()
                .position(latLng)
                .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(mo);
        saveMarkerToDB(latLng);
    }


    void saveMarkerToDB(LatLng latLng){
        markerDB.markerDao().insert(new MyMarker(latLng.latitude, latLng.longitude));
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        // TODO: new Activity - open galerea/camera
        Intent intent = new Intent(this, MarkerImageActivity.class);

        intent.putExtra("latitude", marker.getPosition().latitude);
        intent.putExtra("longitude", marker.getPosition().longitude);

        startActivityForResult(intent, IMAGE_ACTIVITY);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
