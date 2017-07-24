package com.efd.striketectablet.activity.trainingstats.trainingProgress;

import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.trainingstats.charts.TrainingProgressBarchartSettings;
import com.efd.striketectablet.activity.trainingstats.charts.TrainingProgressBarchartView;
import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;
import com.efd.striketectablet.activity.trainingstats.charts.stats.TrainingDataSeries;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.StatisticTimeFormat;
import com.efd.striketectablet.activity.trainingstats.trainingProgress.stats.TrainingProgressStatAdapterCollection;
import com.efd.striketectablet.database.RoundDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omnic on 7/31/2016.
 */
public class TrainingProgressChangeListener implements RadioGroup.OnCheckedChangeListener, View.OnTouchListener, AdapterView.OnItemSelectedListener {

    private final TrainingDataProvider trainingDataProvider;
    private Resources resources;
    private TrainingBarchartSettingsProvider settingsProvider;
    private final TrainingProgressBarchartView barChartView;
    private TrainingProgressStatAdapterCollection statHandler;
    private View rootView;

    private StatType currentStatType = StatType.TIME;
    private StatisticTimeFormat statisticTimeFormat = StatisticTimeFormat.DAY;
    private Map<StatType, Integer> colorMap;

    private Date selectedDate = new Date();

    public TrainingProgressChangeListener(TrainingDataProvider trainingDataProvider,
                                          Resources resources,
                                          TrainingBarchartSettingsProvider settingsProvider,
                                          TrainingProgressBarchartView barChartView,
                                          TrainingProgressStatAdapterCollection statHandler,
                                          View rootView) {
        this.trainingDataProvider = trainingDataProvider;
        this.resources = resources;
        this.settingsProvider = settingsProvider;
        this.barChartView = barChartView;
        this.statHandler = statHandler;
        this.rootView = rootView;
        mapColors();
    }

    private void mapColors() {
        this.colorMap = new HashMap<>();
        this.colorMap.put(StatType.TIME, this.resources.getColor(R.color.time_text_color));
        this.colorMap.put(StatType.POWER, this.resources.getColor(R.color.power_text_color));
        this.colorMap.put(StatType.PUNCHES, this.resources.getColor(R.color.punches_text_color));
        this.colorMap.put(StatType.SPEED, this.resources.getColor(R.color.speed_text_color));
        this.colorMap.put(StatType.FORCE, this.resources.getColor(R.color.force_text_color));
    }

    public void setTimeFormat(StatisticTimeFormat timeFormat) {
        this.statisticTimeFormat = timeFormat;
    }

    public void onTimeCheckedChanged(StatisticTimeFormat format) {
        this.statisticTimeFormat = format;
        updateChart();
    }

    public void setSelectedDate(Date date){
        this.selectedDate = date;
        this.statisticTimeFormat = StatisticTimeFormat.ROUNDS;
        updateChart();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
//            case R.id.progress_radio_time_daily:
//                this.statisticTimeFormat = StatisticTimeFormat.DAY;
//                break;
//            case R.id.progress_radio_time_weekly:
//                this.statisticTimeFormat = StatisticTimeFormat.WEEK;
//                break;
//            case R.id.progress_radio_time_monthly:
//                this.statisticTimeFormat = StatisticTimeFormat.MONTHLY;
//                break;
        }
        updateChart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateChart();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouch(View viewTouched, MotionEvent event) {
        int viewId = viewTouched.getId();
        if (viewId == R.id.training_progress_button_time) {
            this.currentStatType = StatType.TIME;
        } else if (viewId == R.id.training_progress_button_punches) {
            this.currentStatType = StatType.PUNCHES;
        } else if (viewId == R.id.training_progress_button_speed) {
            this.currentStatType = StatType.SPEED;
        } else if (viewId == R.id.training_progress_button_force) {
            this.currentStatType = StatType.FORCE;
        }
        Log.d("Test", " ==== currentStatType:" + this.currentStatType);
        updateChart();
        updateTitlebarGraphic();
        return true;
    }

    private void updateTitlebarGraphic() {
        switch (this.currentStatType) {
            case TIME:
                setTitlebar(R.drawable.training_header_time);
                break;
            case PUNCHES:
                setTitlebar(R.drawable.training_header_punches);
                break;
            case SPEED:
                setTitlebar(R.drawable.training_header_speed);
                break;
            case FORCE:
                setTitlebar(R.drawable.training_header_force);
                break;
            case POWER:
                setTitlebar(R.drawable.training_header_power);
                break;
        }
    }

    private void setTitlebar(int drawableId) {
        ImageView statHeader = (ImageView) this.rootView.findViewById(R.id.training_progress_stat_header);
        statHeader.setImageDrawable(rootView.getResources().getDrawable(drawableId));
    }

    private void updateChart() {
        Log.d("Test", " ==== statisticTimeFormat:" + this.statisticTimeFormat);
        switch (this.statisticTimeFormat) {

            case DAY:
                displayDataByDay();
                break;
            case WEEK:
                displayDataByWeek();
                break;
            case MONTHLY:
                displayDataByMonth();
                break;
            case ROUNDS:
                displayDataByRounds();
                break;
        }
    }

    private void displayDataByDay() {
        int year = getSelectedYear();
        int month = getSelectedMonth();
        TrainingDataSeries trainingDataSeries = this.trainingDataProvider.getDataByDay(year, month);
        TrainingProgressBarchartSettings settings = this.settingsProvider.getSettingsForDays();
        setColors(settings);
        barChartView.setSettings(settings);
        barChartView.setDataSet(trainingDataSeries);
        barChartView.setCurrentStat(this.currentStatType);
        updateStatistics(trainingDataSeries);

    }


    private void displayDataByWeek() {
        int year = getSelectedYear();
        int month = getSelectedMonth();
        TrainingDataSeries trainingDataSeries = this.trainingDataProvider.getDataByWeek(year, month);
        TrainingProgressBarchartSettings settings = this.settingsProvider.getSettingsForWeeks();
        setColors(settings);
        barChartView.setSettings(settings);
        barChartView.setDataSet(trainingDataSeries);
        barChartView.setCurrentStat(this.currentStatType);
        updateStatistics(trainingDataSeries);

    }

    private void displayDataByMonth() {
        int year = getSelectedYear();
        TrainingDataSeries trainingDataSeries = this.trainingDataProvider.getDataByMonth(year);
        TrainingProgressBarchartSettings settings = this.settingsProvider.getSettingsForYear();
        setColors(settings);
        barChartView.setSettings(settings);
        barChartView.setDataSet(trainingDataSeries);
        barChartView.setCurrentStat(this.currentStatType);
        updateStatistics(trainingDataSeries);

    }

    public List<RoundDto> getAllRounds(){
        int year = getSelectedYear();
        int month = getSelectedMonth();

        return this.trainingDataProvider.getAllRoundsInfo(year, month);
    }

    public List<RoundDto> getAllRounds(Date date){
        int year = getSelectedYear();
        int month = getSelectedMonth();

        return this.trainingDataProvider.getAllRoundsInfo(date);
    }

    private void displayDataByRounds() {
        int year = getSelectedYear();
        int month = getSelectedMonth();

//        TrainingDataSeries trainingDataSeries = this.trainingDataProvider.getDataByRounds(year, month);
        TrainingDataSeries trainingDataSeries = this.trainingDataProvider.getDataByRounds(selectedDate);
        TrainingProgressBarchartSettings settings = this.settingsProvider.getSettingsForRounds();
        setColors(settings);
        barChartView.setSettings(settings);
        barChartView.setDataSet(trainingDataSeries);
        barChartView.setCurrentStat(this.currentStatType);
        updateStatistics(trainingDataSeries);
    }

    private void updateStatistics(TrainingDataSeries trainingDataSeries) {
        for (IBarchartValue value : trainingDataSeries) {
            if (value.shouldHighlight()) {
                this.statHandler.setValues(value);
            }
        }

        this.statHandler.setSelected(this.currentStatType);
    }


    private void setColors(TrainingProgressBarchartSettings settings) {
        int color = this.colorMap.get(this.currentStatType);
        settings.setBarColor(color);
        settings.setLabelColor(resources.getColor(R.color.default_text_color));
    }

    private int getSelectedMonth() {
        Spinner monthDropdown = (Spinner) this.rootView.findViewById(R.id.training_progress_dropdown_month);
        MonthVM selectedMonth = (MonthVM) monthDropdown.getSelectedItem();
        if (selectedMonth == null) {
            return 0;
        }
        return selectedMonth.getIndex();
    }

    private int getSelectedYear() {
        Spinner yearDropdown = (Spinner) this.rootView.findViewById(R.id.training_progress_dropdown_year);

        Object selectedItem = yearDropdown.getSelectedItem();
        if (selectedItem == null) {
            return 0;
        }
        return (int) selectedItem;
    }


}
