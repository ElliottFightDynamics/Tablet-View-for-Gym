package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.workout.WorkoutFragment;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class WorkoutRoundListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<Integer> roundnumists;

    WorkoutFragment workoutFragment;

    private int currentPosition = 0;
    private WorkoutDTO workoutDTO;

    public WorkoutRoundListAdapter(Context context, ArrayList<Integer> roundnumists, WorkoutFragment workoutFragment){
        super(context, 0, roundnumists);

        mContext = context;
        this.roundnumists = roundnumists;
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

    public void setData(ArrayList<Integer> comboLists){
        this.roundnumists = comboLists;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return roundnumists.get(position);
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

        final Integer roundnum = getItem(position);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolorinworkout));
            workoutFragment.updateSet(workoutDTO, position);
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }


        viewHolder.roundNameView.setText("ROUND " + (roundnum + 1));
//        viewHolder.comboStringView.setText(comboDTO.getCombos());

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

