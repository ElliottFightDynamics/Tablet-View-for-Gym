package com.efd.striketectablet.activity.compare.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efd.striketectablet.DTO.PunchesRankDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingSessionDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;

import java.util.ArrayList;

public class CompareSessionListAdapter extends ArrayAdapter<DBTrainingSessionDTO> {

    private Context mContext;
    private ArrayList<DBTrainingSessionDTO> items;

    LayoutInflater inflater;

    private int selectedPosition = 0;

    public CompareSessionListAdapter(Context context, ArrayList<DBTrainingSessionDTO> items) {
        super(context, 0, items);

        mContext = context;

        this.items = items;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData( ArrayList<DBTrainingSessionDTO> items){
        this.items = items;
    }

    public void setSelectedPosition(int selectedPosition){
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }

    public DBTrainingSessionDTO getCurrentSession(){
        return getItem(selectedPosition);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public DBTrainingSessionDTO getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_compare_session_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.backgroundView = (LinearLayout)convertView.findViewById(R.id.background);
            viewHolder.sessionNameView = (TextView)convertView.findViewById(R.id.session_num);
            viewHolder.sessionDurationView = (TextView)convertView.findViewById(R.id.session_duration);
            viewHolder.sessionStartTimeView = (TextView)convertView.findViewById(R.id.training_time);
            viewHolder.totalCountView = (TextView)convertView.findViewById(R.id.total_count);
            viewHolder.avgspeedView = (TextView)convertView.findViewById(R.id.avgspeed);
            viewHolder.avgforceView = (TextView)convertView.findViewById(R.id.avgforce);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position == selectedPosition){
            viewHolder.backgroundView.setBackgroundResource(R.drawable.compare_sessionselected_bg);
            viewHolder.sessionNameView.setTextColor(mContext.getResources().getColor(R.color.orange));
            viewHolder.sessionDurationView.setTextColor(mContext.getResources().getColor(R.color.orange));
//            viewHolder.backgroundView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.backgroundView.setBackgroundResource(R.drawable.compare_sessionunselected_bg);
            viewHolder.sessionNameView.setTextColor(mContext.getResources().getColor(R.color.punches_text_color));
            viewHolder.sessionDurationView.setTextColor(mContext.getResources().getColor(R.color.punches_text_color));
//            viewHolder.backgroundView.setVisibility(View.GONE);
        }

        DBTrainingSessionDTO sessionDTO = getItem(position);
        viewHolder.sessionNameView.setText("SESSION " + (position + 1));
        viewHolder.sessionStartTimeView.setText(StatisticUtil.changeMilestoSessionDate(sessionDTO.getStartTime()));
        int duration = (int)(Long.parseLong(sessionDTO.getEndTime()) - Long.parseLong(sessionDTO.getStartTime()));
        viewHolder.sessionDurationView.setText(CommonUtils.changeMillisecondsToTime(duration));
        viewHolder.totalCountView.setText(String.valueOf(sessionDTO.getTotalPunchCount()));
        viewHolder.avgforceView.setText(String.valueOf(Math.round(sessionDTO.getAvgForce())) + " LBS");
        viewHolder.avgspeedView.setText(String.valueOf(Math.round(sessionDTO.getAvgSpeed())) + " MPH");

        Log.e("Super", "info file = " + sessionDTO.getLefthandInfo() + "    " + sessionDTO.getRighthandInfo());

//        DBTrainingSessionDTO rankDTO = getItem(position);
//        viewHolder.numView.setText((position + 1) + ".");
//        viewHolder.punchtypeView.setText(rankDTO.punchtype);
//        viewHolder.speedView.setText((int)rankDTO.avg_speed + " MPH");
//        viewHolder.forceView.setText((int)rankDTO.avg_force + " LBS");
//        viewHolder.punchcountView.setText(rankDTO.punch_count + "");
//        viewHolder.punchEffectView.setText(rankDTO.punch_percent + " %");

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout backgroundView;
        public TextView sessionNameView, sessionDurationView, sessionStartTimeView, totalCountView, avgspeedView, avgforceView;
    }
}
