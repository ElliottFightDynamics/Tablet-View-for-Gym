package com.striketec.fanapp.model.users.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 13-11-2017.
 */

public class UserInfo {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("isSelected")
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
