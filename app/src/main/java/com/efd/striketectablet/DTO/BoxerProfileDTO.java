package com.efd.striketectablet.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class BoxerProfileDTO implements Parcelable {

    private int chest;
    private int inseam;
    private int reach;
    private String stance;
    private int waist;
    private int weight;
    private int height;

    private String leftDevice;
    private String rightDevice;
    private String gloveType;
    private String skillLevel;

    public BoxerProfileDTO() {}

    protected BoxerProfileDTO(Parcel in) {
        this.chest = in.readInt();
        this.inseam = in.readInt();
        this.reach = in.readInt();
        this.stance = in.readString();

        this.waist = in.readInt();
        this.weight = in.readInt();
        this.height = in.readInt();
        this.leftDevice = in.readString();
        this.rightDevice = in.readString();
        this.gloveType = in.readString();
        this.skillLevel = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chest);
        dest.writeInt(inseam);
        dest.writeInt(reach);
        dest.writeString(this.stance);
        dest.writeInt(waist);
        dest.writeInt(weight);
        dest.writeInt(height);
        dest.writeString(this.leftDevice);
        dest.writeString(this.rightDevice);
        dest.writeString(this.gloveType);
        dest.writeString(this.skillLevel);
    }

    public int getChest(){
        return chest;
    }

    public void setChest(int chest){
        this.chest = chest;
    }

    public int getInseam(){
        return inseam;
    }

    public void setInseam(int inseam){
        this.inseam = inseam;
    }

    public int getReach(){
        return reach;
    }

    public void setReach(int reach){
        this.reach = reach;
    }

    public String getStance(){
        return stance;
    }

    public void setStance(String stance){
        this.stance = stance;
    }

    public int getWaist(){
        return waist;
    }

    public void setWaist(int waist){
        this.waist = waist;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public String getLeftDevice(){
        return leftDevice;
    }

    public void setLeftDevice(String leftDevice){
        this.leftDevice = leftDevice;
    }

    public String getRightDevice(){
        return rightDevice;
    }

    public void setRightDevice(String rightDevice){
        this.rightDevice = rightDevice;
    }

    public String getGloveType(){
        return gloveType;
    }

    public void setGloveType(String gloveType){
        this.gloveType = gloveType;
    }

    public String getSkillLevel(){
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel){
        this.skillLevel = skillLevel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BoxerProfileDTO> CREATOR = new Creator<BoxerProfileDTO>() {
        @Override
        public BoxerProfileDTO createFromParcel(Parcel source) {
            return new BoxerProfileDTO(source);
        }

        @Override
        public BoxerProfileDTO[] newArray(int size) {
            return new BoxerProfileDTO[size];
        }
    };
}
