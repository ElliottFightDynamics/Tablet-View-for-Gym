package com.efd.striketectablet.database;

/**
 * Created by omnic on 8/30/2016.
 */
public class RoundDto {
    private final int userId;
    private int sessionId;
    private final int totalPunches;
    private final float maxForce;
    private final float maxSpeed;
    private final float averageForce;
    private final float averageSpeed;
    private final int elapsedSeconds;

    public RoundDto(int userId, int sessionId, int totalPunches, float maxForce, float maxSpeed, float averageForce, float averageSpeed, int elapsedSeconds) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.totalPunches = totalPunches;
        this.maxForce = maxForce;
        this.maxSpeed = maxSpeed;
        this.averageForce = averageForce;
        this.averageSpeed = averageSpeed;
        this.elapsedSeconds = elapsedSeconds;
    }

    public int getUserId() {
        return userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getTotalPunches() {
        return totalPunches;
    }

    public float getMaxForce() {
        return maxForce;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getAverageForce() {
        return averageForce;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }
}
