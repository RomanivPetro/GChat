package com.android.romaniv.gchat;

import android.content.Context;
import android.location.Location;
import android.util.Log;

/**
 * Created by Петро on 02.05.2015.
 */
public class TrackingLocationReceiver extends LocationReceiver {
    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
        Log.d("GChat", this + " Got location from ->> " + loc.getProvider() + ": "
                + loc.getLatitude() + ", " + loc.getLongitude());
    }
}
