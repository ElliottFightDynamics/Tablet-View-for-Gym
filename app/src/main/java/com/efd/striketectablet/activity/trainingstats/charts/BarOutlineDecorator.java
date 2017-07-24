package com.efd.striketectablet.activity.trainingstats.charts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.TrainingDataSeries;

/**
 * Created by omnic on 8/6/2016.
 */
public class BarOutlineDecorator implements IChartDecorator {

    private final Paint highlightPaint;
    private final int highlightSize;

    public BarOutlineDecorator() {
        this.highlightSize = 4;
        this.highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.highlightPaint.setColor(Color.rgb(255, 255, 255));
    }

    public void setColor(int color) {
        this.highlightPaint.setColor(color);
    }

    @Override
    public void preDecorate(Canvas canvas, TrainingDataSeries dataSet, TrainingProgressBarchartSettings settings) {

    }

    @Override
    public void postDecorate(Canvas canvas, TrainingDataSeries dataSet, TrainingProgressBarchartSettings settings) {
        int height = canvas.getHeight();
        for (int itemNumber = 0; itemNumber < dataSet.size(); itemNumber++) {
            IBarchartValue value = dataSet.get(itemNumber);
            if (value.shouldHighlight()) {
                float startX = settings.getXForItemNumber(itemNumber);
                float endX = startX + settings.getItemWidth();

                //left
                canvas.drawRect(startX, 0, startX + highlightSize, height, highlightPaint);
                //right
                canvas.drawRect(endX - highlightSize, 0, endX, height, highlightPaint);
                //top
                canvas.drawRect(startX, 0, endX, highlightSize, highlightPaint);
                //bottom
                canvas.drawRect(startX, height - highlightSize, endX, height, highlightPaint);

            }
        }
    }

}
