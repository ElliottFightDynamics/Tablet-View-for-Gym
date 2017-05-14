package com.efd.striketectablet.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class PunchesRankDTO implements Serializable{
    public String punchtype;
    public double avg_speed;
    public double avg_force;
    public int punch_count;

    public PunchesRankDTO(String punchtype, double avg_speed, double avg_force, int punch_count) {
        super();
        this.punchtype = punchtype;
        this.avg_speed = avg_speed;
        this.avg_force = avg_force;
        this.punch_count = punch_count;
    }

    public PunchesRankDTO(){

    }
}
