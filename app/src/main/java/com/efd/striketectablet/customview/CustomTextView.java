package com.efd.striketectablet.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.util.StatisticUtil;

public class CustomTextView extends TextView {

	/*public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
		TypefaceManager.getInstance().applyTypeface(this, context, attrs);
	}*/

    /**
     * Convenience method in case I need to change the font from code as well.
     *
     * @param textStyle
     */
	/*public void setTextStyle(TextStyle textStyle) {
		TypefaceManager.getInstance().applyTypeface(this, textStyle);
	}*/
    public CustomTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context);
    }

    public boolean setCustomFont(Context ctx) {

        setTypeface(StatisticUtil.blenderproBook);
        return true;
    }


}
