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


public class OctagonImageView extends ImageView {

    private static final float DEFAULT_BORDER = 1f;

    private Path octagonPath;
    private Path borderPath;
    private Paint borderPaint;
    private float borderWidth = DEFAULT_BORDER;

    public OctagonImageView(Context context) {
        super(context);
        init();
    }

    public OctagonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OctagonImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        float corner = width * 0.07f;
        if (height < width) {
            corner = height * 0.1f;
        }

        this.octagonPath.reset();
        this.octagonPath.moveTo(corner + borderWidth, borderWidth);
        this.octagonPath.lineTo(width - corner - borderWidth, borderWidth);
        this.octagonPath.lineTo(width - borderWidth, corner + borderWidth);
        this.octagonPath.lineTo(width - borderWidth, height - corner - borderWidth);
        this.octagonPath.lineTo(width - corner - borderWidth, height - borderWidth);
        this.octagonPath.lineTo(corner + borderWidth, height - borderWidth);
        this.octagonPath.lineTo(borderWidth, height - corner - borderWidth);
        this.octagonPath.lineTo(borderWidth, corner + borderWidth);
        this.octagonPath.close();

        this.borderPath.reset();
        this.borderPath.moveTo(corner, 0);
        this.borderPath.lineTo(width - corner, 0);
        this.borderPath.lineTo(width, corner);
        this.borderPath.lineTo(width, height - corner);
        this.borderPath.lineTo(width - corner, height);
        this.borderPath.lineTo(corner, height);
        this.borderPath.lineTo(0, height - corner);
        this.borderPath.lineTo(0, corner);
        this.borderPath.close();
        invalidate();
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawPath(borderPath, borderPaint);
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
