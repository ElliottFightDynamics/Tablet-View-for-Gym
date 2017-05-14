package com.efd.striketectablet.activity.trainingstats;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.efd.striketectablet.DTO.PunchesRankDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.customview.CustomTextView;

import java.util.ArrayList;

public class PunchesRankAdapter extends ArrayAdapter<PunchesRankDTO> {

    private Context mContext;
    private ArrayList<PunchesRankDTO> items;

    LayoutInflater inflater;

    public PunchesRankAdapter(Context context, ArrayList<PunchesRankDTO> items) {
        super(context, 0, items);

        mContext = context;

        this.items = items;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData( ArrayList<PunchesRankDTO> items){
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public PunchesRankDTO getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_punches_rank_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.parent);
            viewHolder.numView = (CustomTextView)convertView.findViewById(R.id.num);
            viewHolder.punchtypeView = (CustomTextView)convertView.findViewById(R.id.punch_type);
            viewHolder.speedView = (CustomTextView)convertView.findViewById(R.id.avg_speed);
            viewHolder.forceView = (CustomTextView)convertView.findViewById(R.id.avg_force);
            viewHolder.punchcountView = (CustomTextView)convertView.findViewById(R.id.punch_count);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position % 2 == 0){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.list_odd_item));
//            viewHolder.parentView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_bg));
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        PunchesRankDTO rankDTO = getItem(position);
        viewHolder.numView.setText((position + 1) + ".");
        viewHolder.punchtypeView.setText(rankDTO.punchtype);
        viewHolder.speedView.setText(rankDTO.avg_speed + "MPH");
        viewHolder.forceView.setText(rankDTO.avg_force + "LBS");
        viewHolder.punchcountView.setText(rankDTO.punch_count + "HITS");

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView numView, punchtypeView, speedView, forceView, punchcountView;
    }
}
