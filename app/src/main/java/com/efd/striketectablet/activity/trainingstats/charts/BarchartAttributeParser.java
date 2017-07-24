package com.efd.striketectablet.activity.trainingstats.charts;

import android.content.res.TypedArray;

import com.efd.striketectablet.R;

/**
 * Created by omnic on 8/23/2016.
 */
public class BarchartAttributeParser {
    public static void parse(TrainingProgressBarchartView barchartView, TypedArray typedArray) {
        try {
            boolean showGrid = typedArray.getBoolean(R.styleable.TrainingProgressBarchartView_showGrid, false);
            boolean showOutline = typedArray.getBoolean(R.styleable.TrainingProgressBarchartView_showOutline, false);
            boolean shouldDrawLabelBackground = typedArray.getBoolean(R.styleable.TrainingProgressBarchartView_drawLabelBackground, true);
            barchartView.setShouldDrawLabelBackground(shouldDrawLabelBackground);
            if (showGrid) {
                addGrid(barchartView, typedArray);
            }
            if (showOutline) {
                addOutline(barchartView, typedArray);
            }
        } finally {
            typedArray.recycle();
        }
    }

    private static void addOutline(TrainingProgressBarchartView barchartView, TypedArray typedArray) {
        int highlightColor = typedArray.getColor(R.styleable.TrainingProgressBarchartView_outlineColor, 0);
        BarOutlineDecorator barOutlineDecorator = new BarOutlineDecorator();
        barOutlineDecorator.setColor(highlightColor);

        barchartView.addDecorator(barOutlineDecorator);
    }

    private static void addGrid(TrainingProgressBarchartView barchartView, TypedArray typedArray) {
        int horizontalCount = typedArray.getInt(R.styleable.TrainingProgressBarchartView_gridHorizontalLines, 0);
        int lineSize = typedArray.getInt(R.styleable.TrainingProgressBarchartView_gridLineSize, 0);
        int paddingTop = typedArray.getInt(R.styleable.TrainingProgressBarchartView_gridPaddingTop, 0);
        int gridColor = typedArray.getColor(R.styleable.TrainingProgressBarchartView_gridColor, 0);

        BarchartGridDecorator gridDecorator = BarchartGridDecorator.horizontal(horizontalCount);
        gridDecorator.setLineSize(lineSize);
        gridDecorator.setTopPadding(paddingTop);
        gridDecorator.setGridColor(gridColor);

        barchartView.addDecorator(gridDecorator);
    }
}
