package com.efd.striketectablet.activity.trainingstats.charts.stats;

import com.efd.striketectablet.DTO.CalendarSummaryDTO;
import com.efd.striketectablet.utilities.CommonUtils;

/**
 * Created by omnic on 8/30/2016.
 */
public class CalendarBasedBarchartValue extends AbstractBarchartValue {

    private final CalendarSummaryDTO calendarSummaryDTO;

    public CalendarBasedBarchartValue(String label, CalendarSummaryDTO calendarSummaryDTO) {
        super(label);
        this.calendarSummaryDTO = calendarSummaryDTO;
    }

    @Override
    public float getValue(StatType statType) {
        switch (statType) {
            case TIME:
                return CommonUtils.strTimeToSeconds(calendarSummaryDTO.getTotalTrainingTime());
            case PUNCHES:
                return calendarSummaryDTO.getTotalPunches();
            case SPEED:
                return calendarSummaryDTO.getAverageSpeed();
            case FORCE:
                return calendarSummaryDTO.getAverageForce();
        }
        return 0;
    }
}
