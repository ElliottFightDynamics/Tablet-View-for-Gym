package com.efd.striketectablet.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class SetsDTO implements Serializable{
    private String name;
    private ArrayList<ComboDTO> comboslists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  ArrayList<ComboDTO> getComboslists() {
        return comboslists;
    }

    public void setComboslists( ArrayList<ComboDTO> comboslists) {
        this.comboslists = comboslists;
    }

    public SetsDTO(String name, ArrayList<ComboDTO> comboslists) {
        super();
        this.name = name;
        this.comboslists = comboslists;
    }

    public SetsDTO(){

    }
}
