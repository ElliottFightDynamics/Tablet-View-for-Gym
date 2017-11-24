package com.striketec.fanapp.view.users.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.striketec.fanapp.R;
import com.striketec.fanapp.model.users.dto.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sukhbirs on 13-11-2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context mContext;
    private List<UserInfo> mUserInfoList, mSelectedUserInfoList;

    public UsersAdapter(Context mContext, List<UserInfo> mUserInfoList) {
        this.mContext = mContext;
        this.mUserInfoList = mUserInfoList;
        mSelectedUserInfoList = new ArrayList<>();
    }

    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new UsersAdapter.MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final UserInfo mUserInfo = mUserInfoList.get(position);
        holder.mUserNameTV.setText(mUserInfo.getFirstName() + " " + mUserInfo.getLastName());
        holder.mUserEmailTV.setText(mUserInfo.getEmail());
        holder.mUserRowOuterLayout.setBackgroundDrawable(null);
        holder.mUserImage.setBackgroundDrawable(null);
        if (mUserInfo.isSelected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mUserRowOuterLayout.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_red));
            } else {
                holder.mUserRowOuterLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_red));
            }
            holder.mUserNameTV.setTextColor(mContext.getResources().getColor(R.color.color_1));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mUserRowOuterLayout.setBackground(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
            } else {
                holder.mUserRowOuterLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_round_corner_with_outline_grey));
            }
            holder.mUserNameTV.setTextColor(mContext.getResources().getColor(R.color.user_name_text_color));
        }
        holder.mUserRowOuterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInfo.isSelected()) {
                    mUserInfo.setSelected(false);
                    mSelectedUserInfoList.remove(mUserInfo);
                } else {
                    mUserInfo.setSelected(true);
                    mSelectedUserInfoList.add(mUserInfo);
                }
                notifyDataSetChanged();
            }
        });
        // User profile picture
        if (mUserInfo.getPhotoUrl() != null && mUserInfo.getPhotoUrl().length() > 0) {
            Glide.with(mContext).load(mUserInfo.getPhotoUrl()).into(holder.mUserImage);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.mUserImage.setBackground(mContext.getResources().getDrawable(R.drawable.avatar));
            } else {
                holder.mUserImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.avatar));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUserInfoList.size();
    }

    /**
     * Method to get list of selected users.
     *
     * @return
     */
    public List<UserInfo> getmSelectedUserInfoList() {
        return mSelectedUserInfoList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUserImage;
        private TextView mUserNameTV, mUserEmailTV;
        private RelativeLayout mUserRowOuterLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mUserImage = itemView.findViewById(R.id.img_user);
            mUserNameTV = itemView.findViewById(R.id.tv_user_name);
            mUserEmailTV = itemView.findViewById(R.id.tv_user_email);
            mUserRowOuterLayout = itemView.findViewById(R.id.user_row_item_outer_layout);
        }
    }
}
