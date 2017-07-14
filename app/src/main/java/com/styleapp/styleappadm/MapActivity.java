package com.styleapp.styleappadm;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback{

    private static final int INITIAL_ZOOM_LEVEL = 16;
    private GoogleMap mapa;
    // private boolean markerexists =false;
    private static final String TAG = MapActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0533671, -77.08557689999998), INITIAL_ZOOM_LEVEL));
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            Toast.makeText(this, "Necesita habilitar los permisos de su ubicación", Toast.LENGTH_LONG).show();
        }
        mapa.addMarker(new MarkerOptions().position(new LatLng(-12.0533671, -77.08557689999998)).title("Posición elegida").snippet("StyleApp"));
    }

}