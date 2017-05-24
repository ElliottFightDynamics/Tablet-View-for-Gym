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

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.util.ComboSetUtil;

import java.util.ArrayList;

public class EditNewCombinationListAdapter extends ArrayAdapter<String> {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<String> keylist;
    NewCombinationActivity combinationActivity;
//
    public EditNewCombinationListAdapter(Context context, ArrayList<String> keylist){
        super(context, 0, keylist);

        mContext = context;
        combinationActivity = (NewCombinationActivity)context;
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
            convertView = inflater.inflate(R.layout.item_addpunch_list, null);
            viewHolder = new ViewHolder();
            viewHolder.keyView = (TextView)convertView.findViewById(R.id.key);
            viewHolder.nameView = (TextView)convertView.findViewById(R.id.name);
            viewHolder.divider = (ImageView)convertView.findViewById(R.id.divider);
            viewHolder.settings = (ImageView)convertView.findViewById(R.id.settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        String key = getItem(position);
        viewHolder.keyView.setText(key);
        viewHolder.nameView.setText(ComboSetUtil.punchTypeMap.get(key));

        viewHolder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                combinationActivity.showSettings(position);
            }
        });

        if (position == keylist.size() - 1){
            viewHolder.divider.setVisibility(View.GONE);
        }else
            viewHolder.divider.setVisibility(View.VISIBLE);

        return convertView;
    }

    public static class ViewHolder {

        public TextView keyView, nameView;
        public ImageView divider, settings;
    }
}

