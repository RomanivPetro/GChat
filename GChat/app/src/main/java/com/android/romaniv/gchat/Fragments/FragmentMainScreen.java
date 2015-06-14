package com.android.romaniv.gchat.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.romaniv.gchat.InternetCommunication.AsyncResponse;
import com.android.romaniv.gchat.InternetCommunication.GChatItemsTask;
import com.android.romaniv.gchat.InternetCommunication.NETConstants;
import com.android.romaniv.gchat.InternetCommunication.Utils;
import com.android.romaniv.gchat.LocationReceiver;
import com.android.romaniv.gchat.PagerAdapter;
import com.android.romaniv.gchat.R;
import com.android.romaniv.gchat.Run;
import com.android.romaniv.gchat.RunManager;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBProvider;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Петро on 01.05.2015.
 */
public class FragmentMainScreen extends Fragment implements AsyncResponse  {

    private BroadcastReceiver mLocationReceiver = new LocationReceiver() {
        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mLastLocation = loc;
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            int toastText = enabled ? R.string.gps_enabled : R.string.gps_disabled;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
    };

    private GChatItemsTask AsyncTask = new GChatItemsTask();
    private RunManager mRunManager;
    private Run mRun;
    private Location mLastLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("GChat", "Fragment MainScreen OnCreate");

    }
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        Profile profile = Profile.getCurrentProfile();
        AsyncTask.delegate = this;
        CheckUsers(profile);
        LoginInQB();
        Log.d("GChat", "Fragment LoginButton onCreateView");
        View view = inflater.inflate(R.layout.fragment_viewpager,container,false);
        ViewPager pager = (ViewPager)view.findViewById(R.id.viewPager);
        pager.setAdapter(new PagerAdapter(getChildFragmentManager(), getActivity().getApplicationContext()));
        mRunManager = RunManager.get(getActivity());
        mRun = mRunManager.startNewRun();
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mLocationReceiver,
                new IntentFilter(RunManager.ACTION_LOCATION));
    }
    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
    }

    private void CheckUsers(Profile profile){
        Log.d("GChat", "Fragment Main Screen 888888");
        if(profile != null) {
            Log.d("GChat", "Facebook login: " + profile.getLastName().toString());
            Log.d("GChat", "Facebook access token: " + AccessToken.getCurrentAccessToken().getToken().toString());
            String method = NETConstants.CheckUser;
            String parameters = "{\"sId\":\""+profile.getId()+"\"}";
            Log.d("GChat", "Params: " + parameters);
            AsyncTask.execute(new String[]{method, parameters});
        }
    }

    public void processFinish(String output) {
        Log.d("GChat", "My Result 985: " + output);
        //ParseJson(output);

    }

    public void AddUserFinish(String output) {
        Log.d("GChat", "AdduserFinishListener: " + output);
    }

    public void SelectAllUserFinish(String output){};

    public void CheckUserFinish(String output) {
        Log.d("GChat", "CheckUserFinishListener: " + output);
        JSONObject dataJsonObj = null;
        try {
            dataJsonObj = new JSONObject(output);
            JSONArray arr = dataJsonObj.getJSONArray("response");
            Log.d("GChat", "arr lenght :" + arr.length());
            if (arr.length() > 0) {
                Log.d("GChat", "arr lenght " + arr.length());
            }
            else{
                AddUser();
            }
        }
        catch (JSONException ex){
            //-----
            Log.d("GChat", "Json Exception" + ex.toString());
        }
    }

    private void AddUser(){
        AsyncTask = new GChatItemsTask();
        AsyncTask.delegate = this;
        Profile profile = Profile.getCurrentProfile();
        String method = NETConstants.AddNewUser;
        String parameters = "{\"Name\":\""+profile.getFirstName()+"\", \"sName\":\""+profile.getLastName()+"\",\"sId\":\""+profile.getId()+"\", \"Status\":\"1\",\"Ip\":\""+ Utils.getIp()+"\"}";
        Log.d("GChat", "Params: " + parameters);
        AsyncTask.execute(new String[]{method, parameters});
    }

    private void LoginInQB(){
        Log.d("QB", "in method LogininQB");
        QBChatService chatService;
        if (!QBChatService.isInitialized()) {
            QBChatService.init(getActivity().getApplicationContext());
            chatService = QBChatService.getInstance();
        }
        QBSettings.getInstance().fastConfigInit("23767", "PXEkGwMKx8h6uZ8", "J-WgCQsAy5e2P4y");
        QBAuth.createSession(new QBEntityCallbackImpl<QBSession>() {

            @Override
        public void onSuccess(QBSession session, Bundle params) {
            Log.d("QB", "onSuccess create session ");
        }

        @Override
        public void onError(List<String> errors) {
            Log.d("QB", "onError create session" + errors);

        }
    });





    }
}
