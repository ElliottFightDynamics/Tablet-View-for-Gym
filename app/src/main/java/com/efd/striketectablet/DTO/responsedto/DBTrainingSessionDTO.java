package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class DBTrainingSessionDTO implements Parcelable{
    private Integer id;
    private String startTime;
    private String endTime;
    private String trainingSessionDate;
    private String trainingType;
    private Integer userID;
    private Double avgSpeed;
    private Double avgForce;
    private Double maxSpeed;
    private Double maxForce;
    private int totalPunchCount;
    private String serverTime;

    public DBTrainingSessionDTO(Integer id, String startTime, String endTime, String trainingSessionDate, String trainingType,
                                Double avgSpeed, Double avgForce, Double maxSpeed, Double maxForce, int totalPunchCount, Integer userID, String serverTime){
        super();
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainingSessionDate = trainingSessionDate;
        this.trainingType = trainingType;
        this.userID = userID;
        this.avgSpeed = avgSpeed;
        this.avgForce = avgForce;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.totalPunchCount = totalPunchCount;
        this.serverTime = serverTime;
    }

    public Integer getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTrainingSessionDate() {
        return trainingSessionDate;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public Integer getUserID() {
        return userID;
    }

    public Double getAvgSpeed(){
        return avgSpeed;
    }

    public Double getAvgForce(){
        return avgForce;
    }

    public Double getMaxSpeed(){
        return maxSpeed;
    }

    public Double getMaxForce(){
        return maxForce;
    }

    public int getTotalPunchCount(){
        return totalPunchCount;
    }

    public String getServerTime(){
        return serverTime;
    }


    @Override
    public String toString() {
        return "DBTrainingSessionDTO [id=" + id + ", startTime=" + startTime
                + ", endTime=" + endTime + ", trainingSessionDate=" + trainingSessionDate + ", trainingType="
                + trainingType + ", userID=" + userID + ", avgforce=" + avgForce
                + ", avgspeed=" + avgSpeed + ", maxspeed =  " + maxSpeed + " , max force = " + maxForce + ", totalcount = " + totalPunchCount + ", servertime = " + serverTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected DBTrainingSessionDTO(Parcel in){
        this.id = in.readInt();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.trainingSessionDate = in.readString();
        this.trainingType = in.readString();
        this.userID = in.readInt();
        this.avgSpeed = in.readDouble();
        this.avgForce = in.readDouble();
        this.maxForce = in.readDouble();
        this.maxSpeed = in.readDouble();
        this.totalPunchCount = in.readInt();
        this.serverTime = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.trainingSessionDate);
        dest.writeString(this.trainingType);
        dest.writeInt(this.userID);
        dest.writeDouble(this.avgSpeed);
        dest.writeDouble(this.avgForce);
        dest.writeDouble(this.maxForce);
        dest.writeDouble(this.maxSpeed);
        dest.writeInt(this.totalPunchCount);
        dest.writeString(this.serverTime);
    }

    public static final Creator<DBTrainingSessionDTO> CREATOR = new Creator<DBTrainingSessionDTO>() {
        @Override
        public DBTrainingSessionDTO createFromParcel(Parcel source) {
            return new DBTrainingSessionDTO(source);
        }

        @Override
        public DBTrainingSessionDTO[] newArray(int size) {
            return new DBTrainingSessionDTO[size];
        }
    };
}
