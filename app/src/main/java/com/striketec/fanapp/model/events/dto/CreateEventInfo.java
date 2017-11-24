package com.striketec.fanapp.model.events.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 24-11-2017.
 * This is model class to store the event details to be created.
 */

public class CreateEventInfo {
    @SerializedName("event_title")
    private String eventTitle;
    @SerializedName("location")
    private int locationId;
    @SerializedName("description")
    private String eventDescription;
    @SerializedName("from_date")
    private String fromDate;
    @SerializedName("from_time")
    private String fromTime;
    @SerializedName("to_date")
    private String toDate;
    @SerializedName("to_time")
    private String toTime;
    @SerializedName("type_of_activity")
    private String eventActivityType;
    @SerializedName("all_day")
    private boolean isAllDay;
    @SerializedName("user_id")
    private String userIds;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getEventActivityType() {
        return eventActivityType;
    }

    public void setEventActivityType(String eventActivityType) {
        this.eventActivityType = eventActivityType;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
