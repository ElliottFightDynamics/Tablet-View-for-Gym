package com.efd.striketectablet.activity.compare;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.punch.Punch;
import com.efd.striketectablet.DTO.responsedto.AnalyzeCSVDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingSessionDTO;
import com.efd.striketectablet.DTO.responsedto.PunchInfoDTO;
import com.efd.striketectablet.DTO.responsedto.PunchLogPlottingDTO;
import com.efd.striketectablet.DTO.responsedto.PunchWindowPlottingDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionListDTO;
import com.efd.striketectablet.DTO.responsedto.SessionPunchinfosDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.compare.adapter.CompareSessionListAdapter;
import com.efd.striketectablet.activity.compare.adapter.GraphListViewAdpater;
import com.efd.striketectablet.activity.training.workout.NewWorkoutActivity;
import com.efd.striketectablet.activity.trainingstats.adapter.CommonStatsAdapter;
import com.efd.striketectablet.activity.trainingstats.adapter.TodayStatsPageAdapter;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.EditNewWorkoutListAdapter;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.api.RetrofitSingletonCSV;
import com.efd.striketectablet.customview.LogsLineView;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.util.IndicatorCallback;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class TrainingCompareFragment extends Fragment {

    private View view;
    private ListView sessionListView;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout punchtypeParent, punchDetailParent;
    private TextView leftBtn, rightBtn, totalcountView, avgspeedView, avgforceView;
    private TextView graphVelocityView, graphAccelerationView, graphForceView;
    private LogsLineView graphView;
    private ListView graphListView;

    private ArrayList<String> punchtypeList = new ArrayList<>();
    private ArrayList<String> punchType = new ArrayList<>();
    private SparseBooleanArray selectedpunchArray = new SparseBooleanArray();

    private ArrayList<PunchInfoDTO> punchInfoDTOs = new ArrayList<>();
    private ArrayList<PunchInfoDTO> leftPunchInfoDTOS = new ArrayList<>();
    private ArrayList<PunchInfoDTO>  sortedLeftPunchInfos = new ArrayList<>();
    private ArrayList<PunchInfoDTO>  sortedRightPunchInfos = new ArrayList<>();

    private ArrayList<PunchInfoDTO>  rightPunchInfoDTS = new ArrayList<>();
    private SparseBooleanArray selectpunchDtailArray = new SparseBooleanArray();

    private boolean isLeft = true;
    private int graphType = 0;

    private CompareSessionListAdapter sessionListAdapter;
    private GraphListViewAdpater graphListViewAdpater;
    private MainActivity mainActivity;

    private ArrayList<DBTrainingSessionDTO> dbTrainingSessionDTOs = new ArrayList<>();



    public static TrainingCompareFragment trainingCompareFragment;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Fragment newInstance() {
        trainingCompareFragment = new TrainingCompareFragment();
        return trainingCompareFragment;
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

        view = inflater.inflate(R.layout.fragment_trainingcompare, container, false);

        punchtypeList.add("JABS");
        punchtypeList.add("STRAIGHTS");
        punchtypeList.add("HOOKS");
        punchtypeList.add("UPPERCUTS");
        punchtypeList.add("UNKNOWNS");

        punchType.add("jab");
        punchType.add("straight");
        punchType.add("hook");
        punchType.add("uppercut");
        punchType.add("unknown");

        for (int i = 0; i < punchtypeList.size(); i++){
            if (i == 0){
                selectedpunchArray.put(i, true);
            }else
                selectedpunchArray.put(i, false);
        }

        initView();

        return view;
    }

    private void initView(){
        sessionListView = (ListView)view.findViewById(R.id.sessionlistview);
        sessionListAdapter = new CompareSessionListAdapter(mainActivity, dbTrainingSessionDTOs);
        sessionListView.setAdapter(sessionListAdapter);

        sessionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sessionListAdapter.getSelectedPosition() != position) {
                    sessionListAdapter.setSelectedPosition(position);
                    getSessionpunchInfos(sessionListAdapter.getItem(position));
                    sessionListAdapter.notifyDataSetChanged();

                    graphListViewAdpater.setData(new ArrayList<PunchInfoDTO>());
                    graphListViewAdpater.notifyDataSetChanged();
                }
            }
        });

        horizontalScrollView = (HorizontalScrollView)view.findViewById(R.id.punchtype_horizontalview);
        punchtypeParent = (LinearLayout)view.findViewById(R.id.punchtype_parent);
        punchDetailParent = (LinearLayout)view.findViewById(R.id.punchdetail_parent);

        leftBtn = (TextView)view.findViewById(R.id.leftpunch);
        rightBtn = (TextView)view.findViewById(R.id.rightpunch);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLeft){
                    isLeft = true;
                    updateLeftandRight();
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLeft){
                    isLeft = false;
                    updateLeftandRight();
                }
            }
        });

        totalcountView = (TextView)view.findViewById(R.id.punchtotalcount);
        avgspeedView = (TextView)view.findViewById(R.id.punchavgspeed);
        avgforceView = (TextView)view.findViewById(R.id.punchavgforce);

        graphForceView = (TextView)view.findViewById(R.id.force);
        graphAccelerationView = (TextView)view.findViewById(R.id.acceleration);
        graphVelocityView = (TextView)view.findViewById(R.id.velocity);

        graphVelocityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphType != 0){
                    graphType = 0;
                    updateGraph();
                }
            }
        });

        graphAccelerationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphType != 1){
                    graphType = 1;
                    updateGraph();
                }
            }
        });

        graphForceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphType != 2){
                    graphType = 2;
                    updateGraph();
                }
            }
        });

        graphView = (LogsLineView)view.findViewById(R.id.graph);
        graphListView = (ListView)view.findViewById(R.id.graph_list);
        graphListViewAdpater = new GraphListViewAdpater(mainActivity, new ArrayList<PunchInfoDTO>(), this);
        graphListView.setAdapter(graphListViewAdpater);

        updateGraph();
        updateLeftandRight();
        insertpunchTypeView();
    }

    private void getSessionpunchInfos(final DBTrainingSessionDTO dbTrainingSessionDTO){

        Map<String, String> querymap = new HashMap<>();
        if (TextUtils.isEmpty(dbTrainingSessionDTO.getLefthandInfo()) && TextUtils.isEmpty(dbTrainingSessionDTO.getRighthandInfo()))
            return;

        if (!TextUtils.isEmpty(dbTrainingSessionDTO.getLefthandInfo())){
            if (!mainActivity.sessionInfosDTOMap.containsKey(dbTrainingSessionDTO.getLefthandInfo())) {
                querymap.put("leftHand", dbTrainingSessionDTO.getLefthandInfo());
            }else {
                leftPunchInfoDTOS.clear();
                leftPunchInfoDTOS.addAll(mainActivity.sessionInfosDTOMap.get(dbTrainingSessionDTO.getLefthandInfo()));

                if (isLeft){
                    insertPunchDetail();
                }
            }
        }

        if (!TextUtils.isEmpty(dbTrainingSessionDTO.getRighthandInfo())){
            if (!mainActivity.sessionInfosDTOMap.containsKey(dbTrainingSessionDTO.getRighthandInfo())) {
                querymap.put("rightHand", dbTrainingSessionDTO.getRighthandInfo());
            }else {
                rightPunchInfoDTS.clear();
                rightPunchInfoDTS.addAll(mainActivity.sessionInfosDTOMap.get(dbTrainingSessionDTO.getRighthandInfo()));

                if (!isLeft){
                    insertPunchDetail();
                }
            }
        }

        if (querymap.size() > 0){
            RetrofitSingletonCSV.CSV_REST.getSessionPunchInfos(querymap).enqueue(new IndicatorCallback<SessionPunchinfosDTO>(mainActivity, false) {
                @Override
                public void onResponse(Call<SessionPunchinfosDTO> call, Response<SessionPunchinfosDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        SessionPunchinfosDTO sessionPunchinfosDTO = response.body();
                        List<PunchInfoDTO> leftInfos = sessionPunchinfosDTO.getLeftHand();
                        List<PunchInfoDTO> rightInfos = sessionPunchinfosDTO.getRightHand();

                        if (leftInfos != null && leftInfos.size() > 0){
                            mainActivity.sessionInfosDTOMap.put(dbTrainingSessionDTO.getLefthandInfo(), leftInfos);
                            leftPunchInfoDTOS.clear();
                            leftPunchInfoDTOS.addAll(leftInfos);

                            for (int j = 0; j < leftPunchInfoDTOS.size(); j++){
                                Log.e("Super", "left punches  = " + leftPunchInfoDTOS.get(j).getPunch_type() + "     " + dbTrainingSessionDTO.getLefthandInfo());
                            }
                            if (isLeft)
                                insertPunchDetail();
                        }

                        if (rightInfos != null && rightInfos.size() > 0){
                            mainActivity.sessionInfosDTOMap.put(dbTrainingSessionDTO.getRighthandInfo(), rightInfos);
                            rightPunchInfoDTS.clear();
                            rightPunchInfoDTS.addAll(rightInfos);

                            for (int j = 0; j < rightPunchInfoDTS.size(); j++){
                                Log.e("Super", "right punches  = " + rightPunchInfoDTS.get(j).getPunch_type() + "     " + dbTrainingSessionDTO.getRighthandInfo());
                            }

                            if (!isLeft)
                                insertPunchDetail();
                        }

                    } else {
                        Log.e("Super", "response is null");
                    }
                }
                @Override
                public void onFailure(Call<SessionPunchinfosDTO> call, Throwable t) {
                    super.onFailure(call, t);
                    Log.e("Super", "uploading csv file = ", t);
                }
            });
        }
    }

    private void updateGraph(){
        if (graphType == 0){
            graphVelocityView.setBackgroundResource(R.drawable.selected_punch_bg);
            graphAccelerationView.setBackgroundResource(R.drawable.unselected_punch_bg);
            graphForceView.setBackgroundResource(R.drawable.unselected_punch_bg);
            graphView.setGraphType(LogsLineView.GRAPH_TYPE.VELOCITY);
        }else if (graphType == 1){
            graphVelocityView.setBackgroundResource(R.drawable.unselected_punch_bg);
            graphAccelerationView.setBackgroundResource(R.drawable.selected_punch_bg);
            graphForceView.setBackgroundResource(R.drawable.unselected_punch_bg);
            graphView.setGraphType(LogsLineView.GRAPH_TYPE.ACCELERATION);
        }else {
            graphVelocityView.setBackgroundResource(R.drawable.unselected_punch_bg);
            graphAccelerationView.setBackgroundResource(R.drawable.unselected_punch_bg);
            graphForceView.setBackgroundResource(R.drawable.selected_punch_bg);
            graphView.setGraphType(LogsLineView.GRAPH_TYPE.FORCE);
        }
    }

    private void updateLeftandRight(){
        if (isLeft){
            leftBtn.setBackgroundResource(R.drawable.compare_handselected);
            rightBtn.setBackgroundResource(R.drawable.compare_handunselected);
            insertPunchDetail();
        }else {
            leftBtn.setBackgroundResource(R.drawable.compare_handunselected);
            rightBtn.setBackgroundResource(R.drawable.compare_handselected);
            insertPunchDetail();
        }
    }

    private void insertPunchDetail(){
        punchDetailParent.removeAllViews();
        graphListViewAdpater.setData(new ArrayList<PunchInfoDTO>());
        graphListViewAdpater.notifyDataSetChanged();

        if (isLeft){

            selectpunchDtailArray = new SparseBooleanArray();

            sortedLeftPunchInfos.clear();
            sortedLeftPunchInfos.addAll(sortPunches(leftPunchInfoDTOS));

            updateTotalInfosForSelectedPunch(sortedLeftPunchInfos);

            for (int i = 0; i < sortedLeftPunchInfos.size(); i++){
                selectpunchDtailArray.put(i, false);
            }

            insertPunchDetailView(sortedLeftPunchInfos);

        }else {

            selectpunchDtailArray = new SparseBooleanArray();

            sortedRightPunchInfos.clear();
            sortedRightPunchInfos.addAll(sortPunches(rightPunchInfoDTS));
            Log.e("Super", "right =   " + sortedRightPunchInfos.size());
            updateTotalInfosForSelectedPunch(sortedRightPunchInfos);

            for (int i = 0; i < sortedRightPunchInfos.size(); i++){
                selectpunchDtailArray.put(i, false);
            }

            insertPunchDetailView(sortedRightPunchInfos);
        }

        graphView.setPunchLogs(new ArrayList<List<PunchLogPlottingDTO>>(), new ArrayList<PunchInfoDTO>());
    }

    private void updateTotalInfosForSelectedPunch(ArrayList<PunchInfoDTO> punchInfoDTOs){
        int totalCount, avgspeed, avgforce;

        float totalSpeed = 0, totalForce = 0;

        totalCount = punchInfoDTOs.size();

        for (int i = 0; i < totalCount; i++){
            totalSpeed += Float.parseFloat(punchInfoDTOs.get(i).getPunch_speed());
            totalForce += Float.parseFloat(punchInfoDTOs.get(i).getImpulse());
        }

        if (totalCount == 0){
            avgspeed = 0;
            avgforce = 0;
        }else {
            avgspeed = (int)totalSpeed / totalCount;
            avgforce = (int)totalForce / totalCount;
        }

        totalcountView.setText(String.valueOf(totalCount));
        avgspeedView.setText(String.valueOf(avgspeed));
        avgforceView.setText(String.valueOf(avgforce));
    }

    private ArrayList<PunchInfoDTO> sortPunches(ArrayList<PunchInfoDTO> punchInfoDTOs){
        ArrayList<PunchInfoDTO> result = new ArrayList<>();
        for (int i =0; i < selectedpunchArray.size(); i++){
            if (selectedpunchArray.get(i)){
                for (int j = 0; j < punchInfoDTOs.size(); j++){
                    if (punchInfoDTOs.get(j).getPunch_type().equalsIgnoreCase(punchType.get(i)))
                        result.add(punchInfoDTOs.get(j));

                }

                break;
            }
        }

        Log.e("Super", "sorted = " + result.size());

        return result;
    }

    private void insertPunchDetailView(ArrayList<PunchInfoDTO> punchInfoDTOs){
        for (int i = 0; i < punchInfoDTOs.size(); i++){
            addPunchDetailView(punchInfoDTOs.get(i), i);
        }
    }

    private void addPunchDetailView(PunchInfoDTO punchInfoDTO, final int position){
        final LinearLayout newLayout = (LinearLayout)mainActivity.getLayoutInflater().inflate(R.layout.item_punchdetail_row, null);

        final TextView punchtimeView, punchspeedView, punchforceView, punchDurationView;
        LinearLayout punchdetailbgView;

        punchtimeView = (TextView)newLayout.findViewById(R.id.punchtime);
        punchspeedView = (TextView)newLayout.findViewById(R.id.punch_speed);
        punchforceView = (TextView)newLayout.findViewById(R.id.punch_force);
        punchDurationView = (TextView)newLayout.findViewById(R.id.punch_duration);
        punchdetailbgView = (LinearLayout)newLayout.findViewById(R.id.punch_detail_parent);

        punchtimeView.setText("1:22:04 PM");
        punchspeedView.setText(String.valueOf((int)Float.parseFloat(punchInfoDTO.getPunch_speed())) + " MPH");
        punchforceView.setText(String.valueOf((int)Float.parseFloat(punchInfoDTO.getImpulse())) + " LBS");
        punchDurationView.setText(String.valueOf((int)(Float.parseFloat(punchInfoDTO.getThrust_duration()) * 1000)) + " MS");

        punchDetailParent.addView(newLayout, position);

        updatePunchDetailView(position);

        punchtimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectpunchDtailArray.get(position)){
                    selectpunchDtailArray.put(position, false);
                    updatePunchDetailView(position);
                }else {

                    //check condition
                    if (checkAddable(selectpunchDtailArray)) {
                        selectpunchDtailArray.put(position, true);
                        updatePunchDetailView(position);
                    }else {
                        StatisticUtil.showToastMessage("Can not compare more than 5 punches");
                    }
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        newLayout.setLayoutParams(params);
    }

    private boolean checkAddable(SparseBooleanArray booleanArray){

        int checkedNum = 0;

        for (int i = 0; i < booleanArray.size(); i++){
            if (booleanArray.get(i))
                checkedNum++;

            if (checkedNum >= 5)
                return false;
        }

        return true;
    }

    private void updatePunchDetailView(int position){

        LinearLayout childView = (LinearLayout)punchDetailParent.getChildAt(position);
        TextView punchtimeView;
        LinearLayout punchdetailbgView;

        punchtimeView = (TextView)childView.findViewById(R.id.punchtime);
        punchdetailbgView = (LinearLayout)childView.findViewById(R.id.punch_detail_parent);

        if (selectpunchDtailArray.get(position)){
            punchtimeView.setBackgroundResource(R.drawable.selected_punch_bg);
            punchdetailbgView.setBackgroundColor(getResources().getColor(R.color.punchdetail_selected));

            PunchInfoDTO punchInfoDTO;
            if (isLeft){
                punchInfoDTO = sortedLeftPunchInfos.get(position);
                getPlottingLogs(punchInfoDTO, sessionListAdapter.getCurrentSession().getLefthandInfo());
            }else {
                punchInfoDTO = sortedRightPunchInfos.get(position);
                getPlottingLogs(punchInfoDTO, sessionListAdapter.getCurrentSession().getRighthandInfo());
            }

        }else {
            punchtimeView.setBackgroundResource(R.drawable.unselected_punch_bg);
            punchdetailbgView.setBackgroundColor(getResources().getColor(R.color.transparent));

            PunchInfoDTO punchInfoDTO;

            if (isLeft){
                punchInfoDTO = sortedLeftPunchInfos.get(position);
                removeItemFromGraph(punchInfoDTO, sessionListAdapter.getCurrentSession().getLefthandInfo());
            }else {
                punchInfoDTO = sortedRightPunchInfos.get(position);
                removeItemFromGraph(punchInfoDTO, sessionListAdapter.getCurrentSession().getRighthandInfo());
            }
        }
    }

    public void removeActionInGraph(PunchInfoDTO punchInfoDTO){
        int position = -1;
        if (punchInfoDTO != null) {
            if (isLeft) {
                if (sortedLeftPunchInfos.contains(punchInfoDTO)) {
                    position = sortedLeftPunchInfos.indexOf(punchInfoDTO);
                }else {
                    graphListViewAdpater.setData(new ArrayList<PunchInfoDTO>());
                    graphListViewAdpater.notifyDataSetChanged();
                }
            } else {
                if (sortedRightPunchInfos.contains(punchInfoDTO)) {
                    position = sortedRightPunchInfos.indexOf(punchInfoDTO);
                }else {
                    graphListViewAdpater.setData(new ArrayList<PunchInfoDTO>());
                    graphListViewAdpater.notifyDataSetChanged();
                }
            }
        }

        if (position != -1) {
            selectpunchDtailArray.put(position, false);
            updatePunchDetailView(position);
        }
    }

    private void getPlottingLogs(final PunchInfoDTO punchInfoDTO, final String filename){
        final String key = filename + "-" + punchInfoDTO.getPunch_window_start() + "-" + punchInfoDTO.getPunch_window_end();
        if (mainActivity.punchLogsMap.containsKey(key)){
            //add punch logs to graph
            addItemToGraph(punchInfoDTO, filename);
        }else {
            RetrofitSingletonCSV.CSV_REST.getPlottingLogs(filename, punchInfoDTO.getPunch_window_start(), punchInfoDTO.getPunch_window_end()).enqueue(new IndicatorCallback<PunchWindowPlottingDTO>(MainActivity.getInstance(), false) {
                @Override
                public void onResponse(Call<PunchWindowPlottingDTO> call, Response<PunchWindowPlottingDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        PunchWindowPlottingDTO windowPlottingDTO = response.body();
                        Log.e("Super", "punch windows = " + filename + "   " + punchInfoDTO.getPunch_window_start() + "   " + punchInfoDTO.getPunch_window_end());
                        Log.e("Super", "punches window plottings e= " + windowPlottingDTO.getError() + "    " + windowPlottingDTO.getData().size());

                        if (windowPlottingDTO.getData().size() > 0){
                            mainActivity.punchLogsMap.put(key, windowPlottingDTO.getData());
                            //add punch logs to graph
                            addItemToGraph(punchInfoDTO, filename);
                        }
                    } else {
                    }
                }
                @Override
                public void onFailure(Call<PunchWindowPlottingDTO> call, Throwable t) {
                    super.onFailure(call, t);
                }
            });
        }
    }

    public void removeItemFromGraph(PunchInfoDTO punchInfoDTO, String filename){
        ArrayList<PunchInfoDTO> punchInfoDTOs = graphView.getPunchInfos();
        ArrayList<List<PunchLogPlottingDTO>> punchPlots = graphView.getPunchLogs();

        String key = filename + "-" + punchInfoDTO.getPunch_window_start() + "-" + punchInfoDTO.getPunch_window_end();

        punchInfoDTOs.remove(punchInfoDTO);
        punchPlots.remove(mainActivity.punchLogsMap.get(key));

        graphView.setPunchLogs(punchPlots, punchInfoDTOs);

        graphListViewAdpater.setData(punchInfoDTOs);
        graphListViewAdpater.notifyDataSetChanged();
    }

    private void addItemToGraph(PunchInfoDTO punchInfoDTO, String filename){
        ArrayList<PunchInfoDTO> punchInfoDTOs = graphView.getPunchInfos();
        ArrayList<List<PunchLogPlottingDTO>> punchPlots = graphView.getPunchLogs();

        String key = filename + "-" + punchInfoDTO.getPunch_window_start() + "-" + punchInfoDTO.getPunch_window_end();
        punchInfoDTOs.add(punchInfoDTO);
        punchPlots.add(mainActivity.punchLogsMap.get(key));

        graphView.setPunchLogs(punchPlots, punchInfoDTOs);
        graphListViewAdpater.setData(punchInfoDTOs);
        graphListViewAdpater.notifyDataSetChanged();
    }

    private void insertpunchTypeView(){
        for (int i = 0; i < punchtypeList.size(); i++){
            addpunchTypeView(i);
        }
    }

    private void addpunchTypeView(final int position){
        final LinearLayout newLayout = (LinearLayout)mainActivity.getLayoutInflater().inflate(R.layout.item_punchtype_row, null);

        final TextView punchtypeView;

        punchtypeView = (TextView)newLayout.findViewById(R.id.punchtype);
        punchtypeView.setText(punchtypeList.get(position));

        punchtypeParent.addView(newLayout, position);

        if (selectedpunchArray.get(position)){
            punchtypeView.setBackgroundResource(R.drawable.selected_punch_bg);
        }else {
            punchtypeView.setBackgroundResource(R.drawable.unselected_punch_bg);
        }

        punchtypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedpunchArray.get(position))
                    return;

                for (int j = 0; j < selectedpunchArray.size(); j++){
                    if (j == position){
                        selectedpunchArray.put(j, true);
                    }else {
                        selectedpunchArray.put(j, false);
                    }
                }

                updatePunchtypeViews();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        newLayout.setLayoutParams(params);

//        horizontalScrollView.postDelayed(new Runnable() {
//            public void run() {
//                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
//            }
//        }, 100L);
    }

    private void updatePunchtypeViews(){
        int childcount = punchtypeParent.getChildCount();

        for (int i = 0; i < childcount; i++){
            LinearLayout childView = (LinearLayout)punchtypeParent.getChildAt(i);
            TextView punchtypeView = (TextView)childView.findViewById(R.id.punchtype);

            if (selectedpunchArray.get(i)){
                punchtypeView.setBackgroundResource(R.drawable.selected_punch_bg);
            }else {
                punchtypeView.setBackgroundResource(R.drawable.unselected_punch_bg);
            }
        }

        insertPunchDetail();
    }

    @Override
    public void onResume() {
        super.onResume();

        String formatteddate = simpleDateFormat.format(new Date());
        dbTrainingSessionDTOs = MainActivity.db.getSessionsofDay(Integer.parseInt(mainActivity.userId), formatteddate);

        sessionListAdapter.setSelectedPosition(0);
        sessionListAdapter.setData(dbTrainingSessionDTOs);
        sessionListAdapter.notifyDataSetChanged();

        if (dbTrainingSessionDTOs.size() > 0)
            getSessionpunchInfos(sessionListAdapter.getItem(0));
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
