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
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.sets.NewSetRoutineActivity;
import com.efd.striketectablet.activity.training.sets.SetsFragment;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class SetListAdapter extends ArrayAdapter<SetsDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;
    SetsFragment setsFragment;

    ArrayList<SetsDTO> setLists;
    private SparseBooleanArray checkedList = new SparseBooleanArray();

    private int currentPosition = 0;

    public SetListAdapter(Context context, ArrayList<SetsDTO> setLists, SetsFragment setsFragment){
        super(context, 0, setLists);

        mContext = context;
        this.setLists = setLists;
        this.mainActivity = (MainActivity)context;
        this.setsFragment = setsFragment;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<SetsDTO> setLists){
        this.setLists = setLists;
        initCheck();
    }

    @Nullable
    @Override
    public SetsDTO getItem(int position) {
        return setLists.get(position);
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
            convertView = inflater.inflate(R.layout.item_setlist, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.set_parent);
            viewHolder.setNameView = (CustomTextView)convertView.findViewById(R.id.set_name);
            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.set_settings);
            viewHolder.checkView = (CheckedTextView)convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final SetsDTO setsDTO = getItem(position);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolor));
            setsFragment.updateDetail(setsDTO);
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

//        viewHolder.comboNameView.setText(comboDTO.getName());
//        viewHolder.comboStringView.setText(comboDTO.getCombos());

        viewHolder.setNameView.setText(setsDTO.getName());

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
                currentPosition = position;
                toggleCheck(position);
            }
        });

        return convertView;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView setNameView;
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
        for (int i = 0; i< setLists.size(); i++){
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

        CustomTextView editView, deleteView;
//        shareView = (CustomTextView)dialog.findViewById(R.id.combo_share);
//        shareView.setText(mainActivity.getResources().getString(R.string.share));
        editView = (CustomTextView)dialog.findViewById(R.id.combo_edit);
        editView.setText(mainActivity.getResources().getString(R.string.edit));

        deleteView = (CustomTextView)dialog.findViewById(R.id.combo_delete);
        deleteView.setText(mainActivity.getResources().getString(R.string.delete));

//        shareView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (setsDTO != null){
//                    StatisticUtil.showToastMessage("Share Set: " + setsDTO.getName());
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
                Intent editSetIntent = new Intent(mainActivity, NewSetRoutineActivity.class);
                editSetIntent.putExtra(EFDConstants.EDIT_SETS, true);
                editSetIntent.putExtra(EFDConstants.EDIT_SETPOSITION, position);
                mainActivity.startActivity(editSetIntent);
                mainActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                dialog.dismiss();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(mainActivity);
                setsDTOs.remove(position);
                SharedPreferencesUtils.saveSetList(mainActivity, setsDTOs);

                setsDTOs.remove(position);
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

