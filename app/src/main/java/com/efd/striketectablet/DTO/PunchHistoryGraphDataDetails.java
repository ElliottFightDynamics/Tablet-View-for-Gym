package com.efd.striketectablet.DTO;

public class PunchHistoryGraphDataDetails {
    public String boxersHand;
    public String punchType;
    public String punchForce;
    public String punchSpeed;

    public PunchHistoryGraphDataDetails(String boxersHand, String punchType, String punchForce, String punchSpeed) {
        this.boxersHand = boxersHand;
        this.punchType = punchType;
        this.punchForce = punchForce;
        this.punchSpeed = punchSpeed;
    }

    @Override
    public String toString() {
        return ("boxersHand: " + boxersHand + ", punchType: " + punchType + ", punchForce: " + punchForce + ", punchSpeed: " + punchSpeed);
    }
}
