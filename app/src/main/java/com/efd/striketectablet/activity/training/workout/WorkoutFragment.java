package com.efd.striketectablet.activity.training.workout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.PresetListAdapter;
import com.efd.striketectablet.adapter.WorkoutListAdapter;
import com.efd.striketectablet.adapter.WorkoutRoundListAdapter;
import com.efd.striketectablet.adapter.WorkoutSetListAdapter;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class WorkoutFragment extends Fragment {

    View view;
    TextView roundTimeView, restTimeView, preparTimeView, warningTimeView, glovesView, totalTimeView;

    ListView workoutListView, roundListView, setListView;
    WorkoutListAdapter workoutListAdapter;
    WorkoutRoundListAdapter workoutRoundListAdapter;
    WorkoutSetListAdapter workoutSetListAdapter;

    ArrayList<WorkoutDTO> workoutDTOs;
    ArrayList<Integer> roundNums;
    ArrayList<Integer> setIDs;



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
        setIDs = new ArrayList<>();

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
        setListView = (ListView)view.findViewById(R.id.set_listview);

        workoutListAdapter = new WorkoutListAdapter(getActivity(), workoutDTOs, this);
        workoutRoundListAdapter = new WorkoutRoundListAdapter(getActivity(), roundNums, this);
        workoutSetListAdapter = new WorkoutSetListAdapter(getActivity(), setIDs);

        workoutListView.setAdapter(workoutListAdapter);
        roundListView.setAdapter(workoutRoundListAdapter);
        setListView.setAdapter(workoutSetListAdapter);

        updateTime(null);
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

        if (workoutDTO == null)
            workoutRoundListAdapter.notifyDataSetChanged();

        updateTime(workoutDTO);

        workoutRoundListAdapter.setWorkoutDTO(workoutDTO);

//        workoutRoundListAdapter.setWorkoutDTO(workoutDTO);

        roundNums.addAll(generateRoundNumArray(workoutDTO.getRoundcount()));
        workoutRoundListAdapter.notifyDataSetChanged();
    }

    public void updateSet(WorkoutDTO workoutDTO, int position){
        if (setIDs.size() > 0)
            setIDs.clear();

        if (workoutDTO == null)
            workoutSetListAdapter.notifyDataSetChanged();

        ArrayList<Integer> ids = workoutDTO.getRoundsetIDs().get(position);
        setIDs.addAll(ids);

        workoutSetListAdapter.notifyDataSetChanged();
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
}

