package com.efd.striketectablet.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.efd.striketectablet.util.StatisticUtil;

public class CustomTextViewFontEfDigit extends TextView {

    public CustomTextViewFontEfDigit(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomTextViewFontEfDigit(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public CustomTextViewFontEfDigit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context);
    }

    public boolean setCustomFont(Context ctx) {

        setTypeface(StatisticUtil.efdigits);
        return true;
    }


}
