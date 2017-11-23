package com.striketec.fanapp.view.events.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.events.EventLocationInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This adapter class is used to display the list of event location on Create Event screen step 1.
 */

public class EventLocationListAdapter extends RecyclerView.Adapter<EventLocationListAdapter.MyViewHolder> {

    private Context mContext;
    private List<EventLocationInfo> mEventLocationInfoList;
    private OnItemClickListener mOnItemClickListener;
    private EventLocationInfo mLastSelectedEventLocationInfo;

    public EventLocationListAdapter(Context mContext, List<EventLocationInfo> mEventLocationInfoList, EventLocationInfo mLastSelectedEventLocationInfo,
                                    OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mEventLocationInfoList = mEventLocationInfoList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mLastSelectedEventLocationInfo = mLastSelectedEventLocationInfo;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_location_list_row_item, parent, false);

        return new MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final EventLocationInfo mEventLocationInfo = mEventLocationInfoList.get(position);
        holder.mEventLocationName.setText(mEventLocationInfo.getLocationName());
        // initially no item selected
        if (mLastSelectedEventLocationInfo == null) {
            holder.mEventLocationName.setTextColor(mContext.getResources().getColor(R.color.label_text_color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
            holder.mRowOuterRelativeLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
        } else if (mEventLocationInfo.getId() == mLastSelectedEventLocationInfo.getId()) {
            holder.mEventLocationName.setTextColor(mContext.getResources().getColor(R.color.color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_add));
            holder.mRowOuterRelativeLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_red));
        } else {
            holder.mEventLocationName.setTextColor(mContext.getResources().getColor(R.color.label_text_color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
            holder.mRowOuterRelativeLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
        }
        holder.mRowOuterRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtils.showToast(mContext, position + "-" + mEventLocationInfo);
                mOnItemClickListener.onItemClick(position, mEventLocationInfo);
                mLastSelectedEventLocationInfo = mEventLocationInfo;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEventLocationInfoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, EventLocationInfo eventLocationInfo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRowOuterRelativeLayout;
        private ImageView mSelectUnselectImg;
        private TextView mEventLocationName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRowOuterRelativeLayout = itemView.findViewById(R.id.outer_linear_layout);
            mSelectUnselectImg = itemView.findViewById(R.id.img_select_unselect_icon);
            mEventLocationName = itemView.findViewById(R.id.tv_event_location_name);
        }
    }
}
