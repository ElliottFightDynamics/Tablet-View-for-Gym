package com.striketec.fanapp.model.events.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 24-11-2017.
 * This is model class for create event response info.
 */

public class CreateEventResInfo {
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
