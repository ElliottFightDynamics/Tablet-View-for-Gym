package com.efd.striketectablet.DTO;

public class BoxerProfileDTO {
    private int boxerID;
    private int boxerVersion;
    private int chest;
    private int inseam;
    private String leftDevice;
    private int reach;
    private String rightDevice;
    private String stance;
    private int userId;
    private int waist;
    private int weight;
    private String gloveType;
    private String skillLevel;
    private int height;

    public int getBoxerID() {
        return boxerID;
    }

    public void setBoxerID(int boxerID) {
        this.boxerID = boxerID;
    }

    public int getBoxerVersion() {
        return boxerVersion;
    }

    public void setBoxerVersion(int boxerVersion) {
        this.boxerVersion = boxerVersion;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getInseam() {
        return inseam;
    }

    public void setInseam(int inseam) {
        this.inseam = inseam;
    }

    public String getLeftDevice() {
        return leftDevice;
    }

    public void setLeftDevice(String leftDevice) {
        this.leftDevice = leftDevice;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public String getRightDevice() {
        return rightDevice;
    }

    public void setRightDevice(String rightDevice) {
        this.rightDevice = rightDevice;
    }

    public String getStance() {
        return stance;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGloveType() {
        return gloveType;
    }

    public void setGloveType(String gloveType) {
        this.gloveType = gloveType;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BoxerProfileDTO(int boxerID, int boxerVersion, int chest,
                           int inseam, String leftDevice, int reach, String rightDevice,
                           String stance, int userId, int waist, int weight, String gloveType,
                           String skillLevel, int height) {
        super();
        this.boxerID = boxerID;
        this.boxerVersion = boxerVersion;
        this.chest = chest;
        this.inseam = inseam;
        this.leftDevice = leftDevice;
        this.reach = reach;
        this.rightDevice = rightDevice;
        this.stance = stance;
        this.userId = userId;
        this.waist = waist;
        this.weight = weight;
        this.gloveType = gloveType;
        this.skillLevel = skillLevel;
        this.height = height;
    }

    @Override
    public String toString() {
        return "BoxerProfileDTO [boxerID=" + boxerID + ", boxerVersion="
                + boxerVersion + ", chest=" + chest + ", inseam=" + inseam
                + ", leftDevice=" + leftDevice + ", reach=" + reach
                + ", rightDevice=" + rightDevice + ", stance=" + stance
                + ", userId=" + userId + ", waist=" + waist + ", weight="
                + weight + ", gloveType=" + gloveType + ", skillLevel="
                + skillLevel + ", height=" + height + "]";
    }
}
