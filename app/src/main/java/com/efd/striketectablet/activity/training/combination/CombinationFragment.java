package com.efd.striketectablet.activity.training.combination;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.quickstart.QuickStartTrainingActivity;
import com.efd.striketectablet.activity.training.round.RoundTrainingActivity;
import com.efd.striketectablet.adapter.ComboListAdapter;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.PresetListAdapter;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class CombinationFragment extends Fragment {

    View view;
    ListView comboListView;

    MainActivity mainActivityInstance;

    ComboListAdapter comboAdapter;


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

        comboAdapter = new ComboListAdapter(mainActivityInstance, comboDatas);
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
