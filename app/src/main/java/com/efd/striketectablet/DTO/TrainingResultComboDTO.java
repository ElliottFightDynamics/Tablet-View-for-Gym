package com.efd.striketectablet.DTO;

import java.util.ArrayList;

public class TrainingResultComboDTO {

    private String time;
    private String comboname;
    private ArrayList<TrainingResultPunchDTO> punches;

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getComboname(){
        return comboname;
    }

    public void setComboname(String comboname){
        this.comboname = comboname;
    }

    public ArrayList<TrainingResultPunchDTO> getPunches(){
        return punches;
    }

    public void setPunches(ArrayList<TrainingResultPunchDTO> punches){
        this.punches = punches;
    }



    public TrainingResultComboDTO(String comboname, ArrayList<TrainingResultPunchDTO> punches, String time) {
        super();
        this.comboname = comboname;
        this.punches = punches;
        this.time = time;
    }

    public TrainingResultComboDTO(){
    }
}
