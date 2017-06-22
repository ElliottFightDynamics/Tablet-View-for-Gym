package com.efd.striketectablet.activity.training.combination;

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

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.ComboSetTrainingActivity;
import com.efd.striketectablet.adapter.CombinationListAdapter;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CombinationFragment extends Fragment {

    View view;
    ListView comboListView;
    Button starttrainingBtn;

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

        starttrainingBtn = (Button)view.findViewById(R.id.training_start_button);
        starttrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCombinationTraining();
            }
        });
    }

    private void startCombinationTraining(){
        if (comboDatas.size() == 0)
            return;

        Intent combosetIntent = new Intent(getActivity(), ComboSetTrainingActivity.class);
        combosetIntent.putExtra(EFDConstants.TRAININGTYPE, EFDConstants.COMBINATION);
        combosetIntent.putExtra(EFDConstants.COMBO_ID, comboAdapter.getItem(comboAdapter.getCurrentPosition()).getId());
        getActivity().startActivity(combosetIntent);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
