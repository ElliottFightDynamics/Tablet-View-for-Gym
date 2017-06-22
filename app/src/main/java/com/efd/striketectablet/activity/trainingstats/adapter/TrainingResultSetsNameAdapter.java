package com.efd.striketectablet.activity.trainingstats.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.fragment.ComboStatsFragment;
import com.efd.striketectablet.activity.trainingstats.fragment.SetStatsFragment;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;

import java.util.ArrayList;

public class TrainingResultSetsNameAdapter extends ArrayAdapter<TrainingResultSetDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<TrainingResultSetDTO> resultSetDTOs;

    SetStatsFragment fragment;

    private int currentPosition = 0;

    public TrainingResultSetsNameAdapter(Context context, ArrayList<TrainingResultSetDTO> resultSetDTOs, SetStatsFragment fragment){
        super(context, 0, resultSetDTOs);

        mContext = context;
        this.resultSetDTOs = resultSetDTOs;
        this.mainActivity = (MainActivity)context;
        this.fragment = fragment;

        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }

    public void setData(ArrayList<TrainingResultSetDTO> resultSetDTOs){
        this.resultSetDTOs = resultSetDTOs;
    }

    @Nullable
    @Override
    public TrainingResultSetDTO getItem(int position) {
        return resultSetDTOs.get(position);
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

        TrainingResultSetDTO resultSetDTO = getItem(position);

        final String comboName = resultSetDTO.getSetname();

        Log.e("Super", "comboname =111 " + comboName);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolor));

            fragment.updateCombos(resultSetDTO);
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        viewHolder.punchkeysView.setVisibility(View.GONE);
        viewHolder.nameView.setText(comboName);
        viewHolder.timeView.setText(StatisticUtil.changeMilestoDate(resultSetDTO.getTime()));

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

