package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.customview.CustomTextView;

import java.util.ArrayList;

public class PresetListAdapter extends ArrayAdapter<PresetDTO> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<PresetDTO> presetList;
    private int currentPresetPosition;
//
    public PresetListAdapter(Context context, ArrayList<PresetDTO> presetList, int currentPresetPosition){
        super(context, 0, presetList);

        mContext = context;
        this.currentPresetPosition = currentPresetPosition;
        this.presetList = presetList;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<PresetDTO> presetList){
        this.presetList = presetList;
    }

    @Override
    public int getCount() {
        return presetList.size() + 1;
    }

    @Nullable
    @Override
    public PresetDTO getItem(int position) {
        return presetList.get(position);
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
            convertView = inflater.inflate(R.layout.item_preset_popup_child, null);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (CustomTextView)convertView.findViewById(R.id.preset_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (position == 0){
            viewHolder.nameView.setText("Save new");
        }else {
            PresetDTO presetDTO = presetList.get(position - 1);
            viewHolder.nameView.setText(presetDTO.getName());
        }

        if (position == currentPresetPosition + 1){
            viewHolder.nameView.setBackgroundColor(mContext.getResources().getColor(R.color.preset_selected_bg));
        }else {
            viewHolder.nameView.setBackgroundColor(mContext.getResources().getColor(R.color.preset_popup_bg));
        }

        return convertView;
    }

    public static class ViewHolder {

        public CustomTextView nameView;
    }
}

