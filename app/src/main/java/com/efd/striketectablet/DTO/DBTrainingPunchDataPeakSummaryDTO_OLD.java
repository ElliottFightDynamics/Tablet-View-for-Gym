package com.efd.striketectablet.DTO;

public class DBTrainingPunchDataPeakSummaryDTO_OLD {

    Integer id;
    Integer hook;
    Integer jab;
    String punchDataPeakSummaryDate;
    Integer speedFlag;
    Integer straight;
    Integer trainingDataId;
    Integer uppercut;
    Integer sync;
    String syncDate;
    Long serverID;

    public DBTrainingPunchDataPeakSummaryDTO_OLD(Integer id, Integer hook, Integer jab,
                                                 String punchDataPeakSummaryDate, Integer speedFlag, Integer straight, Integer trainingDataId,
                                                 Integer uppercut, Integer sync, String syncDate, Long serverID) {
        super();
        this.id = id;
        this.hook = hook;
        this.jab = jab;
        this.punchDataPeakSummaryDate = punchDataPeakSummaryDate;
        this.speedFlag = speedFlag;
        this.straight = straight;
        this.trainingDataId = trainingDataId;
        this.uppercut = uppercut;
        this.sync = sync;
        this.syncDate = syncDate;
        this.serverID = serverID;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHook() {
        return hook;
    }

    public Integer getJab() {
        return jab;
    }

    public String getPunchDataPeakSummaryDate() {
        return punchDataPeakSummaryDate;
    }

    public Integer getSpeedFlag() {
        return speedFlag;
    }

    public Integer getStraight() {
        return straight;
    }

    public Integer getTrainingDataId() {
        return trainingDataId;
    }

    public Integer getUppercut() {
        return uppercut;
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
        return "DBTrainingPunchDataPeakSummary [id=" + id + ", hook=" + hook
                + ", jab=" + jab + ", punchDataPeakSummaryDate=" + punchDataPeakSummaryDate
                + ", speedFlag=" + speedFlag + ", straight=" + straight
                + ", trainingDataId=" + trainingDataId + ", uppercut="
                + uppercut + ", sync=" + sync + ", syncDate=" + syncDate
                + ", serverID=" + serverID + "]";
    }

}
