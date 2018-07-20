package com.twinforce.trashreminder;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AndroidLocation location;
    GeofenceManager geoManager;

    Button bCheckLocation;
    TextView tvLocations;

    Handler h = new Handler();
    int delay = 1000; //milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bCheckLocation = (Button) findViewById(R.id.bCheckLocation);
        bCheckLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckButtonPressed();
            }
        });
        tvLocations = (TextView) findViewById(R.id.tvLocations);


        location = new AndroidLocation(this);




        h.postDelayed(new Runnable(){
            public void run(){
                tvLocations.setText(LogString.log);
                h.postDelayed(this, delay);
            }
        }, delay);


        String json = loadJSONFromAsset();
        Gson gson = new Gson();
        NewsItem[] newsArray = gson.fromJson(json, NewsItem[].class);
        List<NewsItem> news = new ArrayList<>(Arrays.asList(newsArray));
        int i =0;
        for (NewsItem newsItem:
        news){
            i++;
            Log.v("NEW "+i,newsItem.title);
            Log.v("NEW CONTENT "+i,newsItem.content);
        }


    }
String showText = "";
    private void onCheckButtonPressed(){
        location.show();
        showText += location.getCurrentLatitude()+","+location.getCurrentLongitude()+"\n";
        //tvLocations.setText(showText);

    }

    public void onGoogleApiConnected(){
        LogString.addLog("On google API connected");
        geoManager = new GeofenceManager(this, location);
        geoManager.createGeofence("Test");
        geoManager.addGeofences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        location.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        location.disconnect();


    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("NewsJSON");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }




}
