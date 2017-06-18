package com.efd.striketectablet.activity.trainingstats.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.R;

import java.util.ArrayList;

public class WorkoutExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<TrainingResultSetDTO> roundResults;

    public WorkoutExpandableListAdapter(Context context, ArrayList<TrainingResultSetDTO> roundResults) {
        mContext = context;
        this.roundResults = roundResults;
    }

    public void setData (ArrayList<TrainingResultSetDTO> roundResults){
        this.roundResults = roundResults;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return roundResults.get(groupPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildHolder holder;

        if (convertView == null) {
            holder = new ChildHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.level_second_expand, null);
            holder.comboListView = (ExpandableListView)convertView.findViewById(R.id.roundlistview);


            convertView.setTag(holder);
        } else {
            holder = (ChildHolder)convertView.getTag();
        }

        holder.comboListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
            {
                holder.comboListView.expandGroup(itemPosition);
                return true;
            }
        });


        SetExpandableListAdapter adapter = new SetExpandableListAdapter(mContext, roundResults.get(groupPosition).getCombos());
        holder.comboListView.setAdapter(adapter);

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;//comboResults.get(groupPosition).getPunches().size();
    }

    public Object getGroup(int groupPosition) {
        return roundResults.get(groupPosition);
    }

    public int getGroupCount() {
        return roundResults.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        GroupHolder holder;

        if (convertView == null) {
            holder = new GroupHolder();
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.level_second_group, null);
            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
            holder.roundName = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder)convertView.getTag();
        }

        holder.roundName.setText(roundResults.get(groupPosition).getSetname());

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class GroupHolder {
        public TextView roundName;
    }

    public static class ChildHolder {
        public ExpandableListView comboListView;
    }
}
