package com.android.romaniv.gchat.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.romaniv.gchat.InternetCommunication.AsyncResponse;
import com.android.romaniv.gchat.InternetCommunication.GChatItemsTask;
import com.android.romaniv.gchat.InternetCommunication.NETConstants;
import com.android.romaniv.gchat.InternetCommunication.Utils;
import com.android.romaniv.gchat.R;


/**
 * Created by Петро on 02.05.2015.
 */
public class SecondFragment extends Fragment implements AsyncResponse {
    private static final String TAG = "GChatHttpRequest";
    private GChatItemsTask AsyncTask = new GChatItemsTask();
    private TextView tv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        tv = (TextView) v.findViewById(R.id.tvFragSecond);
        tv.setText(getArguments().getString("msg"));
        AsyncTask.delegate = this;

        Log.d(TAG, "Started Task: ");
        Log.d(TAG, "Current IP: "+ Utils.getIPAddress(true));
        Log.d(TAG, "Current external IP: "+ Utils.getIp());
        //Передати параметри в потік
        String method = NETConstants.MethodReadFromDB;
        String parameters = "{}";
        AsyncTask.execute(new String[]{method, parameters});
        return v;
    }

    public static SecondFragment newInstance(String text) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }

    public void processFinish(String output){
        Log.d(TAG, "My Result: " + output);
        tv.setText(output);
    }

}
