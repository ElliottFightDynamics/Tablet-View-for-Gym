package com.efd.striketectablet.activity.trainingstats.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.efd.striketectablet.activity.trainingstats.fragment.ComboStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.RoundStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.SetStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.TotalInfoStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.WorkoutStatsFragment;

public class TodayStatsPageAdapter extends FragmentPagerAdapter {
    private Context _context;

    public TodayStatsPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        if(position == 0) {
            f = RoundStatsFragment.newInstance();
        }else if(position == 1) {
            f = TotalInfoStatsFragment.newInstance();
        } else if(position == 2) {
            f = ComboStatsFragment.newInstance();
        } else if(position == 3) {
            f = SetStatsFragment.newInstance();
        }else if(position == 4) {
            f = WorkoutStatsFragment.newInstance();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
