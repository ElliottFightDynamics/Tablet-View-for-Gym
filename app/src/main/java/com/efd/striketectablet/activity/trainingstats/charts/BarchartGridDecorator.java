package com.efd.striketectablet.activity.trainingstats.charts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.efd.striketectablet.activity.trainingstats.charts.stats.TrainingDataSeries;

/**
 * Created by omnic on 8/6/2016.
 */
public class BarchartGridDecorator implements IChartDecorator {

    private final boolean shouldDrawHorizontal;
    private final boolean shouldDrawVertical;
    private final int gridLines;
    private final Paint gridPaint;
    private int lineSize;
    private boolean isInBackground;
    private int topPadding;

    public static BarchartGridDecorator horizontal(int gridLines) {
        return new BarchartGridDecorator(true, false, gridLines);
    }

    private BarchartGridDecorator(boolean horizontal, boolean vertical, int gridLines) {
        this.shouldDrawHorizontal = horizontal;
        this.shouldDrawVertical = vertical;
        this.gridLines = gridLines;
        this.isInBackground = true;
        this.gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.lineSize = 1;
        this.gridPaint.setColor(Color.argb(128, 64, 64, 200));
    }

    public void setGridColor(int color) {
        this.gridPaint.setColor(color);
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public void setTopPadding(int padding) {
        this.topPadding = padding;
    }

    @Override
    public void preDecorate(Canvas canvas, TrainingDataSeries dataSet, TrainingProgressBarchartSettings settings) {
        if (isInBackground) {
            drawGrid(canvas, settings.getManualVerticalScale());
        }
    }

    @Override
    public void postDecorate(Canvas canvas, TrainingDataSeries dataSet, TrainingProgressBarchartSettings settings) {
        if (!isInBackground) {
            drawGrid(canvas, settings.getManualVerticalScale());
        }
    }

    private void drawGrid(Canvas canvas, float verticalScale) {
        float width = canvas.getWidth();
        float height = (canvas.getHeight() * verticalScale) - topPadding;

        float horizontalIncrement = width / gridLines;
        float verticalIncrement = height / gridLines;

        if (shouldDrawVertical) {
            for (int x = 0; x <= width; x += horizontalIncrement) {
                canvas.drawRect(x, 0, x + lineSize, height, gridPaint);
            }
        }
        if (shouldDrawHorizontal) {
            for (int y = (int) (height + topPadding); y >= topPadding; y -= verticalIncrement) {
                canvas.drawRect(0, y, width, y + lineSize, gridPaint);
            }
        }
    }

}
