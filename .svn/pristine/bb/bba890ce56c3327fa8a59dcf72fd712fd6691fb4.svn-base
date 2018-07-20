package com.twinforce.trashreminder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TF4 on 22/12/2016.
 */

public class GeofenceManager implements ResultCallback {
    List mGeofenceList;
    static final int GEOFENCE_RADIUS_IN_METERS = 20;
    AndroidLocation mLocation;

    PendingIntent mGeofencePendingIntent ;
    Context mContext;

    public GeofenceManager(Context context, AndroidLocation location) {
        mGeofenceList = new ArrayList();
        mLocation = location;
        mContext = context;
        LogString.addLog("Created GeofenceManager");
    }

    public void createGeofence(String id) {
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(id)
                .setCircularRegion(
                        mLocation.getCurrentLatitude(),
                        mLocation.getCurrentLongitude(),
                        GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(1000)
                .build());
        LogString.addLog("Created Geofence");
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        LogString.addLog("Geofencing requested");
        return builder.build();
    }

    public void addGeofences() {
        LocationServices.GeofencingApi.addGeofences(
                mLocation.getGoogleApiClient(),
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
        LogString.addLog("Added Geofences");
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(mContext, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        LogString.addLog("Created geofence intent");
        return PendingIntent.getService(mContext, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onResult(Result result) {
        String s = (result.getStatus().isSuccess())? "SUCCES" : "FAIL";
        LogString.addLog("RESULT: "+s);
    }
}
