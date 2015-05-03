package com.android.romaniv.gchat.ActivityClasses;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.romaniv.gchat.R;

/**
 * Created by Петро on 01.05.2015.
 */
public class SecondActivity extends ActionBarActivity {
    private String stringArr;
    private TextView mTextDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_viewpager);
        Log.d("GChat", "SecondActivity OnCreate");


    }
}
