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
import android.view.Gravity;
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
        setGravity(Gravity.CENTER);
        setBackground(null);
        setButtonDrawable(null);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setTypeface(tf);
        init();
    }

    private void init() {
        this.hexagonPath = new Path();
        this.selectorPath = new Path();

        this.mSelectedPaint = new Paint();
        this.mSelectedPaint.setColor(Color.parseColor("#235264"));
        this.mSelectedPaint.setStyle(Paint.Style.STROKE);
        this.mSelectedPaint.setStrokeWidth(4f);

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

        this.hexagonPath.reset();
        if (mType == LEFT) {
            this.hexagonPath.moveTo(width, 0);
            this.hexagonPath.lineTo(corner, 0);
            this.hexagonPath.lineTo(0, height / 2);
            this.hexagonPath.lineTo(corner, height);
            this.hexagonPath.lineTo(width, height);
        } else if (mType == MIDDLE) {
            this.hexagonPath.moveTo(0, 0);
            this.hexagonPath.lineTo(width, 0);
            this.hexagonPath.moveTo(width, height);
            this.hexagonPath.lineTo(0, height);
        } else {
            this.hexagonPath.moveTo(0, 0);
            this.hexagonPath.lineTo(width - corner, 0);
            this.hexagonPath.lineTo(width, height / 2);
            this.hexagonPath.lineTo(width - corner, height);
            this.hexagonPath.lineTo(0, height);
        }
        if (mBorder != NONE) {
            float dHeight = height * (40f / 100f);
            float stY = (height - dHeight) / 2f;
            float endY = height - stY;
            if (mBorder == RIGHT) {
                this.hexagonPath.moveTo(width, stY);
                this.hexagonPath.lineTo(width, endY);
            } else if (mBorder == LEFT) {
                this.hexagonPath.moveTo(0, stY);
                this.hexagonPath.lineTo(0, endY);
            } else if (mBorder == BOTH) {
                this.hexagonPath.moveTo(0, stY);
                this.hexagonPath.lineTo(0, endY);
                this.hexagonPath.moveTo(width, stY);
                this.hexagonPath.lineTo(width, endY);
            }
        }

        this.selectorPath.reset();
        float selectorWidth = width / 13f;
        float middle = width / 2;
        float selectorHeight = 7f;
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

        invalidate();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (isChecked()) {
            setTextColor(getResources().getColor(R.color.white));
        } else {
            setTextColor(getResources().getColor(R.color.orange));
        }
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
//        c.clipPath(hexagonPath, Region.Op.INTERSECT);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
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
