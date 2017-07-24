package com.efd.striketectablet.activity.trainingstats.charts.stats;

/**
 * Created by omnic on 8/30/2016.
 */
public abstract class AbstractBarchartValue implements IBarchartValue {

    private String label;
    private boolean shouldHighlight;

    public AbstractBarchartValue(String label){
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void setHighlight(boolean shouldHighlight) {
        this.shouldHighlight = shouldHighlight;
    }

    @Override
    public boolean shouldHighlight() {
        return this.shouldHighlight;
    }
}
