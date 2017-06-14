package com.efd.striketectablet.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;
    private ArrayList<String> mObjects;
    private int mKind;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> objects, int spinnerKind) {
        super(context, textViewResourceId, objects);

        mContext = context;
        mResource = textViewResourceId;
        mObjects = objects;
        mKind = spinnerKind;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(mResource, parent, false);
        TextView label = (TextView) row.findViewById(R.id.custom_spinner_with_img_textView);
        label.setText(mObjects.get(position));
        return row;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = null;
        if (mKind == EFDConstants.SPINNER_WHITE)
            row = inflater.inflate(R.layout.custom_spinner, parent, false);
        else if (mKind == EFDConstants.SPINNER_GREEN)
            row = inflater.inflate(R.layout.custom_spinner_green, parent, false);
        else if (mKind == EFDConstants.SPINNER_RED)
            row = inflater.inflate(R.layout.custom_spinner_red, parent, false);
        else if (mKind == EFDConstants.SPINNER_PUNCH)
            row = inflater.inflate(R.layout.custom_spinner_punch, parent, false);
        else if (mKind == EFDConstants.SPINNER_QUESTION)
            row = inflater.inflate(R.layout.custom_spinner_question, parent, false);

        TextView label = (TextView) row.findViewById(R.id.custom_spinner_textView);
        label.setText(mObjects.get(position));
        return row;
    }
}
