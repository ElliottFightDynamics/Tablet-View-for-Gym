package com.efd.striketectablet.activity.trainingstats.trainingProgress;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by omnic on 8/27/2016.
 */
public abstract class AbstractTrainingProgressFragment extends Fragment {

    private MainActivity mainActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivity = (MainActivity) activity;
    }

    protected abstract int getLayoutId();

    protected abstract void initTrainingFragment(View rootView);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mainActivity.setLayoutValues("TRAINING PROGRESS", getLayoutId());
        View rootView = inflater.inflate(getLayoutId(), container, false);

        addYearsToDropdown(rootView);
        addMonthsToDropdown(rootView);

        initTrainingFragment(rootView);
        return rootView;
    }

    protected int getUserId() {
        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userId = sharedPreference.getString("userId", null);
        return Integer.parseInt(userId);
    }

    protected void addImageTouchListener(View rootView, TrainingProgressChangeListener timespanListener, int viewId) {
        ImageView timeButton = (ImageView) rootView.findViewById(viewId);
        timeButton.setOnTouchListener(timespanListener);
    }

    private void addYearsToDropdown(View rootView) {
        Spinner dropdownYear = getYearDropdown(rootView);
        ArrayList<Integer> years = new ArrayList<>();
        // TODO : only display years for which the user has data available
        years.add(2015);
        years.add(2016);
        years.add(2017);
        years.add(2018);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this.getActivity(), R.layout.training_progress_spinner_right, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownYear.setAdapter(dataAdapter);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        dropdownYear.setSelection(years.indexOf(currentYear));
    }

    private void addMonthsToDropdown(View rootView) {
        final Spinner monthDropdown = getMonthDropdown(rootView);
        ArrayList<MonthVM> months = new ArrayList<>();
        months.add(new MonthVM("Jan", 0));
        months.add(new MonthVM("Feb", 1));
        months.add(new MonthVM("Mar", 2));
        months.add(new MonthVM("Apr", 3));
        months.add(new MonthVM("May", 4));
        months.add(new MonthVM("Jun", 5));
        months.add(new MonthVM("Jul", 6));
        months.add(new MonthVM("Aug", 7));
        months.add(new MonthVM("Sept", 8));
        months.add(new MonthVM("Oct", 9));
        months.add(new MonthVM("Nov", 10));
        months.add(new MonthVM("Dec", 11));

        ArrayAdapter<MonthVM> monthVMArrayAdapter = new ArrayAdapter<>(this.getActivity(), R.layout.training_progress_spinner_left, months);
        monthVMArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthDropdown.setAdapter(monthVMArrayAdapter);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        monthDropdown.setSelection(currentMonth, true);
    }


    private Spinner getYearDropdown(View rootView) {
        return (Spinner) rootView.findViewById(R.id.training_progress_dropdown_year);
    }

    private Spinner getMonthDropdown(View rootView) {
        return (Spinner) rootView.findViewById(R.id.training_progress_dropdown_month);
    }

}
