package com.efd.striketectablet.DTO;

public class TrainingStatsPunchTypeInfoDTO {

    public String punchtype;
    public double avgspeed;
    public double avgforce;
    public double minspeed;
    public double maxspeed;
    public double minforce;
    public double maxforce;
    public int punchcount;
    public double totaltime;

    public TrainingStatsPunchTypeInfoDTO(String punchtype, Double avgspeed, Double avgforce, Double maxspeed, Double minspeed, Double maxforce, Double minforce, int punchcount, Double totaltime ) {
        super();
        this.punchtype = punchtype;
        this.avgspeed = avgspeed;
        this.avgforce = avgforce;
        this.maxspeed = maxspeed;
        this.minspeed = minspeed;
        this.maxforce = maxforce;
        this.minforce = minforce;
        this.punchcount = punchcount;
        this.totaltime = totaltime;
    }

    public TrainingStatsPunchTypeInfoDTO(){

    }

    public String toString() {
        return "Punch Info [type=" + punchtype + ", avgspeed=" + avgspeed + ", avgforce=" + avgforce + ", punchcount=" + punchcount + ", totaltime=" + totaltime + "]";
    }
}
