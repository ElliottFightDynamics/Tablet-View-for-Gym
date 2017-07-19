package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class DBTrainingPunchStatDTO implements Parcelable{

    private Integer id;
    private String punchedDate;
    private String punchType;
    private Double avgSpeed;
    private Double avgForce;
    private Double maxSpeed;
    private Double maxForce;
    private Double minSpeed;
    private Double minForce;
    private int punchCount;
    private Double totalTime;
    private Integer userID;
    private String serverTime;

    public DBTrainingPunchStatDTO(Integer id, String punchedDate, String punchType, Double avgSpeed, Double avgForce,
                                  Double maxSpeed, Double maxForce, Double minSpeed, Double minForce, int punchCount, Double totalTime, Integer userID, String serverTime){
        super();
        this.id = id;
        this.punchedDate = punchedDate;
        this.punchType = punchType;
        this.avgSpeed = avgSpeed;
        this.avgForce = avgForce;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.minSpeed = minSpeed;
        this.minForce = minForce;
        this.punchCount = punchCount;
        this.totalTime = totalTime;
        this.userID = userID;
        this.serverTime = serverTime;
    }

    public Integer getId() {
        return id;
    }

    public String getPunchedDate() {
        return punchedDate;
    }

    public String getPunchType() {
        return punchType;
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

    public Double getMinSpeed(){
        return minSpeed;
    }

    public Double getMinForce(){
        return minForce;
    }

    public int getPunchCount(){
        return punchCount;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getServerTime(){return serverTime;}

    @Override
    public String toString() {
        return "DBTrainingPunchState [id=" + id + ", punchedDate=" + punchedDate
                + ", punchType=" + punchType + ", punchCount=" + punchCount + ", userID=" + userID + ", avgforce=" + avgForce
                + ", avgspeed=" + avgSpeed + ", maxspeed =  " + maxSpeed + " , max force = " + maxForce + ", totaltime = " + totalTime + ", servertime = " + serverTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected DBTrainingPunchStatDTO(Parcel in){
        this.id = in.readInt();
        this.punchedDate = in.readString();
        this.punchType = in.readString();
        this.avgSpeed = in.readDouble();
        this.avgForce = in.readDouble();
        this.maxForce = in.readDouble();
        this.maxSpeed = in.readDouble();
        this.minForce = in.readDouble();
        this.minSpeed = in.readDouble();
        this.punchCount = in.readInt();
        this.totalTime = in.readDouble();
        this.userID = in.readInt();
        this.serverTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.punchedDate);
        dest.writeString(this.punchType);
        dest.writeDouble(this.avgSpeed);
        dest.writeDouble(this.avgForce);
        dest.writeDouble(this.maxForce);
        dest.writeDouble(this.maxSpeed);
        dest.writeDouble(this.minForce);
        dest.writeDouble(this.minSpeed);
        dest.writeInt(this.punchCount);
        dest.writeDouble(this.totalTime);
        dest.writeInt(this.userID);
        dest.writeString(this.serverTime);

    }

    public static final Creator<DBTrainingPunchStatDTO> CREATOR = new Creator<DBTrainingPunchStatDTO>() {
        @Override
        public DBTrainingPunchStatDTO createFromParcel(Parcel source) {
            return new DBTrainingPunchStatDTO(source);
        }

        @Override
        public DBTrainingPunchStatDTO[] newArray(int size) {
            return new DBTrainingPunchStatDTO[size];
        }
    };
}
