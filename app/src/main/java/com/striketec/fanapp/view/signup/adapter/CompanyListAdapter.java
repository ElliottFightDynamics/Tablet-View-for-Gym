package com.striketec.fanapp.view.signup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.signup.dto.CompanyInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 10-11-2017.
 * This adapter class is used to display the list of companies on new user registration page.
 */

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.MyViewHolder> {

    private Context mContext;
    private List<CompanyInfo> mCompanyInfoList;
    private OnItemClickListener mOnItemClickListener;
    private CompanyInfo mLastSelectedCompanyInfo;

    public CompanyListAdapter(Context mContext, List<CompanyInfo> mCompanyInfoList, CompanyInfo mLastSelectedCompanyInfo, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mCompanyInfoList = mCompanyInfoList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mLastSelectedCompanyInfo = mLastSelectedCompanyInfo;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_list_row_item, parent, false);

        return new MyViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CompanyInfo mCompanyInfo = mCompanyInfoList.get(position);
        holder.mCompanyNameText.setText(mCompanyInfo.getCompanyName());
        // initially no item selected
        if (mLastSelectedCompanyInfo == null) {
            holder.mCompanyNameText.setTextColor(mContext.getResources().getColor(R.color.label_text_color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
        } else if (mCompanyInfo.getId() == mLastSelectedCompanyInfo.getId()) {
            holder.mCompanyNameText.setTextColor(mContext.getResources().getColor(R.color.color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_add));
        } else {
            holder.mCompanyNameText.setTextColor(mContext.getResources().getColor(R.color.label_text_color_1));
            holder.mSelectUnselectImg.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_menu_camera));
        }
        holder.mRowOuterRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogUtils.showToast(mContext, position + "-" + mCompanyInfo);
                mOnItemClickListener.onItemClick(position, mCompanyInfo);
                mLastSelectedCompanyInfo = mCompanyInfo;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCompanyInfoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, CompanyInfo companyInfo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRowOuterRelativeLayout;
        private ImageView mSelectUnselectImg;
        private TextView mCompanyNameText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRowOuterRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.outer_linear_layout);
            mSelectUnselectImg = (ImageView) itemView.findViewById(R.id.img_select_unselect_icon);
            mCompanyNameText = (TextView) itemView.findViewById(R.id.tv_company_name);
        }
    }
}
