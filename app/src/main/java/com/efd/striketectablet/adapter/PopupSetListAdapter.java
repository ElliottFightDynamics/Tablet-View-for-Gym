package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class PopupSetListAdapter extends ArrayAdapter<SetsDTO> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<SetsDTO> setlists;

    public PopupSetListAdapter(Context context, ArrayList<SetsDTO> setlists){
        super(context, 0, setlists);

        mContext = context;
        this.setlists = setlists;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<SetsDTO> keylist){
        this.setlists = keylist;
    }

    @Override
    public int getCount() {
        return setlists.size() ;
    }

    @Nullable
    @Override
    public SetsDTO getItem(int position) {
        return setlists.get(position);
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
            convertView = inflater.inflate(R.layout.item_addpunch_popup_child, null);
            viewHolder = new ViewHolder();
            viewHolder.nameView = (CustomTextView)convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        SetsDTO set = getItem(position);
        viewHolder.nameView.setText(set.getName());

        return convertView;
    }

    public static class ViewHolder {

        public CustomTextView nameView;
    }
}

