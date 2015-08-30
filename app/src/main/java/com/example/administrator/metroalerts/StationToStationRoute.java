package com.example.administrator.metroalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by Sunny on 8/29/2015.
 */
public class StationToStationRoute extends FragmentActivity implements OnMapReadyCallback {
    String Station1, Station2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metro_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent i = getIntent();
        Station1 = i.getStringExtra("from");
        Station2 = i.getStringExtra("to");
    }
    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        final DatabaseHandler db = new DatabaseHandler(this);
        List<CustomMarker> temp = db.getStationToStationLoc(Station1, Station2);
        PolylineOptions rectOptions = new PolylineOptions()
                .add(temp.get(0).loc);

        map.addMarker(new MarkerOptions().position(temp.get(0).loc).title("Station in "+temp.get(0).name));
        map.moveCamera(CameraUpdateFactory.newLatLng(temp.get(0).loc));

        for(int i = 0 ; i < temp.size();i++){
            rectOptions.add(temp.get(i).loc1);
            map.addMarker(new MarkerOptions().position(temp.get(i).loc1).title("Station in "+temp.get(i).name1));
            map.moveCamera(CameraUpdateFactory.newLatLng(temp.get(i).loc1));
        }
        map.addPolyline(rectOptions);
    }
}
