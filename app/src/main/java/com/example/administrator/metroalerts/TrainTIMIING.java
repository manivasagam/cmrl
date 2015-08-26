package com.example.administrator.metroalerts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Administrator on 8/26/2015.
 */
public class TrainTIMIING extends Activity {
    Spinner TrainLov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_time);
        TrainLov = (Spinner) findViewById(R.id.train_list_lov);

        DatabaseHandler db = new DatabaseHandler(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, db.getAllTrains());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TrainLov.setAdapter(dataAdapter);
    }
}
