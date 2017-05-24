package com.efd.striketectablet.activity.training.combination;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.adapter.CombinationListAdapter;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class CombinationFragment extends Fragment {

    View view;
    ListView comboListView;

    MainActivity mainActivityInstance;

    CombinationListAdapter comboAdapter;


    ArrayList<ComboDTO> comboDatas;

    private static Context mContext;
    public static CombinationFragment combinationFragment;

    public static Fragment newInstance(Context context) {
        mContext = context;
        combinationFragment = new CombinationFragment();
        return combinationFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivityInstance = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_combination, container, false);

        comboDatas = new ArrayList<>();

        initViews();

        return view;
    }

    private void initViews(){
        comboListView = (ListView)view.findViewById(R.id.combo_listview);

        comboAdapter = new CombinationListAdapter(mainActivityInstance, comboDatas);
        comboListView.setAdapter(comboAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadComboDatas();
    }

    private void loadComboDatas(){

        if (comboDatas != null && comboDatas.size() > 0)
            comboDatas.clear();

        comboDatas.addAll(SharedPreferencesUtils.getSavedCombinationList(getActivity()));

        comboAdapter.notifyDataSetChanged();

    }
}
