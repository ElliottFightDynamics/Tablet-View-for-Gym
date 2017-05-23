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
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class PunchListAdapter extends ArrayAdapter<String> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<String> keylist;
    private int currentPresetPosition;
//
    public PunchListAdapter(Context context, ArrayList<String> keylist){
        super(context, 0, keylist);

        mContext = context;
        this.currentPresetPosition = currentPresetPosition;
        this.keylist = keylist;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<String> keylist){
        this.keylist = keylist;
    }

    @Override
    public int getCount() {
        return keylist.size() ;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return keylist.get(position);
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
            convertView = inflater.inflate(R.layout.item_addcomboset_popup_child, null);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (CustomTextView)convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        String key = getItem(position);
        String content = key + " - " + ComboSetUtil.punchTypeMap.get(key);
        viewHolder.nameView.setText(content);

        return convertView;
    }

    public static class ViewHolder {

        public CustomTextView nameView;
    }
}

