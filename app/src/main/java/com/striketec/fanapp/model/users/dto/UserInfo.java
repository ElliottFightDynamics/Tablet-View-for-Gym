package com.striketec.fanapp.model.users.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 13-11-2017.
 * This class represents the user info that can take participate in any event.
 */

public class UserInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("photo_url")
    private String photoUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birthday")
    private String dob;
    @SerializedName("weight")
    private int weight;
    @SerializedName("height")
    private int height;

    @SerializedName("isSelected")
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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
