package com.efd.striketectablet.activity.trainingstats.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultPunchDTO;
import com.efd.striketectablet.R;

import java.util.ArrayList;

public class SetExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<TrainingResultComboDTO> comboResults;

    public SetExpandableListAdapter(Context context, ArrayList<TrainingResultComboDTO> comboResults) {
        mContext = context;
        this.comboResults = comboResults;
    }

    public void setData (ArrayList<TrainingResultComboDTO> comboResults){
        this.comboResults = comboResults;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return comboResults.get(groupPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }




    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder holder;

        if (convertView == null) {
            holder = new ChildHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.level_third_group, null);

            holder.punchListView = (ListView)convertView.findViewById(R.id.punchlistview);
//            holder.typeView = (CustomTextView)convertView.findViewById(R.id.punch_type);
//            holder.speedView = (CustomTextView)convertView.findViewById(R.id.speed_value);
//            holder.forceView = (CustomTextView)convertView.findViewById(R.id.force_value);
//            holder.typeindicatorView = (CustomTextView)convertView.findViewById(R.id.punch_type_text);
//            holder.resultView = (ImageView)convertView.findViewById(R.id.result);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder)convertView.getTag();
        }


        TrainingResultPunchesAdapter adapter = new TrainingResultPunchesAdapter(mContext, comboResults.get(groupPosition).getPunches());
        holder.punchListView.setAdapter(adapter);

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.punchListView.getLayoutParams();
//        Log.e("Super", "punch size = " + groupPosition + "   " + comboResults.get(groupPosition).getPunches().size() + "   " + params.height);
//        params.height = 1000;
//        holder.punchListView.setLayoutParams(params);

        TrainingResultPunchDTO punchDTO = comboResults.get(groupPosition).getPunches().get(childPosition);
//
//        holder.typeView.setText(punchDTO.getPunchtype());
//        holder.typeindicatorView.setText(punchDTO.getPunchKey());
//        holder.forceView.setText(punchDTO.getForce() + " LBS");
//        holder.speedView.setText(punchDTO.getSpeed() + " MPH");
//
//        if (punchDTO.getSuccess()){
//            holder.resultView.setBackgroundResource(R.drawable.green_btn);
//            holder.speedView.setTextColor(mContext.getResources().getColor(R.color.white));
//            holder.forceView.setTextColor(mContext.getResources().getColor(R.color.white));
//            holder.typeView.setTextColor(mContext.getResources().getColor(R.color.punches_text_color));
//        }else{
//            holder.resultView.setBackgroundResource(R.drawable.red_btn);
//            holder.speedView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
//            holder.forceView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
//            holder.typeView.setTextColor(mContext.getResources().getColor(R.color.speed_text_color));
//        }

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;//comboResults.get(groupPosition).getPunches().size();
    }

    public Object getGroup(int groupPosition) {
        return comboResults.get(groupPosition);
    }

    public int getGroupCount() {
        return comboResults.size();
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
            convertView = infalInflater.inflate(R.layout.level_first_group, null);
            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
            holder.comboName = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder)convertView.getTag();
        }

        Log.e("Super", "punch size 1111= " + groupPosition + "   " + comboResults.get(groupPosition).getPunches().size());
        holder.comboName.setText(comboResults.get(groupPosition).getComboname());

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class GroupHolder {
        public TextView comboName;
    }

    public static class ChildHolder {
        public ListView punchListView;
//        public CustomTextView typeView, speedView, forceView, typeindicatorView;
//        public ImageView resultView;
    }
}
