package com.efd.striketectablet.DTO;

public class DBTrainingSessionDTO_OLD {

    private Integer id;
    private String startTime;
    private String endTime;
    private String trainingSessionDate;
    private String trainingType;
    /**
     * Represents server user id
     */
    private Integer userID;
    private Integer sync;
    private String syncDate;
    private Long serverID;

    public DBTrainingSessionDTO_OLD(Integer id, String startTime, String endTime,
                                    String date, String trainingType, Integer userID, Integer sync,
                                    String syncDate, Long serverID) {
        super();
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainingSessionDate = date;
        this.trainingType = trainingType;
        this.userID = userID;
        this.sync = sync;
        this.syncDate = syncDate;
        this.serverID = serverID;
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

    public Integer getSync() {
        return sync;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public Long getServerID() {
        return serverID;
    }

    @Override
    public String toString() {
        return "DBTrainingSessionDTO_OLD [id=" + id + ", startTime=" + startTime
                + ", endTime=" + endTime + ", trainingSessionDate=" + trainingSessionDate + ", trainingType="
                + trainingType + ", userID=" + userID + ", sync=" + sync
                + ", syncDate=" + syncDate + ", serverID=" + serverID + "]";
    }
}
