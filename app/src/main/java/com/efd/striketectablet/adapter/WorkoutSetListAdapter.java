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
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class WorkoutSetListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<Integer> setIDLists;

    public WorkoutSetListAdapter(Context context, ArrayList<Integer> setIDLists){
        super(context, 0, setIDLists);

        mContext = context;
        this.setIDLists = setIDLists;
        this.mainActivity = (MainActivity)context;

        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Integer> comboLists){
        this.setIDLists = comboLists;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return setIDLists.get(position);
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
            viewHolder.setNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
//            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
//            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final Integer setID = getItem(position);
        SetsDTO setsDTO = ComboSetUtil.getSetDtowithID(setID);
        if (setsDTO != null)
            viewHolder.setNameView.setText(setsDTO.getName());
//        viewHolder.comboStringView.setText(comboDTO.getCombos());

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView setNameView;//, comboStringView;//, comboRangeView;
//        public ImageView settingsView;
    }
}

