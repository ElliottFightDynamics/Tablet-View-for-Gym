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
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.fragment.ComboStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.SetStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.WorkoutStatsFragment;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.StatisticUtil;

import java.util.ArrayList;

public class TrainingResultCombosNameAdapter extends ArrayAdapter<TrainingResultComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<TrainingResultComboDTO> resultComboDTOs;

    ComboStatsFragment fragment;

    private int currentPosition = 0;

    public TrainingResultCombosNameAdapter(Context context, ArrayList<TrainingResultComboDTO> resultComboDTOs, ComboStatsFragment fragment){
        super(context, 0, resultComboDTOs);

        mContext = context;
        this.resultComboDTOs = resultComboDTOs;
        this.mainActivity = (MainActivity)context;
        this.fragment = fragment;

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
            convertView = inflater.inflate(R.layout.item_stats_namelist, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.nameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
            viewHolder.timeView = (CustomTextView)convertView.findViewById(R.id.time);
            viewHolder.punchkeysView = (CustomTextView)convertView.findViewById(R.id.punch_keys);
//            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
//            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TrainingResultComboDTO resultComboDTO = getItem(position);

        final String comboName = resultComboDTO.getComboname();

        Log.e("Super", "comboname =111 " + comboName);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolorinworkout));

            fragment.updateResult(resultComboDTO);
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }


        viewHolder.nameView.setText(comboName);
        viewHolder.punchkeysView.setText(ComboSetUtil.getPunchkeyDetail(resultComboDTO));
        viewHolder.timeView.setText(StatisticUtil.changeMilestoDate(resultComboDTO.getTime()));

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
        public CustomTextView nameView, timeView, punchkeysView;

    }
}

