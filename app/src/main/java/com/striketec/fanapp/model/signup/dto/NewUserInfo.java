package com.striketec.fanapp.model.signup.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 17-11-2017.
 */

public class NewUserInfo {
    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
