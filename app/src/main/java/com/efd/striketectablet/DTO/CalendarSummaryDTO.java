package com.efd.striketectablet.DTO;

public class CalendarSummaryDTO {

    private int userID;
    private int totalPunches;
    private int maxForce;
    private int maxSpeed;
    private final float averageForce;
    private final float averageSpeed;
    private String totalTrainingTime;
    private String date;

    public CalendarSummaryDTO(int userID,
                              int totalPunches,
                              int maxForce,
                              int maxSpeed,
                              float averageForce,
                              float averageSpeed,
                              String totalTrainingTime,
                              String date) {
        super();
        this.userID = userID;
        this.totalPunches = totalPunches;
        this.maxForce = maxForce;
        this.maxSpeed = maxSpeed;
        this.averageForce = averageForce;
        this.averageSpeed = averageSpeed;
        this.totalTrainingTime = totalTrainingTime;
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public int getTotalPunches() {
        return totalPunches;
    }

    public int getMaxForce() {
        return maxForce;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public float getAverageForce() {
        return averageForce;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public String getTotalTrainingTime() {
        return totalTrainingTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    @Override
    public String toString() {
        return "CalendarDTO [userID=" + userID + ", totalPunches="
                + totalPunches + ", maxForce=" + maxForce + ", maxSpeed="
                + maxSpeed + ", totalTrainingTime=" + totalTrainingTime
                + ", trainingSessionDate=" + date + "]";
    }


}
