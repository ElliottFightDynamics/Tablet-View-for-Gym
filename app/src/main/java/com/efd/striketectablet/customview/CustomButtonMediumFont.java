package com.efd.striketectablet.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.efd.striketectablet.util.StatisticUtil;

public class CustomButtonMediumFont extends Button {

    public CustomButtonMediumFont(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomButtonMediumFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomButtonMediumFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public void setCustomFont(Context ctx) {
        setTypeface(StatisticUtil.blenderproMedium);
    }

}
