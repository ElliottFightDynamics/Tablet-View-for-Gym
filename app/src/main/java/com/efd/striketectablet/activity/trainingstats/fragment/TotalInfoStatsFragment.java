package com.efd.striketectablet.activity.trainingstats.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.PunchesRankDTO;
import com.efd.striketectablet.DTO.TrainingStatsPunchTypeInfoDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.CommonStatsAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.PunchesRankAdapter;
import com.efd.striketectablet.customview.CustomCircleView;
import com.efd.striketectablet.util.PresetUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TotalInfoStatsFragment extends Fragment{

    MainActivity mainActivityInstance;

    private ListView punchrankListView, commonstatsListView;
    View speedSelectView, forceSelectView, countSelectView, percentSelectView;

    CustomCircleView circlePercentView;
    TextView activePercentView, inactivePercentView;

    private PunchesRankAdapter rankAdapter;
    private CommonStatsAdapter commonStatsAdapter;

    private ArrayList<PunchesRankDTO> rankDTOs;
    private ArrayList<String> keyLists, valueLists;
    private HashMap<String, String> valueHashMap;
    private int activeTime = 0, inactiveTime = 0;


    private int currentSelected = -1;

    TextView bestpunchSpeedView, bestpunchForceView, totalPunchesView, activeTimeView, inactiveTimeView, damageView;

    public static TotalInfoStatsFragment totalInfoStatsFragment;

    public static Fragment newInstance() {
        totalInfoStatsFragment = new TotalInfoStatsFragment();
        return totalInfoStatsFragment;
    }

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
        View view = inflater.inflate(R.layout.training_stats_fragment, container, false);

        rankDTOs = new ArrayList<>();
        keyLists = new ArrayList<>();
        valueLists = new ArrayList<>();
        valueHashMap = new HashMap<>();

        activePercentView = (TextView)view.findViewById(R.id.active_percent);
        inactivePercentView = (TextView)view.findViewById(R.id.inactive_percent);

        bestpunchSpeedView = (TextView)view.findViewById(R.id.bestpunch_speed);
        bestpunchForceView = (TextView)view.findViewById(R.id.bestpunch_force);
        totalPunchesView = (TextView)view.findViewById(R.id.total_punch);
        activeTimeView =(TextView)view.findViewById(R.id.active_time);
        inactiveTimeView = (TextView)view.findViewById(R.id.inactive_time);
        damageView = (TextView)view.findViewById(R.id.total_damage);

        speedSelectView = view.findViewById(R.id.speedselect);
        forceSelectView = view.findViewById(R.id.forceselect);
        countSelectView = view.findViewById(R.id.countselect);
        percentSelectView = view.findViewById(R.id.percentselect);

        circlePercentView = (CustomCircleView)view.findViewById(R.id.stats_percent);

        speedSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelected != 0){
                    refreshShow(0);
                }
            }
        });

        forceSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelected != 1){
                    refreshShow(1);
                }
            }
        });

        countSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelected != 2){
                    refreshShow(2);
                }
            }
        });

        percentSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelected != 3){
                    refreshShow(3);
                }
            }
        });

        punchrankListView = (ListView)view.findViewById(R.id.punchrank_listview);
        commonstatsListView = (ListView)view.findViewById(R.id.commonstats_listview);

        rankAdapter = new PunchesRankAdapter(getActivity(), rankDTOs);
        punchrankListView.setAdapter(rankAdapter);

        commonStatsAdapter = new CommonStatsAdapter(getActivity(), keyLists, valueLists);
        commonstatsListView.setAdapter(commonStatsAdapter);

        circlePercentView.setDeactivePaint(getResources().getColor(R.color.advise_deactivecolor));
        circlePercentView.setStrokeWidth(18);

        circlePercentView.setActivePaint(getResources().getColor(R.color.force_text_color));
        circlePercentView.setInnerPaint(getResources().getColor(R.color.speed_text_color));

//        updateCirlceView(true, 320, 240);

        return view;
    }

    private void loadStatsInfo(){
        String currentDay = TrainingStatsFragment.trainingStatsFragment.getCurrentSelectedDay();
        if (keyLists != null && keyLists.size() > 0)
            keyLists.clear();
        if (valueLists != null && valueLists.size() > 0)
            valueLists.clear();

        bestpunchSpeedView.setText("0");
        bestpunchForceView.setText("0");
        totalPunchesView.setText("0");
        activeTimeView.setText("00:00:00");
        inactiveTimeView.setText("00:00:00");
        activePercentView.setText("0%");
        inactivePercentView.setText("0%");
        updateCirlceView(true, 0, 0);

        initData();

        //set total time
        int totaltime = MainActivity.db.getTodayTotalTime(currentDay);
        valueLists.set(keyLists.indexOf("TRAINING TIME"), PresetUtil.changeSecondsToHours(totaltime));

        ArrayList<TrainingStatsPunchTypeInfoDTO> punchTypeInfoDTOs = MainActivity.db.getTrainingStats(currentDay);
        if (punchTypeInfoDTOs.size() == 0){
            commonStatsAdapter.setData(keyLists, valueLists);
            commonStatsAdapter.notifyDataSetChanged();
            return;
        }

        TrainingStatsPunchTypeInfoDTO punchTypeInfoDTO;

        //set best punch
        Collections.sort(punchTypeInfoDTOs, BEST_COMPARATOR);
        punchTypeInfoDTO = punchTypeInfoDTOs.get(0);
        bestpunchSpeedView.setText(String.valueOf((int)(punchTypeInfoDTO.avgspeed)));
        bestpunchForceView.setText(String.valueOf((int)punchTypeInfoDTO.avgforce));

        //get total punch
        int totalpunchcount = 0;
        double sumavgspeed = 0, sumavgforce = 0, sumactivetime = 0;
        for (int i = 0; i < punchTypeInfoDTOs.size(); i++){
            totalpunchcount += punchTypeInfoDTOs.get(i).punchcount;
            sumavgspeed += punchTypeInfoDTOs.get(i).punchcount * punchTypeInfoDTOs.get(i).avgspeed;
            sumavgforce += punchTypeInfoDTOs.get(i).punchcount * punchTypeInfoDTOs.get(i).avgforce;
            sumactivetime += punchTypeInfoDTOs.get(i).totaltime;
        }

        int activePercent = (int)(sumactivetime / totaltime * 100);
        int inactivePercent = 100 - activePercent;

        int activeAngle = (int)(sumactivetime / totaltime * 360);
        int inactiveAngle = 360 - activeAngle;

        activePercentView.setText(activePercent + "%");
        inactivePercentView.setText(inactivePercent + "%");

        updateCirlceView(true, activeAngle, inactiveAngle);

        activeTimeView.setText(PresetUtil.changeSecondsToHours((int)sumactivetime));
        inactiveTimeView.setText(PresetUtil.changeSecondsToHours((int)(totaltime - sumactivetime)));

        totalPunchesView.setText(String.valueOf(totalpunchcount));
        valueLists.set(keyLists.indexOf("AVG SPEED"), (int)(sumavgspeed / totalpunchcount) + " MPH");
        valueLists.set(keyLists.indexOf("AVG POWER"), (int)(sumavgforce / totalpunchcount) + " LBS");

        if (rankDTOs != null && rankDTOs.size() > 0)
            rankDTOs.clear();

        //punch rank listview
        for (int i = 0; i < punchTypeInfoDTOs.size(); i++){
            TrainingStatsPunchTypeInfoDTO tmp = punchTypeInfoDTOs.get(i);
            rankDTOs.add(new PunchesRankDTO(tmp.punchtype, tmp.avgspeed, tmp.avgforce, tmp.punchcount, (int)((float)tmp.punchcount / totalpunchcount * 100)));
        }

        refreshShow(0);

        //max speed, min speed, fastest , slowest punch
        Collections.sort(punchTypeInfoDTOs, MAX_SPEED_COMPARATOR);
        punchTypeInfoDTO = punchTypeInfoDTOs.get(0);
        valueLists.set(keyLists.indexOf("MAX SPEED"), (int)(punchTypeInfoDTO.avgspeed) + " MPH");
        valueLists.set(keyLists.indexOf("FASTEST PUNCH"), punchTypeInfoDTO.punchtype);

        punchTypeInfoDTO = punchTypeInfoDTOs.get(punchTypeInfoDTOs.size() - 1);
        valueLists.set(keyLists.indexOf("MIN SPEED"), (int)(punchTypeInfoDTO.avgspeed) + " MPH");
        valueLists.set(keyLists.indexOf("SLOWEST PUNCH"), punchTypeInfoDTO.punchtype);

        //max power, min power, most powerful, weakest punch
        Collections.sort(punchTypeInfoDTOs, MAX_FORCE_COMPARATOR);
        punchTypeInfoDTO = punchTypeInfoDTOs.get(0);
        valueLists.set(keyLists.indexOf("MAX POWER"), (int)(punchTypeInfoDTO.avgforce) + " LBS");
        valueLists.set(keyLists.indexOf("MOST POWERFUL PUNCH"), punchTypeInfoDTO.punchtype);

        punchTypeInfoDTO = punchTypeInfoDTOs.get(punchTypeInfoDTOs.size() - 1);
        valueLists.set(keyLists.indexOf("MIN POWER"), (int)(punchTypeInfoDTO.avgforce) + " LBS");
        valueLists.set(keyLists.indexOf("WEAKEST PUNCH"), punchTypeInfoDTO.punchtype);

        //most, least effective
        Collections.sort(punchTypeInfoDTOs, MOST_EFFECT_COMPARATOR);
        punchTypeInfoDTO = punchTypeInfoDTOs.get(0);
        valueLists.set(keyLists.indexOf("MOST EFFECTIVE"), punchTypeInfoDTO.punchtype);
        punchTypeInfoDTO = punchTypeInfoDTOs.get(punchTypeInfoDTOs.size() - 1);
        valueLists.set(keyLists.indexOf("LEAST EFFECTIVE"), punchTypeInfoDTO.punchtype);

        //most thrown punch
        Collections.sort(punchTypeInfoDTOs, MOST_THROWN_COMPARATOR);
        punchTypeInfoDTO = punchTypeInfoDTOs.get(0);
        valueLists.set(keyLists.indexOf("MOST THROWN PUNCH"), punchTypeInfoDTO.punchtype);

        //avg punches per minute
        int avgpunchpermin = (int)((float)totalpunchcount / totaltime * 60);
        valueLists.set(keyLists.indexOf("AVG PUNCHES PER MINUTE"), String.valueOf(avgpunchpermin));

        commonStatsAdapter.setData(keyLists, valueLists);
        commonStatsAdapter.notifyDataSetChanged();
    }

    private void updateCirlceView(boolean hasinner, float outerangle, float innerangle){
        circlePercentView.setAngle(outerangle);
        circlePercentView.setInnerAngle(innerangle);
        circlePercentView.setInner(hasinner);

//        if (outerangle > 280)
//            circlePercentView.setActivePaint(getResources().getColor(R.color.force_text_color));
//        else
//            circlePercentView.setActivePaint(getResources().getColor(R.color.speed_text_color));
//
//        if (innerangle > 280)
//            circlePercentView.setInnerPaint(getResources().getColor(R.color.force_text_color));
//        else
//            circlePercentView.setInnerPaint(getResources().getColor(R.color.speed_text_color));

        circlePercentView.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Super", "overview on resume");
        loadStatsInfo();
    }

    private void initData(){
        keyLists.add("TRAINING TYPE");
        keyLists.add("TRAINING TIME");
        keyLists.add("MAX SPEED");
        keyLists.add("MIN SPEED");
        keyLists.add("AVG SPEED");
        keyLists.add("MAX POWER");
        keyLists.add("MIN POWER");
        keyLists.add("AVG POWER");
        keyLists.add("MOST EFFECTIVE");
        keyLists.add("LEAST EFFECTIVE");
        keyLists.add("MOST THROWN PUNCH");
        keyLists.add("REACTION TIME");
        keyLists.add("MOST POWERFUL PUNCH");
        keyLists.add("FASTEST PUNCH");
        keyLists.add("SLOWEST PUNCH");
        keyLists.add("WEAKEST PUNCH");
        keyLists.add("AVG PUNCHES PER MINUTE");
        keyLists.add("FATIGUE");
        keyLists.add("MAX KICK SPEED");
        keyLists.add("MIN KICK SPEED");
        keyLists.add("AVG KICK SPEED");

        valueLists.add("BOXING");
        valueLists.add("00:00:00");
        valueLists.add("0 MPH");
        valueLists.add("0 MPH");
        valueLists.add("0 MPH");
        valueLists.add("0 LBS");
        valueLists.add("0 LBS");
        valueLists.add("0 LBS");
        valueLists.add("");
        valueLists.add("");
        valueLists.add("");
        valueLists.add("0 SEC");
        valueLists.add("");
        valueLists.add("");
        valueLists.add("");
        valueLists.add("");
        valueLists.add("");
        valueLists.add("0%");
        valueLists.add("0 LBS");
        valueLists.add("0 LBS");
        valueLists.add("0 LBS");
    }

    private void refreshShow(int select){
        switch (select){
            case 0:
                currentSelected = 0;
                Collections.sort(rankDTOs, SPEED_COMPARATOR);
                rankAdapter.setData(rankDTOs);
                rankAdapter.notifyDataSetChanged();

                speedSelectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_selected));
                forceSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                countSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                percentSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;

            case 1:
                currentSelected = 1;
                Collections.sort(rankDTOs, FORCE_COMPARATOR);
                rankAdapter.setData(rankDTOs);
                rankAdapter.notifyDataSetChanged();

                forceSelectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_selected));
                speedSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                countSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                percentSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;

            case 2:
                currentSelected = 2;
                Collections.sort(rankDTOs, COUNT_COMPARATOR);
                rankAdapter.setData(rankDTOs);
                rankAdapter.notifyDataSetChanged();

                countSelectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_selected));
                forceSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                speedSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                percentSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;

            case 3:
                currentSelected = 3;
                Collections.sort(rankDTOs, PERCENT_COMPARATOR);
                rankAdapter.setData(rankDTOs);
                rankAdapter.notifyDataSetChanged();

                percentSelectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_selected));
                countSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                forceSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                speedSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;
        }
    }

    private Comparator<PunchesRankDTO> SPEED_COMPARATOR = new Comparator<PunchesRankDTO>() {
        @Override
        public int compare(PunchesRankDTO lhs, PunchesRankDTO rhs) {
            return (int) (rhs.avg_speed * 10 - lhs.avg_speed * 10);
        }
    };

    private Comparator<PunchesRankDTO> FORCE_COMPARATOR = new Comparator<PunchesRankDTO>() {
        @Override
        public int compare(PunchesRankDTO lhs, PunchesRankDTO rhs) {
            return (int) (rhs.avg_force * 10 - lhs.avg_force * 10);
        }
    };

    private Comparator<PunchesRankDTO> COUNT_COMPARATOR = new Comparator<PunchesRankDTO>() {
        @Override
        public int compare(PunchesRankDTO lhs, PunchesRankDTO rhs) {
            return (int) (rhs.punch_count * 10 - lhs.punch_count * 10);
        }
    };

    private Comparator<PunchesRankDTO> PERCENT_COMPARATOR = new Comparator<PunchesRankDTO>() {
        @Override
        public int compare(PunchesRankDTO lhs, PunchesRankDTO rhs) {
            return (int) (rhs.punch_percent - lhs.punch_percent);
        }
    };


    private Comparator<TrainingStatsPunchTypeInfoDTO> BEST_COMPARATOR = new Comparator<TrainingStatsPunchTypeInfoDTO>() {
        @Override
        public int compare(TrainingStatsPunchTypeInfoDTO lhs, TrainingStatsPunchTypeInfoDTO rhs) {
            return (int) (rhs.avgforce * rhs.avgspeed - lhs.avgforce * lhs.avgspeed);
        }
    };

    private Comparator<TrainingStatsPunchTypeInfoDTO> MAX_SPEED_COMPARATOR = new Comparator<TrainingStatsPunchTypeInfoDTO>() {
        @Override
        public int compare(TrainingStatsPunchTypeInfoDTO lhs, TrainingStatsPunchTypeInfoDTO rhs) {
            return (int) (rhs.avgspeed * 10 - lhs.avgspeed * 10);
        }
    };

    private Comparator<TrainingStatsPunchTypeInfoDTO> MAX_FORCE_COMPARATOR = new Comparator<TrainingStatsPunchTypeInfoDTO>() {
        @Override
        public int compare(TrainingStatsPunchTypeInfoDTO lhs, TrainingStatsPunchTypeInfoDTO rhs) {
            return (int) (rhs.avgforce * 10 - lhs.avgforce * 10);
        }
    };


    private Comparator<TrainingStatsPunchTypeInfoDTO> MOST_EFFECT_COMPARATOR = new Comparator<TrainingStatsPunchTypeInfoDTO>() {
        @Override
        public int compare(TrainingStatsPunchTypeInfoDTO lhs, TrainingStatsPunchTypeInfoDTO rhs) {
            return (int) (rhs.avgforce * rhs.punchcount - lhs.avgforce * lhs.punchcount);
        }
    };

    private Comparator<TrainingStatsPunchTypeInfoDTO> MOST_THROWN_COMPARATOR = new Comparator<TrainingStatsPunchTypeInfoDTO>() {
        @Override
        public int compare(TrainingStatsPunchTypeInfoDTO lhs, TrainingStatsPunchTypeInfoDTO rhs) {
            return (int) (rhs.punchcount - lhs.punchcount);
        }
    };

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
