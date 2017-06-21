package com.efd.striketectablet.DTO;

import java.util.ArrayList;

public class TrainingResultSetDTO {
    private String time;
    private String setname;
    private ArrayList<TrainingResultComboDTO> combos;

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }


    public String getSetname(){
        return setname;
    }

    public void setSetname(String setname){
        this.setname = setname;
    }

    public ArrayList<TrainingResultComboDTO> getCombos(){
        return combos;
    }

    public void setCombos(ArrayList<TrainingResultComboDTO> combos){
        this.combos = combos;
    }


    public TrainingResultSetDTO(String setname, ArrayList<TrainingResultComboDTO> combos, String time) {
        super();
        this.setname = setname;
        this.combos = combos;
        this.time = time;
    }

    public TrainingResultSetDTO(){
    }
}
