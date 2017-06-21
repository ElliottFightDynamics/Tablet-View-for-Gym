package com.efd.striketectablet.activity.trainingstats.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.TodayStatsPageAdapter;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.Date;

public class TrainingStatsFragment extends Fragment {

    private View view;
    TextView overviewView, combinationView, setView, scriptedView;
    View overviewHighlight, combinationHighlight, setHighlight, scriptedHighlight;

    Spinner daySpinner, monthSpinner, yearSpinner;
    CustomSpinnerAdapter dayAdapter, monthAdapter, yearAdapter;

    private ViewPager mViewPager;
    private TodayStatsPageAdapter pageAdapter;

    private MainActivity mainActivity;
    private static FragmentManager fragmentManager;

    int statsPosition = -1;

    public static TrainingStatsFragment trainingStatsFragment;

    public static Fragment newInstance() {
        trainingStatsFragment = new TrainingStatsFragment();
        return trainingStatsFragment;
    }

    private String currentSelectedDay;


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

        view = inflater.inflate(R.layout.fragment_trainingstat, container, false);

        initView();

        return view;
    }

    private void initView(){
        fragmentManager = getChildFragmentManager();

        daySpinner = (Spinner)view.findViewById(R.id.day_spinner);
        dayAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_digit_with_img, PresetUtil.dayList, EFDConstants.SPINNER_DIGIT_ORANGE);
        daySpinner.setAdapter(dayAdapter);

        monthSpinner = (Spinner)view.findViewById(R.id.month_spinner);
        monthAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_text_with_img, PresetUtil.threeCharMonthList, EFDConstants.SPINNER_TEXT_ORANGE);
        monthSpinner.setAdapter(monthAdapter);

        yearSpinner = (Spinner)view.findViewById(R.id.year_spinner);
        yearAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_digit_with_img, PresetUtil.statYearList, EFDConstants.SPINNER_DIGIT_ORANGE);
        yearSpinner.setAdapter(yearAdapter);

        overviewView = (TextView)view.findViewById(R.id.overview);
        combinationView = (TextView)view.findViewById(R.id.combination);
        setView = (TextView)view.findViewById(R.id.set);
        scriptedView = (TextView)view.findViewById(R.id.scripted);

        overviewHighlight = view.findViewById(R.id.overview_highlight);
        combinationHighlight = view.findViewById(R.id.combination_highlight);
        setHighlight = view.findViewById(R.id.set_highlight);
        scriptedHighlight = view.findViewById(R.id.scripted_highlight);

        mViewPager = (ViewPager) view.findViewById(R.id.stats_viewpager);
        pageAdapter = new TodayStatsPageAdapter(mainActivity, fragmentManager);
        mViewPager.setAdapter(pageAdapter);

        overviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        combinationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });

        setView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        scriptedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTrainingStats();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTrainingStats();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTrainingStats();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showToday();
        updateTab(0);
    }

    private void showToday(){
        Date date = new Date();

        String year = ((String) android.text.format.DateFormat.format("yyyy", date));
        String month = ((String) android.text.format.DateFormat.format("MMM", date));
        String day = ((String) android.text.format.DateFormat.format("dd", date));

        daySpinner.setSelection(PresetUtil.getDayPosition(day));
        monthSpinner.setSelection(PresetUtil.getMonthPosition(month));
        yearSpinner.setSelection(PresetUtil.getStatYearPosition(year));
    }

    private void updateTrainingStats(){
        String currentDay = yearSpinner.getSelectedItem().toString() + "-" + monthSpinner.getSelectedItem().toString() + "-" + daySpinner.getSelectedItem().toString() ;
        Log.e("Super", "selected day = " + currentDay);
    }

    public void updateStatFragment(int position){
        mViewPager.setCurrentItem(position);
        updateTab(position);
    }

    private void updateTab(int position){
        switch (position) {
            case 0:
                overviewView.setTextColor(getResources().getColor(R.color.white));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                overviewHighlight.setVisibility(View.VISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);
                break;

            case 1:
                overviewView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.white));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                overviewHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.VISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);
                break;

            case 2:
                overviewView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.white));
                scriptedView.setTextColor(getResources().getColor(R.color.orange));

                overviewHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.VISIBLE);
                scriptedHighlight.setVisibility(View.INVISIBLE);
                break;

            case 3:
                overviewView.setTextColor(getResources().getColor(R.color.orange));
                combinationView.setTextColor(getResources().getColor(R.color.orange));
                setView.setTextColor(getResources().getColor(R.color.orange));
                scriptedView.setTextColor(getResources().getColor(R.color.white));

                overviewHighlight.setVisibility(View.INVISIBLE);
                combinationHighlight.setVisibility(View.INVISIBLE);
                setHighlight.setVisibility(View.INVISIBLE);
                scriptedHighlight.setVisibility(View.VISIBLE);

                break;
        }
    }
}
