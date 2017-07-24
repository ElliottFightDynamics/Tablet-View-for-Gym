package com.efd.striketectablet.activity.trainingstats.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;
import com.efd.striketectablet.activity.trainingstats.charts.stats.TrainingDataSeries;

import java.util.ArrayList;

/**
 * Created by omnic on 7/31/2016.
 */
public class TrainingProgressBarchartView extends View {

    private static final int ROUNDED_RECTANGLE_RADIUS = 5;
    private static final int LABEL_PADDING = 4;
    private static final int HIGHLIGHT_SIZE = 6;


    private final ArrayList<IChartDecorator> chartDecorators;
    private final ArrayList<IBarchartClickListener> clickListeners;
    private StatType currentStat;
    private TrainingProgressBarchartSettings settings;
    private TrainingDataSeries dataSet;

    private float maxValue = 0.0f;
    private float screenVerticalScale = 1.0f;
    private boolean shouldDrawLabelBackground;

    public TrainingProgressBarchartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.shouldDrawLabelBackground = true;
        this.dataSet = new TrainingDataSeries();
        this.settings = new TrainingProgressBarchartSettings();
        this.chartDecorators = new ArrayList<>();
        this.clickListeners = new ArrayList<>();
    this.currentStat = StatType.TIME;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TrainingProgressBarchartView, 0, 0);
        BarchartAttributeParser.parse(this, typedArray);
    }

    public void addDecorator(IChartDecorator decorator) {
        this.chartDecorators.add(decorator);
    }

    public void setShouldDrawLabelBackground(boolean shouldDrawLabelBackground) {
        this.shouldDrawLabelBackground = shouldDrawLabelBackground;
    }


    public void addBarClickListener(IBarchartClickListener listener) {
        this.clickListeners.add(listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        preDecorate(canvas);
        for (int i = 0; i < dataSet.size(); i++) {
            IBarchartValue barchartValue = this.dataSet.get(i);
            drawBar(canvas, i, barchartValue);
            drawLabel(canvas, i, barchartValue);
        }
        postDecorate(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() ;
        if (action == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int itemIndex = this.settings.getItemNumberForX(x);
            if (itemIndex <= dataSet.size()) {
                IBarchartValue barchartValue = dataSet.get(itemIndex);
                resetHighlights(barchartValue);
                notifyObserversOfClick(x, y, barchartValue);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void resetHighlights(IBarchartValue valueToHighlight) {
        for(IBarchartValue barchartValue : this.dataSet){
            barchartValue.setHighlight(false);
        }
        valueToHighlight.setHighlight(true);
        invalidate();
    }

    private void notifyObserversOfClick(float x, float y, IBarchartValue barchartData) {
        for (IBarchartClickListener listener : this.clickListeners) {
            listener.barClicked(x, y, barchartData);
        }
    }

    private void preDecorate(Canvas canvas) {
        for (IChartDecorator decorator : this.chartDecorators) {
            decorator.preDecorate(canvas, this.dataSet, this.settings);
        }
    }

    private void postDecorate(Canvas canvas) {
        for (IChartDecorator decorator : this.chartDecorators) {
            decorator.postDecorate(canvas, this.dataSet, this.settings);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewWidth = (int) ((dataSet.size() * settings.getItemWidth()) + (dataSet.size() * settings.getItemPadding()));
        setMeasuredDimension(viewWidth, heightMeasureSpec);
    }

    private void drawBar(Canvas canvas, int itemNumber, IBarchartValue simpleBarchartValue) {
        float barHeight = simpleBarchartValue.getValue(this.currentStat) * this.screenVerticalScale * this.settings.getManualVerticalScale();

        float barWidth = this.settings.getBarWidth();
        RectF fullBar = new RectF(0, 0, barWidth, barHeight);

        float itemWidth = this.settings.getItemWidth();
        float barCenterOffset = (itemWidth - barWidth) / 2f;
        fullBar.left = barCenterOffset + this.settings.getXForItemNumber(itemNumber);
        fullBar.right = fullBar.left + barWidth;
        fullBar.top = getHeight() - barHeight + this.settings.getPaddingTop();
        fullBar.bottom = getHeight() - this.settings.getPaddingTop();

        RectF topHighlight = new RectF(fullBar);
        topHighlight.bottom = fullBar.top + HIGHLIGHT_SIZE;
        RectF bottomHighlight = new RectF(fullBar);
        bottomHighlight.top = fullBar.bottom - HIGHLIGHT_SIZE;

        canvas.drawRoundRect(fullBar, ROUNDED_RECTANGLE_RADIUS, ROUNDED_RECTANGLE_RADIUS, this.settings.getBarSecondaryPaint());
        canvas.drawRoundRect(topHighlight, ROUNDED_RECTANGLE_RADIUS, ROUNDED_RECTANGLE_RADIUS, this.settings.getBarPaint());
        canvas.drawRoundRect(bottomHighlight, ROUNDED_RECTANGLE_RADIUS, ROUNDED_RECTANGLE_RADIUS, this.settings.getBarPaint());
    }

    private void drawLabel(Canvas canvas, int itemNumber, IBarchartValue simpleBarchartValue) {
        String label = simpleBarchartValue.getLabel();
        float startX = this.settings.getXForItemNumber(itemNumber);
        float labelWidth = this.settings.getLabelWidth();
        float centerOffset = (this.settings.getItemWidth() - labelWidth) / 2f;

        Rect textBounds = new Rect();
        Paint labelPaint = this.settings.getLabelPaint();

        labelPaint.getTextBounds(label, 0, label.length(), textBounds);
        float paddingTop = this.settings.getPaddingTop();


        RectF labelBackground = new RectF();
        labelBackground.left = startX + centerOffset;
        labelBackground.right = labelBackground.left + labelWidth;
        labelBackground.top = paddingTop;
        labelBackground.bottom = paddingTop + labelPaint.getTextSize() + (LABEL_PADDING * 4);

        if (shouldDrawLabelBackground) {
            canvas.drawRoundRect(labelBackground, ROUNDED_RECTANGLE_RADIUS, ROUNDED_RECTANGLE_RADIUS, this.settings.getLabelBackgroundPaint());
        }

        float textOffset = (labelWidth - textBounds.width()) / 2f;
        float textX = labelBackground.left + textOffset;
        float textY = paddingTop + LABEL_PADDING + labelPaint.getTextSize();

        canvas.drawText(label, textX, textY, labelPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateVerticalScale();
    }

    public void setDataSet(TrainingDataSeries dataSet) {
        this.dataSet = dataSet;
        updateVerticalScale();
        invalidate();
        requestLayout();
    }

    public void setCurrentStat(StatType currentStat) {
        this.currentStat = currentStat;
        updateVerticalScale();
    }

    private void updateVerticalScale() {
        this.maxValue = 1.0f;
        this.screenVerticalScale = 1.0f;

        int countOfValue = 0;
        float delta = 0;

        for (IBarchartValue barValue : this.dataSet) {
            float value = barValue.getValue(this.currentStat);
            this.maxValue = (value > this.maxValue) ? value : this.maxValue;

            if (value != 0)
                countOfValue++;
        }
        float viewHeight = this.getHeight() - this.settings.getPaddingTop() - this.settings.getPaddingBottom();

        if (countOfValue == 1)
            delta = 10;

        this.screenVerticalScale = viewHeight / (this.maxValue + delta);

    }

    public void setSettings(TrainingProgressBarchartSettings settings) {
        this.settings = settings;
        invalidate();
        requestLayout();
    }
}
