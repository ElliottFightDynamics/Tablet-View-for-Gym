package com.efd.striketectablet.DTO;

import java.io.Serializable;

public class PresetDTO implements Serializable{
    private String name;
    private int rounds;
    private int round_time;
    private int rest;
    private int prepare;
    private int warning;
    private int weight;
    private int glove;
    private int gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getRound_time() {
        return round_time;
    }

    public void setRound_time(int round_time) {
        this.round_time = round_time;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getPrepare() {
        return prepare;
    }

    public void setPrepare(int prepare) {
        this.prepare = prepare;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public int getGlove() {
        return glove;
    }

    public void setGlove(int glove) {
        this.glove = glove;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public PresetDTO (String name, int rounds, int round_time, int rest, int prepare, int warning, int weight, int glove, int gender ) {
        super();
        this.name = name;
        this.rounds = rounds;
        this.round_time = round_time;
        this.rest = rest;
        this.prepare = prepare;
        this.warning = warning;
        this.weight = weight;
        this.glove = glove;
        this.gender = gender;
    }

    public PresetDTO(){

    }
}
