package com.example.administrator.metroalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/26/2015.
 */
public class StationINFO extends Activity {

    ListView stationsLov;
    TrainInfoAdapter adapter;
    List<TrainInfo> list;
    TextView stationInto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_info);


        final DatabaseHandler db = new DatabaseHandler(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, db.getAlLStations());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       //Toast.makeText(getApplicationContext(),db.getStationInfo(StationsLov.getSelectedItem().toString()).size()+"",Toast.LENGTH_LONG).show();
         stationsLov = (ListView) findViewById(R.id.trains_list);
        stationInto = (TextView) findViewById(R.id.station_name);
        list= new ArrayList<TrainInfo>();
        Intent intent = getIntent();
        stationInto.setText("Routes for "+intent.getStringExtra("station")+" :");
        list = db.getStationInfo(intent.getStringExtra("station"));
        adapter=new TrainInfoAdapter(this,list);
//        StationsLov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                list.clear();
//                list.addAll( db.getStationInfo(StationsLov.getSelectedItem().toString()));
//               // Toast.makeText(getApplicationContext(), list.size() + "", Toast.LENGTH_LONG).show();
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        stationsLov.setAdapter(adapter);

    }
}
