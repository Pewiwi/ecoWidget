package com.twinforce.trashreminder;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by TF4 on 22/12/2016.
 */

public class GeofenceTransitionsIntentService extends IntentService {

    public GeofenceTransitionsIntentService() {
        super("GofencingService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
           /* String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);*/
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        List triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        Geofence geo =(Geofence) triggeringGeofences.get(0);
        switch (geofenceTransition){
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                LogString.addLog("GEOFENCING: "+ geo.getRequestId() +" - Dwell");
                break;
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                LogString.addLog("GEOFENCING: "+ geo.getRequestId() +" - Enter");
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                LogString.addLog("GEOFENCING: "+ geo.getRequestId() +" - Exit");
                break;
        }

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
           // List triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            /*// Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);*/
        } else {
            // Log the error.
           /* Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
                    geofenceTransition));*/
        }
    }
}
