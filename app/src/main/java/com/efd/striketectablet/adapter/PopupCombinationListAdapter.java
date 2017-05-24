package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class PopupCombinationListAdapter extends ArrayAdapter<ComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<ComboDTO> comboLists;

    public PopupCombinationListAdapter(Context context, ArrayList<ComboDTO> comboLists){
        super(context, 0, comboLists);

        mContext = context;
        this.comboLists = comboLists;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<ComboDTO> comboLists){
        this.comboLists = comboLists;
    }

    @Override
    public int getCount() {
        return comboLists.size() ;
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
            convertView = inflater.inflate(R.layout.item_addcombo_popup_child, null);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (TextView)convertView.findViewById(R.id.combo_name);
            viewHolder.stringView = (TextView)convertView.findViewById(R.id.combo_string);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ComboDTO comboDTO = getItem(position);
        viewHolder.nameView.setText(comboDTO.getName());
        viewHolder.stringView.setText(comboDTO.getCombos());

        return convertView;
    }

    public static class ViewHolder {

        public TextView nameView, stringView;
    }
}

