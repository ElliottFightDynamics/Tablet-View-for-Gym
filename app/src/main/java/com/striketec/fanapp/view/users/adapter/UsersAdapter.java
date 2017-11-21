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
import com.striketec.fanapp.model.users.dto.UserInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 13-11-2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context mContext;
    private List<UserInfo> mUserInfoList;

    public UsersAdapter(Context mContext, List<UserInfo> mUserInfoList) {
        this.mContext = mContext;
        this.mUserInfoList = mUserInfoList;
    }

    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new UsersAdapter.MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.MyViewHolder holder, int position) {
        final UserInfo mUserInfo = mUserInfoList.get(position);
        holder.mUserNameTV.setText(mUserInfo.getName());
        holder.mUserEmailTV.setText(mUserInfo.getEmail());
        holder.mUserRowOuterLayout.setBackgroundDrawable(null);
        if (mUserInfo.isSelected()) {
            holder.mUserRowOuterLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_red));
            holder.mUserNameTV.setTextColor(mContext.getResources().getColor(R.color.color_1));
        } else {
            holder.mUserRowOuterLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
            holder.mUserNameTV.setTextColor(mContext.getResources().getColor(R.color.user_name_text_color));
        }
        holder.mUserRowOuterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInfo.isSelected()) {
                    mUserInfo.setSelected(false);
                } else {
                    mUserInfo.setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUserImage;
        private TextView mUserNameTV, mUserEmailTV;
        private RelativeLayout mUserRowOuterLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mUserImage = (ImageView) itemView.findViewById(R.id.img_user);
            mUserNameTV = (TextView) itemView.findViewById(R.id.tv_user_name);
            mUserEmailTV = (TextView) itemView.findViewById(R.id.tv_user_email);
            mUserRowOuterLayout = (RelativeLayout) itemView.findViewById(R.id.user_row_item_outer_layout);
        }
    }
}
