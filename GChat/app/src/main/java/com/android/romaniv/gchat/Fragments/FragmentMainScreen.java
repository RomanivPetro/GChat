package com.android.romaniv.gchat.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.romaniv.gchat.LocationReceiver;
import com.android.romaniv.gchat.PagerAdapter;
import com.android.romaniv.gchat.R;
import com.android.romaniv.gchat.Run;
import com.android.romaniv.gchat.RunManager;

/**
 * Created by Петро on 01.05.2015.
 */
public class FragmentMainScreen extends Fragment {
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
}
