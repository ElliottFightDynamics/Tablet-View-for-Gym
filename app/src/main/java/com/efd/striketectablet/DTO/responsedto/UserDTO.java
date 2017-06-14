package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDTO implements Parcelable {

    private int id;
    private boolean accountExpired;
    private boolean accountLocked;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private boolean enabled;
    private String gender;
    private String password;
    private boolean passwordExpired;
    private String username;
    private String emailId;
    private CountryDTO country;
    private String zipcode;

    public UserDTO() {}

    protected UserDTO(Parcel in) {
        this.id = in.readInt();
        this.accountExpired = in.readByte() != 0;
        this.accountLocked = in.readByte() != 0;
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.dateOfBirth = in.readString();
        this.enabled = in.readByte() != 0;
        this.gender = in.readString();
        this.password = in.readString();
        this.passwordExpired = in.readByte() != 0;
        this.username = in.readString();
        this.emailId = in.readString();
        this.country = in.readParcelable(CountryDTO.class.getClassLoader());
        this.zipcode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte(this.accountExpired ? (byte) 1 : (byte) 0);
        dest.writeByte(this.accountLocked ? (byte) 1 : (byte) 0);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.dateOfBirth);
        dest.writeByte(this.enabled ? (byte) 1 : (byte) 0);
        dest.writeString(this.gender);
        dest.writeString(this.password);
        dest.writeByte(this.passwordExpired ? (byte) 1 : (byte) 0);
        dest.writeString(this.username);
        dest.writeString(this.emailId);
        dest.writeParcelable(this.country, flags);
        dest.writeString(this.zipcode);
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean getAccountExpired(){
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired){
        this.accountExpired = accountExpired;
    }

    public boolean getAccountLocked(){
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked){
        this.accountLocked = accountLocked;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getDateOfBirth(){
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean getPasswordExpired(){
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired){
        this.passwordExpired = passwordExpired;
    }

    public String getUsername(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    public CountryDTO getCountry(){
        return country;
    }

    public void setCountry(CountryDTO country){
        this.country = country;
    }

    public String getZipcode(){
        return zipcode;
    }

    public void setZipcode(String zipcode){
        this.zipcode = zipcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<UserDTO> CREATOR = new Parcelable.Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel source) {
            return new UserDTO(source);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };
}
