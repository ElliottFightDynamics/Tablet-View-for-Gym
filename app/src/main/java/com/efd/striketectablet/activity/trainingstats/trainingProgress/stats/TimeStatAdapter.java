package com.efd.striketectablet.activity.trainingstats.trainingProgress.stats;

import android.view.View;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;

/**
 * Created by omnic on 8/27/2016.
 */
public class TimeStatAdapter extends AbstractStatDisplayAdapter {
    public TimeStatAdapter(View view) {
        super(view);
    }

    @Override
    protected int getTextViewId() {
        return R.id.training_stat_time;
    }

    @Override
    protected StatType getStatType() {
        return StatType.TIME;
    }

    @Override
    protected String formatStatValue(float statValue) {
        int minutes = (int) Math.floor(statValue / 60f);
        int seconds = (int) (statValue % 60);
        String value = minutes + ":" + seconds;
        return value;
    }

    @Override
    protected int getButtonPressedImage() {
        return R.drawable.training_time_pressed;
    }

    @Override
    protected int getButtonUnpressedImage() {
        return R.drawable.training_time;
    }

    @Override
    protected int getButtonId() {
        return R.id.training_progress_button_time;
    }

}
