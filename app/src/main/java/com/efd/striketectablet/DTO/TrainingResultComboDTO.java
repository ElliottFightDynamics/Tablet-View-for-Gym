package com.efd.striketectablet.DTO;

import java.util.ArrayList;

public class TrainingResultComboDTO {

    private String comboname;
    private ArrayList<TrainingResultPunchDTO> punches;

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



    public TrainingResultComboDTO(String comboname, ArrayList<TrainingResultPunchDTO> punches) {
        super();
        this.comboname = comboname;
        this.punches = punches;
    }

    public TrainingResultComboDTO(){
    }
}
