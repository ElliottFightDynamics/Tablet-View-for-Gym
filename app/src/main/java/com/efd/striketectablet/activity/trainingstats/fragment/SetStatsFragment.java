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
import com.efd.striketectablet.activity.trainingstats.adapter.SetExpandableListAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultCombosAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultPunchesAdapter;

import java.util.ArrayList;


public class SetStatsFragment extends Fragment {

    View view;
    TextView setNameView;

    ListView comboListView, resultView;
    LinearLayout resultParent;

    ArrayList<TrainingResultComboDTO>  comboResults;
    ArrayList<TrainingResultPunchDTO>  punchDTOs;

    TrainingResultCombosAdapter combosAdapter;
    TrainingResultPunchesAdapter punchesAdapter;

    MainActivity mainActivity;


    public static SetStatsFragment setStatsFragment;

    public static Fragment newInstance() {
        setStatsFragment = new SetStatsFragment();
        return setStatsFragment;
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

        view = inflater.inflate(R.layout.fragment_setstats, container, false);
        comboResults = new ArrayList<>();
        punchDTOs = new ArrayList<>();

        resultParent = (LinearLayout)view.findViewById(R.id.resultview);

        setNameView = (TextView)view.findViewById(R.id.set_name);
        setNameView.setText("");

        comboListView = (ListView) view.findViewById(R.id.combolistview);
        resultView = (ListView)view.findViewById(R.id.punches_listview);

        punchesAdapter = new TrainingResultPunchesAdapter(getActivity(), punchDTOs);
        combosAdapter = new TrainingResultCombosAdapter(getActivity(), comboResults, this, true);

        comboListView.setAdapter(combosAdapter);
        resultView.setAdapter(punchesAdapter);

//        combolistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
//        {
//            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
//            {
//                combolistView.expandGroup(itemPosition);
//                return true;
//            }
//        });

        return view;
    }

//    private void loadComboResult(){
//        ArrayList<TrainingResultPunchDTO> punchDTOs = new ArrayList<>();
//
//        TrainingResultPunchDTO punchDTO1 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
//        TrainingResultPunchDTO punchDTO2 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
//        TrainingResultPunchDTO punchDTO3 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
//        TrainingResultPunchDTO punchDTO4 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
//        TrainingResultPunchDTO punchDTO5 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);
//        TrainingResultPunchDTO punchDTO6 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
//        TrainingResultPunchDTO punchDTO7 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
//        TrainingResultPunchDTO punchDTO8 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
//        TrainingResultPunchDTO punchDTO9 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
//        TrainingResultPunchDTO punchDTO10 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);
//        TrainingResultPunchDTO punchDTO11 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
//        TrainingResultPunchDTO punchDTO12 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
//        TrainingResultPunchDTO punchDTO13 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
//        TrainingResultPunchDTO punchDTO14 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
//        TrainingResultPunchDTO punchDTO15 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);
//
//        punchDTOs.add(punchDTO1);
//        punchDTOs.add(punchDTO2);
//        punchDTOs.add(punchDTO3);
//        punchDTOs.add(punchDTO4);
//        punchDTOs.add(punchDTO5);
//        punchDTOs.add(punchDTO6);
//        punchDTOs.add(punchDTO7);
//        punchDTOs.add(punchDTO8);
//        punchDTOs.add(punchDTO9);
//        punchDTOs.add(punchDTO10);
//        punchDTOs.add(punchDTO11);
//        punchDTOs.add(punchDTO12);
//        punchDTOs.add(punchDTO13);
//        punchDTOs.add(punchDTO14);
//        punchDTOs.add(punchDTO15);
//        punchDTOs.add(punchDTO1);
//        punchDTOs.add(punchDTO2);
//        punchDTOs.add(punchDTO3);
//        punchDTOs.add(punchDTO4);
//        punchDTOs.add(punchDTO5);
//        punchDTOs.add(punchDTO6);
//        punchDTOs.add(punchDTO7);
//        punchDTOs.add(punchDTO8);
//        punchDTOs.add(punchDTO9);
//        punchDTOs.add(punchDTO10);
//        punchDTOs.add(punchDTO11);
//        punchDTOs.add(punchDTO12);
//        punchDTOs.add(punchDTO13);
//        punchDTOs.add(punchDTO14);
//        punchDTOs.add(punchDTO15);
//        punchDTOs.add(punchDTO1);
//        punchDTOs.add(punchDTO2);
//        punchDTOs.add(punchDTO3);
//        punchDTOs.add(punchDTO4);
//        punchDTOs.add(punchDTO5);
//        punchDTOs.add(punchDTO6);
//        punchDTOs.add(punchDTO7);
//        punchDTOs.add(punchDTO8);
//        punchDTOs.add(punchDTO9);
//        punchDTOs.add(punchDTO10);
//        punchDTOs.add(punchDTO11);
//        punchDTOs.add(punchDTO12);
//        punchDTOs.add(punchDTO13);
//        punchDTOs.add(punchDTO14);
//        punchDTOs.add(punchDTO15);
//
//        TrainingResultComboDTO comboDTO = new TrainingResultComboDTO("Combo 1", punchDTOs);
//
//        ArrayList<TrainingResultPunchDTO> punchDTOs1 = new ArrayList<>();
//
//        TrainingResultPunchDTO punchDTO111 = new TrainingResultPunchDTO("Right Jab", "1", 200, 50, true);
//        TrainingResultPunchDTO punchDTO21 = new TrainingResultPunchDTO("Right Jab", "2", 200, 50, false);
//        TrainingResultPunchDTO punchDTO31 = new TrainingResultPunchDTO("Right Jab", "3", 200, 50, false);
//        TrainingResultPunchDTO punchDTO41 = new TrainingResultPunchDTO("Right Jab", "4", 200, 50, true);
//        TrainingResultPunchDTO punchDTO51 = new TrainingResultPunchDTO("Right Jab", "5", 200, 50, true);
//
//        punchDTOs1.add(punchDTO111);
//        punchDTOs1.add(punchDTO21);
//        punchDTOs1.add(punchDTO31);
//        punchDTOs1.add(punchDTO41);
//        punchDTOs1.add(punchDTO51);
//
//        TrainingResultComboDTO comboDTO1 = new TrainingResultComboDTO("Combo 2", punchDTOs1, "3023-23-23");
//
//        ArrayList<TrainingResultComboDTO> comboDTOs = new ArrayList<>();
//        comboDTOs.add(comboDTO);
//        comboDTOs.add(comboDTO1);
//
//        setNameView.setText("SET 1");
//
//        comboResults.clear();
//        comboResults.addAll(comboDTOs);
//        combosAdapter.setData(comboResults);
//        combosAdapter.notifyDataSetChanged();
//    }

    private void loadSetResult(){
        if (mainActivity.trainingResultSetDTO != null){
            comboResults.clear();

            setNameView.setText(mainActivity.trainingResultSetDTO.getSetname());

            comboResults.addAll(mainActivity.trainingResultSetDTO.getCombos());
            combosAdapter.setData(comboResults);
            combosAdapter.notifyDataSetChanged();
            resultParent.setVisibility(View.VISIBLE);
        }else {
            setNameView.setText("Set Training is Empty");
            resultParent.setVisibility(View.INVISIBLE);
        }
    }

    public void updateResult(TrainingResultComboDTO resultComboDTO){
        if (punchDTOs.size() > 0){
            punchDTOs.clear();
        }


        punchDTOs.addAll(resultComboDTO.getPunches());
        punchesAdapter.setData(punchDTOs);

        Log.e("Super", "set stats update result = " + resultComboDTO.getPunches().size() + "   " + punchesAdapter.getCount());

        punchesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSetResult();
        Log.e("Super", "stat on resume");
//        loadComboResult();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
