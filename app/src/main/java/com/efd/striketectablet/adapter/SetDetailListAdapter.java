package com.efd.striketectablet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;

import java.util.ArrayList;

public class SetDetailListAdapter extends ArrayAdapter<ComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<ComboDTO> comboLists;

    public SetDetailListAdapter(Context context, ArrayList<ComboDTO> comboLists){
        super(context, 0, comboLists);

        mContext = context;
        this.comboLists = comboLists;
        this.mainActivity = (MainActivity)context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<ComboDTO> comboLists){
        this.comboLists = comboLists;
    }

    @Nullable
    @Override
    public ComboDTO getItem(int position) {
        return comboLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_setdetaillist, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.comboNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
            viewHolder.comboRangeView = (CustomTextView)convertView.findViewById(R.id.combo_range);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final ComboDTO comboDTO = getItem(position);

//        if (position % 2 == 0){
//            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.comboset_bg));
//        }else {
//            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
//        }

        viewHolder.comboNameView.setText(comboDTO.getName());
        viewHolder.comboStringView.setText(comboDTO.getCombos());

        if (comboDTO.getRange() == 0){
            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.shortrange));
        }else if(comboDTO.getRange() == 1){
            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.midrange));
        }else {
            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.longrange));
        }

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView comboNameView, comboStringView, comboRangeView;
    }
}

