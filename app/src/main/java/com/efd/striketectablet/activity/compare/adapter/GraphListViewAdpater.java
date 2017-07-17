package com.efd.striketectablet.activity.compare.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efd.striketectablet.DTO.responsedto.DBTrainingSessionDTO;
import com.efd.striketectablet.DTO.responsedto.PunchInfoDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.compare.TrainingCompareFragment;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;

import java.util.ArrayList;

public class GraphListViewAdpater extends ArrayAdapter<PunchInfoDTO> {

    private Context mContext;
    private ArrayList<PunchInfoDTO> items;
    private String filename;

    LayoutInflater inflater;
    TrainingCompareFragment compareFragment;

    private int[] graphColors = {
            R.color.graph_first, R.color.graph_second, R.color.graph_third, R.color.graph_forth, R.color.graph_fifth };

    public GraphListViewAdpater(Context context, ArrayList<PunchInfoDTO> items, TrainingCompareFragment compareFragment) {
        super(context, 0, items);

        mContext = context;
        this.compareFragment = compareFragment;
        this.items = items;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public void setData( ArrayList<PunchInfoDTO> items){
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public PunchInfoDTO getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_punch_graph_list_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.timeView = (TextView)convertView.findViewById(R.id.time);
            viewHolder.firstView = (TextView)convertView.findViewById(R.id.first);
            viewHolder.secondView = (TextView)convertView.findViewById(R.id.second);
            viewHolder.thirdView = (TextView)convertView.findViewById(R.id.third);
            viewHolder.closeView = (ImageView) convertView.findViewById(R.id.closeview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final PunchInfoDTO punchInfoDTO = getItem(position);

        if (position < 5){
            viewHolder.timeView.setTextColor(mContext.getResources().getColor(graphColors[position]));
            viewHolder.firstView.setTextColor(mContext.getResources().getColor(graphColors[position]));
            viewHolder.secondView.setTextColor(mContext.getResources().getColor(graphColors[position]));
            viewHolder.thirdView.setTextColor(mContext.getResources().getColor(graphColors[position]));
        }else {
            viewHolder.timeView.setTextColor(mContext.getResources().getColor(graphColors[0]));
            viewHolder.firstView.setTextColor(mContext.getResources().getColor(graphColors[0]));
            viewHolder.secondView.setTextColor(mContext.getResources().getColor(graphColors[0]));
            viewHolder.thirdView.setTextColor(mContext.getResources().getColor(graphColors[0]));
        }

        viewHolder.timeView.setText("1:22:04 PM");
        viewHolder.firstView.setText(String.valueOf((int)Float.parseFloat(punchInfoDTO.getPunch_speed())));
        viewHolder.secondView.setText(String.valueOf((int)Float.parseFloat(punchInfoDTO.getImpulse())));
        viewHolder.thirdView.setText(String.valueOf((int)(Float.parseFloat(punchInfoDTO.getThrust_duration()) * 1000)));

        viewHolder.closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareFragment.removeActionInGraph(punchInfoDTO);
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        public ImageView closeView;
        public TextView timeView, firstView, secondView, thirdView;
    }
}
