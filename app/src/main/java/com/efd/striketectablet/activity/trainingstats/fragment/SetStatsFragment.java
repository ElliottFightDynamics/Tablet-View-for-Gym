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
import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultCombosAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultPunchesAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TrainingResultSetsNameAdapter;
import com.efd.striketectablet.util.ComboSetUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SetStatsFragment extends Fragment {

    View view;
    TextView setNameView;

    ListView comboListView, resultView, setListView;
    LinearLayout resultParent;

    ArrayList<TrainingResultSetDTO>  setDTOs;
    ArrayList<TrainingResultComboDTO>  comboResults;
    ArrayList<TrainingResultPunchDTO>  punchDTOs;

    TrainingResultSetsNameAdapter setsNameAdapter;
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
        setDTOs = new ArrayList<>();
        comboResults = new ArrayList<>();
        punchDTOs = new ArrayList<>();

        resultParent = (LinearLayout)view.findViewById(R.id.resultview);

        setNameView = (TextView)view.findViewById(R.id.set_name);
        setNameView.setText("");

        setListView = (ListView) view.findViewById(R.id.setlistview);
        comboListView = (ListView) view.findViewById(R.id.combolistview);
        resultView = (ListView)view.findViewById(R.id.punches_listview);

        setsNameAdapter = new TrainingResultSetsNameAdapter(getActivity(), setDTOs, this);
        punchesAdapter = new TrainingResultPunchesAdapter(getActivity(), punchDTOs);
        combosAdapter = new TrainingResultCombosAdapter(getActivity(), comboResults, this, true);

        setListView.setAdapter(setsNameAdapter);
        comboListView.setAdapter(combosAdapter);
        resultView.setAdapter(punchesAdapter);

        return view;
    }

    private void loadSetResult(){

        String currentDay = TrainingStatsFragment.trainingStatsFragment.getCurrentSelectedDay();

        ArrayList<TrainingResultSetDTO> resultSets = ComboSetUtil.getSetstatsforDay(MainActivity.db, currentDay);

        for (int i = 0; i < resultSets.size(); i++){
            Log.e("Super", "setname 11111  = " + resultSets.get(i).getSetname());
        }

        if (resultSets.size() > 0){

            Collections.sort(resultSets, SET_COMPARATOR);
            setDTOs.clear();
            setsNameAdapter.setCurrentPosition(0);
            setDTOs.addAll(resultSets);
            setsNameAdapter.notifyDataSetChanged();
            setNameView.setVisibility(View.GONE);
            resultParent.setVisibility(View.VISIBLE);
        }else {
            resultParent.setVisibility(View.GONE);
            setNameView.setText("Set Training is Empty");
        }

//        if (mainActivity.trainingResultSetDTO != null){
//            comboResults.clear();
//
//            setNameView.setText(mainActivity.trainingResultSetDTO.getSetname());
//
//            comboResults.addAll(mainActivity.trainingResultSetDTO.getCombos());
//            combosAdapter.setData(comboResults);
//            combosAdapter.notifyDataSetChanged();
//            resultParent.setVisibility(View.VISIBLE);
//        }else {
//            setNameView.setText("Set Training is Empty");
//            resultParent.setVisibility(View.INVISIBLE);
//        }
    }

    public void updateCombos(TrainingResultSetDTO resultSetDTO){
        comboResults.clear();
        comboResults.addAll(resultSetDTO.getCombos());
        combosAdapter.setCurrentPosition(0);
        combosAdapter.notifyDataSetChanged();
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

    private Comparator<TrainingResultSetDTO> SET_COMPARATOR = new Comparator<TrainingResultSetDTO>() {
        @Override
        public int compare(TrainingResultSetDTO lhs, TrainingResultSetDTO rhs) {
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
