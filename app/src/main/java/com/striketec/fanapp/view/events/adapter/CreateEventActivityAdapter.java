package com.striketec.fanapp.view.events.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.utils.DialogUtils;
import com.striketec.fanapp.view.events.CreateEventActivityInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 21-11-2017.
 * This adapter class is used to display the list of activities on Create Event Step 2 page.
 */

public class CreateEventActivityAdapter extends RecyclerView.Adapter<CreateEventActivityAdapter.MyViewHolder> {

    private Context mContext;
    private List<CreateEventActivityInfo> mCreateEventActivityInfoList;
    private OnItemClickListener mOnItemClickListener;
    private CreateEventActivityInfo mLastSelectedActivityInfo;

    public CreateEventActivityAdapter(Context mContext, List<CreateEventActivityInfo> mCreateEventActivityInfoList, CreateEventActivityInfo mLastSelectedActivityInfo, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mCreateEventActivityInfoList = mCreateEventActivityInfoList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mLastSelectedActivityInfo = mLastSelectedActivityInfo;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_event_activity_row_item, parent, false);

        return new MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CreateEventActivityInfo mActivityInfo = mCreateEventActivityInfoList.get(position);
        holder.mActivityNameText.setText(mActivityInfo.getActivityName());
        holder.mActivityDescriptionText.setText(mActivityInfo.getDescription());
        holder.mActivityImg.setImageResource(mActivityInfo.getActivityDrawable());
        // initially no item selected
        if (mLastSelectedActivityInfo != null && mActivityInfo.getActivityName() == mLastSelectedActivityInfo.getActivityName()) {
            holder.mActivityNameText.setTextColor(mContext.getResources().getColor(R.color.color_1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mSelectUnselectImg.setBackground(mContext.getResources().getDrawable(android.R.drawable.ic_menu_add));
                holder.mRowInnerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_red));
            } else {
                holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_add));
                holder.mRowInnerLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_red));
            }
        } else {
            holder.mActivityNameText.setTextColor(mContext.getResources().getColor(R.color.label_text_color_1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mSelectUnselectImg.setBackground(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
                holder.mRowInnerLayout.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
            } else {
                holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
                holder.mRowInnerLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
            }
        }
        holder.mRowInnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showToast(mContext, position + "-" + mActivityInfo.getActivityName());
                mOnItemClickListener.onItemClick(position, mActivityInfo);
                mLastSelectedActivityInfo = mActivityInfo;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCreateEventActivityInfoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, CreateEventActivityInfo activityInfo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRowInnerLayout;
        private ImageView mSelectUnselectImg, mActivityImg;
        private TextView mActivityNameText, mActivityDescriptionText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRowInnerLayout = itemView.findViewById(R.id.inner_layout);
            mSelectUnselectImg = itemView.findViewById(R.id.img_select_unselect_icon);
            mActivityImg = itemView.findViewById(R.id.img_create_event_activity);
            mActivityNameText = itemView.findViewById(R.id.tv_create_event_activity_name);
            mActivityDescriptionText = itemView.findViewById(R.id.tv_activity_description);
        }
    }
}
