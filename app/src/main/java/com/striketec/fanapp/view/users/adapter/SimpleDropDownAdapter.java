package com.striketec.fanapp.view.users.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.striketec.fanapp.R;

import java.util.List;

/**
 * Created by Sukhbirs on 10-11-2017.
 * This adapter class is used to display the drop down items without Cancel/Ok buttons.
 */

public class SimpleDropDownAdapter extends RecyclerView.Adapter<SimpleDropDownAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mDropDownItemsList;
    private OnItemClickListener mOnItemClickListener;
    private String mLastSelectedDropDownItem;

    public SimpleDropDownAdapter(Context mContext, List<String> mDropDownItemsList, String mLastSelectedDropDownItem, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mDropDownItemsList = mDropDownItemsList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mLastSelectedDropDownItem = mLastSelectedDropDownItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_drop_down_row_item, parent, false);

        return new MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String mDropDownItemName = mDropDownItemsList.get(position);
        holder.mDropDownRowItemTV.setText(mDropDownItemName);
        holder.mRowOuterRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtils.showToast(mContext, position + "-" + mDropDownItemName);
                mOnItemClickListener.onItemClick(position, mDropDownItemName);
                mLastSelectedDropDownItem = mDropDownItemName;
                notifyDataSetChanged();
            }
        });
        if (mDropDownItemName.equals(mLastSelectedDropDownItem)) {
            holder.mDropDownRowItemTV.setTextColor(mContext.getResources().getColor(R.color.color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_add));
        } else {
            holder.mDropDownRowItemTV.setTextColor(mContext.getResources().getColor(R.color.label_text_color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
        }
    }

    @Override
    public int getItemCount() {
        return mDropDownItemsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String itemName);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRowOuterRelativeLayout;
        private ImageView mSelectUnselectImg;
        private TextView mDropDownRowItemTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRowOuterRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.outer_linear_layout);
            mSelectUnselectImg = (ImageView) itemView.findViewById(R.id.img_select_unselect_icon);
            mDropDownRowItemTV = (TextView) itemView.findViewById(R.id.tv_drop_down_item_name);
        }
    }
}
