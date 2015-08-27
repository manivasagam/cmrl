package com.example.administrator.metroalerts;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Administrator on 8/27/2015.
 */
public class MapMetro extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metro_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        final DatabaseHandler db = new DatabaseHandler(this);
        List<CustomMarker> temp = db.getAlLStationsLoc();
        for(int i = 0 ; i < temp.size();i++){

            map.addMarker(new MarkerOptions().position(temp.get(i).loc).title("Station in "+temp.get(i).name));
            map.moveCamera(CameraUpdateFactory.newLatLng(temp.get(i).loc));
        }

    }
}
