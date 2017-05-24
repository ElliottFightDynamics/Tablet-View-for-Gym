package com.efd.striketectablet.activity.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.combination.CombinationFragment;
import com.efd.striketectablet.activity.training.combination.NewCombinationActivity;
import com.efd.striketectablet.activity.training.sets.NewSetRoutineActivity;
import com.efd.striketectablet.activity.training.sets.SetsFragment;
import com.efd.striketectablet.adapter.PresetListAdapter;
import com.efd.striketectablet.adapter.TrainingTabPageAdapter;

public class TrainingFragment extends Fragment {

    private MainActivity mainActivityInstance;

//    RelativeLayout quickstartLayout, roundLayout;
    View view;
    TextView quickstartView, timerView, combinationView, setView, scriptedView;
    View quickstartHighlight, timerHighlight, combinationHighlight, setHighlight, scriptedHighlight;
    ImageView plusBtn;

    ViewPager trainingViewPager;
    TrainingTabPageAdapter pageAdapter;

    private static FragmentManager fragmentManager;

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
        quickstartView = (TextView)view.findViewById(R.id.quickstart);
        timerView = (TextView)view.findViewById(R.id.timer);
        combinationView = (TextView)view.findViewById(R.id.combination);
        setView = (TextView)view.findViewById(R.id.set);
        scriptedView = (TextView)view.findViewById(R.id.scripted);

        quickstartHighlight = view.findViewById(R.id.quickstart_highlight);
        timerHighlight = view.findViewById(R.id.timer_highlight);
        combinationHighlight = view.findViewById(R.id.combination_highlight);
        setHighlight = view.findViewById(R.id.set_highlight);
        scriptedHighlight = view.findViewById(R.id.scripted_highlight);

        trainingViewPager = (ViewPager)view.findViewById(R.id.training_viewpager);

        fragmentManager = getChildFragmentManager();
        pageAdapter = new TrainingTabPageAdapter(mainActivityInstance, fragmentManager);
        trainingViewPager.setAdapter(pageAdapter);

        quickstartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingViewPager.setCurrentItem(0);
            }
        });

        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingViewPager.setCurrentItem(1);
            }
        });

        combinationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingViewPager.setCurrentItem(2);
            }
        });

        setView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingViewPager.setCurrentItem(3);
            }
        });

        scriptedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingViewPager.setCurrentItem(4);
            }
        });

        trainingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        plusBtn = (ImageView)view.findViewById(R.id.addbtn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trainingViewPager.getCurrentItem() == 2){
                    CombinationFragment combinationFragment = CombinationFragment.combinationFragment;
                    Intent newComboIntent = new Intent(getActivity(), NewCombinationActivity.class);
                    startActivity(newComboIntent);
                    mainActivityInstance.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);;
                }else if (trainingViewPager.getCurrentItem() == 3){
                    SetsFragment setsFragment = SetsFragment.setsFragment;
                    Intent newSetIntent = new Intent(getActivity(), NewSetRoutineActivity.class);
                    startActivity(newSetIntent);
                    mainActivityInstance.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);;
                }
            }
        });

        trainingViewPager.setCurrentItem(0);
        updateTab(0);
    }

    private void updateTab(int position){
        switch (position) {
            case 0:
                quickstartView.setTextColor(getResources().getColor(R.color.white));
                timerView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                quickstartHighlight.setVisibility(View.VISIBLE);
                timerHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);

                plusBtn.setVisibility(View.INVISIBLE);
                break;
            case 1:
                quickstartView.setTextColor(getResources().getColor(R.color.orange));
                timerView.setTextColor(getResources().getColor(R.color.white));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                quickstartHighlight.setVisibility(View.INVISIBLE);
                timerHighlight.setVisibility(View.VISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);

                plusBtn.setVisibility(View.INVISIBLE);
                break;
            case 2:
                quickstartView.setTextColor(getResources().getColor(R.color.orange));
                timerView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.white));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                quickstartHighlight.setVisibility(View.INVISIBLE);
                timerHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.VISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);

                plusBtn.setVisibility(View.VISIBLE);

                break;
            case 3:
                quickstartView.setTextColor(getResources().getColor(R.color.orange));
                timerView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.white));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                quickstartHighlight.setVisibility(View.INVISIBLE);
                timerHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.VISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);

                plusBtn.setVisibility(View.VISIBLE);
                break;
            case 4:
                quickstartView.setTextColor(getResources().getColor(R.color.orange));
                timerView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.white));

                quickstartHighlight.setVisibility(View.INVISIBLE);
                timerHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.VISIBLE);

                plusBtn.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
