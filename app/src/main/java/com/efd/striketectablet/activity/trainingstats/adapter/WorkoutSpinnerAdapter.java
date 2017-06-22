package com.efd.striketectablet.activity.trainingstats.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultWorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;

public class WorkoutSpinnerAdapter extends ArrayAdapter<TrainingResultWorkoutDTO> {

    private Context mContext;
    private ArrayList<TrainingResultWorkoutDTO> mObjects;

    public WorkoutSpinnerAdapter(Context context, ArrayList<TrainingResultWorkoutDTO> objects) {
        super(context, 0, objects);

        mContext = context;
        mObjects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_stats_namelist, parent, false);
        TextView nameView = (CustomTextView)row.findViewById(R.id.combo_name);
        TextView timeView = (CustomTextView)row.findViewById(R.id.time);

        TrainingResultWorkoutDTO resultWorkoutDTO = mObjects.get(position);

        nameView.setText(resultWorkoutDTO.getWorkoutname());
        timeView.setText(StatisticUtil.changeMilestoDate(resultWorkoutDTO.getTime()));

        if (position %2 == 1){
            row.setBackgroundColor(mContext.getResources().getColor(R.color.preset_selected_bg));
        }else {
            row.setBackgroundColor(mContext.getResources().getColor(R.color.preset_popup_bg));
        }
        return row;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_spinner_workout_orange_text, parent, false);

        TextView label = (TextView) row.findViewById(R.id.custom_spinner_textView);
        label.setText(mObjects.get(position).getWorkoutname());

        return row;
    }
}
