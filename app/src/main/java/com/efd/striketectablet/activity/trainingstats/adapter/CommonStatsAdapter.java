package com.efd.striketectablet.activity.trainingstats.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.efd.striketectablet.R;
import com.efd.striketectablet.customview.CustomTextView;

import java.util.ArrayList;

public class CommonStatsAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> keyLists;
    private ArrayList<String> valueLists;
    LayoutInflater inflater;

    public CommonStatsAdapter(Context context, ArrayList<String> keyLists, ArrayList<String> values) {
        super(context, 0, keyLists);

        mContext = context;

        this.keyLists = keyLists;
        this.valueLists = values;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<String> keyLists, ArrayList<String> values){
        this.keyLists = keyLists;
        this.valueLists = values;
    }

    @Override
    public int getCount() {
        return keyLists.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return keyLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_common_stats_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (RelativeLayout)convertView.findViewById(R.id.parent);
            viewHolder.keyView = (CustomTextView)convertView.findViewById(R.id.key);
            viewHolder.valueView = (CustomTextView)convertView.findViewById(R.id.value);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position % 2 == 0){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.list_odd_item));
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        viewHolder.keyView.setText(keyLists.get(position));
        viewHolder.valueView.setText(valueLists.get(position));

        if(keyLists.get(position).equalsIgnoreCase("max speed") || keyLists.get(position).equalsIgnoreCase("max power")){
            viewHolder.keyView.setTextColor(mContext.getResources().getColor(R.color.force_text_color));
        }else if (keyLists.get(position).equalsIgnoreCase("min speed") || keyLists.get(position).equalsIgnoreCase("min power")){
            viewHolder.keyView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
        }else {
            viewHolder.keyView.setTextColor(mContext.getResources().getColor(R.color.punches_text_color));
        }

        return convertView;
    }

    public static class ViewHolder {

        public RelativeLayout parentView;
        public CustomTextView keyView, valueView;
    }
}
