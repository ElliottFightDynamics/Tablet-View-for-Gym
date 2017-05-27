package com.efd.striketectablet.activity.training.sets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.ComboSetTrainingActivity;
import com.efd.striketectablet.adapter.SetRoutineCombinationListAdapter;
import com.efd.striketectablet.adapter.SetRoutineListAdapter;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class SetsFragment extends Fragment {

    View view;
    ListView setListView, setdetailListView;
    Button starttrainingBtn;

    MainActivity mainActivityInstance;

    SetRoutineListAdapter setAdapter;
    SetRoutineCombinationListAdapter detailListAdapter;

    ArrayList<SetsDTO> setsDatas;
    ArrayList<Integer> comboDTOs;

    private static Context mContext;
    public static SetsFragment setsFragment;

    public static Fragment newInstance(Context context) {
        mContext = context;
        setsFragment = new SetsFragment();
        return setsFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivityInstance = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sets, container, false);

        setsDatas = new ArrayList<>();
        comboDTOs = new ArrayList<>();

        initViews();

        return view;
    }

    private void initViews(){
        setListView = (ListView)view.findViewById(R.id.set_listview);

        setAdapter = new SetRoutineListAdapter(mainActivityInstance, setsDatas, this);
        setListView.setAdapter(setAdapter);

        setdetailListView = (ListView)view.findViewById(R.id.combo_listview);
        detailListAdapter = new SetRoutineCombinationListAdapter(mainActivityInstance, comboDTOs);
        setdetailListView.setAdapter(detailListAdapter);

        starttrainingBtn = (Button)view.findViewById(R.id.training_start_button);
        starttrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetsTraining();
            }
        });
    }

    private void startSetsTraining(){

        if (setsDatas.size() == 0){
            return;
        }

        Intent combosetIntent = new Intent(getActivity(), ComboSetTrainingActivity.class);
        combosetIntent.putExtra(EFDConstants.TRAININGTYPE, EFDConstants.SETS);
        combosetIntent.putExtra(EFDConstants.SET_ID, setAdapter.getItem(setAdapter.getCurrentPosition()).getId());
        getActivity().startActivity(combosetIntent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void updateDetail(SetsDTO setsDTO){
        if (comboDTOs.size() > 0){
            comboDTOs.clear();
        }

        if (setsDTO == null){
            detailListAdapter.notifyDataSetChanged();
            return;
        }

        comboDTOs.addAll(setsDTO.getComboIDLists());
        detailListAdapter.notifyDataSetChanged();
    }

    private void loadSetsData(){

        if (setsDatas.size() > 0){
            setsDatas.clear();
        }

        setsDatas.addAll(SharedPreferencesUtils.getSavedSetList(mainActivityInstance));
        setAdapter.notifyDataSetChanged();

        if (setsDatas.size() == 0){
            updateDetail(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSetsData();
    }
}
