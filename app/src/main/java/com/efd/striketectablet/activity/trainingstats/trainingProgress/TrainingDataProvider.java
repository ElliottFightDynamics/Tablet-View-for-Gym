package com.efd.striketectablet.activity.trainingstats.trainingProgress;

import com.efd.striketectablet.App;
import com.efd.striketectablet.DTO.CalendarSummaryDTO;
import com.efd.striketectablet.activity.trainingstats.charts.stats.CalendarBasedBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.EmptyBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.PerRoundBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.TrainingDataSeries;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.database.RoundDto;
import com.efd.striketectablet.utilities.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by omnic on 7/31/2016.
 */
public class TrainingDataProvider {
    private final SimpleDateFormat dateFormatter;
    private final Calendar currentDate;
    private DBAdapter databaseAdapter;
    private int userId;

    public TrainingDataProvider(DBAdapter databaseAdapter, int userId) {
        this.databaseAdapter = databaseAdapter;
        this.userId = userId;
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        this.currentDate = Calendar.getInstance();
    }

    public TrainingDataSeries getDataByMonth(int year) {
        SimpleDateFormat labelFormatter = new SimpleDateFormat("MMM");
        TrainingDataSeries dataSet = new TrainingDataSeries();
        for (int month = 0; month < 12; month++) {
            Calendar calendar = getCalendar(year, month, 1);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);

            String threeLetterMonth = labelFormatter.format(calendar.getTime());
            IBarchartValue barchartValue = getSummaryForDateRange(threeLetterMonth, daysInMonth, calendar);
            barchartValue.setHighlight(isCurrentMonth(calendar));
            dataSet.add(barchartValue);
        }

        return dataSet;
    }

    private boolean isCurrentMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) == this.currentDate.get(Calendar.MONTH);
    }


    public TrainingDataSeries getDataByWeek(int year, int month) {
        Calendar calendar = getCalendar(year, month, 1);

        int oneWeek = 7;
        TrainingDataSeries trainingDataSeries = new TrainingDataSeries();

        while (calendar.get(Calendar.MONTH) == month) {
            calendar.add(Calendar.DAY_OF_MONTH, oneWeek);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String label = (dayOfMonth - 7) + " > " + (dayOfMonth);

            IBarchartValue barchartValue = getSummaryForDateRange(label, oneWeek, calendar);
            barchartValue.setHighlight(isCurrentWeek(calendar));
            trainingDataSeries.add(barchartValue);
        }
        return trainingDataSeries;
    }

    private boolean isCurrentWeek(Calendar calendar) {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int currentDay = this.currentDate.get(Calendar.DAY_OF_MONTH);
        return currentDay <= dayOfMonth && currentDay > dayOfMonth - 7;
    }

    public TrainingDataSeries getDataByDay(int year, int month) {
        Calendar calendar = getCalendar(year, month, 1);
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        TrainingDataSeries trainingDataSeries = new TrainingDataSeries();
        for (int dayOfMonth = 1; dayOfMonth <= totalDays; dayOfMonth++) {
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            IBarchartValue barchartValue = getSummaryForDateRange(String.valueOf(dayOfMonth),1, calendar);
            IBarchartValue barchartValue = getSummaryForDateRange(String.valueOf(dayOfMonth),0, calendar);
            barchartValue.setHighlight(isCurrentDay(calendar));
            trainingDataSeries.add(barchartValue);
        }

        return trainingDataSeries;
    }

    public TrainingDataSeries getDataByRounds(int year, int month) {
//        Calendar calendar = getCalendar(year, month, 1);
//        Date startDate = calendar.getTime();
//
//        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
//        Date endDate = calendar.getTime();

        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        Date endDate = calendar.getTime();

        List<RoundDto> rounds = this.databaseAdapter.getRoundSummaryData(startDate, endDate, Integer.parseInt(CommonUtils.getServerUserId(App.getContext())));

        TrainingDataSeries trainingDataSeries = new TrainingDataSeries();
        for (int i = 0; i < rounds.size(); i++) {
            trainingDataSeries.add(new PerRoundBarchartValue(String.valueOf(i+1), rounds.get(i)));
        }
        return trainingDataSeries;
    }

    public TrainingDataSeries getDataByRounds(Date date) {
        List<RoundDto> rounds = this.databaseAdapter.getRoundSummaryData(date, date, Integer.parseInt(CommonUtils.getServerUserId(App.getContext())));

        TrainingDataSeries trainingDataSeries = new TrainingDataSeries();
        for (int i = 0; i < rounds.size(); i++) {
            trainingDataSeries.add(new PerRoundBarchartValue(String.valueOf(i+1), rounds.get(i)));
        }
        return trainingDataSeries;
    }

    public List<RoundDto> getAllRoundsInfo(int year, int month){
//        Calendar calendar = getCalendar(year, month, 1);
//        Date startDate = calendar.getTime();
//
//        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
//        Date endDate = calendar.getTime();
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        Date endDate = calendar.getTime();

        List<RoundDto> rounds = this.databaseAdapter.getRoundSummaryData(startDate, endDate, Integer.parseInt(CommonUtils.getServerUserId(App.getContext())));

        return rounds;
    };

    public List<RoundDto> getAllRoundsInfo(Date date){
        List<RoundDto> rounds = this.databaseAdapter.getRoundSummaryData(date, date, Integer.parseInt(CommonUtils.getServerUserId(App.getContext())));

        return rounds;
    };

    private boolean isCurrentDay(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) == this.currentDate.get(Calendar.DAY_OF_MONTH);
    }

    public IBarchartValue getSummaryForDateRange(String label, int spanInDays, Calendar targetDate) {
        String dateString = this.dateFormatter.format(targetDate.getTime());
        CalendarSummaryDTO calendarSummaryData = this.databaseAdapter.getCalendarSummaryData(dateString, spanInDays, Integer.parseInt(CommonUtils.getServerUserId(App.getContext())));
        if(calendarSummaryData==null){
            return new EmptyBarchartValue(label);
        } else {
            return new CalendarBasedBarchartValue(label, calendarSummaryData);
        }
    }

    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }


}
