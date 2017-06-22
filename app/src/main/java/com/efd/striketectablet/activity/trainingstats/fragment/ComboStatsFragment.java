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
import com.efd.striketectablet.DTO.TrainingStatsPunchTypeInfoDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultCombosAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultCombosNameAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultPunchesAdapter;
import com.efd.striketectablet.util.ComboSetUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ComboStatsFragment extends Fragment {

    View view;
    TextView comboNameView;
    ListView comboListView, punchlistView;
    LinearLayout resultParent;

    ArrayList<TrainingResultComboDTO>  resultComboDTOs;
    ArrayList<TrainingResultPunchDTO>  punchDTOs;

    TrainingResultCombosNameAdapter combosAdapter;
    TrainingResultPunchesAdapter punchesAdapter;

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
        resultComboDTOs = new ArrayList<>();
        punchDTOs = new ArrayList<>();

        comboNameView = (TextView)view.findViewById(R.id.combo_name);
        comboNameView.setText("");

        comboListView = (ListView)view.findViewById(R.id.combolistview);
        punchlistView = (ListView)view.findViewById(R.id.punches_listview);

        punchesAdapter = new TrainingResultPunchesAdapter(getActivity(), punchDTOs);
        punchlistView.setAdapter(punchesAdapter);

        combosAdapter = new TrainingResultCombosNameAdapter(getActivity(), resultComboDTOs, this);
        comboListView.setAdapter(combosAdapter);

        resultParent = (LinearLayout)view.findViewById(R.id.resultview);


        return view;
    }

    private void loadComboResult(){

        String currentDay = TrainingStatsFragment.trainingStatsFragment.getCurrentSelectedDay();

        Log.e("Super", "currentday =" + currentDay);

        ArrayList<TrainingResultComboDTO> resultCombos = ComboSetUtil.getCombostatsforDay(MainActivity.db, currentDay);

        for (int i = 0; i < resultCombos.size(); i++){
            Log.e("Super", "comboname  = " + resultCombos.get(i).getComboname());
        }

        if (resultCombos.size() > 0){
            Collections.sort(resultCombos, COMBO_COMPARATOR);
            resultComboDTOs.clear();
            resultComboDTOs.addAll(resultCombos);
            combosAdapter.setCurrentPosition(0);
            combosAdapter.notifyDataSetChanged();
            comboNameView.setVisibility(View.GONE);
            resultParent.setVisibility(View.VISIBLE);
        }else {
            resultParent.setVisibility(View.GONE);
            comboNameView.setVisibility(View.VISIBLE);
            comboNameView.setText("Combo Training is Empty");
        }
//        if (mainActivity.trainingresultComboDTO != null){
//            punchDTOs.clear();
//
//            comboNameView.setText(mainActivity.trainingresultComboDTO.getComboname());
//
//            punchDTOs.addAll(mainActivity.trainingresultComboDTO.getPunches());
//            punchesAdapter.notifyDataSetChanged();
//        }else {
//            comboNameView.setText("Combo Training is Empty");
//        }
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
        loadComboResult();
        Log.e("Super", "combo on resume");
//        loadResult();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private Comparator<TrainingResultComboDTO> COMBO_COMPARATOR = new Comparator<TrainingResultComboDTO>() {
        @Override
        public int compare(TrainingResultComboDTO lhs, TrainingResultComboDTO rhs) {
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
