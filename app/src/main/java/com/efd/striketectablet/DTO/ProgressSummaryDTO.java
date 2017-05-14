package com.efd.striketectablet.DTO;

public class ProgressSummaryDTO {

    int userID;
    String date;
    String summaryType;

    // LEFT JAB
    double leftJabSpeed;
    double leftJabForce;

    // LEFT STRAIGHT
    double leftStraightSpeed;
    double leftStraightForce;

    // LEFT HOOK
    double leftHookSpeed;
    double leftHookForce;

    // LEFT UPPERCUT
    double leftUppercutSpeed;
    double leftUppercutForce;

    // LEFT UNRECOGNIZED
    double leftUnrecognizedSpeed;
    double leftUnrecognizedForce;

    // RIGHT JAB
    double rightJabSpeed;
    double rightJabForce;

    // RIGHT STRAIGHT
    double rightStraightSpeed;
    double rightStraightForce;

    // RIGHT HOOK
    double rightHookSpeed;
    double rightHookForce;

    // RIGHT UPPERCUT
    double rightUppercutSpeed;
    double rightUppercutForce;

    // RIGHT UNRECOGNIZED
    double rightUnrecognizedSpeed;
    double rightUnrecognizedForce;

    public ProgressSummaryDTO(int userID, String date, String summaryType,
                              double leftJabSpeed, double leftJabForce, double leftStraightSpeed,
                              double leftStraightForce, double leftHookSpeed,
                              double leftHookForce, double leftUppercutSpeed,
                              double leftUppercutForce, double leftUnrecognizedSpeed,
                              double leftUnrecognizedForce, double rightJabSpeed,
                              double rightJabForce, double rightStraightSpeed,
                              double rightStraightForce, double rightHookSpeed,
                              double rightHookForce, double rightUppercutSpeed,
                              double rightUppercutForce, double rightUnrecognizedSpeed,
                              double rightUnrecognizedForce) {
        super();
        this.userID = userID;
        this.date = date;
        this.summaryType = summaryType;
        this.leftJabSpeed = leftJabSpeed;
        this.leftJabForce = leftJabForce;
        this.leftStraightSpeed = leftStraightSpeed;
        this.leftStraightForce = leftStraightForce;
        this.leftHookSpeed = leftHookSpeed;
        this.leftHookForce = leftHookForce;
        this.leftUppercutSpeed = leftUppercutSpeed;
        this.leftUppercutForce = leftUppercutForce;
        this.leftUnrecognizedSpeed = leftUnrecognizedSpeed;
        this.leftUnrecognizedForce = leftUnrecognizedForce;
        this.rightJabSpeed = rightJabSpeed;
        this.rightJabForce = rightJabForce;
        this.rightStraightSpeed = rightStraightSpeed;
        this.rightStraightForce = rightStraightForce;
        this.rightHookSpeed = rightHookSpeed;
        this.rightHookForce = rightHookForce;
        this.rightUppercutSpeed = rightUppercutSpeed;
        this.rightUppercutForce = rightUppercutForce;
        this.rightUnrecognizedSpeed = rightUnrecognizedSpeed;
        this.rightUnrecognizedForce = rightUnrecognizedForce;
    }

    public int getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public String getSummaryType() {
        return summaryType;
    }

    public double getLeftJabSpeed() {
        return leftJabSpeed;
    }

    public double getLeftJabForce() {
        return leftJabForce;
    }

    public double getLeftStraightSpeed() {
        return leftStraightSpeed;
    }

    public double getLeftStraightForce() {
        return leftStraightForce;
    }

    public double getLeftHookSpeed() {
        return leftHookSpeed;
    }

    public double getLeftHookForce() {
        return leftHookForce;
    }

    public double getLeftUppercutSpeed() {
        return leftUppercutSpeed;
    }

    public double getLeftUppercutForce() {
        return leftUppercutForce;
    }

    public double getLeftUnrecognizedSpeed() {
        return leftUnrecognizedSpeed;
    }

    public double getLeftUnrecognizedForce() {
        return leftUnrecognizedForce;
    }

    public double getRightJabSpeed() {
        return rightJabSpeed;
    }

    public double getRightJabForce() {
        return rightJabForce;
    }

    public double getRightStraightSpeed() {
        return rightStraightSpeed;
    }

    public double getRightStraightForce() {
        return rightStraightForce;
    }

    public double getRightHookSpeed() {
        return rightHookSpeed;
    }

    public double getRightHookForce() {
        return rightHookForce;
    }

    public double getRightUppercutSpeed() {
        return rightUppercutSpeed;
    }

    public double getRightUppercutForce() {
        return rightUppercutForce;
    }

    public double getRightUnrecognizedSpeed() {
        return rightUnrecognizedSpeed;
    }

    public double getRightUnrecognizedForce() {
        return rightUnrecognizedForce;
    }

    @Override
    public String toString() {
        return "ProgressSummaryDTO [userID=" + userID + ", trainingSessionDate=" + date
                + ", summaryType=" + summaryType + ", leftJabSpeed="
                + leftJabSpeed + ", leftJabForce=" + leftJabForce
                + ", leftStraightSpeed=" + leftStraightSpeed
                + ", leftStraightForce=" + leftStraightForce
                + ", leftHookSpeed=" + leftHookSpeed + ", leftHookForce="
                + leftHookForce + ", leftUppercutSpeed=" + leftUppercutSpeed
                + ", leftUppercutForce=" + leftUppercutForce
                + ", leftUnrecognizedSpeed=" + leftUnrecognizedSpeed
                + ", leftUnrecognizedForce=" + leftUnrecognizedForce
                + ", rightJabSpeed=" + rightJabSpeed + ", rightJabForce="
                + rightJabForce + ", rightStraightSpeed=" + rightStraightSpeed
                + ", rightStraightForce=" + rightStraightForce
                + ", rightHookSpeed=" + rightHookSpeed + ", rightHookForce="
                + rightHookForce + ", rightUppercutSpeed=" + rightUppercutSpeed
                + ", rightUppercutForce=" + rightUppercutForce
                + ", rightUnrecognizedSpeed=" + rightUnrecognizedSpeed
                + ", rightUnrecognizedForce=" + rightUnrecognizedForce + "]";
    }

}
