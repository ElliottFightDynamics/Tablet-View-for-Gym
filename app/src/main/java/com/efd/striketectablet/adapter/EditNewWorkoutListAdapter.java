package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.activity.training.workout.NewWorkoutActivity;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class EditNewWorkoutListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Integer> comboIDList;
    NewWorkoutActivity workoutActivity;
    int roundposition;
//
    public EditNewWorkoutListAdapter(Context context, ArrayList<Integer> comboIDList, int roundposition){
        super(context, 0, comboIDList);

        mContext = context;
        workoutActivity = (NewWorkoutActivity)context;
        this.comboIDList = comboIDList;
        this.roundposition = roundposition;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setRoundposition(int roundposition){
        this.roundposition = roundposition;
    }

    public void setData (ArrayList<Integer> keylist){
        this.comboIDList = keylist;
    }

    @Override
    public int getCount() {
        return comboIDList.size() ;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return comboIDList.get(position);
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
            convertView = inflater.inflate(R.layout.item_addset_list, null);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (TextView)convertView.findViewById(R.id.name);
            viewHolder.divider = (ImageView)convertView.findViewById(R.id.divider);
            viewHolder.settings = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        int comboid = getItem(position);
        ComboDTO comboDTO = ComboSetUtil.getComboDtowithID(comboid);
        viewHolder.nameView.setText(comboDTO.getName());

        viewHolder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutActivity.showSettings(position, roundposition);
            }
        });

        if (position == comboIDList.size() - 1){
            viewHolder.divider.setVisibility(View.GONE);
        }else
            viewHolder.divider.setVisibility(View.VISIBLE);

        return convertView;
    }

    public static class ViewHolder {

        public TextView  nameView;
        public ImageView divider, settings;
    }
}

