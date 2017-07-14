package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class DBTrainingPlanResultDTO implements Parcelable{
    private Integer id;
    private String planType;
    private String trainingResult;
    private String trainingDate;
    private Integer userID;
    private String serverTime;

    public DBTrainingPlanResultDTO(Integer id, String planType, String trainingResult, String trainingDate, Integer userID, String serverTime){
        super();
        this.id = id;
        this.planType = planType;
        this.trainingResult = trainingResult;
        this.trainingDate = trainingDate;
        this.userID = userID;
        this.serverTime  = serverTime;
    }

    public Integer getId() {
        return id;
    }

    public String getPlanType() {
        return planType;
    }

    public String getTrainingResult() {
        return trainingResult;
    }

    public String getTrainingDate() {
        return trainingDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getServerTime(){
        return serverTime;
    }

    @Override
    public String toString() {
        return "DBTrainingPlanResultDTO [id=" + id + ", planType=" + planType + ", trainingResult=" + trainingResult + ", trainingDate=" + trainingDate + ", userID=" + userID + ",  servertime = " + serverTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected DBTrainingPlanResultDTO(Parcel in){
        this.id = in.readInt();
        this.planType = in.readString();
        this.trainingResult = in.readString();
        this.trainingDate = in.readString();
        this.userID = in.readInt();
        this.userID = in.readInt();
        this.serverTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.planType);
        dest.writeString(this.trainingResult);
        dest.writeString(this.trainingDate);
        dest.writeInt(this.userID);
        dest.writeString(this.serverTime);
    }

    public static final Creator<DBTrainingPlanResultDTO> CREATOR = new Creator<DBTrainingPlanResultDTO>() {
        @Override
        public DBTrainingPlanResultDTO createFromParcel(Parcel source) {
            return new DBTrainingPlanResultDTO(source);
        }

        @Override
        public DBTrainingPlanResultDTO[] newArray(int size) {
            return new DBTrainingPlanResultDTO[size];
        }
    };
}
