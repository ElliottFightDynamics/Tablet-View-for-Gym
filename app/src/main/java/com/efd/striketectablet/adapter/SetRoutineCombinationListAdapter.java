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
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class SetRoutineCombinationListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<Integer> comboIDLists;

    public SetRoutineCombinationListAdapter(Context context, ArrayList<Integer> comboIDLists){
        super(context, 0, comboIDLists);

        mContext = context;
        this.comboIDLists = comboIDLists;
        this.mainActivity = (MainActivity)context;

        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Integer> comboLists){
        this.comboIDLists = comboLists;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return comboIDLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_setroutine_detail_row, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.comboNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final Integer comboID = getItem(position);
        ComboDTO comboDTO = ComboSetUtil.getComboDtowithID(comboID);

        viewHolder.comboNameView.setText(comboDTO.getName());
        viewHolder.comboStringView.setText(comboDTO.getCombos());

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView comboNameView, comboStringView;//, comboRangeView;
        public ImageView settingsView;
    }
}

