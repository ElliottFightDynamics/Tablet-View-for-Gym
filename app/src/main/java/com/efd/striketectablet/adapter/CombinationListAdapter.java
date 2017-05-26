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
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.activity.training.sets.SetsFragment;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class CombinationListAdapter extends ArrayAdapter<ComboDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;

    ArrayList<ComboDTO> comboLists;
    private int currentPosition = 0;

    public CombinationListAdapter(Context context, ArrayList<ComboDTO> comboLists){
        super(context, 0, comboLists);

        mContext = context;
        this.comboLists = comboLists;
        this.mainActivity = (MainActivity)context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<ComboDTO> comboLists){
        this.comboLists = comboLists;
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
            convertView = inflater.inflate(R.layout.item_combincation_row, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.combo_parent);
            viewHolder.comboNameView = (CustomTextView)convertView.findViewById(R.id.combo_name);
            viewHolder.comboStringView = (CustomTextView)convertView.findViewById(R.id.combo_string);
            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.combo_settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final ComboDTO comboDTO = getItem(position);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolor));
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        viewHolder.comboNameView.setText(comboDTO.getName());
        viewHolder.comboStringView.setText(comboDTO.getCombos());

        viewHolder.settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings(comboDTO);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition != position) {
                    currentPosition = position;
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView comboNameView, comboStringView;//, comboRangeView;
        public ImageView settingsView;
    }

    public void showSettings(final ComboDTO comboDTO){
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
        editView = (CustomTextView)dialog.findViewById(R.id.combo_edit);
        editView.setText(mainActivity.getResources().getString(R.string.edit));

        deleteView = (CustomTextView)dialog.findViewById(R.id.combo_delete);
        deleteView.setText(mainActivity.getResources().getString(R.string.delete));

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editComboIntent = new Intent(mainActivity, NewCombinationActivity.class);
                editComboIntent.putExtra(EFDConstants.EDIT_COMBINATION, true);
                editComboIntent.putExtra(EFDConstants.EDIT_COMBOID, comboDTO.getId());
                mainActivity.startActivity(editComboIntent);

                mainActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                dialog.dismiss();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComboSetUtil.deleteComboDto(comboDTO);

                comboLists.remove(comboDTO);
                if (currentPosition >= comboLists.size())
                    currentPosition = 0;

                notifyDataSetChanged();
                dialog.dismiss();

                //delete combo from set
                ComboSetUtil.deleteComboFromAllSets(comboDTO);
                ComboSetUtil.deleteComboFromAllWorkout(comboDTO);

                if (SetsFragment.setsFragment != null)
                    SetsFragment.setsFragment.onResume();
            }
        });

        dialog.show();
    }
}

