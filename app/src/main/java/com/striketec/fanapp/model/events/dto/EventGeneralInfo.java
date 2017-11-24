package com.striketec.fanapp.model.events.dto;

/**
 * Created by Sukhbirs on 22-11-2017.
 */

public class EventGeneralInfo {
    private String eventTitle;
    private EventLocationInfo eventLocationInfo;
    private String eventLocationName;
    private String eventDescription;
    private String eventStartDate;
    private String eventStartTime;
    private String eventEndDate;
    private String eventEndTime;
    private boolean isAllDay;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public EventLocationInfo getEventLocationInfo() {
        return eventLocationInfo;
    }

    public void setEventLocationInfo(EventLocationInfo eventLocationInfo) {
        this.eventLocationInfo = eventLocationInfo;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }
}
