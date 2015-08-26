package com.example.administrator.metroalerts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    ImageButton StationInfo,NearestStation,FindRoute,TrainTiming, UpdateDb,SocialShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StationInfo = (ImageButton) findViewById(R.id.station_info);
        NearestStation = (ImageButton) findViewById(R.id.nearest_station);
        FindRoute = (ImageButton) findViewById(R.id.find_route);
        TrainTiming = (ImageButton) findViewById(R.id.train_timing);
        UpdateDb = (ImageButton) findViewById(R.id.update_db);
        SocialShare = (ImageButton) findViewById(R.id.share_us);

        StationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,StationINFO.class);
                startActivity(i);
            }
        });
        NearestStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,NearestSTATION.class);
                startActivity(i);
            }
        });
        FindRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,FindROUTE.class);
                startActivity(i);
            }
        });
        TrainTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,TrainTIMIING.class);
                startActivity(i);
            }
        });


        UpdateDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i  = new Intent(MainActivity.this,TrainTIMIING.class);
//                startActivity(i);
            }
        });

        SocialShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i  = new Intent(MainActivity.this,TrainTIMIING.class);
//                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
