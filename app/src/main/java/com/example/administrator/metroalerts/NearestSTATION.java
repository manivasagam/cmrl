package com.example.administrator.metroalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/26/2015.
 */
public class NearestSTATION extends Activity {
    ListView stationsLov;
    NearestStationsAdapter adapter;
    List<TrainInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearest_station);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        GPSTracker gps = new GPSTracker(this);

        final DatabaseHandler db = new DatabaseHandler(this);

        stationsLov = (ListView) findViewById(R.id.trains_list);

        list= new ArrayList<TrainInfo>();
        if(gps.canGetLocation()){
            //Add
            //Toast.makeText(getApplicationContext(),Double.toString(gps.getLatitude())+","+ Double.toString(gps.getLongitude()),Toast.LENGTH_LONG).show();
            list = db.GetNearestTrainInfoList(Double.toString(gps.getLatitude()), Double.toString(gps.getLongitude()));
            adapter=new NearestStationsAdapter(this,list);
            stationsLov.setAdapter(adapter);
            stationsLov.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(NearestSTATION.this, StationINFO.class);
                    i.putExtra("station", list.get(position).name);
                    startActivity(i);
                }

            });
        }else{
            gps.showSettingsAlert();
        }


    }
}
