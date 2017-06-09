package com.efd.striketectablet.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.efd.striketectablet.R;

public class HexagonLayout extends LinearLayout {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int NONE = 2;

    private Path hexagonPath;
    private Paint pathPaint;
    private int mColor;
    private int mCoef;
    private boolean fill;
    private int mType;

    public HexagonLayout(Context context) {
        super(context);
        init(context, null);
    }

    public HexagonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HexagonLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        mColor = Color.YELLOW;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.HexagonLayout, 0, 0);
            try {
                mColor = a.getColor(R.styleable.HexagonLayout_hrl_hexagon_color, Color.YELLOW);
                mCoef = a.getInt(R.styleable.HexagonLayout_hrl_angle_coef, 33);
                fill = a.getBoolean(R.styleable.HexagonLayout_hrl_fill, true);
                mType = a.getInt(R.styleable.HexagonLayout_hrl_type, NONE);
                if (mCoef > 100) {
                    mCoef = 100;
                }
            } catch (Exception e) {
                Log.d("ATTRIBUTES", "init: " + e);
            } finally {
                a.recycle();
            }
        }
        setBackground(null);
        init();
    }

    private void init() {
        this.hexagonPath = new Path();
        this.pathPaint = new Paint();
        this.pathPaint.setColor(mColor);
        if (fill) {
            this.pathPaint.setStyle(Paint.Style.FILL);
        } else {
            this.pathPaint.setStyle(Paint.Style.STROKE);
        }
        this.pathPaint.setStrokeWidth(2f);
    }

    private void calculatePath() {
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        float corner = height * (mCoef / 100f);
        float margin = height * 0.03f;

        this.hexagonPath.reset();
        if (mType == NONE) {
            this.hexagonPath.moveTo(corner, margin);
            this.hexagonPath.lineTo(width - corner, margin);
            this.hexagonPath.lineTo(width - margin, height / 2);
            this.hexagonPath.lineTo(width - corner, height - margin);
            this.hexagonPath.lineTo(corner, height - margin);
            this.hexagonPath.lineTo(margin, height / 2);
        } else if (mType == LEFT) {
            this.hexagonPath.moveTo(margin, margin);
            this.hexagonPath.lineTo(width - corner, margin);
            this.hexagonPath.lineTo(width - margin, height / 2);
            this.hexagonPath.lineTo(width - corner, height - margin);
            this.hexagonPath.lineTo(margin, height - margin);
        } else if (mType == RIGHT) {
            this.hexagonPath.moveTo(corner, margin);
            this.hexagonPath.lineTo(width - margin, margin);
            this.hexagonPath.lineTo(width - margin, height - margin);
            this.hexagonPath.lineTo(corner, height - margin);
            this.hexagonPath.lineTo(margin, height / 2);
        }
        this.hexagonPath.close();
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        c.drawPath(hexagonPath, pathPaint);
        super.onDraw(c);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        calculatePath();
    }
}