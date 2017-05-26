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

import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.activity.training.workout.NewWorkoutActivity;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class EditNewWorkoutListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Integer> setIDList;
    NewWorkoutActivity workoutActivity;
    int roundposition;
//
    public EditNewWorkoutListAdapter(Context context, ArrayList<Integer> setIDList, int roundposition){
        super(context, 0, setIDList);

        mContext = context;
        workoutActivity = (NewWorkoutActivity)context;
        this.setIDList = setIDList;
        this.roundposition = roundposition;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<Integer> keylist){
        this.setIDList = keylist;
    }

    @Override
    public int getCount() {
        return setIDList.size() ;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return setIDList.get(position);
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

        int setid = getItem(position);
        SetsDTO setsDTO = ComboSetUtil.getSetDtowithID(setid);
        viewHolder.nameView.setText(setsDTO.getName());

        viewHolder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutActivity.showSettings(position, roundposition);
            }
        });

        if (position == setIDList.size() - 1){
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

