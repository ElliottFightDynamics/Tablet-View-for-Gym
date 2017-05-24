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
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.activity.training.sets.NewSetRoutineActivity;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class EditComboListAdapter extends ArrayAdapter<Integer> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Integer> positionLists;
    NewSetRoutineActivity setRoutineActivity;

    ArrayList<ComboDTO> comboLists;
//
    public EditComboListAdapter(Context context, ArrayList<Integer> positionLists){
        super(context, 0, positionLists);

        mContext = context;
        setRoutineActivity = (NewSetRoutineActivity)context;
        this.positionLists = positionLists;
        comboLists = SharedPreferencesUtils.getSavedCombinationList(setRoutineActivity);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData (ArrayList<Integer> positionLists){
        this.positionLists = positionLists;
    }

    @Override
    public int getCount() {
        return positionLists.size() ;
    }

    @Nullable
    @Override
    public Integer getItem(int position) {
        return positionLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_combodetail, null);
            viewHolder = new ViewHolder();
            viewHolder.stringView = (TextView)convertView.findViewById(R.id.combo_string);
            viewHolder.nameView = (TextView)convertView.findViewById(R.id.combo_name);
            viewHolder.divider = (ImageView)convertView.findViewById(R.id.divider);
            viewHolder.settings = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        int comboposition = getItem(position);
        ComboDTO comboDTO = comboLists.get(comboposition);

        viewHolder.nameView.setText(comboDTO.getName());
        viewHolder.stringView.setText(comboDTO.getCombos());

        viewHolder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRoutineActivity.showSettings(position);
            }
        });

        if (position == positionLists.size() - 1){
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

