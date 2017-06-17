package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.efd.striketectablet.activity.training.quickstart.QuickstartTrainingPresetFragment;
import com.efd.striketectablet.activity.training.round.TrainingPresetFragment;
import com.efd.striketectablet.activity.training.combination.CombinationFragment;
import com.efd.striketectablet.activity.training.workout.WorkoutFragment;
import com.efd.striketectablet.activity.training.sets.SetsFragment;

public class TrainingTabPageAdapter extends FragmentPagerAdapter {
    private Context _context;

    public TrainingTabPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        if(position == 0) {
            f = QuickstartTrainingPresetFragment.newInstance(_context, "quickstart");
        } else if(position == 1) {
            f = TrainingPresetFragment.newInstance(_context, "round");
        } else if(position == 2) {
            f = CombinationFragment.newInstance(_context);
        }else if (position == 3){
            f = SetsFragment.newInstance(_context);
        }else if (position == 4){
            f = WorkoutFragment.newInstance(_context);
        }
        return f;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
