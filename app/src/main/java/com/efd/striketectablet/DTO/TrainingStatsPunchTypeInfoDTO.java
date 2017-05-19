package com.efd.striketectablet.DTO;

public class TrainingStatsPunchTypeInfoDTO {

    public String punchtype;
    public double avgspeed;
    public double avgforce;
    public int punchcount;
    public double totaltime;

    public TrainingStatsPunchTypeInfoDTO(String punchtype, Double avgspeed, Double avgforce, int punchcount, Double totaltime ) {
        super();
        this.punchtype = punchtype;
        this.avgspeed = avgspeed;
        this.avgforce = avgforce;
        this.punchcount = punchcount;
        this.totaltime = totaltime;
    }

    public TrainingStatsPunchTypeInfoDTO(){

    }

    public String toString() {
        return "Punch Info [type=" + punchtype + ", avgspeed=" + avgspeed + ", avgforce=" + avgforce + ", punchcount=" + punchcount + ", totaltime=" + totaltime + "]";
    }
}
