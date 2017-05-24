package com.efd.striketectablet.activity.training.sets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.adapter.ComboListAdapter;
import com.efd.striketectablet.adapter.SetDetailListAdapter;
import com.efd.striketectablet.adapter.SetListAdapter;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class SetsFragment extends Fragment {

    View view;
    ListView setListView, setdetailListView;

    MainActivity mainActivityInstance;

    SetListAdapter setAdapter;
    SetDetailListAdapter detailListAdapter;

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

        setAdapter = new SetListAdapter(mainActivityInstance, setsDatas, this);
        setListView.setAdapter(setAdapter);

        setdetailListView = (ListView)view.findViewById(R.id.combo_listview);
        detailListAdapter = new SetDetailListAdapter(mainActivityInstance, comboDTOs);
        setdetailListView.setAdapter(detailListAdapter);


    }

    public void updateDetail(SetsDTO setsDTO){
        if (comboDTOs.size() > 0){
            comboDTOs.clear();
        }

        comboDTOs.addAll(setsDTO.getComboPositionlists());
        detailListAdapter.setData(comboDTOs);
        detailListAdapter.notifyDataSetChanged();
    }

    private void loadSetsData(){

        if (setsDatas.size() > 0){
            setsDatas.clear();
        }

        setsDatas.addAll(SharedPreferencesUtils.getSavedSetList(mainActivityInstance));
        setAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSetsData();
    }
}
