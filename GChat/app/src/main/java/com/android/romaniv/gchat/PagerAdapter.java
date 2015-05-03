package com.android.romaniv.gchat;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.romaniv.gchat.Fragments.FirstFragment;
import com.android.romaniv.gchat.Fragments.SecondFragment;


/**
 * Created by Петро on 02.05.2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int _count = 2;
    private Context context;
    public PagerAdapter(FragmentManager fm, Context nContext) {
        super(fm);
        context = nContext;
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return FirstFragment.newInstance("FirstFragment, Instance 1");
            default: return SecondFragment.newInstance("SecondFragment, Instance 2");
        }
    }

    @Override
    public int getCount() {
        return _count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return context.getString(R.string.page_title1);
            case 1:
                return context.getString(R.string.page_title2);
            default:
                return null;
        }

    }
}


