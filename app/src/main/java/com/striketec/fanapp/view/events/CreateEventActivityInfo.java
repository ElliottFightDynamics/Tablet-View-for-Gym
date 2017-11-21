package com.striketec.fanapp.view.events;

/**
 * Created by Sukhbirs on 21-11-2017.
 * This class represents the activity details which needs to be selected on step 2 while creating event.
 */

public class CreateEventActivityInfo {
    private String activityName;
    private int activityDrawable;
    private String description;
    private boolean isSelected;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityDrawable() {
        return activityDrawable;
    }

    public void setActivityDrawable(int activityDrawable) {
        this.activityDrawable = activityDrawable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "CreateEventActivityInfo{" +
                "activityName='" + activityName + '\'' +
                ", activityDrawable=" + activityDrawable +
                ", description='" + description + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
