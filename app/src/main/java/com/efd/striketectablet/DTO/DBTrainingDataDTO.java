package com.efd.striketectablet.DTO;

public class DBTrainingDataDTO {

    Integer id;
    Integer isLeftHand;
    Integer sessionID;
    Integer userID;
    Integer sync;
    String syncDate;
    Long serverID;



    public DBTrainingDataDTO(Integer id, Integer isLeftHand, Integer sessionID, Integer userID,
                             Integer sync, String syncDate, Long serverID) {
        super();
        this.id = id;
        this.isLeftHand = isLeftHand;
        this.sessionID = sessionID;
        this.userID = userID;
        this.sync = sync;
        this.syncDate = syncDate;
        this.serverID = serverID;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIsLeftHand() {
        return isLeftHand;
    }

    public Integer getSessionID() {
        return sessionID;
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
        return "DBTrainingDataDTO [id=" + id + ", isLeftHand=" + isLeftHand
                + ", sessionID=" + sessionID + ", userID=" + userID + ", sync="
                + sync + ", syncDate=" + syncDate + ", serverID=" + serverID
                + "]";
    }

}
