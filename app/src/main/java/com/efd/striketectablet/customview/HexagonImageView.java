package com.efd.striketectablet.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.ImageView;


public class HexagonImageView extends ImageView {

    private static final float DEFAULT_BORDER = 1f;

    private Path octagonPath;
    private Path borderPath;
    private Paint borderPaint;
    private float borderWidth = DEFAULT_BORDER;

    public HexagonImageView(Context context) {
        super(context);
        init();
    }

    public HexagonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexagonImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        calculatePath();
    }

    private void init() {
        this.octagonPath = new Path();
        this.borderPath = new Path();
        this.borderPaint = new Paint();
        this.borderPaint.setColor(Color.parseColor("#215264"));
//        this.borderPaint.setColor(Color.parseColor("#153141"));
        this.borderPaint.setStyle(Paint.Style.FILL);
    }

    private void calculatePath() {
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        float w25 = width * 0.25f;
        float w75 = width * 0.75f;
        float h50 = height / 2f;

        this.octagonPath.reset();
        this.octagonPath.moveTo(w25 + borderWidth, borderWidth);
        this.octagonPath.lineTo(w75 - borderWidth, borderWidth);
        this.octagonPath.lineTo(width - borderWidth, h50);
        this.octagonPath.lineTo(w75 - borderWidth, height - borderWidth);
        this.octagonPath.lineTo(w25 + borderWidth, height - borderWidth);
        this.octagonPath.lineTo(borderWidth, h50);
        this.octagonPath.close();

        this.borderPath.reset();
        this.borderPath.moveTo(w25, 0);
        this.borderPath.lineTo(w75, 0);
        this.borderPath.lineTo(width, h50);
        this.borderPath.lineTo(w75, height);
        this.borderPath.lineTo(w25, height);
        this.borderPath.lineTo(0, h50);
        this.borderPath.close();
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
//        c.drawPath(borderPath, borderPaint);
        c.clipPath(octagonPath, Region.Op.INTERSECT);
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
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
