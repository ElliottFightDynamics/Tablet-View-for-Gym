package com.efd.striketectablet.activity.trainingstats.charts;

import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;

/**
 * Created by omnic on 8/30/2016.
 */
public interface IBarchartClickListener {
    void barClicked(float x, float y, IBarchartValue barchartData);
}
