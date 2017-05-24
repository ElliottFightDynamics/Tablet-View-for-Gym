package com.efd.striketectablet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.training.sets.NewSetRoutineActivity;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class EditNewSetRoutineListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Integer> idLists;
    NewSetRoutineActivity setRoutineActivity;

    public EditNewSetRoutineListAdapter(Context context, ArrayList<Integer> idLists){
        super(context, 0, idLists);

        mContext = context;
        setRoutineActivity = (NewSetRoutineActivity)context;
        this.idLists = idLists;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<Integer> positionLists){
        this.idLists = positionLists;
    }

    @Override
    public int getCount() {
        return idLists.size() ;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return idLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_addcombo_list, null);
            viewHolder = new ViewHolder();
            viewHolder.stringView = (TextView)convertView.findViewById(R.id.combo_string);
            viewHolder.nameView = (TextView)convertView.findViewById(R.id.combo_name);
            viewHolder.divider = (ImageView)convertView.findViewById(R.id.divider);
            viewHolder.settings = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        int comboID = getItem(position);
        ComboDTO comboDTO = ComboSetUtil.getComboDtowithID(comboID);

        viewHolder.nameView.setText(comboDTO.getName());
        viewHolder.stringView.setText(comboDTO.getCombos());

        viewHolder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRoutineActivity.showSettings(position);
            }
        });

        if (position == idLists.size() - 1){
            viewHolder.divider.setVisibility(View.GONE);
        }else
            viewHolder.divider.setVisibility(View.VISIBLE);

        return convertView;
    }

    public static class ViewHolder {

        public TextView nameView, stringView;
        public ImageView divider, settings;
    }
}

