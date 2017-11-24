package com.striketec.fanapp.model.events.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This DTO class contains response data of event location.
 */

public class EventLocationInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String locationName;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
