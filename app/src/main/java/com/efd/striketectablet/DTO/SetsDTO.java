package com.efd.striketectablet.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class SetsDTO implements Serializable{
    private String name;
    private ArrayList<Integer> comboPositionlists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  ArrayList<Integer> getComboPositionlists() {
        return comboPositionlists;
    }

    public void setComboPositionlists( ArrayList<Integer> comboPositionlists) {
        this.comboPositionlists = comboPositionlists;
    }

    public SetsDTO(String name, ArrayList<Integer> comboPositionlists) {
        super();
        this.name = name;
        this.comboPositionlists = comboPositionlists;
    }

    public SetsDTO(){

    }
}
