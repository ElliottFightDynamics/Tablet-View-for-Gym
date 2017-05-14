package com.efd.striketectablet.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.efd.striketectablet.util.StatisticUtil;

public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public void setCustomFont(Context ctx) {
        setTypeface(StatisticUtil.blenderproBook);
    }

}
