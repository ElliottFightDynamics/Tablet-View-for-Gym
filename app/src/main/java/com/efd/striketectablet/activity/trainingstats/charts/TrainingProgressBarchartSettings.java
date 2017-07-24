package com.efd.striketectablet.activity.trainingstats.charts;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by omnic on 7/31/2016.
 */
public class TrainingProgressBarchartSettings {

    private final Paint labelBackgroundPaint;
    private final Paint barPaint;
    private final Paint barSecondaryPaint;
    private final Paint labelPaint;

    private float barWidth = 10;
    private float labelWidth = 10;
    private float itemWidth = 10;
    private float barPadding = 5;

    private float paddingTop = 10f;
    private float paddingLeft = 10f;
    private float paddingRight = 10f;
    private float paddingBottom = 10f;

    private float manualVerticalScale = 0.9f;

    public TrainingProgressBarchartSettings() {
        this.barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.barPaint.setColor(Color.BLUE);
        this.barSecondaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.barSecondaryPaint.setColor(Color.argb(128, 0, 0, 255));

        this.labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.labelPaint.setColor(Color.WHITE);
        this.labelPaint.setTextSize(28);

        this.labelBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.labelBackgroundPaint.setColor(Color.argb(128, 255, 255, 255));
    }

    public float getManualVerticalScale() {
        return manualVerticalScale;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public float getItemPadding() {
        return barPadding;
    }

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public float getPaddingTop() {
        return paddingTop;
    }

    public float getPaddingBottom() {
        return paddingBottom;
    }

    public Paint getBarSecondaryPaint() {
        return barSecondaryPaint;
    }

    public Paint getBarPaint() {
        return barPaint;
    }

    public Paint getLabelPaint() {
        return labelPaint;
    }


    public void setBarColor(int color) {
        this.barPaint.setColor(color);
        this.barSecondaryPaint.setColor(setColorAlpha(color, 100));
    }


    public void setLabelColor(int color) {
        this.labelPaint.setColor(color);
        this.labelBackgroundPaint.setColor(setColorAlpha(color, 100));
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
    }

    public void setBarPadding(int barPadding) {
        this.barPadding = barPadding;
    }

    public void setManualVerticalScale(float manualVerticalScale) {
        this.manualVerticalScale = manualVerticalScale;
    }

    private static int setColorAlpha(int color, int alpha) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }


    public Paint getLabelBackgroundPaint() {
        return labelBackgroundPaint;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public float getXForItemNumber(int itemNumber) {
        return itemNumber * this.getItemWidth() + itemNumber * this.getItemPadding();
    }

    public int getItemNumberForX(float x){
        return (int) Math.floor(x / (getItemWidth() + getItemPadding()));
    }

    public void setLabelWidth(float labelWidth) {
        this.labelWidth = labelWidth;
    }

    public float getLabelWidth() {
        return this.labelWidth;
    }


}
