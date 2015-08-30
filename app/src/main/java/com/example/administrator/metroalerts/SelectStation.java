package com.example.administrator.metroalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/27/2015.
 */
public class SelectStation extends Activity {
    ListView stationsLov;
    NearestStationsAdapter adapter;
    List<TrainInfo> list,listTemp;
    EditText SearchString;
    Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_station);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final DatabaseHandler db = new DatabaseHandler(this);

        stationsLov = (ListView) findViewById(R.id.trains_list);
        SearchString = (EditText) findViewById(R.id.search_string);
        list= db.GetAllStations();
        adapter=new NearestStationsAdapter(this,list);
        stationsLov.setAdapter(adapter);


        listTemp= new ArrayList<TrainInfo>(list);
        Search = (Button) findViewById(R.id.search_button);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                for (int i = 0; i < listTemp.size(); i++) {
                    if (SearchString.getText().toString().trim().toLowerCase().contains(listTemp.get(i).name.toLowerCase())) {
                        list.add(listTemp.get(i));

                    }
                }
                adapter.notifyDataSetChanged();
            }
        });


        stationsLov.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SelectStation.this, StationINFO.class);
                i.putExtra("station", list.get(position).name);
                startActivity(i);
            }

        });


    }

}
