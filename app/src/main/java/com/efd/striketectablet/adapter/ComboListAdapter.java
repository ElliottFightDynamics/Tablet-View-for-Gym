package com.efd.striketectablet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.combination.CombinationFragment;
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class ComboListAdapter extends ArrayAdapter<ComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<ComboDTO> comboLists;
    private SparseBooleanArray checkedList = new SparseBooleanArray();


    public ComboListAdapter(Context context, ArrayList<ComboDTO> comboLists){
        super(context, 0, comboLists);

        mContext = context;
        this.comboLists = comboLists;
        this.mainActivity = (MainActivity)context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<ComboDTO> comboLists){
        this.comboLists = comboLists;
        initCheck();
    }

    @Nullable
    @Override
    public ComboDTO getItem(int position) {
        return comboLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_combolist, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.comboNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
//            viewHolder.comboRangeView = (CustomTextView)convertView.findViewById(R.id.combo_range);
            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.combo_settings);
            viewHolder.checkView = (CheckedTextView)convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final ComboDTO comboDTO = getItem(position);

        if (position % 2 == 0){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.comboset_bg));
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        viewHolder.comboNameView.setText(comboDTO.getName());
        viewHolder.comboStringView.setText(comboDTO.getCombos());

        Boolean checked = checkedList.get(position);
        viewHolder.checkView.setChecked(checked);

//        if (comboDTO.getRange() == 0){
//            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.shortrange));
//        }else if(comboDTO.getRange() == 1){
//            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.midrange));
//        }else {
//            viewHolder.comboRangeView.setText(mContext.getResources().getString(R.string.longrange));
//        }

        viewHolder.settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings(position);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCheck(position);
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView comboNameView, comboStringView;//, comboRangeView;
        public ImageView settingsView;
        public CheckedTextView checkView;
    }

    public ArrayList<Integer> getCheckedPositions() {
        ArrayList<Integer> checkedPositions = new ArrayList<Integer>();

        for (int i = 0; i < checkedList.size(); i++) {
            if (checkedList.get(i)) {
                checkedPositions.add(i);
            }
        }

        return checkedPositions;
    }

    private void initCheck(){
        for (int i = 0; i< comboLists.size(); i++){
            checkedList.put(i, false);
        }
    }

    private void toggleCheck(int pos){
        if (checkedList.get(pos)) {
            checkedList.put(pos, false);
        } else {
            checkedList.put(pos, true);
        }

        notifyDataSetChanged();

    }

    public void showSettings(final int position){
        final Dialog dialog = new Dialog(mainActivity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comboset);

        final CustomTextView  editView, deleteView;
//        shareView = (CustomTextView)dialog.findViewById(R.id.combo_share);
//        shareView.setText(mainActivity.getResources().getString(R.string.share));
        editView = (CustomTextView)dialog.findViewById(R.id.combo_edit);
        editView.setText(mainActivity.getResources().getString(R.string.edit));

        deleteView = (CustomTextView)dialog.findViewById(R.id.combo_delete);
        deleteView.setText(mainActivity.getResources().getString(R.string.delete));

//        shareView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (comboDTO != null){
//                    StatisticUtil.showToastMessage("Share Combo: " + comboDTO.getName());
//                }else {
//                    StatisticUtil.showToastMessage("Invalid Data");
//                }
//
//                dialog.dismiss();
//            }
//        });

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editComboIntent = new Intent(mainActivity, NewCombinationActivity.class);
                editComboIntent.putExtra(EFDConstants.EDIT_COMBINATION, true);
                editComboIntent.putExtra(EFDConstants.EDIT_COMBOPOSITION, position);
                mainActivity.startActivity(editComboIntent);

                mainActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                dialog.dismiss();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ComboDTO> comboDTOs = SharedPreferencesUtils.getSavedCombinationList(mainActivity);
                comboDTOs.remove(position);
                SharedPreferencesUtils.saveCombinationList(mainActivity, comboDTOs);

                comboLists.remove(position);
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

