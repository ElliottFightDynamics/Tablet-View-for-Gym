package com.efd.striketectablet.activity.trainingstats.charts.stats;

/**
 * Created by omnic on 8/30/2016.
 */
public interface IBarchartValue {

    float getValue(StatType statType);

    String getLabel();

    void setHighlight(boolean shouldHighlight);

    boolean shouldHighlight();
}
