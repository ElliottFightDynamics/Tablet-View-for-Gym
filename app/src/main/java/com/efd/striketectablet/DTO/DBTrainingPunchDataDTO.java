package com.efd.striketectablet.DTO;

public class DBTrainingPunchDataDTO {

    Integer id;
    Double maxForce;
    Double maxSpeed;
    String punchDataDate;
    String punchType;
    Integer trainingDataId;
    Integer sync;
    String syncDate;
    Long serverID;



    public DBTrainingPunchDataDTO(Integer id, Double maxForce, Double maxSpeed,
                                  String punchDataDate, String punchType, Integer trainingDataId,
                                  Integer sync, String syncDate, Long serverID) {
        super();
        this.id = id;
        this.maxForce = maxForce;
        this.maxSpeed = maxSpeed;
        this.punchDataDate = punchDataDate;
        this.punchType = punchType;
        this.trainingDataId = trainingDataId;
        this.sync = sync;
        this.syncDate = syncDate;
        this.serverID = serverID;
    }

    public Integer getId() {
        return id;
    }

    public Double getMaxForce() {
        return maxForce;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public String getPunchDataDate() {
        return punchDataDate;
    }

    public String getPunchType() {
        return punchType;
    }

    public Integer getTrainingDataId() {
        return trainingDataId;
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
        return "DBTrainingPunchDataDTO [id=" + id + ", maxForce=" + maxForce
                + ", maxSpeed=" + maxSpeed + ", punchDataDate=" + punchDataDate
                + ", punchType=" + punchType + ", trainingDataId="
                + trainingDataId + ", sync=" + sync + ", syncDate=" + syncDate
                + ", serverID=" + serverID + "]";
    }

}
