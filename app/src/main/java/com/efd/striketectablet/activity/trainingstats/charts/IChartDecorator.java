package com.efd.striketectablet.activity.trainingstats.charts;

import android.graphics.Canvas;

import com.efd.striketectablet.activity.trainingstats.charts.stats.TrainingDataSeries;


/**
 * Created by omnic on 8/6/2016.
 */
public interface IChartDecorator {
    void preDecorate(Canvas canvas, TrainingDataSeries dataSet, TrainingProgressBarchartSettings settings);

    void postDecorate(Canvas canvas, TrainingDataSeries dataSet, TrainingProgressBarchartSettings settings);
}
