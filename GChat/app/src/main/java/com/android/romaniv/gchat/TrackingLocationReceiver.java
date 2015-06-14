package com.android.romaniv.gchat;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.android.romaniv.gchat.InternetCommunication.AsyncResponse;
import com.android.romaniv.gchat.InternetCommunication.GChatItemsTask;
import com.android.romaniv.gchat.InternetCommunication.NETConstants;
import com.android.romaniv.gchat.InternetCommunication.Utils;
import com.facebook.AccessToken;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Петро on 02.05.2015.
 */
public class TrackingLocationReceiver extends LocationReceiver implements AsyncResponse {
    private GChatItemsTask AsyncTask = new GChatItemsTask();
    private Location currentLocation;
    @Override
    protected void onLocationReceived(Context c, Location loc) {
        currentLocation = loc;
        RunManager.get(c).insertLocation(loc);
        Log.d("GChat", this + " Got location from ->> " + loc.getProvider() + ": "
                + loc.getLatitude() + ", " + loc.getLongitude());
        Profile profile = Profile.getCurrentProfile();
        if(profile != null) {
            UpdateLocation(loc);
            SelectUsers(loc);
        }
    }

    public void processFinish(String output) {
    }

    public void AddUserFinish(String output) {
    }

    public void CheckUserFinish(String output) {

    }

    public void SelectAllUserFinish(String output){
        Log.d("GChat", "SelectAllUserFinish: " + output);
        JSONObject dataJsonObj = null;
        try {
            dataJsonObj = new JSONObject(output);
            JSONArray arr = dataJsonObj.getJSONArray("response");
            Log.d("GChat", "arr lenght :" + arr.length());
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject user = arr.getJSONObject(i);
                DistanceCalc.Point point = new DistanceCalc.Point(user.getDouble("longitude"), user.getDouble("latitude"));
                DistanceCalc calc = new DistanceCalc(currentLocation, point);
                double distance = calc.getDistance();
                Log.d("GChat", "distancet :" + distance);
            }

        }
        catch (JSONException ex){
            //-----
            Log.d("GChat", "Json Exception" + ex.toString());
        }
    }

    private void UpdateLocation(Location loc){
        AsyncTask.delegate = this;
        Profile profile = Profile.getCurrentProfile();
        Log.d("GChat", "Update location. User: " + profile.getName());
        if(profile != null) {
            Log.d("GChat", "Facebook access token: " + AccessToken.getCurrentAccessToken().getToken().toString());
            String method = NETConstants.UpdateUserData;
            String parameters = "{\"Id\":\""+profile.getId()+"\",\"Status\":\"1\",\"Ip\":\""+ Utils.getIp()+"\",\"Latitude\":\""+ loc.getLatitude()+"\",\"Longitude\":\""+loc.getLongitude()+"\"}";
            Log.d("GChat", "Params: " + parameters);
            AsyncTask.execute(new String[]{method, parameters});
        }
    }

    private void SelectUsers(Location Loc){
        AsyncTask = new GChatItemsTask();
        AsyncTask.delegate = this;
        String method = NETConstants.SelectAllUser;
        String parameters = "{}";
        Log.d("GChat", "Params: " + parameters);
        AsyncTask.execute(new String[]{method, parameters});
    }
}
