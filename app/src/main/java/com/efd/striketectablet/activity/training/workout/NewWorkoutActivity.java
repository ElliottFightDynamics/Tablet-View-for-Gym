package com.efd.striketectablet.activity.training.workout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.EditNewSetRoutineListAdapter;
import com.efd.striketectablet.adapter.EditNewWorkoutListAdapter;
import com.efd.striketectablet.adapter.PopupCombinationListAdapter;
import com.efd.striketectablet.adapter.PopupSetListAdapter;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.mmaGlove.Integrator;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class NewWorkoutActivity extends AppCompatActivity {

    public static final int ROUND_TIME = 1;
    public static final int REST_TIME = 2;
    public static final int PREPARE_TIME = 3;
    public static final int WARNING_TIME = 4;

    TextView titleView;
    ImageView btnBack;
    TextView saveBtn;
    EditText workoutName;

    TextView roundTimeView, restTimeView, prepareTimeView, warningTimeView, totalTimeView ;
    LinearLayout roundParent, restParent, prepareParent, warningParent;
    Spinner   gloveTypeSpinner;
    SpinnerAdapter gloveAdapter;

    HorizontalScrollView horizontalScrollView;
    LinearLayout workflowParent;

//    ListView combodetailListView;
//    EditNewSetRoutineListAdapter detailAdapter;

    boolean editmode = false;
    int workoutID = -1;

    int currentRoundTimePosition = 0;
    int currentRestPosition = 0;
    int currentPreparePosition = 0;
    int currentWarningPosition = 0;

    ArrayList<ArrayList<Integer>> roundsetLists;
    ArrayList<EditNewWorkoutListAdapter> detailAdapterList;
    int currentRoundPosition = 0;

    private ArrayList<SetsDTO> savedSets;
    private ArrayList<ImageView> plusViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_workout);

        editmode = getIntent().getBooleanExtra(EFDConstants.EDIT_WORKOUT, false);
        if (editmode){
            workoutID = getIntent().getIntExtra(EFDConstants.EDIT_WORKOUTID, -1);
        }

        savedSets = SharedPreferencesUtils.getSavedSetList(this);
        roundsetLists = new ArrayList<>();
        detailAdapterList = new ArrayList<>();
        plusViewList = new ArrayList<>();

        initViews();
    }

    private void initViews(){
        titleView = (TextView)findViewById(R.id.workout_title);
        if (editmode){
            titleView.setText(getResources().getString(R.string.edit_workout_title));
        }else {
            titleView.setText(getResources().getString(R.string.new_workout_title));
        }

        workoutName = (EditText)findViewById(R.id.workout_name);

        btnBack = (ImageView)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn = (TextView)findViewById(R.id.savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });

        currentRoundTimePosition = PresetUtil.timeList.size() / 2;
        currentRestPosition = PresetUtil.timeList.size() / 2;
        currentPreparePosition = PresetUtil.timeList.size() / 2;
        currentWarningPosition = PresetUtil.warningList.size() / 2;

        roundTimeView = (TextView)findViewById(R.id.round_time);
        roundParent = (LinearLayout)findViewById(R.id.round_parent);
        roundParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker(ROUND_TIME);
            }
        });

        restTimeView = (TextView)findViewById(R.id.rest_time);
        restParent = (LinearLayout)findViewById(R.id.rest_parent);
        restParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker(REST_TIME);
            }
        });

        prepareTimeView = (TextView)findViewById(R.id.prepare_time);
        prepareParent = (LinearLayout)findViewById(R.id.prepare_parent);
        prepareParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker(PREPARE_TIME);
            }
        });

        warningTimeView = (TextView)findViewById(R.id.warning_time);
        warningParent = (LinearLayout)findViewById(R.id.warning_parent);
        warningParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker(WARNING_TIME);
            }
        });

        //set glove spinner
        gloveTypeSpinner = (Spinner)findViewById(R.id.glove_spinner);
        gloveAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_with_img, PresetUtil.gloveList, EFDConstants.SPINNER_WHITE);
        gloveTypeSpinner.setAdapter(gloveAdapter);

        totalTimeView = (TextView)findViewById(R.id.total_time);

        roundTimeView.setText(PresetUtil.timeList.get(currentRoundTimePosition));
        restTimeView.setText(PresetUtil.timeList.get(currentRestPosition));
        prepareTimeView.setText(PresetUtil.timeList.get(currentPreparePosition));
        warningTimeView.setText(PresetUtil.warningTimewithSecList.get(currentWarningPosition));

        horizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalview);
        workflowParent = (LinearLayout)findViewById(R.id.workflow_parent);

        saveBtn.setBackgroundResource(R.drawable.orange_btn_bg);
        saveBtn.setTextColor(getResources().getColor(R.color.orange));

        setTotalTime();
    }

    private void addNewRoundWorkflow(final int roundNum){
        if (roundNum > 0){
            plusViewList.get(roundNum-1).setVisibility(View.INVISIBLE);
        }
        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_roundworkout_row, null);

        final ArrayList<Integer> setLists = new ArrayList<>();
        roundsetLists.add(roundNum, setLists);

        TextView roundNameView, addSetView;
        final ImageView plusView;
        ListView setListView;

        roundNameView = (TextView)newLayout.findViewById(R.id.roundname);
        addSetView = (TextView)newLayout.findViewById(R.id.addset);
        plusView = (ImageView)newLayout.findViewById(R.id.addround);
        setListView = (ListView)newLayout.findViewById(R.id.setlistview);
        EditNewWorkoutListAdapter detailAdapter = new EditNewWorkoutListAdapter(NewWorkoutActivity.this, setLists, roundNum);
        setListView.setAdapter(detailAdapter);
        detailAdapterList.add(roundNum, detailAdapter);
        plusViewList.add(plusView);

        plusView.setEnabled(false);

        roundNameView.setText("ROUND " + (roundNum + 1));

        plusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRoundPosition++;
                addNewRoundWorkflow(currentRoundPosition);
            }
        });

        addSetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetListDialog(setLists.size(), false, roundNum);
            }
        });

        workflowParent.addView(newLayout, roundNum);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLayout.setLayoutParams(params);

        horizontalScrollView.postDelayed(new Runnable() {
            public void run() {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    private void showWheelPicker(final int type){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_prepare_warning_picker);
        dialog.setCancelable(false);

        //set prepare picker
        WheelView wheelPicker = (WheelView)dialog.findViewById(R.id.preparewarning_picker);
        ArrayList dataList;

        int currentposition = 0;

        switch (type){
            case ROUND_TIME:
                dataList = PresetUtil.timeList;
                currentposition = currentRoundTimePosition;
                break;

            case REST_TIME:
                dataList = PresetUtil.timeList;
                currentposition = currentRestPosition;
                break;

            case PREPARE_TIME:
                dataList = PresetUtil.timeList;
                currentposition = currentPreparePosition;
                break;

            case WARNING_TIME:
                dataList = PresetUtil.warningTimewithSecList;
                currentposition = currentWarningPosition;
                break;
            default:
                dataList = PresetUtil.timeList;
        }

        ArrayWheelAdapter wheelAdapter = new ArrayWheelAdapter(NewWorkoutActivity.this, dataList.toArray());

        wheelAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        wheelAdapter.setItemTextResource(R.id.text);
        wheelAdapter.setActiveTextColor(getResources().getColor(R.color.prepare_select));
        wheelAdapter.setDeactiveTextColor(getResources().getColor(R.color.prepare_unselect));
        wheelAdapter.setActiveTextSzie(45);
        wheelAdapter.setDeactiveTextSize(40);
        wheelPicker.setViewAdapter(wheelAdapter);
        wheelPicker.setVisibleItems(3);

        wheelPicker.setCurrentItem(currentposition);

        wheelPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                switch (type) {
                    case ROUND_TIME:
                        currentRoundTimePosition = newValue;
                        roundTimeView.setText(PresetUtil.timeList.get(newValue));
                        setTotalTime();
                        break;

                    case REST_TIME:
                        currentRestPosition = newValue;
                        restTimeView.setText(PresetUtil.timeList.get(newValue));
                        setTotalTime();
                        break;

                    case PREPARE_TIME:
                        currentPreparePosition = newValue;
                        prepareTimeView.setText(PresetUtil.timeList.get(newValue));
                        setTotalTime();
                        break;

                    case WARNING_TIME:
                        currentWarningPosition = newValue;
                        warningTimeView.setText(PresetUtil.warningTimewithSecList.get(newValue));
                        break;
                }
            }
        });

        CustomTextView setBtn = (CustomTextView)dialog.findViewById(R.id.okbtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void setTotalTime(){
        int round = currentRoundPosition + 1;
        int roundtime = Integer.parseInt(PresetUtil.timerwitSecsList.get(currentRoundTimePosition));
        int resttime = Integer.parseInt(PresetUtil.timerwitSecsList.get(currentRestPosition));
        int preparetime = Integer.parseInt(PresetUtil.timerwitSecsList.get(currentPreparePosition));

        String totalTime = PresetUtil.changeSecondsToHours(round * (roundtime + resttime) + preparetime);
        totalTimeView.setText(totalTime);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (editmode){
            loadWorkoutDetail();
        }else {
            addNewRoundWorkflow(currentRoundPosition);
        }
    }

    private void saveWorkout(){

        if (TextUtils.isEmpty(workoutName.getText().toString())){
            StatisticUtil.showToastMessage("Workout name can't be empty");
            return;
        }

        if (roundsetLists.get(0).size() == 0){
            StatisticUtil.showToastMessage("please add more than 1 Set");
            return;
        }

        if (editmode){
            WorkoutDTO workoutDTO = new WorkoutDTO(workoutID, workoutName.getText().toString(), currentRoundPosition + 1, roundsetLists, currentRoundTimePosition, currentRestPosition,
                    currentPreparePosition, currentWarningPosition, gloveTypeSpinner.getSelectedItemPosition());
            ComboSetUtil.updateWorkoutDto(workoutDTO);
        }else {
            WorkoutDTO workoutDTO = new WorkoutDTO(SharedPreferencesUtils.increaseSetID(this), workoutName.getText().toString(), currentRoundPosition + 1, roundsetLists, currentRoundTimePosition, currentRestPosition,
                    currentPreparePosition, currentWarningPosition, gloveTypeSpinner.getSelectedItemPosition());

            ComboSetUtil.addWorkoutDto(workoutDTO);
        }

        finish();
    }

    private void loadWorkoutDetail(){
        WorkoutDTO workoutDTO = ComboSetUtil.getWorkoutDtoWithID(workoutID);

        if (workoutDTO != null){
            workoutName.setText(workoutDTO.getName());

            for (int i = 0; i < workoutDTO.getRoundcount(); i++){
                addNewRoundWorkflow(i);
            }

            if (roundsetLists != null && roundsetLists.size() > 0){
                roundsetLists.clear();
            }

            roundsetLists.addAll(workoutDTO.getRoundsetIDs());

            for (int i = 0; i < workoutDTO.getRoundcount(); i++){
                detailAdapterList.get(i).setData(workoutDTO.getRoundsetIDs().get(i));
                detailAdapterList.get(i).notifyDataSetChanged();
                updatePlusBtn(i);
            }

            currentRoundTimePosition = workoutDTO.getRound();
            currentRestPosition = workoutDTO.getRest();
            currentPreparePosition = workoutDTO.getPrepare();
            currentWarningPosition = workoutDTO.getWarning();

            roundTimeView.setText(PresetUtil.timeList.get(currentRoundTimePosition));
            restTimeView.setText(PresetUtil.timeList.get(currentRestPosition));
            prepareTimeView.setText(PresetUtil.timeList.get(currentPreparePosition));
            warningTimeView.setText(PresetUtil.warningTimewithSecList.get(currentWarningPosition));

            gloveTypeSpinner.setSelection(workoutDTO.getGlove());
        }
    }

//    private void updateSaveBtn(){
//        if (roundsetLists.get(0).size() > 0){
//            saveBtn.setBackgroundResource(R.drawable.orange_btn_bg);
//            saveBtn.setTextColor(getResources().getColor(R.color.orange));
//        }else {
//            saveBtn.setBackgroundResource(R.drawable.trainingstats_btn_fill);
//            saveBtn.setTextColor(getResources().getColor(R.color.black));
//        }
//    }

    private void updatePlusBtn(int roundposition){
        ArrayList<Integer> setLists = roundsetLists.get(roundposition);
        ImageView plusView = plusViewList.get(roundposition);

        if (setLists.size() > 0){
            plusView.setImageResource(R.drawable.plus_active);
            plusView.setEnabled(true);
        }else {
            plusView.setImageResource(R.drawable.plus_inactive);
            plusView.setEnabled(false);
        }

//        if (comboIDLists.size() > 0){
//            saveBtn.setBackgroundResource(R.drawable.orange_btn_bg);
//            saveBtn.setTextColor(getResources().getColor(R.color.orange));
//        }else {
//            saveBtn.setBackgroundResource(R.drawable.trainingstats_btn_fill);
//            saveBtn.setTextColor(getResources().getColor(R.color.black));
//        }
    }

    public void showSettings(final int position, final int roundposition){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_editcomboset);

        final TextView replaceView, insertaboveView, insertbelowView, deleteView;
        replaceView = (TextView)dialog.findViewById(R.id.replace);
        replaceView.setText(getResources().getString(R.string.replace_set));
        insertaboveView = (TextView)dialog.findViewById(R.id.insert_above);
        insertbelowView = (TextView)dialog.findViewById(R.id.insert_below);
        deleteView = (TextView)dialog.findViewById(R.id.delete);

        replaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetListDialog(position, true, roundposition);
                dialog.dismiss();
            }
        });

        insertaboveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetListDialog(position, false, roundposition);
                dialog.dismiss();
            }
        });

        insertbelowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetListDialog(position + 1, false, roundposition);
                dialog.dismiss();
            }
        });


        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> setids = roundsetLists.get(roundposition);
                setids.remove(position);
                detailAdapterList.get(roundposition).notifyDataSetChanged();
                dialog.dismiss();

                updatePlusBtn(roundposition);
                roundsetLists.set(roundposition, setids);
            }
        });

        dialog.show();
    }

    private void showSetListDialog(final int comboposition, final boolean replace, final int roundposition){

        final ArrayList<Integer> setLists = roundsetLists.get(roundposition);

        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_addcombo);

        TextView titleView = (TextView)dialog.findViewById(R.id.titleview);
        titleView.setText(getResources().getString(R.string.dialog_workouttitle));

        TextView cancelView = (TextView)dialog.findViewById(R.id.cancel_btn);
        final ListView setListView = (ListView)dialog.findViewById(R.id.comboset_listview);

        final PopupSetListAdapter adapter = new PopupSetListAdapter(this, savedSets);
        setListView.setAdapter(adapter);

        setListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();

                SetsDTO setsDTO = adapter.getItem(position);
                if (replace){
                    setLists.set(comboposition, setsDTO.getId());
                }else {
                    setLists.add(comboposition, setsDTO.getId());
                }

                roundsetLists.set(roundposition, setLists);

                detailAdapterList.get(roundposition).notifyDataSetChanged();

                updatePlusBtn(roundposition);
            }
        });


        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
