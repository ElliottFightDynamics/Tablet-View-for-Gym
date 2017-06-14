package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthenticationDTO implements Parcelable {

    private String success;
    private String secureAccessToken;
    private String message;
    private UserDTO user;
    private BoxerProfileDTO boxerProfile;

    public AuthenticationDTO() {}

    protected AuthenticationDTO(Parcel in) {
        this.success = in.readString();
        this.secureAccessToken = in.readString();
        this.message = in.readString();
        this.user = in.readParcelable(UserDTO.class.getClassLoader());
        this.boxerProfile = in.readParcelable(BoxerProfileDTO.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(success);
        dest.writeString(secureAccessToken);
        dest.writeString(message);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(boxerProfile, flags);
    }

    public String getSuccess(){
        return success;
    }

    public void setSuccess(String success){
        this.success = success;
    }

    public String getSecureAccessToken(){
        return secureAccessToken;
    }

    public void setSecureAccessToken(String secureAccessToken){
        this.secureAccessToken = secureAccessToken;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public UserDTO getUser(){
        return user;
    }

    public void setUser(UserDTO user){
        this.user = user;
    }

    public BoxerProfileDTO getBoxerProfile(){
        return boxerProfile;
    }

    public void setBoxerProfile(BoxerProfileDTO boxerProfile){
        this.boxerProfile = boxerProfile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthenticationDTO> CREATOR = new Creator<AuthenticationDTO>() {
        @Override
        public AuthenticationDTO createFromParcel(Parcel source) {
            return new AuthenticationDTO(source);
        }

        @Override
        public AuthenticationDTO[] newArray(int size) {
            return new AuthenticationDTO[size];
        }
    };
}
