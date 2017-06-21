package com.efd.striketectablet.activity.trainingstats.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultPunchDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultPunchesAdapter;

import java.util.ArrayList;


public class ComboStatsFragment extends Fragment {

    View view;
    TextView comboNameView;
    ListView punchlistView;

    ArrayList<TrainingResultPunchDTO>  punchDTOs;

    TrainingResultPunchesAdapter adapter;

    MainActivity mainActivity;


    public static ComboStatsFragment comboStatsFragment;

    public static Fragment newInstance() {
        comboStatsFragment = new ComboStatsFragment();
        return comboStatsFragment;
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

        view = inflater.inflate(R.layout.fragment_combostats, container, false);
        punchDTOs = new ArrayList<>();

        comboNameView = (TextView)view.findViewById(R.id.combo_name);
        comboNameView.setText("");

        punchlistView = (ListView)view.findViewById(R.id.punches_listview);

        adapter = new TrainingResultPunchesAdapter(getActivity(), punchDTOs);
        punchlistView.setAdapter(adapter);

        return view;
    }

    private void loadComboResult(){
        if (mainActivity.trainingresultComboDTO != null){
            punchDTOs.clear();

            comboNameView.setText(mainActivity.trainingresultComboDTO.getComboname());

            punchDTOs.addAll(mainActivity.trainingresultComboDTO.getPunches());
            adapter.notifyDataSetChanged();
        }else {
            comboNameView.setText("Combo Training is Empty");
        }
    }

//    private void loadResult(){
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
//        adapter.setData(comboDTO.getPunches());
//        adapter.notifyDataSetChanged();
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        loadComboResult();
        Log.e("Super", "combo on resume");
//        loadResult();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
