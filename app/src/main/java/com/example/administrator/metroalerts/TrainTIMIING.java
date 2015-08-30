package com.example.administrator.metroalerts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/26/2015.
 */
public class TrainTIMIING extends Activity {
    Spinner TrainLov;
    ListView stationsLov;
    TrainInfoAdapter adapter;
    List<TrainInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_time);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        TrainLov = (Spinner) findViewById(R.id.train_list_lov);

        final DatabaseHandler db = new DatabaseHandler(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, db.getAllTrains());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TrainLov.setAdapter(dataAdapter);

        stationsLov = (ListView) findViewById(R.id.trains_list);

        list= new ArrayList<TrainInfo>();
        list = db.GetTrainTimings(TrainLov.getSelectedItem().toString());
        adapter=new TrainInfoAdapter(this,list);
        stationsLov.setAdapter(adapter);
        TrainLov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list.clear();
                list.addAll(db.GetTrainTimings(TrainLov.getSelectedItem().toString()));
               // Toast.makeText(getApplicationContext(), list.size() + "", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
