package com.striketec.fanapp.model.signup.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 16-11-2017.
 */

public class CompanyInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("company_name")
    private String companyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
