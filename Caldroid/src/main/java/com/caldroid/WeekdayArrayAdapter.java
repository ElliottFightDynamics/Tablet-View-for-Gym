package com.caldroid;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Customize the weekday gridview
 */
public class WeekdayArrayAdapter extends ArrayAdapter<String> {
    // public static int textColor = Color.WHITE;
    public static int textColor = Color.parseColor("#53d9ff");
    Context context;

    public WeekdayArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
    }

    // To prevent cell highlighted when clicked
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    // Set color to gray and text size to 12sp
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // To customize text size and color
        TextView textView = (TextView) super.getView(position, convertView, parent);
        // Set content
        String item = getItem(position);

        // Show smaller text if the size of the text is 4 or more in some locale
        if (item.length() <= 3) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        }

        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setBackgroundResource(R.drawable.calendar_day_name_bg);
        textView.setHorizontallyScrolling(false);
        textView.setVerticalScrollBarEnabled(false);
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(this.context.getAssets(), "fonts/BlenderPro-Medium.otf");
        } catch (Exception e) {
            Log.e("TypeFace Error", "Could not get typeface: " + e.getMessage());
        }


        textView.setTypeface(tf);

        // Set different padding for mobile & tablet screens.
        if ((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE
                || (getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_XLARGE) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            textView.setPadding(0, 13, 0, 0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        } else {
            textView.setPadding(0, 17, 0, 0);
        }
        return textView;
    }
}
