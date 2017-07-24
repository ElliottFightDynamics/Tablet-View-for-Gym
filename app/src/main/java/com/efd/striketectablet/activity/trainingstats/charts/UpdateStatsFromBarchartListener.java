package com.efd.striketectablet.activity.trainingstats.charts;

import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.TrainingProgressStatAdapterCollection;

/**
 * Created by omnic on 8/30/2016.
 */
public class UpdateStatsFromBarchartListener implements IBarchartClickListener {
    private TrainingProgressStatAdapterCollection statHandler;

    public UpdateStatsFromBarchartListener(TrainingProgressStatAdapterCollection statHandler) {
        this.statHandler = statHandler;
    }

    @Override
    public void barClicked(float x, float y, IBarchartValue barchartData) {
        statHandler.setValues(barchartData);
    }
}
