package com.efd.striketectablet.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkoutDTO implements Serializable{
    private int id;
    private String name;
    private int roundcount;
    private ArrayList<ArrayList<Integer>> roundsetIDs;   //this is array of set id
    private int round;
    private int rest;
    private int prepare;
    private int warning;
    private int glove;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getRoundcount(){
        return roundcount;
    }

    public void setRoundcount(int roundcount){
        this.roundcount = roundcount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRound(){
        return round;
    }

    public void setRound(int round){
        this.round = round;
    }

    public int getRest(){
        return rest;
    }

    public void setRest(int rest){
        this.rest = rest;
    }

    public int getPrepare(){
        return prepare;
    }

    public void setPrepare(int prepare){
        this.prepare = prepare;
    }

    public int getWarning(){
        return warning;
    }

    public void setWarning(int warning){
        this.warning = warning;
    }

    public int getGlove(){
        return glove;
    }

    public void setGlove(int glove){
        this.glove = glove;
    }

    public  ArrayList<ArrayList<Integer>> getRoundsetIDs() {
        return roundsetIDs;
    }

    public void setRoundsetIDs(ArrayList<ArrayList<Integer>>  roundsetIDs) {
        this.roundsetIDs = roundsetIDs;
    }

    public WorkoutDTO(int id, String name, int roundcount, ArrayList<ArrayList<Integer>> roundsetIDs, int round, int rest, int prepare, int warning, int glove) {
        super();
        this.id = id;
        this.name = name;
        this.roundcount = roundcount;
        this.roundsetIDs = roundsetIDs;
        this.round = round;
        this.rest = rest;
        this.prepare = prepare;
        this.warning = warning;
        this.glove = glove;
    }

    public WorkoutDTO(){

    }
}
