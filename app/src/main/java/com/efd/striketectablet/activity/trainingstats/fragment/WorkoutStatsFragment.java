package com.efd.striketectablet.activity.trainingstats.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultPunchDTO;
import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.DTO.TrainingResultWorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultCombosAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultPunchesAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultRoundListAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.WorkoutSpinnerAdapter;
import com.efd.striketectablet.util.ComboSetUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class WorkoutStatsFragment extends Fragment {

    View view;
    TextView workoutNameView;
    ListView roundlistview, combolistview, punchlistView;
    LinearLayout resultView;
    Spinner workoutSpinner;

    ArrayList<TrainingResultWorkoutDTO>  workoutResults;
    ArrayList<TrainingResultSetDTO>  roundResults;
    ArrayList<TrainingResultComboDTO> comboResults;
    ArrayList<TrainingResultPunchDTO> punchesResults;

    TrainingResultRoundListAdapter roundListAdapter;
    TrainingResultCombosAdapter combosAdapter;
    TrainingResultPunchesAdapter punchesAdapter;

    WorkoutSpinnerAdapter workoutSpinnerAdapter;

    MainActivity mainActivity;


    public static WorkoutStatsFragment workoutStatsFragment;

    public static Fragment newInstance() {
        workoutStatsFragment = new WorkoutStatsFragment();
        return workoutStatsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = MainActivity.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workoutstats, container, false);
        workoutResults = new ArrayList<>();
        roundResults = new ArrayList<>();
        comboResults = new ArrayList<>();
        punchesResults = new ArrayList<>();

        resultView = (LinearLayout)view.findViewById(R.id.resultview);
        workoutNameView = (TextView)view.findViewById(R.id.workout_name);
        workoutNameView.setText("");

        roundlistview = (ListView) view.findViewById(R.id.round_listview);
        combolistview = (ListView)view.findViewById(R.id.combo_listview);
        punchlistView = (ListView)view.findViewById(R.id.punches_listview);

        roundListAdapter = new TrainingResultRoundListAdapter(getActivity(), roundResults, this);
        roundlistview.setAdapter(roundListAdapter);

        combosAdapter = new TrainingResultCombosAdapter(getActivity(), comboResults, this, false);
        combolistview.setAdapter(combosAdapter);

        punchesAdapter = new TrainingResultPunchesAdapter(getActivity(), punchesResults);
        punchlistView.setAdapter(punchesAdapter);

        workoutSpinner = (Spinner)view.findViewById(R.id.workout_spinner);
        workoutSpinnerAdapter = new WorkoutSpinnerAdapter(getActivity(), workoutResults);
        workoutSpinner.setAdapter(workoutSpinnerAdapter);

        workoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateWorkout();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    public void updateResult(TrainingResultComboDTO resultComboDTO){
        if (punchesResults.size() > 0){
            punchesResults.clear();
        }

        punchesResults.addAll(resultComboDTO.getPunches());
        punchesAdapter.setData(punchesResults);
        punchesAdapter.notifyDataSetChanged();
    }

    public void updateCombos(TrainingResultSetDTO resultSetDTO){
        if (comboResults.size() > 0){
            comboResults.clear();
        }

        comboResults.addAll(resultSetDTO.getCombos());
        combosAdapter.setCurrentPosition(0);
        combosAdapter.setData(comboResults);
        combosAdapter.notifyDataSetChanged();
    }

    private void updateWorkout(){
        TrainingResultWorkoutDTO workoutDTO = (TrainingResultWorkoutDTO)workoutSpinner.getSelectedItem();
        if (workoutDTO != null && workoutDTO.getRoundcombos() != null && workoutDTO.getRoundcombos().size() > 0){
            Log.e("Super", "update workout = " + workoutDTO.getRoundcombos().get(0).getCombos().size());
        }

        roundResults.clear();
        roundListAdapter.setCurrentPosition(0);
        roundResults.addAll(workoutDTO.getRoundcombos());
        roundListAdapter.notifyDataSetChanged();
    }

    private void loadWorkoutResult(){
        String currentDay = TrainingStatsFragment.trainingStatsFragment.getCurrentSelectedDay();

        ArrayList<TrainingResultWorkoutDTO> resultWorkoutDTOs = ComboSetUtil.getWorkoutstatsforDay(MainActivity.db, currentDay);

        for (int i = 0; i < resultWorkoutDTOs.size(); i++){
            Log.e("Super", "workoutname  = " + resultWorkoutDTOs.get(i).getWorkoutname());
        }

        if (resultWorkoutDTOs.size() > 0){
            Collections.sort(resultWorkoutDTOs, WORKOUT_COMPARATOR);
            workoutResults.clear();
            workoutResults.addAll(resultWorkoutDTOs);

            workoutSpinnerAdapter.notifyDataSetChanged();
            workoutSpinner.setSelection(0);
            updateWorkout();
            workoutNameView.setVisibility(View.GONE);
            resultView.setVisibility(View.VISIBLE);

//            updateWorkout((TrainingResultWorkoutDTO)workoutSpinner.getSelectedItem());
        }else {
            resultView.setVisibility(View.GONE);
            workoutNameView.setText("Workout Training is Empty");
        }

//        if (mainActivity.trainingResultWorkoutDTO != null){
//            roundResults.clear();
//
//            workoutNameView.setText(mainActivity.trainingResultWorkoutDTO.getWorkoutname());
//            roundResults.addAll(mainActivity.trainingResultWorkoutDTO.getRoundcombos());
//            roundListAdapter.notifyDataSetChanged();
//            resultView.setVisibility(View.VISIBLE);
//        }else {
//            workoutNameView.setText("Workout Training is Empty");
//            resultView.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkoutResult();
        Log.e("Super", "workout on resume");
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private Comparator<TrainingResultWorkoutDTO> WORKOUT_COMPARATOR = new Comparator<TrainingResultWorkoutDTO>() {
        @Override
        public int compare(TrainingResultWorkoutDTO lhs, TrainingResultWorkoutDTO rhs) {
            return (int) (Long.parseLong(rhs.getTime()) - Long.parseLong(lhs.getTime()));
        }
    };

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
