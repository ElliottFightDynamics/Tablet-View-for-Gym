package com.efd.striketectablet.DTO;

public class TrainingResultPunchDTO {

    private String punchtype;
    private String punchKey;
    private long speed;
    private long force;
    private boolean success;

    public String getPunchtype(){
        return punchtype;
    }

    public void setPunchtype(String punchtype){
        this.punchtype = punchtype;
    }

    public String getPunchKey(){
        return punchKey;
    }

    public void setPunchKey(String punchKey){
        this.punchKey = punchKey;
    }

    public long getSpeed(){
        return speed;
    }

    public void setSpeed(long speed){
        this.speed = speed;
    }

    public long getForce(){
        return force;
    }

    public void setForce(long force){
        this.force = force;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public TrainingResultPunchDTO(String punchtype, String punchKey, long speed, long force, boolean success) {
        super();
        this.punchtype = punchtype;
        this.punchKey = punchKey;
        this.speed = speed;
        this.force = force;
        this.success = success;
    }

    public TrainingResultPunchDTO(){

    }

    public String toString() {
        return "Punch Info [type=" + punchtype + ", speed=" + speed + ", force=" + force + ", success=" + success  + "]";
    }
}
