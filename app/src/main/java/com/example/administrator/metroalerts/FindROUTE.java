package com.example.administrator.metroalerts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/26/2015.
 */
public class FindROUTE extends Activity {

    Spinner StationsLovTo;
    Spinner StationsLovFrom;

    ListView stationsLov;
    FindRouteAdapter adapter;
    List<TrainInfo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_route);
        StationsLovTo = (Spinner) findViewById(R.id.stations_list_to);
        StationsLovFrom=(Spinner) findViewById(R.id.stations_list_from);

        final DatabaseHandler db = new DatabaseHandler(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, db.getAlLStations());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StationsLovTo.setAdapter(dataAdapter);
        StationsLovFrom.setAdapter(dataAdapter);
        stationsLov = (ListView) findViewById(R.id.trains_list);

        list= new ArrayList<TrainInfo>();
        list = db.GetFindRoute(StationsLovFrom.getSelectedItem().toString(),StationsLovTo.getSelectedItem().toString());
        adapter=new FindRouteAdapter(this,list);
        stationsLov.setAdapter(adapter);
        StationsLovTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                list.addAll(db.GetFindRoute(StationsLovFrom.getSelectedItem().toString(), StationsLovTo.getSelectedItem().toString()));
                // Toast.makeText(getApplicationContext(), list.size() + "", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        StationsLovFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                list.addAll(db.GetFindRoute(StationsLovFrom.getSelectedItem().toString(),StationsLovTo.getSelectedItem().toString()));
                // Toast.makeText(getApplicationContext(), list.size() + "", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        StationsLovTo.setSelection(1);
    }
}
