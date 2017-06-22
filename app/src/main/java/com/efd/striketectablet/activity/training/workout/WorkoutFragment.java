package com.efd.striketectablet.activity.training.workout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.ComboSetTrainingActivity;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.PresetListAdapter;
import com.efd.striketectablet.adapter.SetRoutineCombinationListAdapter;
import com.efd.striketectablet.adapter.WorkoutListAdapter;
import com.efd.striketectablet.adapter.WorkoutRoundListAdapter;
import com.efd.striketectablet.adapter.WorkoutSetListAdapter;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class WorkoutFragment extends Fragment {

    View view;
    TextView roundTimeView, restTimeView, preparTimeView, warningTimeView, glovesView, totalTimeView;
    Button starttrainingBtn;

    ListView workoutListView, roundListView, comboListView;
    WorkoutListAdapter workoutListAdapter;
    WorkoutRoundListAdapter workoutRoundListAdapter;
//    WorkoutSetListAdapter workoutSetListAdapter;

    SetRoutineCombinationListAdapter workoutComboListAdapter;

    ArrayList<WorkoutDTO> workoutDTOs;
    ArrayList<Integer> roundNums;
    ArrayList<Integer> comboIDs;

    MainActivity mainActivityInstance;

    private static Context mContext;
    public static WorkoutFragment workoutFragment;

    public static Fragment newInstance(Context context) {
        mContext = context;
        workoutFragment = new WorkoutFragment();

        return workoutFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivityInstance = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workout, container, false);

        workoutDTOs = new ArrayList<>();
        roundNums = new ArrayList<>();
        comboIDs = new ArrayList<>();

        initViews();

        return view;
    }

    private void initViews(){

        roundTimeView = (TextView)view.findViewById(R.id.round_time);
        restTimeView = (TextView)view.findViewById(R.id.rest_time);
        preparTimeView = (TextView)view.findViewById(R.id.prepare_time);
        warningTimeView = (TextView)view.findViewById(R.id.warning_time);
        glovesView = (TextView)view.findViewById(R.id.glovevalue);
        totalTimeView = (TextView)view.findViewById(R.id.total_time);

        workoutListView = (ListView)view.findViewById(R.id.workout_listview);
        roundListView = (ListView)view.findViewById(R.id.round_listview);
        comboListView = (ListView)view.findViewById(R.id.combo_listview);

        workoutListAdapter = new WorkoutListAdapter(getActivity(), workoutDTOs, this);
        workoutRoundListAdapter = new WorkoutRoundListAdapter(getActivity(), roundNums, this);
//        workoutSetListAdapter = new WorkoutSetListAdapter(getActivity(), setIDs);
        workoutComboListAdapter =  new SetRoutineCombinationListAdapter(mainActivityInstance, comboIDs);
        workoutListView.setAdapter(workoutListAdapter);
        roundListView.setAdapter(workoutRoundListAdapter);
        comboListView.setAdapter(workoutComboListAdapter);

        updateTime(null);

        starttrainingBtn = (Button)view.findViewById(R.id.training_start_button);
        starttrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutTraining();
            }
        });
    }

    private void startWorkoutTraining(){
        if (workoutDTOs.size() == 0){
            return;
        }

        Intent combosetIntent = new Intent(getActivity(), ComboSetTrainingActivity.class);
        combosetIntent.putExtra(EFDConstants.TRAININGTYPE, EFDConstants.WORKOUT);
        combosetIntent.putExtra(EFDConstants.WORKOUT_ID, workoutListAdapter.getItem(workoutListAdapter.getCurrentPosition()));
        getActivity().startActivity(combosetIntent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void updateTime(WorkoutDTO workoutDTO){

        if (workoutDTO != null) {

            roundTimeView.setText(PresetUtil.timeList.get(workoutDTO.getRound()));
            restTimeView.setText(PresetUtil.timeList.get(workoutDTO.getRest()));
            preparTimeView.setText(PresetUtil.timeList.get(workoutDTO.getPrepare()));
            warningTimeView.setText(PresetUtil.warningTimewithSecList.get(workoutDTO.getWarning()));
            glovesView.setText(PresetUtil.gloveList.get(workoutDTO.getGlove()));

            setTotalTime(workoutDTO);
        }else {
            roundTimeView.setText("0");
            restTimeView.setText("0");
            preparTimeView.setText("0");
            warningTimeView.setText("0");
            glovesView.setText("0");
            totalTimeView.setText("0");
        }
    }

    private void setTotalTime(WorkoutDTO workoutDTO){
        int round = workoutDTO.getRoundcount();
        int roundtime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getRound()));
        int resttime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getRest()));
        int preparetime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getPrepare()));

        String totalTime = PresetUtil.changeSecondsToHours(round * (roundtime + resttime) + preparetime);
        totalTimeView.setText(totalTime);
    }

    private void loadWorkout() {

        if (workoutDTOs.size() > 0) {
            workoutDTOs.clear();
        }

        workoutDTOs.addAll(SharedPreferencesUtils.getSavedWorkouts(mainActivityInstance));
        workoutListAdapter.notifyDataSetChanged();

        if (workoutDTOs.size() == 0) {
            updateRound(null);
        }
    }

    public void updateRound(WorkoutDTO workoutDTO){

        if (roundNums.size() > 0)
            roundNums.clear();

        updateTime(workoutDTO);

        if (workoutDTO == null) {
            workoutRoundListAdapter.notifyDataSetChanged();
            return;
        }

        workoutRoundListAdapter.setCurrentPosition(0);
        workoutRoundListAdapter.setWorkoutDTO(workoutDTO);

//        workoutRoundListAdapter.setWorkoutDTO(workoutDTO);

        roundNums.addAll(generateRoundNumArray(workoutDTO.getRoundcount()));
        workoutRoundListAdapter.notifyDataSetChanged();
    }

    public void updateSet(WorkoutDTO workoutDTO, int position){
        if (comboIDs.size() > 0)
            comboIDs.clear();

        if (workoutDTO == null)
            workoutComboListAdapter.notifyDataSetChanged();

        ArrayList<Integer> ids = workoutDTO.getRoundsetIDs().get(position);
        comboIDs.addAll(ids);

        workoutComboListAdapter.notifyDataSetChanged();
    }

    private ArrayList<Integer> generateRoundNumArray(int size){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++){
            result.add(i);
        }

        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadWorkout();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

