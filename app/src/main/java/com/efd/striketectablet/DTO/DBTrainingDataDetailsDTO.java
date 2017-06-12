package com.efd.striketectablet.DTO;

public class DBTrainingDataDetailsDTO {

    Integer id;
    Integer ax;
    Integer ay;
    Integer az;
    Double currentForce;
    Double currentVelocity;
    String dataReceivedTime;
    Integer gx;
    Integer gy;
    Integer gz;
    Double headTrauma;
    Double milliSeconds;
    Integer temp;
    Integer trainingDataId;
    Integer sync;
    String syncDate;
    Long serverID;


    public DBTrainingDataDetailsDTO(Integer id, Integer ax, Integer ay, Integer az,
                                    Double currentForce, Double currentVelocity,
                                    String dataReceivedTime, Integer gx, Integer gy, Integer gz, Double headTrauma,
                                    Double milliSeconds, Integer temp, Integer trainingDataId, Integer sync,
                                    String syncDate, Long serverID) {
        super();
        this.id = id;
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.currentForce = currentForce;
        this.currentVelocity = currentVelocity;
        this.dataReceivedTime = dataReceivedTime;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
        this.headTrauma = headTrauma;
        this.milliSeconds = milliSeconds;
        this.temp = temp;
        this.trainingDataId = trainingDataId;
        this.sync = sync;
        this.syncDate = syncDate;
        this.serverID = serverID;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAx() {
        return ax;
    }

    public Integer getAy() {
        return ay;
    }

    public Integer getAz() {
        return az;
    }

    public Double getCurrentForce() {
        return currentForce;
    }

    public Double getCurrentVelocity() {
        return currentVelocity;
    }

    public String getDataReceivedTime() {
        return dataReceivedTime;
    }

    public Integer getGx() {
        return gx;
    }

    public Integer getGy() {
        return gy;
    }

    public Integer getGz() {
        return gz;
    }

    public Double getHeadTrauma() {
        return headTrauma;
    }

    public Double getMilliSeconds() {
        return milliSeconds;
    }

    public Integer getTemp() {
        return temp;
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
        return "DBTrainingDataDetailsDTO [id=" + id + ", ax=" + ax + ", ay="
                + ay + ", az=" + az + ", currentForce=" + currentForce
                + ", currentVelocity=" + currentVelocity
                + ", dataReceivedTime=" + dataReceivedTime + ", gx=" + gx
                + ", gy=" + gy + ", gz=" + gz + ", headTrauma=" + headTrauma
                + ", milliSeconds=" + milliSeconds + ", temp=" + temp
                + ", trainingDataId=" + trainingDataId + ", sync=" + sync
                + ", syncDate=" + syncDate + ", serverID=" + serverID + "]";
    }


}
