package com.efd.striketectablet.activity.trainingstats.charts.stats;


import com.efd.striketectablet.database.RoundDto;

/**
 * Created by omnic on 7/31/2016.
 */
public class PerRoundBarchartValue extends AbstractBarchartValue {

    private final RoundDto roundData;

    public PerRoundBarchartValue(String label, RoundDto roundData) {
        super(label);
        this.roundData = roundData;
    }

    @Override
    public float getValue(StatType statType) {
        switch (statType) {
            case TIME:
                return roundData.getElapsedSeconds();
            case PUNCHES:
                return roundData.getTotalPunches();
            case SPEED:
                return roundData.getAverageSpeed();
            case FORCE:
                return roundData.getAverageForce();
        }
        return 0;
    }
}
