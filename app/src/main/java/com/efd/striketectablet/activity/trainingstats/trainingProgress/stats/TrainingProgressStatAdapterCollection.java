package com.efd.striketectablet.activity.trainingstats.trainingProgress.stats;

import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 8/16/2016.
 */
public class TrainingProgressStatAdapterCollection {

    private final List<ITrainingStatDisplayAdapter> statAdapters;

    public TrainingProgressStatAdapterCollection() {
        this.statAdapters = new ArrayList<>();
    }

    public void addStat(ITrainingStatDisplayAdapter statAdapter) {
        statAdapters.add(statAdapter);
    }

    public void setSelected(StatType statType) {
        for (ITrainingStatDisplayAdapter displayAdapter : this.statAdapters) {
            displayAdapter.setSelected(statType);
        }
    }

    public void setValues(IBarchartValue barchartData) {
        for (ITrainingStatDisplayAdapter displayAdapter : this.statAdapters) {
            displayAdapter.setValues(barchartData);
        }
    }
}
