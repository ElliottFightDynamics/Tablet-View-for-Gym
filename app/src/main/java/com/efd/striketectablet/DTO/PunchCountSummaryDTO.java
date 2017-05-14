package com.efd.striketectablet.DTO;

public class PunchCountSummaryDTO {

    /**
     * row id for this record
     */
    Integer id;
    String userID;
    String date;
    String summaryType;
    Integer totalPunchCount;

    // LEFT JAB
    Double leftJabAverage;
    Double leftJabToday;

    // LEFT STRAIGHT
    Double leftStraightAverage;
    Double leftStraightToday;

    // LEFT HOOK
    Double leftHookAverage;
    Double leftHookToday;

    // LEFT UPPERCUT
    Double leftUppercutAverage;
    Double leftUppercutToday;

    // LEFT UNRECOGNIZED
    Double leftUnrecognizedAverage;
    Double leftUnrecognizedToday;

    // RIGHT JAB
    Double rightJabAverage;
    Double rightJabToday;

    // RIGHT STRAIGHT
    Double rightStraightAverage;
    Double rightStraightToday;

    // RIGHT HOOK
    Double rightHookAverage;
    Double rightHookToday;

    // RIGHT UPPERCUT
    Double rightUppercutAverage;
    Double rightUppercutToday;

    // RIGHT UNRECOGNIZED
    Double rightUnrecognizedAverage;
    Double rightUnrecognizedToday;

    public PunchCountSummaryDTO(String userID, String date, String summaryType,
                                Integer totalPunchCount, Double leftJabAverage, Double leftJabToday,
                                Double leftStraightAverage, Double leftStraightToday,
                                Double leftHookAverage, Double leftHookToday,
                                Double leftUppercutAverage, Double leftUppercutToday,
                                Double leftUnrecognizedAverage, Double leftUnrecognizedToday,
                                Double rightJabAverage, Double rightJabToday,
                                Double rightStraightAverage, Double rightStraightToday,
                                Double rightHookAverage, Double rightHookToday,
                                Double rightUppercutAverage, Double rightUppercutToday,
                                Double rightUnrecognizedAverage, Double rightUnrecognizedToday) {
        super();
        this.userID = userID;
        this.date = date;
        this.summaryType = summaryType;
        this.totalPunchCount = totalPunchCount;
        this.leftJabAverage = leftJabAverage;
        this.leftJabToday = leftJabToday;
        this.leftStraightAverage = leftStraightAverage;
        this.leftStraightToday = leftStraightToday;
        this.leftHookAverage = leftHookAverage;
        this.leftHookToday = leftHookToday;
        this.leftUppercutAverage = leftUppercutAverage;
        this.leftUppercutToday = leftUppercutToday;
        this.leftUnrecognizedAverage = leftUnrecognizedAverage;
        this.leftUnrecognizedToday = leftUnrecognizedToday;
        this.rightJabAverage = rightJabAverage;
        this.rightJabToday = rightJabToday;
        this.rightStraightAverage = rightStraightAverage;
        this.rightStraightToday = rightStraightToday;
        this.rightHookAverage = rightHookAverage;
        this.rightHookToday = rightHookToday;
        this.rightUppercutAverage = rightUppercutAverage;
        this.rightUppercutToday = rightUppercutToday;
        this.rightUnrecognizedAverage = rightUnrecognizedAverage;
        this.rightUnrecognizedToday = rightUnrecognizedToday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public String getSummaryType() {
        return summaryType;
    }

    public Integer getTotalPunchCount() {
        return totalPunchCount;
    }

    public Double getLeftJabAverage() {
        return leftJabAverage;
    }

    public Double getLeftJabToday() {
        return leftJabToday;
    }

    public Double getLeftStraightAverage() {
        return leftStraightAverage;
    }

    public Double getLeftStraightToday() {
        return leftStraightToday;
    }

    public Double getLeftHookAverage() {
        return leftHookAverage;
    }

    public Double getLeftHookToday() {
        return leftHookToday;
    }

    public Double getLeftUppercutAverage() {
        return leftUppercutAverage;
    }

    public Double getLeftUppercutToday() {
        return leftUppercutToday;
    }

    public Double getLeftUnrecognizedAverage() {
        return leftUnrecognizedAverage;
    }

    public Double getLeftUnrecognizedToday() {
        return leftUnrecognizedToday;
    }

    public Double getRightJabAverage() {
        return rightJabAverage;
    }

    public Double getRightJabToday() {
        return rightJabToday;
    }

    public Double getRightStraightAverage() {
        return rightStraightAverage;
    }

    public Double getRightStraightToday() {
        return rightStraightToday;
    }

    public Double getRightHookAverage() {
        return rightHookAverage;
    }

    public Double getRightHookToday() {
        return rightHookToday;
    }

    public Double getRightUppercutAverage() {
        return rightUppercutAverage;
    }

    public Double getRightUppercutToday() {
        return rightUppercutToday;
    }

    public Double getRightUnrecognizedAverage() {
        return rightUnrecognizedAverage;
    }

    public Double getRightUnrecognizedToday() {
        return rightUnrecognizedToday;
    }

    @Override
    public String toString() {
        return "PunchCountSummaryDTO [userID=" + userID + ", trainingSessionDate=" + date
                + ", summaryType=" + summaryType + ", totalPunchCount="
                + totalPunchCount + ", leftJabAverage=" + leftJabAverage
                + ", leftJabToday=" + leftJabToday + ", leftStraightAverage="
                + leftStraightAverage + ", leftStraightToday="
                + leftStraightToday + ", leftHookAverage=" + leftHookAverage
                + ", leftHookToday=" + leftHookToday + ", leftUppercutAverage="
                + leftUppercutAverage + ", leftUppercutToday="
                + leftUppercutToday + ", leftUnrecognizedAverage="
                + leftUnrecognizedAverage + ", leftUnrecognizedToday="
                + leftUnrecognizedToday + ", rightJabAverage="
                + rightJabAverage + ", rightJabToday=" + rightJabToday
                + ", rightStraightAverage=" + rightStraightAverage
                + ", rightStraightToday=" + rightStraightToday
                + ", rightHookAverage=" + rightHookAverage
                + ", rightHookToday=" + rightHookToday
                + ", rightUppercutAverage=" + rightUppercutAverage
                + ", rightUppercutToday=" + rightUppercutToday
                + ", rightUnrecognizedAverage=" + rightUnrecognizedAverage
                + ", rightUnrecognizedToday=" + rightUnrecognizedToday + "]";
    }

}
