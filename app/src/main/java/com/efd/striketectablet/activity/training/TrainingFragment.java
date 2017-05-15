package com.efd.striketectablet.activity.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.adapter.PresetListAdapter;

public class TrainingFragment extends Fragment {

    private MainActivity mainActivityInstance;

    RelativeLayout quickstartLayout, roundLayout;
    View view;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivityInstance = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.training_fragment, container, false);

        initViews();

        return view;
    }

    private void initViews(){
        quickstartLayout = (RelativeLayout)view.findViewById(R.id.traning_menu_quick_start);
        roundLayout = (RelativeLayout)view.findViewById(R.id.traning_menu_round_traning);

        quickstartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent presetIntent = new Intent(getActivity(), TrainingPresetActivity.class);
                presetIntent.putExtra("type", "quickstart");
                startActivity(presetIntent);
                mainActivityInstance.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        roundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent presetIntent = new Intent(getActivity(), TrainingPresetActivity.class);
                presetIntent.putExtra("type", "round");
                startActivity(presetIntent);
                mainActivityInstance.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
