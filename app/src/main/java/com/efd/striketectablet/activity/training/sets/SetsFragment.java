package com.efd.striketectablet.activity.training.sets;

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

import java.util.ArrayList;

public class SetsFragment extends Fragment {

    View view;
    ListView setListView, setdetailListView;

    MainActivity mainActivityInstance;

    SetListAdapter setAdapter;
    SetDetailListAdapter detailListAdapter;

    ArrayList<SetsDTO> setsDatas;
    ArrayList<ComboDTO> comboDTOs;

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

        loadSetsData();
    }

    public void updateDetail(SetsDTO setsDTO){
        if (comboDTOs.size() > 0){
            comboDTOs.clear();
        }

        comboDTOs.addAll(setsDTO.getComboslists());
        detailListAdapter.setData(comboDTOs);
        detailListAdapter.notifyDataSetChanged();
    }

    private void loadSetsData(){
        //maybe sets datas will be saved in preferences
        ArrayList<String> comboTypes = new ArrayList<>();
        comboTypes.add("1");
        comboTypes.add("2");
        comboTypes.add("SR");
        comboTypes.add("2");
        comboTypes.add("4");
        comboTypes.add("6");
        comboTypes.add("7");
        comboTypes.add("5");
        comboTypes.add("7");
        comboTypes.add("4");

        ComboDTO comboDTO = new ComboDTO("Attack", comboTypes, 0);
        ComboDTO comboDTO1 = new ComboDTO("Crafty", comboTypes, 2);
        ComboDTO comboDTO2 = new ComboDTO("Left overs", comboTypes, 1);
        ComboDTO comboDTO3 = new ComboDTO("Defensive", comboTypes, 2);
        ComboDTO comboDTO4 = new ComboDTO("Movement", comboTypes, 0);
        ComboDTO comboDTO5 = new ComboDTO("Super Banger", comboTypes, 2);
        ComboDTO comboDTO6 = new ComboDTO("Nitro", comboTypes, 1);
        ComboDTO comboDTO7 = new ComboDTO("Custom1", comboTypes, 1);
        ComboDTO comboDTO8 = new ComboDTO("Custom2", comboTypes, 0);
        ComboDTO comboDTO9 = new ComboDTO("Custom3", comboTypes, 0);

        ArrayList<ComboDTO> comboArray = new ArrayList<>();
        comboArray.add(comboDTO);
        comboArray.add(comboDTO1);
        comboArray.add(comboDTO2);

        ArrayList<ComboDTO> comboArray1 = new ArrayList<>();
        comboArray1.add(comboDTO);
        comboArray1.add(comboDTO2);
        comboArray1.add(comboDTO5);
        comboArray1.add(comboDTO6);
        comboArray1.add(comboDTO3);

        ArrayList<ComboDTO> comboArray2 = new ArrayList<>();
        comboArray2.add(comboDTO6);
        comboArray2.add(comboDTO8);
        comboArray2.add(comboDTO7);

        ArrayList<ComboDTO> comboArray3 = new ArrayList<>();
        comboArray3.add(comboDTO);
        comboArray3.add(comboDTO1);
        comboArray3.add(comboDTO2);
        comboArray3.add(comboDTO3);

        ArrayList<ComboDTO> comboArray4 = new ArrayList<>();
        comboArray4.add(comboDTO7);
        comboArray4.add(comboDTO6);
        comboArray4.add(comboDTO2);
        comboArray4.add(comboDTO);

        ArrayList<ComboDTO> comboArray5 = new ArrayList<>();
        comboArray5.add(comboDTO);
        comboArray5.add(comboDTO1);
        comboArray5.add(comboDTO2);
        comboArray5.add(comboDTO3);
        comboArray5.add(comboDTO4);
        comboArray5.add(comboDTO5);
        comboArray5.add(comboDTO4);
        comboArray5.add(comboDTO3);
        comboArray5.add(comboDTO2);
        comboArray5.add(comboDTO1);
        comboArray5.add(comboDTO3);
        comboArray5.add(comboDTO5);

        SetsDTO setsDTO = new SetsDTO("Aggressor", comboArray);
        SetsDTO setsDTO1 = new SetsDTO("Defensive", comboArray1);
        SetsDTO setsDTO2 = new SetsDTO("Agressor", comboArray2);
        SetsDTO setsDTO3 = new SetsDTO("Custom1", comboArray3);
        SetsDTO setsDTO4 = new SetsDTO("Default Combination 1", comboArray4);
        SetsDTO setsDTO5 = new SetsDTO("Custom3", comboArray5);

        setsDatas.add(setsDTO);
        setsDatas.add(setsDTO1);
        setsDatas.add(setsDTO2);
        setsDatas.add(setsDTO3);
        setsDatas.add(setsDTO4);
        setsDatas.add(setsDTO5);

        setAdapter.setData(setsDatas);
        setAdapter.notifyDataSetChanged();
    }
}
