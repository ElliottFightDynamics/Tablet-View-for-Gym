package com.efd.striketectablet.activity.trainingstats.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultPunchDTO;
import com.efd.striketectablet.R;

import java.util.ArrayList;

public class TrainingResultPunchesAdapter extends ArrayAdapter<TrainingResultPunchDTO> {

    private Context mContext;
    private ArrayList<TrainingResultPunchDTO> punchDTOs;
    LayoutInflater inflater;

    public TrainingResultPunchesAdapter(Context context, ArrayList<TrainingResultPunchDTO> punchDTOs) {
        super(context, 0, punchDTOs);

        mContext = context;

        this.punchDTOs = punchDTOs;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<TrainingResultPunchDTO> punchDTOs){
        this.punchDTOs = punchDTOs;
    }

    @Override
    public int getCount() {
        return punchDTOs.size();
    }

    @Nullable
    @Override
    public TrainingResultPunchDTO getItem(int position) {
        return punchDTOs.get(position);
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
            convertView = inflater.inflate(R.layout.item_stats_punches_layout, null);
            viewHolder = new ViewHolder();
//            viewHolder.missedView = (LinearLayout) convertView.findViewById(R.id.missedview);
            viewHolder.typeView = (TextView)convertView.findViewById(R.id.punch_type);
            viewHolder.speedView = (TextView)convertView.findViewById(R.id.speed_value);
            viewHolder.forceView = (TextView)convertView.findViewById(R.id.force_value);
            viewHolder.typeindicatorView = (TextView)convertView.findViewById(R.id.punch_type_text);
            viewHolder.resultView = (ImageView)convertView.findViewById(R.id.result);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TrainingResultPunchDTO punchDTO = getItem(position);

        viewHolder.typeView.setText(punchDTO.getPunchtype());
        viewHolder.typeindicatorView.setText(punchDTO.getPunchKey());
        viewHolder.forceView.setText(punchDTO.getForce() + " LBS");
        viewHolder.speedView.setText(punchDTO.getSpeed() + " MPH");

        if (punchDTO.getSuccess()){
            viewHolder.resultView.setBackgroundResource(R.drawable.green_btn);
            viewHolder.speedView.setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.forceView.setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.typeView.setTextColor(mContext.getResources().getColor(R.color.punches_text_color));
        }else{
            viewHolder.resultView.setBackgroundResource(R.drawable.red_btn);
            viewHolder.speedView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
            viewHolder.forceView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
            viewHolder.typeView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
        }

        return convertView;
    }

    public static class ViewHolder {

//        public LinearLayout missedView;
        public TextView typeView, speedView, forceView, typeindicatorView;
        public ImageView resultView;
    }
}
