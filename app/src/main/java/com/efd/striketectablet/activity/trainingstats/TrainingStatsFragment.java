package com.efd.striketectablet.activity.trainingstats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.efd.striketectablet.DTO.PunchesRankDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.customview.CurveChartView;
import com.efd.striketectablet.customview.CustomCircleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TrainingStatsFragment extends Fragment{

    MainActivity mainActivityInstance;

    private ListView punchrankListView, commonstatsListView;
    View speedSelectView, forceSelectView, countSelectView;

    CustomCircleView circlePercentView;

    private PunchesRankAdapter rankAdapter;
    private CommonStatsAdapter commonStatsAdapter;

    private ArrayList<PunchesRankDTO> rankDTOs;
    private ArrayList<String> keyLists, valueLists;

    private int currentSelected = -1;

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

        speedSelectView = view.findViewById(R.id.speedselect);
        forceSelectView = view.findViewById(R.id.forceselect);
        countSelectView = view.findViewById(R.id.countselect);

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

        punchrankListView = (ListView)view.findViewById(R.id.punchrank_listview);
        commonstatsListView = (ListView)view.findViewById(R.id.commonstats_listview);

        rankAdapter = new PunchesRankAdapter(getActivity(), rankDTOs);
        punchrankListView.setAdapter(rankAdapter);

        commonStatsAdapter = new CommonStatsAdapter(getActivity(), keyLists, valueLists);
        commonstatsListView.setAdapter(commonStatsAdapter);

        initData();

        circlePercentView.setDeactivePaint(getResources().getColor(R.color.advise_deactivecolor));
        circlePercentView.setStrokeWidth(18);

        updateCirlceView(true, 320, 240);

        return view;
    }

    private void updateCirlceView(boolean hasinner, float outerangle, float innerangle){
        circlePercentView.setAngle(outerangle);
        circlePercentView.setInnerAngle(innerangle);
        circlePercentView.setInner(hasinner);

        if (outerangle > 280)
            circlePercentView.setActivePaint(getResources().getColor(R.color.force_text_color));
        else
            circlePercentView.setActivePaint(getResources().getColor(R.color.speed_text_color));

        if (innerangle > 280)
            circlePercentView.setInnerPaint(getResources().getColor(R.color.force_text_color));
        else
            circlePercentView.setInnerPaint(getResources().getColor(R.color.speed_text_color));

        circlePercentView.invalidate();
    }

    private void initData(){
        rankDTOs.add(new PunchesRankDTO("STRAIGHT", 199.2, 27.2, 27));
        rankDTOs.add(new PunchesRankDTO("LEFT HOOK", 189.2, 28.2, 28));
        rankDTOs.add(new PunchesRankDTO("RIGHT HOOK", 399.2, 29.2, 29));
        rankDTOs.add(new PunchesRankDTO("LEFT UPPERCUT", 499.2, 227.2, 30));
        rankDTOs.add(new PunchesRankDTO("RIGHT UPPERCUT", 259.2, 127.2, 31));
        rankDTOs.add(new PunchesRankDTO("SHOVEL HOOK", 239.2, 57.2, 32));
        rankDTOs.add(new PunchesRankDTO("OVERHAND RIGHT", 169.2, 97.2, 33));
        rankDTOs.add(new PunchesRankDTO("SLIP LEFT", 639.2, 457.2, 34));
        rankDTOs.add(new PunchesRankDTO("SLIP RIGHT", 129.2, 217.2, 35));
        rankDTOs.add(new PunchesRankDTO("DUCK LEFT", 119.2, 207.2, 36));
        rankDTOs.add(new PunchesRankDTO("DUCK RIGHT", 109.2, 827.2, 37));

        refreshShow(0);

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
        keyLists.add("MOST OFTEN PUNCH");
        keyLists.add("REACTION TIME");
        keyLists.add("MOST POWERFULL PUNCH");
        keyLists.add("FASTEST PUNCH");
        keyLists.add("SLOWEST PUNCH");
        keyLists.add("WEAKSEST PUNCH");
        keyLists.add("AVG PUNCHES PER MINUTE");
        keyLists.add("FATIGUE");
        keyLists.add("MAX KICK SPEED");
        keyLists.add("MIN KICK SPEED");
        keyLists.add("AVG KICK SPEED");

        valueLists.add("BOXING");
        valueLists.add("3:32:14");
        valueLists.add("27.2 MPH");
        valueLists.add("12.1 MPH");
        valueLists.add("23.1 MPH");
        valueLists.add("622 LBS");
        valueLists.add("110 LBS");
        valueLists.add("429 LBS");
        valueLists.add("RIGHT STRAIGHT");
        valueLists.add("LEFT COEVER");
        valueLists.add("RIGHT HOOK");
        valueLists.add("0.73 SEC");
        valueLists.add("RIGHT STRAIGHT");
        valueLists.add("LEFT STRAIGHT");
        valueLists.add("JAB");
        valueLists.add("JAB");
        valueLists.add("245.4");
        valueLists.add("34%");
        valueLists.add("1,843");
        valueLists.add("2,987.7 LBS");
        valueLists.add("94%");

        commonStatsAdapter.setData(keyLists, valueLists);
        commonStatsAdapter.notifyDataSetChanged();

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
                break;

            case 1:
                currentSelected = 1;
                Collections.sort(rankDTOs, FORCE_COMPARATOR);
                rankAdapter.setData(rankDTOs);
                rankAdapter.notifyDataSetChanged();

                forceSelectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_selected));
                speedSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                countSelectView.setBackgroundColor(getResources().getColor(R.color.transparent));
                break;

            case 2:
                currentSelected = 2;
                Collections.sort(rankDTOs, COUNT_COMPARATOR);
                rankAdapter.setData(rankDTOs);
                rankAdapter.notifyDataSetChanged();

                countSelectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_selected));
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
}
