package com.example.android.got.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter{

    String[] titles = new String[]{"HISTORY","EPISODES"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HistoryFragment();
        } else if(position==1){
            return new EpisodesFragment();
        }
        else
            return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return titles[0];
        } else if(position==1){
            return titles[1];
        }
        else
            return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
