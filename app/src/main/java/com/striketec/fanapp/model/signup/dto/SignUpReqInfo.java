package com.striketec.fanapp.model.signup.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 17-11-2017.
 * It represents request info to create new user web API.
 */

public class SignUpReqInfo {
    @SerializedName("company_id")
    private String companyId;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

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
