package com.efd.striketectablet.DTO;

import java.io.Serializable;

public class PunchDTO implements Serializable{
    private int speed;
    private int power;
    private int time;

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public PunchDTO(int speed, int power, int time) {
        super();
        this.speed = speed;
        this.power = power;
        this.time = time;
    }

    public PunchDTO(){

    }
}
