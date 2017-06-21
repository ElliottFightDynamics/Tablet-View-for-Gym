package com.efd.striketectablet.DTO;

import java.util.ArrayList;

public class TrainingResultWorkoutDTO {


    private String time;
    private String workoutname;
    private ArrayList<TrainingResultSetDTO> roundcombos;

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }


    public String getWorkoutname(){
        return workoutname;
    }

    public void setWorkoutname(String setname){
        this.workoutname = workoutname;
    }

    public ArrayList<TrainingResultSetDTO> getRoundcombos(){
        return roundcombos;
    }

    public void setRoundcombos(ArrayList<TrainingResultSetDTO> roundcombos){
        this.roundcombos = roundcombos;
    }

    public TrainingResultWorkoutDTO(String workoutname, ArrayList<TrainingResultSetDTO> roundcombos, String time) {
        super();
        this.workoutname = workoutname;
        this.roundcombos = roundcombos;
        this.time = time;
    }

    public TrainingResultWorkoutDTO(){
    }
}
