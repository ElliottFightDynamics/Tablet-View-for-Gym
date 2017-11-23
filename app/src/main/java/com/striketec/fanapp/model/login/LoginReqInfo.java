package com.striketec.fanapp.model.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This class is used as request data for login web API.
 */

public class LoginReqInfo {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
