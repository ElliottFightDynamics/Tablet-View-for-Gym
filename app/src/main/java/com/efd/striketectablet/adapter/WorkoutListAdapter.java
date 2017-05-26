package com.efd.striketectablet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.sets.NewSetRoutineActivity;
import com.efd.striketectablet.activity.training.sets.SetsFragment;
import com.efd.striketectablet.activity.training.workout.NewWorkoutActivity;
import com.efd.striketectablet.activity.training.workout.WorkoutFragment;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;

public class WorkoutListAdapter extends ArrayAdapter<WorkoutDTO> {

    Context mContext;
    LayoutInflater inflater;
    MainActivity mainActivity;
    WorkoutFragment workoutFragment;

    ArrayList<WorkoutDTO> workoutDTOs;

    private int currentPosition = 0;

    public WorkoutListAdapter(Context context, ArrayList<WorkoutDTO> workoutDTOs, WorkoutFragment workoutFragment){
        super(context, 0, workoutDTOs);

        mContext = context;
        this.workoutDTOs = workoutDTOs;
        this.mainActivity = (MainActivity)context;
        this.workoutFragment = workoutFragment;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<WorkoutDTO> workoutDTOs){
        this.workoutDTOs = workoutDTOs;
    }

    @Nullable
    @Override
    public WorkoutDTO getItem(int position) {
        return workoutDTOs.get(position);
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
            convertView = inflater.inflate(R.layout.item_setroutine_row, null);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (LinearLayout)convertView.findViewById(R.id.set_parent);
            viewHolder.workoutNameView = (CustomTextView)convertView.findViewById(R.id.set_name);
            viewHolder.settingsView = (ImageView)convertView.findViewById(R.id.set_settings);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final WorkoutDTO workoutDTO = getItem(position);

        if (currentPosition == position){
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.set_selectcolor));
            workoutFragment.updateRound(workoutDTO);
        }else {
            viewHolder.parentView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        viewHolder.workoutNameView.setText(workoutDTO.getName());

        viewHolder.settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings(workoutDTO);

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

    public static class ViewHolder {

        public LinearLayout parentView;
        public CustomTextView workoutNameView;
        public ImageView settingsView;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public void showSettings(final WorkoutDTO workoutDTO){
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
        editView = (CustomTextView)dialog.findViewById(R.id.combo_edit);
        editView.setText(mainActivity.getResources().getString(R.string.edit));

        deleteView = (CustomTextView)dialog.findViewById(R.id.combo_delete);
        deleteView.setText(mainActivity.getResources().getString(R.string.delete));

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editSetIntent = new Intent(mainActivity, NewWorkoutActivity.class);
                editSetIntent.putExtra(EFDConstants.EDIT_WORKOUT, true);
                editSetIntent.putExtra(EFDConstants.EDIT_WORKOUTID, workoutDTO.getId());
                mainActivity.startActivity(editSetIntent);
                mainActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                dialog.dismiss();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComboSetUtil.deleteWorkoutDto(workoutDTO);
                workoutDTOs.remove(workoutDTO);

                if (currentPosition >= workoutDTOs.size())
                    currentPosition = 0;
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

