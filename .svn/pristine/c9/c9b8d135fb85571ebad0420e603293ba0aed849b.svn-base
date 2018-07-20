/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "MyPrefsFile";


    TextView tv;
    String currentTown = Constants.PORRERES;
    String currentResidue = "";
    String currentDay;
    String currentLanguageCode;

    ArrayList<News> storedNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        tv.setText("Iniciando");

        if (storedNews == null)storedNews = new ArrayList<News>();
        currentDay = getCurrentDay();
        LanguageSetup();

        retrieveCurrentResidue(currentTown, currentDay);
        retrieveNews(currentTown);


    }
    private String getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String sDay = "";
        switch (day){
            case Calendar.MONDAY:
                sDay = Constants.MONDAY;
                break;
            case Calendar.TUESDAY:
                sDay = Constants.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                sDay = Constants.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                sDay = Constants.THURSDAY;
                break;
            case Calendar.FRIDAY:
                sDay = Constants.FRIDAY;
                break;
            case Calendar.SATURDAY:
                sDay = Constants.SATURDAY;
                break;
            case Calendar.SUNDAY:
                sDay = Constants.SUNDAY;
                break;
        }
        return sDay;
    }

    private String getDeviceLanguageCode(){
        String dLang = Locale.getDefault().getLanguage().toUpperCase();
        if(!dLang.equals(Constants.SPANISH)
                && !dLang.equals(Constants.CATALAN)
                && !dLang.equals(Constants.ENGLISH)
                && !dLang.equals(Constants.GERMAN)
                && !dLang.equals(Constants.ROMANIAN)
                && !dLang.equals(Constants.ARAB)){
            Log.d("DEV_LANGUAGE UNAVALIBLE", dLang+"Setting english as default");
            return Constants.SPANISH;
        }else {
            return dLang;
        }

    }

    private void LanguageSetup(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if(settings.getString("language", "no").equals("no")) {
            Log.d("NOT SAVED LANGUAGE", "Loading device language");
            currentLanguageCode = getDeviceLanguageCode();
        }else{
            currentLanguageCode = settings.getString("language", "no");
        }
    }

    private void retrieveCurrentResidue(String town, final String dayOfTheWeeK) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("municipios");
        query.whereEqualTo("municipio", currentTown);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject residuesRel, ParseException e) {
                if (e == null) {
                    ParseRelation relResidues = residuesRel.getRelation(dayOfTheWeeK);
                    if (relResidues != null) {
                        final ParseQuery<ParseObject> innerquery = relResidues.getQuery();
                        innerquery.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> residueslist, ParseException e) {
                                if (e == null) {
                                    String residueResult = "";
                                    for (int i = 0; i < residueslist.size(); i++) {
                                        if (i > 0) residueResult += "-";
                                        residueResult += residueslist.get(i).getString("tipo");
                                    }

                                    currentResidue = residueResult;
                                    Log.d("residueResult", "Retrived " + residueResult);
                                } else {
                                    Log.d("residueResult", "Retrived null");
                                }
                            }
                        });

                    } else {
                        Log.d("relResidues", "Retrieved null");
                    }

                } else {
                    Log.d("currentResidue", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void retrieveNews(String town) {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("municipios");
        query.whereEqualTo("municipio", currentTown);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject municipio, ParseException e) {
                if (e == null) {
                    Log.d("news municipio", "" + municipio);
                    final String deb =municipio.getObjectId();
                    ///////////////////////////////////////////////////////////////////////
                    final ParseQuery<ParseObject> innerquery = ParseQuery.getQuery("noticias");
                    innerquery.whereEqualTo("municipios", municipio);
                    innerquery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> noticias, ParseException e) {
                            if (e == null) {
                                Log.d("news",  deb+" "+noticias.size());
                                for (ParseObject po :
                                        noticias) {
                                    Log.d("news po", "" + po.getObjectId());
                                    ParseObject newsLocalized  = po.getParseObject("noticia_"+currentLanguageCode);
                                    if(newsLocalized == null) newsLocalized = po.getParseObject("noticia_ES");
                                    Log.d("noticia_"+currentLanguageCode, "" + newsLocalized.getObjectId());
                                    try {
                                        Log.d("noticia_"+currentLanguageCode+" TITULO", "" + newsLocalized.fetchIfNeeded().getString("titulo"));
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
                                    News currentNews = new News(newsLocalized.getString("titulo"), newsLocalized.getString("subtitulo"), newsLocalized.getString("contenido"), newsLocalized.getString("link"), News.newsType.news);
                                    storedNews.add(currentNews);

                                }
                            } else {
                                Log.d("news", "Error: " + e.getMessage());
                            }
                        }
                    });
                    /////////////////////////////////////////////////////////////////////////
                } else {
                    Log.d("news municipio", "Error: " + e.getMessage());
                }
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
