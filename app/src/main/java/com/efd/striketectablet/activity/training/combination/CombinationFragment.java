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

        comboAdapter = new ComboListAdapter(mainActivityInstance, comboDatas, this);
        comboListView.setAdapter(comboAdapter);

        loadComboDatas();
    }

    private void loadComboDatas(){
        //maybe combo datas will be saved in preferences
        ArrayList<String> comboTypes = new ArrayList<>();
        comboTypes.add("1");
        comboTypes.add("2");
        comboTypes.add("SR");
        comboTypes.add("2");
        comboTypes.add("4");
        comboTypes.add("SL");
        comboTypes.add("7");
        comboTypes.add("5");
        comboTypes.add("DL");
        comboTypes.add("DR");

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

        comboDatas.add(comboDTO);
        comboDatas.add(comboDTO1);
        comboDatas.add(comboDTO2);
        comboDatas.add(comboDTO3);
        comboDatas.add(comboDTO4);
        comboDatas.add(comboDTO5);
        comboDatas.add(comboDTO6);
        comboDatas.add(comboDTO7);
        comboDatas.add(comboDTO8);
        comboDatas.add(comboDTO9);

        comboAdapter.notifyDataSetChanged();

    }

    public void showSettings(final ComboDTO comboDTO){
        final Dialog dialog = new Dialog(mainActivityInstance);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comboset);

        CustomTextView shareView, editView, deleteView;
        shareView = (CustomTextView)dialog.findViewById(R.id.combo_share);
        shareView.setText(getResources().getString(R.string.combo_share));
        editView = (CustomTextView)dialog.findViewById(R.id.combo_edit);
        editView.setText(getResources().getString(R.string.combo_edit));

        deleteView = (CustomTextView)dialog.findViewById(R.id.combo_delete);
        deleteView.setText(getResources().getString(R.string.combo_delete));

        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comboDTO != null){
                    StatisticUtil.showToastMessage("Share Combo: " + comboDTO.getName());
                }else {
                    StatisticUtil.showToastMessage("Invalid Data");
                }

                dialog.dismiss();
            }
        });

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comboDTO != null){
                    StatisticUtil.showToastMessage("Edit Combo: " + comboDTO.getName());
                }else {
                    StatisticUtil.showToastMessage("Invalid Data");
                }

                dialog.dismiss();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comboDTO != null){
                    StatisticUtil.showToastMessage("Delete Combo: " + comboDTO.getName());
                }else {
                    StatisticUtil.showToastMessage("Invalid Data");
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
