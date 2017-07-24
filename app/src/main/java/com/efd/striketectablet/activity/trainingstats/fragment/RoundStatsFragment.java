package com.efd.striketectablet.activity.trainingstats.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.charts.TrainingProgressBarchartSettings;
import com.efd.striketectablet.activity.trainingstats.charts.TrainingProgressBarchartView;
import com.efd.striketectablet.activity.trainingstats.charts.UpdateStatsFromBarchartListener;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.TrainingBarchartSettingsProvider;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.TrainingDataProvider;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.TrainingProgressChangeListener;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.ForceStatAdapter;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.PunchesStatAdapter;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.SpeedStatAdapter;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.StatisticTimeFormat;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.TimeStatAdapter;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.TrainingProgressStatAdapterCollection;
import com.efd.striketectablet.database.RoundDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RoundStatsFragment extends Fragment {

    View view;
    LinearLayout rootView;
    Spinner yearSelector, monthSelector;
    TrainingProgressBarchartView barChartView;
    TrainingProgressChangeListener timespanListener;

    //Total info views
    TextView total_punchesView, total_speedView, total_powerView, selected_trainingtypeView;

    //selected info views
    MainActivity mainActivity;

    public static RoundStatsFragment roundStatsFragment;

    public static Fragment newInstance() {
        roundStatsFragment = new RoundStatsFragment();
        return roundStatsFragment;
    }

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

        view = inflater.inflate(R.layout.fragment_roundstats, container, false);

        initView();

        return view;
    }

    private void initView(){
        rootView = (LinearLayout)view.findViewById(R.id.today_stats);
        yearSelector = (Spinner) view.findViewById(R.id.training_progress_dropdown_year);
        monthSelector = (Spinner) view.findViewById(R.id.training_progress_dropdown_month);

        setBarchartToDisplayByRounds(rootView);

        int userId = getUserId();
        TrainingDataProvider trainingDataProvider = new TrainingDataProvider(MainActivity.db, userId);
        TrainingProgressStatAdapterCollection statHandler = new TrainingProgressStatAdapterCollection();
        statHandler.addStat(new TimeStatAdapter(rootView));
        statHandler.addStat(new PunchesStatAdapter(rootView));
        statHandler.addStat(new SpeedStatAdapter(rootView));
        statHandler.addStat(new ForceStatAdapter(rootView));

        timespanListener = new TrainingProgressChangeListener(
                trainingDataProvider,
                getResources(),
                new TrainingBarchartSettingsProvider(),
                barChartView,
                statHandler,
                rootView);
        timespanListener.onTimeCheckedChanged(StatisticTimeFormat.ROUNDS);

        barChartView.addBarClickListener(new UpdateStatsFromBarchartListener(statHandler));

        addImageTouchListener(rootView, timespanListener, R.id.training_progress_button_time);
        addImageTouchListener(rootView, timespanListener, R.id.training_progress_button_punches);
        addImageTouchListener(rootView, timespanListener, R.id.training_progress_button_speed);
        addImageTouchListener(rootView, timespanListener, R.id.training_progress_button_force);

        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                yearSelector.setOnItemSelectedListener(timespanListener);
                monthSelector.setOnItemSelectedListener(timespanListener);
            }
        }, 200);

//        total_bestroundView = (CustomTextView)findViewById(R.id.today_bestround);
//        total_worstroundView = (CustomTextView)findViewById(R.id.today_worstround);
        total_punchesView = (TextView) view.findViewById(R.id.todaytotal_punches);
        total_speedView = (TextView)view.findViewById(R.id.todaytotal_speed);
        total_powerView = (TextView)view.findViewById(R.id.todaytotal_power);

        selected_trainingtypeView = (TextView)view.findViewById(R.id.todayselected_trainingtype);
//        selected_speedView = (CustomTextView)findViewById(R.id.todayselected_speed);
//        selected_powerView = (CustomTextView)findViewById(R.id.todayselected_power);
//        selected_punchesView = (CustomTextView)findViewById(R.id.todayselected_punches);

//        updateTodayTotalInfo(timespanListener);
    }

    protected void addImageTouchListener(View rootView, TrainingProgressChangeListener timespanListener, int viewId) {
        ImageView timeButton = (ImageView) rootView.findViewById(viewId);
        timeButton.setOnTouchListener(timespanListener);
    }

    private void updateTodayTotalInfo(TrainingProgressChangeListener timespanListener){

        String currentDay = TrainingStatsFragment.trainingStatsFragment.getCurrentSelectedDay();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(currentDay);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        timespanListener.setSelectedDate(convertedDate);

        List<RoundDto> roundDtos = timespanListener.getAllRounds(convertedDate);
        float avgspeed = 0f, avgforce = 0f;
        float totalspeed = 0f, totalforce = 0f;
        int totalPunches = 0;
        float maxPoints;

        if (roundDtos != null && roundDtos.size() > 0) {
            for (int i = 0; i < roundDtos.size(); i++) {
                totalspeed += roundDtos.get(i).getAverageSpeed();
                totalforce += roundDtos.get(i).getAverageForce();
                totalPunches += roundDtos.get(i).getTotalPunches();
            }

            avgforce = totalforce / roundDtos.size();
            avgspeed = totalspeed / roundDtos.size();
        }

        avgforce = ((int)(avgforce * 100)) / 100f;
        avgspeed = ((int)(avgspeed * 100)) / 100f;

        total_speedView.setText(String.valueOf(avgspeed));
        total_powerView.setText(String.valueOf(avgforce));
        total_punchesView.setText(String.valueOf(totalPunches));
    }

    private int getUserId() {
        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userId = sharedPreference.getString("userId", null);
        return Integer.parseInt(userId);
    }

    private TrainingProgressBarchartView setBarchartToDisplayByRounds(View rootView) {
        TrainingBarchartSettingsProvider barchartSettingsProvider = new TrainingBarchartSettingsProvider();
        TrainingProgressBarchartSettings settings = barchartSettingsProvider.getSettingsForRounds();
        barChartView = (TrainingProgressBarchartView) rootView.findViewById(R.id.training_progress_barchart);
        barChartView.setSettings(settings);
        return barChartView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTodayTotalInfo(timespanListener);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
