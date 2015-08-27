package com.example.administrator.metroalerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MainActivity extends Activity {
    ImageButton StationInfo,NearestStation,FindRoute,TrainTiming, UpdateDb,SocialShare,AboutUs,Metroam;
    final DatabaseHandler db = new DatabaseHandler(this);
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("metro", MODE_PRIVATE);
        StationInfo = (ImageButton) findViewById(R.id.station_info);
        NearestStation = (ImageButton) findViewById(R.id.nearest_station);
        FindRoute = (ImageButton) findViewById(R.id.find_route);
        TrainTiming = (ImageButton) findViewById(R.id.train_timing);
        UpdateDb = (ImageButton) findViewById(R.id.update_db);
        SocialShare = (ImageButton) findViewById(R.id.share_us);

        AboutUs = (ImageButton) findViewById(R.id.about_us);
        Metroam = (ImageButton) findViewById(R.id.metro_map);
        String ver = prefs.getString("version", null);
        if( ver== null){
            SharedPreferences.Editor editor = getSharedPreferences("metro", MODE_PRIVATE).edit();
            editor.putString("version", "1");
            editor.commit();
        }


        StationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,SelectStation.class);
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
                if(isNetworkAvailable()){
                    new UpdateDbJob(MainActivity.this).execute();
                }else{
                    Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        Metroam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(MainActivity.this,MapMetro.class);
                startActivity(i);
            }
        });
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("This is all about metro app")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        SocialShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = "I find that app quite useful";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Thanks for sharing"));


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
    class UpdateDbJob extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;
        Context context;
        public UpdateDbJob(Context c) {
            dialog = new ProgressDialog(c);
            context= c;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Updating database");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                SharedPreferences prefs = getSharedPreferences("metro", MODE_PRIVATE);
                String restoredText = prefs.getString("version", null);
                //------------------>>
                String Url = "http://xeamphiil.co.nf/JMS/MetroApp/EventDetails.php?version=" +
                        restoredText;
                Log.e("Error",Url);
                HttpGet httppost = new HttpGet(Url.replaceAll(" ","%20")
                );
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String Resp =  EntityUtils.toString(entity);
                    Log.e("Error",Resp);
                    if(Resp.trim().equals("100")){
                        return "100";
                    }else {

                        SharedPreferences.Editor editor = getSharedPreferences("metro", MODE_PRIVATE).edit();
                        editor.putString("version", Resp.split("::::::")[0]);
                        editor.commit();
                        db.UpgradeDB(Resp.split("::::::")[1]);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(final String Resp) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(Resp.equals("100")){
                Toast.makeText(getApplicationContext(),"No update found",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Database updated",Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
