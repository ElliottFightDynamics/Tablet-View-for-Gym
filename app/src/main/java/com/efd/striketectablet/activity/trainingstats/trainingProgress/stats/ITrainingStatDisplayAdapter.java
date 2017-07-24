package com.efd.striketectablet.activity.trainingstats.trainingProgress.stats;

import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;

/**
 * Created by omnic on 8/27/2016.
 */
public interface ITrainingStatDisplayAdapter {
    void setSelected(StatType currentlySelectedStat);

    void setValues(IBarchartValue barchartData);
}
