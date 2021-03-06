package com.efd.striketectablet.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Region;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.efd.striketectablet.R;

public class HexagonButton extends Button {

    private Path hexagonPath;
    private Paint pathPaint;
    private int mColor;
    private int mCoef;
    private boolean fill;

    public HexagonButton(Context context) {
        super(context);
        init(context, null);
    }

    public HexagonButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HexagonButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/BlenderPro-Book.otf");
        } catch (Exception e) {
            Log.e("TypeFace Error", "Could not get typeface: " + e.getMessage());
        }
        mColor = Color.YELLOW;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.HexagonButton, 0, 0);
            try {
                mColor = a.getColor(R.styleable.HexagonButton_hexagon_color, Color.YELLOW);
                mCoef = a.getInt(R.styleable.HexagonButton_angle_coef, 33);
                fill = a.getBoolean(R.styleable.HexagonButton_fill, true);
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
        setTypeface(tf);
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
        this.hexagonPath.moveTo(corner, margin);
        this.hexagonPath.lineTo(width - corner, margin);
        this.hexagonPath.lineTo(width - margin, height / 2);
        this.hexagonPath.lineTo(width - corner, height - margin);
        this.hexagonPath.lineTo(corner, height - margin);
        this.hexagonPath.lineTo(margin, height / 2);
        this.hexagonPath.close();
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        c.clipPath(hexagonPath, Region.Op.INTERSECT);
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
