package com.efd.striketectablet.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.efd.striketectablet.util.StatisticUtil;

public class CustomCheckBox extends CheckBox {

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomCheckBox(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public boolean setCustomFont(Context ctx) {
        setTypeface(StatisticUtil.blenderproBook);
        return true;
    }

}
