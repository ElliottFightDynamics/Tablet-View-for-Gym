package com.efd.striketectablet.activity.trainingstats.trainingProgress.stats;

import android.view.View;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;

/**
 * Created by omnic on 8/27/2016.
 */
public class PunchesStatAdapter extends AbstractStatDisplayAdapter {
    public PunchesStatAdapter(View view) {
        super(view);
    }

    @Override
    protected int getTextViewId() {
        return R.id.training_stat_punches;
    }

    @Override
    protected StatType getStatType() {
        return StatType.PUNCHES;
    }

    @Override
    protected String formatStatValue(float statValue) {
        return String.valueOf((int)statValue);
    }

    @Override
    protected int getButtonPressedImage() {
        return R.drawable.training_punches_pressed;
    }

    @Override
    protected int getButtonUnpressedImage() {
        return R.drawable.training_punches;
    }

    @Override
    protected int getButtonId() {
        return R.id.training_progress_button_punches;
    }

}
