/**
 *
 */
package com.efd.striketectablet.DTO;

/**
 * @author #1053
 */
public class PunchDataDTO {

    private Integer jabCount;
    private Integer straightCount;

    private Integer leftHookCount;
    private Integer rightHookCount;

    private Integer leftUppercutCount;
    private Integer rightUppercutCount;

    private Integer leftUnrecognizedCount;
    private Integer rightUnrecognizedCount;

    private Integer totalPunchCount;

    public PunchDataDTO() {
        super();
        init();
    }

    private void init() {
        resetValues(0);
    }

    public PunchDataDTO(Integer jabCount, Integer straightCount,
                        Integer leftHookCount, Integer rightHookCount,
                        Integer leftUppercutCount, Integer rightUppercutCount,
                        Integer leftUnrecognizedCount, Integer rightUnrecognizedCount,
                        Integer totalPunchCount) {
        super();
        this.jabCount = jabCount;
        this.straightCount = straightCount;
        this.leftHookCount = leftHookCount;
        this.rightHookCount = rightHookCount;
        this.leftUppercutCount = leftUppercutCount;
        this.rightUppercutCount = rightUppercutCount;
        this.leftUnrecognizedCount = leftUnrecognizedCount;
        this.rightUnrecognizedCount = rightUnrecognizedCount;
        this.totalPunchCount = totalPunchCount;
    }

    public Integer getJabCount() {
        return jabCount;
    }

    public void setJabCount(Integer jabCount) {
        this.jabCount = jabCount;
    }

    public Integer getStraightCount() {
        return (straightCount == null ? 0 : straightCount);
    }

    public void setStraightCount(Integer straightCount) {
        this.straightCount = straightCount;
    }

    public Integer getLeftHookCount() {
        return leftHookCount;
    }

    public void setLeftHookCount(Integer leftHookCount) {
        this.leftHookCount = leftHookCount;
    }

    public Integer getRightHookCount() {
        return rightHookCount;
    }

    public void setRightHookCount(Integer rightHookCount) {
        this.rightHookCount = rightHookCount;
    }

    public Integer getLeftUppercutCount() {
        return leftUppercutCount;
    }

    public void setLeftUppercutCount(Integer leftUppercutCount) {
        this.leftUppercutCount = leftUppercutCount;
    }

    public Integer getRightUppercutCount() {
        return rightUppercutCount;
    }

    public void setRightUppercutCount(Integer rightUppercutCount) {
        this.rightUppercutCount = rightUppercutCount;
    }

    public Integer getLeftUnrecognizedCount() {
        return leftUnrecognizedCount;
    }

    public void setLeftUnrecognizedCount(Integer leftUnrecognizedCount) {
        this.leftUnrecognizedCount = leftUnrecognizedCount;
    }

    public Integer getRightUnrecognizedCount() {
        return rightUnrecognizedCount;
    }

    public void setRightUnrecognizedCount(Integer rightUnrecognizedCount) {
        this.rightUnrecognizedCount = rightUnrecognizedCount;
    }

    public Integer getTotalPunchCount() {
        return totalPunchCount;
    }

    public void setTotalPunchCount(Integer totalPunchCount) {
        this.totalPunchCount = totalPunchCount;
    }

    /**
     * Reset all the values to 0 or null
     *
     * @param value
     */
    public void resetValues(Integer value) {
        setJabCount(value);
        setStraightCount(value);
        setLeftHookCount(value);
        setRightHookCount(value);
        setLeftUppercutCount(value);
        setRightUppercutCount(value);
        setLeftUnrecognizedCount(value);
        setRightUnrecognizedCount(value);
        setTotalPunchCount(value);
    }

    @Override
    public String toString() {
        return "PunchDataDTO [jabCount=" + jabCount + ", straightCount="
                + straightCount + ", leftHookCount=" + leftHookCount
                + ", rightHookCount=" + rightHookCount + ", leftUppercutCount="
                + leftUppercutCount + ", rightUppercutCount="
                + rightUppercutCount + ", leftUnrecognizedCount="
                + leftUnrecognizedCount + ", rightUnrecognizedCount="
                + rightUnrecognizedCount + ", totalPunchCount="
                + totalPunchCount + "]";
    }
}
