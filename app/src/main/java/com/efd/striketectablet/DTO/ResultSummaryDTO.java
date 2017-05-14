package com.efd.striketectablet.DTO;

public class ResultSummaryDTO {

    int userID;
    String date;
    String summaryType;

    // LEFT JAB
    double leftJabSpeed;
    double leftJabForce;
    double leftJabTotal;

    // LEFT STRAIGHT
    double leftStraightSpeed;
    double leftStraightForce;
    double leftStraightTotal;

    // LEFT HOOK
    double leftHookSpeed;
    double leftHookForce;
    double leftHookTotal;

    // LEFT UPPERCUT
    double leftUppercutSpeed;
    double leftUppercutForce;
    double leftUppercutTotal;

    // LEFT UNRECOGNIZED
    double leftUnrecognizedSpeed;
    double leftUnrecognizedForce;
    double leftUnrecognizedTotal;

    // RIGHT JAB
    double rightJabSpeed;
    double rightJabForce;
    double rightJabTotal;

    // RIGHT STRAIGHT
    double rightStraightSpeed;
    double rightStraightForce;
    double rightStraightTotal;

    // RIGHT HOOK
    double rightHookSpeed;
    double rightHookForce;
    double rightHookTotal;

    // RIGHT UPPERCUT
    double rightUppercutSpeed;
    double rightUppercutForce;
    double rightUppercutTotal;

    // RIGHT UNRECOGNIZED
    double rightUnrecognizedSpeed;
    double rightUnrecognizedForce;
    double rightUnrecognizedTotal;

    public ResultSummaryDTO(int userID, String date, String summaryType,
                            double leftJabSpeed, double leftJabForce, double leftJabTotal,
                            double leftStraightSpeed, double leftStraightForce,
                            double leftStraightTotal, double leftHookSpeed,
                            double leftHookForce, double leftHookTotal,
                            double leftUppercutSpeed, double leftUppercutForce,
                            double leftUppercutTotal, double leftUnrecognizedSpeed,
                            double leftUnrecognizedForce, double leftUnrecognizedTotal,
                            double rightJabSpeed, double rightJabForce, double rightJabTotal,
                            double rightStraightSpeed, double rightStraightForce,
                            double rightStraightTotal, double rightHookSpeed,
                            double rightHookForce, double rightHookTotal,
                            double rightUppercutSpeed, double rightUppercutForce,
                            double rightUppercutTotal, double rightUnrecognizedSpeed,
                            double rightUnrecognizedForce, double rightUnrecognizedTotal) {
        super();
        this.userID = userID;
        this.date = date;
        this.summaryType = summaryType;
        this.leftJabSpeed = leftJabSpeed;
        this.leftJabForce = leftJabForce;
        this.leftJabTotal = leftJabTotal;
        this.leftStraightSpeed = leftStraightSpeed;
        this.leftStraightForce = leftStraightForce;
        this.leftStraightTotal = leftStraightTotal;
        this.leftHookSpeed = leftHookSpeed;
        this.leftHookForce = leftHookForce;
        this.leftHookTotal = leftHookTotal;
        this.leftUppercutSpeed = leftUppercutSpeed;
        this.leftUppercutForce = leftUppercutForce;
        this.leftUppercutTotal = leftUppercutTotal;
        this.leftUnrecognizedSpeed = leftUnrecognizedSpeed;
        this.leftUnrecognizedForce = leftUnrecognizedForce;
        this.leftUnrecognizedTotal = leftUnrecognizedTotal;
        this.rightJabSpeed = rightJabSpeed;
        this.rightJabForce = rightJabForce;
        this.rightJabTotal = rightJabTotal;
        this.rightStraightSpeed = rightStraightSpeed;
        this.rightStraightForce = rightStraightForce;
        this.rightStraightTotal = rightStraightTotal;
        this.rightHookSpeed = rightHookSpeed;
        this.rightHookForce = rightHookForce;
        this.rightHookTotal = rightHookTotal;
        this.rightUppercutSpeed = rightUppercutSpeed;
        this.rightUppercutForce = rightUppercutForce;
        this.rightUppercutTotal = rightUppercutTotal;
        this.rightUnrecognizedSpeed = rightUnrecognizedSpeed;
        this.rightUnrecognizedForce = rightUnrecognizedForce;
        this.rightUnrecognizedTotal = rightUnrecognizedTotal;
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

    public double getLeftJabTotal() {
        return leftJabTotal;
    }

    public double getLeftStraightSpeed() {
        return leftStraightSpeed;
    }

    public double getLeftStraightForce() {
        return leftStraightForce;
    }

    public double getLeftStraightTotal() {
        return leftStraightTotal;
    }

    public double getLeftHookSpeed() {
        return leftHookSpeed;
    }

    public double getLeftHookForce() {
        return leftHookForce;
    }

    public double getLeftHookTotal() {
        return leftHookTotal;
    }

    public double getLeftUppercutSpeed() {
        return leftUppercutSpeed;
    }

    public double getLeftUppercutForce() {
        return leftUppercutForce;
    }

    public double getLeftUppercutTotal() {
        return leftUppercutTotal;
    }

    public double getLeftUnrecognizedSpeed() {
        return leftUnrecognizedSpeed;
    }

    public double getLeftUnrecognizedForce() {
        return leftUnrecognizedForce;
    }

    public double getLeftUnrecognizedTotal() {
        return leftUnrecognizedTotal;
    }

    public double getRightJabSpeed() {
        return rightJabSpeed;
    }

    public double getRightJabForce() {
        return rightJabForce;
    }

    public double getRightJabTotal() {
        return rightJabTotal;
    }

    public double getRightStraightSpeed() {
        return rightStraightSpeed;
    }

    public double getRightStraightForce() {
        return rightStraightForce;
    }

    public double getRightStraightTotal() {
        return rightStraightTotal;
    }

    public double getRightHookSpeed() {
        return rightHookSpeed;
    }

    public double getRightHookForce() {
        return rightHookForce;
    }

    public double getRightHookTotal() {
        return rightHookTotal;
    }

    public double getRightUppercutSpeed() {
        return rightUppercutSpeed;
    }

    public double getRightUppercutForce() {
        return rightUppercutForce;
    }

    public double getRightUppercutTotal() {
        return rightUppercutTotal;
    }

    public double getRightUnrecognizedSpeed() {
        return rightUnrecognizedSpeed;
    }

    public double getRightUnrecognizedForce() {
        return rightUnrecognizedForce;
    }

    public double getRightUnrecognizedTotal() {
        return rightUnrecognizedTotal;
    }

    @Override
    public String toString() {
        return "ResultSummaryDTO [userID=" + userID + ", trainingSessionDate=" + date
                + ", summaryType=" + summaryType + ", leftJabSpeed="
                + leftJabSpeed + ", leftJabForce=" + leftJabForce
                + ", leftJabTotal=" + leftJabTotal + ", leftStraightSpeed="
                + leftStraightSpeed + ", leftStraightForce="
                + leftStraightForce + ", leftStraightTotal="
                + leftStraightTotal + ", leftHookSpeed=" + leftHookSpeed
                + ", leftHookForce=" + leftHookForce + ", leftHookTotal="
                + leftHookTotal + ", leftUppercutSpeed=" + leftUppercutSpeed
                + ", leftUppercutForce=" + leftUppercutForce
                + ", leftUppercutTotal=" + leftUppercutTotal
                + ", leftUnrecognizedSpeed=" + leftUnrecognizedSpeed
                + ", leftUnrecognizedForce=" + leftUnrecognizedForce
                + ", leftUnrecognizedTotal=" + leftUnrecognizedTotal
                + ", rightJabSpeed=" + rightJabSpeed + ", rightJabForce="
                + rightJabForce + ", rightJabTotal=" + rightJabTotal
                + ", rightStraightSpeed=" + rightStraightSpeed
                + ", rightStraightForce=" + rightStraightForce
                + ", rightStraightTotal=" + rightStraightTotal
                + ", rightHookSpeed=" + rightHookSpeed + ", rightHookForce="
                + rightHookForce + ", rightHookTotal=" + rightHookTotal
                + ", rightUppercutSpeed=" + rightUppercutSpeed
                + ", rightUppercutForce=" + rightUppercutForce
                + ", rightUppercutTotal=" + rightUppercutTotal
                + ", rightUnrecognizedSpeed=" + rightUnrecognizedSpeed
                + ", rightUnrecognizedForce=" + rightUnrecognizedForce
                + ", rightUnrecognizedTotal=" + rightUnrecognizedTotal + "]";
    }
}
