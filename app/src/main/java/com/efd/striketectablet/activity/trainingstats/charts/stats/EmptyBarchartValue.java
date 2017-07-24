package com.efd.striketectablet.activity.trainingstats.charts.stats;

/**
 * Created by omnic on 8/30/2016.
 */
public class EmptyBarchartValue extends AbstractBarchartValue {

    public EmptyBarchartValue(String label) {
        super(label);
    }

    @Override
    public float getValue(StatType statType) {
        return 0;
    }

}
