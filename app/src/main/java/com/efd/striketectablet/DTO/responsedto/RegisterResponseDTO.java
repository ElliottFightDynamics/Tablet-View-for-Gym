package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class RegisterResponseDTO extends HasSuccess {

    private String traineeServerId;
    private String secureAccessToken;
    private String message;

    public RegisterResponseDTO() {}

    protected RegisterResponseDTO(Parcel in) {
        this.traineeServerId = in.readString();
        this.secureAccessToken = in.readString();
        this.message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.traineeServerId);
        dest.writeString(this.secureAccessToken);
        dest.writeString(this.message);
    }

    public String getTraineeServerId(){
        return traineeServerId;
    }

    public void setTraineeServerId(String traineeServerId){
        this.traineeServerId = traineeServerId;
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

    public static final Creator<RegisterResponseDTO> CREATOR = new Creator<RegisterResponseDTO>() {
        @Override
        public RegisterResponseDTO createFromParcel(Parcel source) {
            return new RegisterResponseDTO(source);
        }

        @Override
        public RegisterResponseDTO[] newArray(int size) {
            return new RegisterResponseDTO[size];
        }
    };
}
