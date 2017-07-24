package com.efd.striketectablet.activity.trainingstats.trainingProgress;

import com.efd.striketectablet.activity.trainingstats.charts.TrainingProgressBarchartSettings;

/**
 * Created by omnic on 7/31/2016.
 */
public class TrainingBarchartSettingsProvider {


    public TrainingProgressBarchartSettings getSettingsForYear() {
        TrainingProgressBarchartSettings barchartSettings = new TrainingProgressBarchartSettings();
        barchartSettings.setBarWidth(60);
        barchartSettings.setItemWidth(90);
        barchartSettings.setLabelWidth(70);
        barchartSettings.setBarPadding(15);

        return barchartSettings;
    }

    public TrainingProgressBarchartSettings getSettingsForWeeks() {
        TrainingProgressBarchartSettings barchartSettings = new TrainingProgressBarchartSettings();
        barchartSettings.setBarWidth(90);
        barchartSettings.setItemWidth(120);
        barchartSettings.setLabelWidth(100);
        barchartSettings.setBarPadding(25);

        return barchartSettings;
    }

    public TrainingProgressBarchartSettings getSettingsForDays() {
        TrainingProgressBarchartSettings barchartSettings = new TrainingProgressBarchartSettings();
        barchartSettings.setBarWidth(10);
        barchartSettings.setItemWidth(50);
        barchartSettings.setLabelWidth(30);
        barchartSettings.setBarPadding(10);

        return barchartSettings;
    }


    public TrainingProgressBarchartSettings getSettingsForRounds() {
        TrainingProgressBarchartSettings barchartSettings = new TrainingProgressBarchartSettings();
        barchartSettings.setBarWidth(60);
        barchartSettings.setItemWidth(80);
        barchartSettings.setLabelWidth(60);
        barchartSettings.setBarPadding(10);
        return barchartSettings;
    }
}
