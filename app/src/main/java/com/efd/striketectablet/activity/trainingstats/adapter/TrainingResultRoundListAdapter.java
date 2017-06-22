package com.efd.striketectablet.activity.trainingstats.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.workout.WorkoutFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.WorkoutStatsFragment;
import com.efd.striketectablet.customview.CustomTextView;

import java.util.ArrayList;

public class TrainingResultRoundListAdapter extends ArrayAdapter<TrainingResultSetDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<TrainingResultSetDTO> trainingResultSetDTOs;

    WorkoutStatsFragment workoutFragment;

    private int currentPosition = 0;
    private WorkoutDTO workoutDTO;

    public TrainingResultRoundListAdapter(Context context, ArrayList<TrainingResultSetDTO> trainingResultSetDTOs, WorkoutStatsFragment workoutFragment){
        super(context, 0, trainingResultSetDTOs);

        mContext = context;
        this.trainingResultSetDTOs = trainingResultSetDTOs;
        this.mainActivity = (MainActivity)context;
        this.workoutFragment = workoutFragment;

        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }

    public void setWorkoutDTO (WorkoutDTO workoutDTO){
        this.workoutDTO = workoutDTO;
    }

    public void setData(ArrayList<TrainingResultSetDTO> trainingResultSetDTOs){
        this.trainingResultSetDTOs = trainingResultSetDTOs;
    }

    @Nullable
    @Override
    public TrainingResultSetDTO getItem(int position) {
        return trainingResultSetDTOs.get(position);
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
            viewHolder.punchkeysView = (CustomTextView)convertView.findViewById(R.id.punch_keys);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final TrainingResultSetDTO trainingResultSetDTO = getItem(position);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolor));
            workoutFragment.updateCombos(trainingResultSetDTO);
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }


        viewHolder.roundNameView.setText(trainingResultSetDTO.getSetname());
        viewHolder.punchkeysView.setVisibility(View.GONE);

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
        public CustomTextView roundNameView, punchkeysView;
    }
}

