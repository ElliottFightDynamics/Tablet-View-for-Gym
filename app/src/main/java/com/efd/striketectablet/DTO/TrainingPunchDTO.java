package com.efd.striketectablet.DTO;

public class TrainingPunchDTO {

    private String punchtype;
    private long speed;
    private long force;
    private double punchtime;

    public String getPunchtype(){
        return punchtype;
    }

    public void setPunchtype(String punchtype){
        this.punchtype = punchtype;
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

    public double getPunchtime(){
        return punchtime;
    }

    public void setPunchtime(double punchtime){
        this.punchtime = punchtime;
    }


    public TrainingPunchDTO(String punchtype, long speed, long force, double punchtime ) {
        super();
        this.punchtype = punchtype;
        this.speed = speed;
        this.force = force;
        this.punchtime = punchtime;
    }

    public TrainingPunchDTO(){

    }

    public String toString() {
        return "Punch Info [type=" + punchtype + ", speed=" + speed + ", force=" + force + ", punchtime=" + punchtime  + "]";
    }
}
