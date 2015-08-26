package com.example.administrator.metroalerts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Administrator on 8/26/2015.
 */
public class FindROUTE extends Activity {

    Spinner StationsLovTo;
    Spinner StationsLovFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_route);
        StationsLovTo = (Spinner) findViewById(R.id.stations_list_from);
        StationsLovFrom=(Spinner) findViewById(R.id.stations_list_to);

        DatabaseHandler db = new DatabaseHandler(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, db.getAlLStations());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StationsLovTo.setAdapter(dataAdapter);
        StationsLovFrom.setAdapter(dataAdapter);
    }
}
