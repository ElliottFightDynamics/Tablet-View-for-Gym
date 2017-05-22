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
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.training.combination.CombinationFragment;
import com.efd.striketectablet.customview.CustomTextView;

import java.util.ArrayList;

public class ComboListAdapter extends ArrayAdapter<ComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    CombinationFragment combinationFragment;

    public ComboListAdapter(Context context, ArrayList<ComboDTO> comboLists, CombinationFragment combinationFragment){
        super(context, 0, comboLists);

        mContext = context;
        this.combinationFragment = combinationFragment;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = inflater.inflate(R.layout.item_combolist, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.comboNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
            viewHolder.comboRangeView = (CustomTextView)convertView.findViewById(R.id.combo_range);
            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.combo_settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final ComboDTO comboDTO = getItem(position);

        if (position % 2 == 0){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.comboset_bg));
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        viewHolder.comboNameView.setText(comboDTO.getName());
        viewHolder.comboStringView.setText(comboDTO.getCombos());

        if (comboDTO.getRange() == 0){
            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.shortrange));
        }else if(comboDTO.getRange() == 1){
            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.midrange));
        }else {
            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.longrange));
        }

        viewHolder.settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                combinationFragment.showSettings(comboDTO);
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView comboNameView, comboStringView, comboRangeView;
        public ImageView settingsView;
    }
}

