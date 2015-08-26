package com.example.administrator.metroalerts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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
        }else{
            gps.showSettingsAlert();
        }

    }
}
