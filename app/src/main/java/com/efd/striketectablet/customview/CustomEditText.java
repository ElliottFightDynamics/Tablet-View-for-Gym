package com.efd.striketectablet.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.efd.striketectablet.util.StatisticUtil;

public class CustomEditText extends EditText {

    public CustomEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context);
    }

    public boolean setCustomFont(Context ctx) {
        setTypeface(StatisticUtil.blenderproBook);
        return true;
    }


}
