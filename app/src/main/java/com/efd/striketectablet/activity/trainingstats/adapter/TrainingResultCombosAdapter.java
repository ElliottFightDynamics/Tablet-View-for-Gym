package com.efd.striketectablet.activity.trainingstats.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.fragment.ComboStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.SetStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.WorkoutStatsFragment;
import com.efd.striketectablet.customview.CustomTextView;

import java.util.ArrayList;

public class TrainingResultCombosAdapter extends ArrayAdapter<TrainingResultComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<TrainingResultComboDTO> resultComboDTOs;

    Fragment fragment;
    boolean isSet = false;

    private int currentPosition = 0;

    public TrainingResultCombosAdapter(Context context, ArrayList<TrainingResultComboDTO> resultComboDTOs, Fragment fragment, boolean isSet){
        super(context, 0, resultComboDTOs);

        mContext = context;
        this.resultComboDTOs = resultComboDTOs;
        this.mainActivity = (MainActivity)context;
        this.fragment = fragment;
        this.isSet = isSet;

        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }

    public void setData(ArrayList<TrainingResultComboDTO> resultComboDTOs){
        this.resultComboDTOs = resultComboDTOs;
    }

    @Nullable
    @Override
    public TrainingResultComboDTO getItem(int position) {
        return resultComboDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_workout_round_row, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.roundNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
//            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
//            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TrainingResultComboDTO resultComboDTO = getItem(position);

        final String comboName = resultComboDTO.getComboname();

        Log.e("Super", "comboname = " + comboName);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolorinworkout));
            if (isSet){
                SetStatsFragment setStatsFragment = (SetStatsFragment)fragment;
                setStatsFragment.updateResult(resultComboDTO);
            }else {
                WorkoutStatsFragment workoutStatsFragment = (WorkoutStatsFragment)fragment;
                workoutStatsFragment.updateResult(resultComboDTO);
            }
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }


        viewHolder.roundNameView.setText(comboName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition != position) {
                    currentPosition = position;
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView roundNameView;//, comboStringView;//, comboRangeView;
//        public ImageView settingsView;
    }
}

