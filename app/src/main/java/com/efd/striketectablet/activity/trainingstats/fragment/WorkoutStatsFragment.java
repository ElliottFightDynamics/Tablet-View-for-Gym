package com.efd.striketectablet.activity.trainingstats.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultPunchDTO;
import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultCombosAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultPunchesAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultRoundListAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.WorkoutExpandableListAdapter;

import java.util.ArrayList;


public class WorkoutStatsFragment extends Fragment {

    View view;
    TextView workoutNameView;
    ListView roundlistview, combolistview, punchlistView;
    LinearLayout resultView;

    ArrayList<TrainingResultSetDTO>  roundResults;
    ArrayList<TrainingResultComboDTO> comboResults;
    ArrayList<TrainingResultPunchDTO> punchesResults;

    TrainingResultRoundListAdapter roundListAdapter;
    TrainingResultCombosAdapter combosAdapter;
    TrainingResultPunchesAdapter punchesAdapter;

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

//        roundlistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
//        {
//            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
//            {
//                roundlistview.expandGroup(itemPosition);
//                return true;
//            }
//        });

        return view;
    }

    public void updateResult(TrainingResultComboDTO resultComboDTO){
        if (punchesResults.size() > 0){
            punchesResults.clear();
        }

        Log.e("Super", "workout update result");

        punchesResults.addAll(resultComboDTO.getPunches());
        punchesAdapter.setData(punchesResults);
        punchesAdapter.notifyDataSetChanged();
    }

    public void updateCombos(TrainingResultSetDTO resultSetDTO){
        if (comboResults.size() > 0){
            comboResults.clear();
        }

        comboResults.addAll(resultSetDTO.getCombos());
        combosAdapter.setData(comboResults);
        combosAdapter.notifyDataSetChanged();
    }

    private void loadComboResult(){
        ArrayList<TrainingResultPunchDTO> punchDTOs = new ArrayList<>();

        TrainingResultPunchDTO punchDTO1 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
        TrainingResultPunchDTO punchDTO2 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
        TrainingResultPunchDTO punchDTO3 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
        TrainingResultPunchDTO punchDTO4 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
        TrainingResultPunchDTO punchDTO5 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);
        TrainingResultPunchDTO punchDTO6 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
        TrainingResultPunchDTO punchDTO7 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
        TrainingResultPunchDTO punchDTO8 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
        TrainingResultPunchDTO punchDTO9 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
        TrainingResultPunchDTO punchDTO10 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);
        TrainingResultPunchDTO punchDTO11 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
        TrainingResultPunchDTO punchDTO12 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
        TrainingResultPunchDTO punchDTO13 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
        TrainingResultPunchDTO punchDTO14 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
        TrainingResultPunchDTO punchDTO15 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);

        punchDTOs.add(punchDTO1);
        punchDTOs.add(punchDTO2);
        punchDTOs.add(punchDTO3);
        punchDTOs.add(punchDTO4);
        punchDTOs.add(punchDTO5);
        punchDTOs.add(punchDTO6);
        punchDTOs.add(punchDTO7);
        punchDTOs.add(punchDTO8);
        punchDTOs.add(punchDTO9);
        punchDTOs.add(punchDTO10);
        punchDTOs.add(punchDTO11);
        punchDTOs.add(punchDTO12);
        punchDTOs.add(punchDTO13);
        punchDTOs.add(punchDTO14);
        punchDTOs.add(punchDTO15);
        punchDTOs.add(punchDTO1);
        punchDTOs.add(punchDTO2);
        punchDTOs.add(punchDTO3);
        punchDTOs.add(punchDTO4);
        punchDTOs.add(punchDTO5);
        punchDTOs.add(punchDTO6);
        punchDTOs.add(punchDTO7);
        punchDTOs.add(punchDTO8);
        punchDTOs.add(punchDTO9);
        punchDTOs.add(punchDTO10);
        punchDTOs.add(punchDTO11);
        punchDTOs.add(punchDTO12);
        punchDTOs.add(punchDTO13);
        punchDTOs.add(punchDTO14);
        punchDTOs.add(punchDTO15);
        punchDTOs.add(punchDTO1);
        punchDTOs.add(punchDTO2);
        punchDTOs.add(punchDTO3);
        punchDTOs.add(punchDTO4);
        punchDTOs.add(punchDTO5);
        punchDTOs.add(punchDTO6);
        punchDTOs.add(punchDTO7);
        punchDTOs.add(punchDTO8);
        punchDTOs.add(punchDTO9);
        punchDTOs.add(punchDTO10);
        punchDTOs.add(punchDTO11);
        punchDTOs.add(punchDTO12);
        punchDTOs.add(punchDTO13);
        punchDTOs.add(punchDTO14);
        punchDTOs.add(punchDTO15);

        TrainingResultComboDTO comboDTO = new TrainingResultComboDTO("Combo 1", punchDTOs);

        ArrayList<TrainingResultPunchDTO> punchDTOs1 = new ArrayList<>();

        TrainingResultPunchDTO punchDTO111 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
        TrainingResultPunchDTO punchDTO21 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
        TrainingResultPunchDTO punchDTO31 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
        TrainingResultPunchDTO punchDTO41 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
        TrainingResultPunchDTO punchDTO51 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);

        punchDTOs1.add(punchDTO111);
        punchDTOs1.add(punchDTO21);
        punchDTOs1.add(punchDTO31);
        punchDTOs1.add(punchDTO41);
        punchDTOs1.add(punchDTO51);

        TrainingResultComboDTO comboDTO1 = new TrainingResultComboDTO("Combo 2", punchDTOs1);

        ArrayList<TrainingResultComboDTO> comboDTOs = new ArrayList<>();
        comboDTOs.add(comboDTO);
        comboDTOs.add(comboDTO1);


        ArrayList<TrainingResultComboDTO> comboDTOs1 = new ArrayList<>();
        comboDTOs1.add(comboDTO);
        comboDTOs1.add(comboDTO1);
        comboDTOs1.add(comboDTO);
        comboDTOs1.add(comboDTO1);

        workoutNameView.setText("Workout 1");

        roundResults.add(new TrainingResultSetDTO("Round 1", comboDTOs));
        roundResults.add(new TrainingResultSetDTO("Round 2", comboDTOs1));
        roundResults.add(new TrainingResultSetDTO("Round 3", comboDTOs));
        roundResults.add(new TrainingResultSetDTO("Round 4", comboDTOs1));

        roundListAdapter.setData(roundResults);
        roundListAdapter.notifyDataSetChanged();
    }

    private void loadWorkoutResult(){
        if (mainActivity.trainingResultWorkoutDTO != null){
            roundResults.clear();

            workoutNameView.setText(mainActivity.trainingResultWorkoutDTO.getWorkoutname());
            roundResults.addAll(mainActivity.trainingResultWorkoutDTO.getRoundcombos());
            roundListAdapter.notifyDataSetChanged();
            resultView.setVisibility(View.VISIBLE);
        }else {
            workoutNameView.setText("Workout Training is Empty");
            resultView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkoutResult();
//        loadComboResult();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
