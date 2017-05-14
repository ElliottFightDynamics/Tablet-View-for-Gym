package com.efd.striketectablet.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

import com.efd.striketectablet.R;

public class HexagonRadioButton extends RadioButton {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int MIDDLE = 2;
    private static final int BOTH = 2;
    private static final int NONE = 3;

    private Path hexagonPath;
    private Path selectorPath;
    private Paint mSelectedPaint;
    private Paint mUnselectedPaint;
    private Paint mSelectorPaint;
    private int mType;
    private int mBorder;

    public HexagonRadioButton(Context context) {
        super(context);
        init(context, null);
    }

    public HexagonRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HexagonRadioButton(Context context, AttributeSet attrs, int defStyle) {
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
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.HexagonRadioButton, 0, 0);
            try {
                mType = a.getInt(R.styleable.HexagonRadioButton_hrb_type, LEFT);
                mBorder = a.getInt(R.styleable.HexagonRadioButton_hrb_divider, NONE);
            } catch (Exception e) {
                Log.d("ATTRIBUTES", "init: " + e);
            } finally {
                a.recycle();
            }
        }
        setBackground(null);
        setButtonDrawable(null);
        setTypeface(tf);
        init();
    }

    private void init() {
        this.hexagonPath = new Path();
        this.selectorPath = new Path();

        this.mSelectedPaint = new Paint();
        this.mSelectedPaint.setColor(Color.parseColor("#B3F4FF"));
        this.mSelectedPaint.setStyle(Paint.Style.STROKE);

        this.mSelectorPaint = new Paint(this.mSelectedPaint);
        this.mSelectorPaint.setStyle(Paint.Style.FILL);

        this.mUnselectedPaint = new Paint();
        this.mUnselectedPaint.setColor(Color.parseColor("#FFBC45"));
        this.mUnselectedPaint.setStyle(Paint.Style.STROKE);
    }

    private void calculatePath() {
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        float corner = height * (33 / 100f);

        if (mType == LEFT) {
            this.hexagonPath.reset();
            this.hexagonPath.moveTo(corner, 0);
            this.hexagonPath.lineTo(width - corner, 0);
            this.hexagonPath.lineTo(width, height / 2);
            this.hexagonPath.lineTo(width - corner, height);
            this.hexagonPath.lineTo(corner, height);
            this.hexagonPath.lineTo(0, height / 2);
            this.hexagonPath.close();
        } else if (mType == MIDDLE) {
            this.hexagonPath.reset();
            this.hexagonPath.moveTo(corner, 0);
            this.hexagonPath.lineTo(width - corner, 0);
            this.hexagonPath.lineTo(width, height / 2);
            this.hexagonPath.lineTo(width - corner, height);
            this.hexagonPath.lineTo(corner, height);
            this.hexagonPath.lineTo(0, height / 2);
            this.hexagonPath.close();
        } else {
            this.hexagonPath.reset();
            this.hexagonPath.moveTo(corner, 0);
            this.hexagonPath.lineTo(width - corner, 0);
            this.hexagonPath.lineTo(width, height / 2);
            this.hexagonPath.lineTo(width - corner, height);
            this.hexagonPath.lineTo(corner, height);
            this.hexagonPath.lineTo(0, height / 2);
            this.hexagonPath.close();
        }

        this.selectorPath.reset();
        if (isChecked()) {
            float selectorWidth = width / 15f;
            float middle = width / 2;
            float selectorHeight = 5f;
            this.selectorPath.moveTo(middle - (selectorWidth / 2), 0);
            this.selectorPath.lineTo(middle + (selectorWidth / 2), 0);
            this.selectorPath.lineTo(middle + (selectorWidth / 2), selectorHeight);
            this.selectorPath.lineTo(middle - (selectorWidth / 2), selectorHeight);
            this.selectorPath.close();

            this.selectorPath.moveTo(middle + (selectorWidth / 2), height);
            this.selectorPath.lineTo(middle - (selectorWidth / 2), height);
            this.selectorPath.lineTo(middle - (selectorWidth / 2), height - selectorHeight);
            this.selectorPath.lineTo(middle + (selectorWidth / 2), height - selectorHeight);
            this.selectorPath.close();
        }

        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
//        c.clipPath(hexagonPath, Region.Op.INTERSECT);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        c.drawPath(hexagonPath, mSelectedPaint);
        if (isChecked()) {
            c.drawPath(selectorPath, mSelectorPaint);
        }
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
