package com.efd.striketectablet.activity.trainingstats.trainingProgress;

/**
 * Created by omnic on 7/31/2016.
 */
public class MonthVM {
    private String displayName;
    private int index;

    public MonthVM(String displayName, int index) {
        this.displayName = displayName;
        this.index = index;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o!=null && o.getClass().equals(Integer.class)){
            return this.index == (int) o;
        }
        if (o == null || getClass() != o.getClass()) return false;

        MonthVM monthVM = (MonthVM) o;

        if (index != monthVM.index) return false;
        return displayName != null ? displayName.equals(monthVM.displayName) : monthVM.displayName == null;

    }

    @Override
    public int hashCode() {
        int result = displayName != null ? displayName.hashCode() : 0;
        result = 31 * result + index;
        return result;
    }
}
