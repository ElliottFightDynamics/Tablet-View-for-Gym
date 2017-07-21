package com.efd.striketectablet.util;

import android.content.Context;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.DTO.TrainingResultWorkoutDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by super on 10/05/2016.
 */
public class ComboSetUtil {

    public static HashMap<String, String> punchTypeMap;
    public static ArrayList<String> keyLists;
    public static Context mContext;

    public static ArrayList<ComboDTO>  defaultCombos;
    public static ArrayList<SetsDTO>   defaultSets;
    public static ArrayList<WorkoutDTO>  defaultWorkouts;

    public static void init(Context context){
        mContext = context;
        punchTypeMap = new HashMap<>();
        keyLists = new ArrayList<>();
        defaultCombos = new ArrayList<>();
        defaultSets = new ArrayList<>();
        defaultWorkouts = new ArrayList<>();

        //punch type
        punchTypeMap.put("1", "Jab");
        punchTypeMap.put("2", "Straight");
        punchTypeMap.put("3", "Left Hook");
        punchTypeMap.put("4", "Right Hook");
        punchTypeMap.put("5", "Left Uppercut");
        punchTypeMap.put("6", "Right Uppercut");
        punchTypeMap.put("7", "Shovel Hook");

        //movement
        punchTypeMap.put("SR", "Slip Right");
        punchTypeMap.put("SL", "Slip Left");
        punchTypeMap.put("DL", "Duck Left");
        punchTypeMap.put("DR", "Duck Right");
        punchTypeMap.put("SF", "Step Forward");
        punchTypeMap.put("SB", "Step Back");

        keyLists.add("1");
        keyLists.add("2");
        keyLists.add("3");
        keyLists.add("4");
        keyLists.add("5");
        keyLists.add("6");
        keyLists.add("7");
        keyLists.add("SR");
        keyLists.add("SL");
        keyLists.add("DL");
        keyLists.add("DR");
        keyLists.add("SF");
        keyLists.add("SB");

        initDefaultCombos();
        initDefaultSets();
        initDefaultWorkouts();
    }

    private static void initDefaultCombos(){
        ArrayList<String>  punchKeyList = new ArrayList<>();
        punchKeyList.add("1");
        punchKeyList.add("2");
        punchKeyList.add("SR");
        punchKeyList.add("2");
        punchKeyList.add("3");
        punchKeyList.add("2");
        punchKeyList.add("5");
        punchKeyList.add("6");
        punchKeyList.add("3");
        punchKeyList.add("2");

        defaultCombos.add(new ComboDTO("Attack", punchKeyList, 1));

        punchKeyList = new ArrayList<>();
        punchKeyList.add("1");
        punchKeyList.add("2");
        punchKeyList.add("5");
        punchKeyList.add("7");
        punchKeyList.add("3");
        punchKeyList.add("2");
        punchKeyList.add("SR");
        punchKeyList.add("5");
        punchKeyList.add("3");
        punchKeyList.add("1");

        defaultCombos.add(new ComboDTO("Crafty", punchKeyList, 2));

        punchKeyList = new ArrayList<>();
        punchKeyList.add("1");
        punchKeyList.add("3");
        punchKeyList.add("5");
        punchKeyList.add("5");
        punchKeyList.add("3");
        punchKeyList.add("1");
        punchKeyList.add("5");
        punchKeyList.add("3");
        punchKeyList.add("3");
        punchKeyList.add("1");

        defaultCombos.add(new ComboDTO("Left overs", punchKeyList, 3));

        punchKeyList = new ArrayList<>();
        punchKeyList.add("1");
        punchKeyList.add("2");
        punchKeyList.add("6");
        punchKeyList.add("7");
        punchKeyList.add("3");
        punchKeyList.add("2");
        punchKeyList.add("5");
        punchKeyList.add("1");
        punchKeyList.add("3");
        punchKeyList.add("2");

        defaultCombos.add(new ComboDTO("Defensive", punchKeyList, 4));

        punchKeyList = new ArrayList<>();
        punchKeyList.add("3");
        punchKeyList.add("5");
        punchKeyList.add("4");
        punchKeyList.add("1");
        punchKeyList.add("5");
        punchKeyList.add("2");
        punchKeyList.add("1");
        punchKeyList.add("6");
        punchKeyList.add("3");
        punchKeyList.add("2");

        defaultCombos.add(new ComboDTO("Super Banger", punchKeyList, 5));
    }

    private static void initDefaultSets(){
        ArrayList<Integer> comboIDList = new ArrayList<>();
        comboIDList.add(1);
        comboIDList.add(2);
        comboIDList.add(3);
        defaultSets.add(new SetsDTO("Aggressor", comboIDList, 1));

        comboIDList = new ArrayList<>();
        comboIDList.add(1);
        comboIDList.add(4);
        comboIDList.add(5);
        defaultSets.add(new SetsDTO("Defensive", comboIDList, 2));

    }

    private static void initDefaultWorkouts(){

        ArrayList<ArrayList<Integer>>  roundComboLists = new ArrayList<>();

        ArrayList<Integer> comboIDList = new ArrayList<>();
        comboIDList.add(1);
        comboIDList.add(2);
        comboIDList.add(3);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(1);
        comboIDList.add(4);
        comboIDList.add(5);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(2);
        comboIDList.add(3);
        comboIDList.add(1);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(3);
        comboIDList.add(4);
        comboIDList.add(2);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(3);
        comboIDList.add(1);
        comboIDList.add(5);
        roundComboLists.add(comboIDList);

        defaultWorkouts.add(new WorkoutDTO(1, "Workout 1", 5, roundComboLists, PresetUtil.timeList.size() / 2, PresetUtil.timeList.size() /2,
                PresetUtil.timeList.size() / 2, PresetUtil.warningList.size() / 2, PresetUtil.gloveList.size() / 2));

        roundComboLists = new ArrayList<>();

        comboIDList = new ArrayList<>();
        comboIDList.add(1);
        comboIDList.add(5);
        comboIDList.add(3);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(2);
        comboIDList.add(4);
        comboIDList.add(3);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(5);
        comboIDList.add(3);
        comboIDList.add(4);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(1);
        comboIDList.add(4);
        comboIDList.add(2);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(3);
        comboIDList.add(1);
        comboIDList.add(5);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(2);
        comboIDList.add(1);
        comboIDList.add(5);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(3);
        comboIDList.add(2);
        comboIDList.add(5);
        roundComboLists.add(comboIDList);

        comboIDList = new ArrayList<>();
        comboIDList.add(3);
        comboIDList.add(4);
        comboIDList.add(1);
        roundComboLists.add(comboIDList);

        defaultWorkouts.add(new WorkoutDTO(2, "Workout 2", 8, roundComboLists, PresetUtil.timeList.size() / 2, PresetUtil.timeList.size() /2,
                PresetUtil.timeList.size() / 2, PresetUtil.warningList.size() / 2, PresetUtil.gloveList.size() / 2));
    }

    public static ComboDTO getComboDtowithID(int id){
        ArrayList<ComboDTO> comboDTOs = SharedPreferencesUtils.getSavedCombinationList(mContext);
        for (int i = 0; i < comboDTOs.size(); i++){
            if (id == comboDTOs.get(i).getId())
                return comboDTOs.get(i);
        }

        return null;
    }

    public static void updateComboDto(ComboDTO comboDTO){
        ArrayList<ComboDTO> comboDTOs = SharedPreferencesUtils.getSavedCombinationList(mContext);
        for (int i = 0; i < comboDTOs.size(); i++){
            if (comboDTO.getId() == comboDTOs.get(i).getId()){
                comboDTOs.set(i, comboDTO);
                SharedPreferencesUtils.saveCombinationList(mContext, comboDTOs);
                return;
            }
        }
    }

    public static void addComboDto(ComboDTO comboDTO){
        ArrayList<ComboDTO> comboDTOs = SharedPreferencesUtils.getSavedCombinationList(mContext);
        comboDTOs.add(comboDTO);
        SharedPreferencesUtils.saveCombinationList(mContext, comboDTOs);
    }

    public static void deleteComboDto(ComboDTO comboDTO){
        ArrayList<ComboDTO> comboDTOs = SharedPreferencesUtils.getSavedCombinationList(mContext);

        for (int i = 0; i < comboDTOs.size(); i++){
            if (comboDTO.getId() == comboDTOs.get(i).getId()) {
                comboDTOs.remove(i);
                continue;
            }
        }

        SharedPreferencesUtils.saveCombinationList(mContext, comboDTOs);
    }

    public static SetsDTO getSetDtowithID(int id){
        ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(mContext);
        for (int i = 0; i < setsDTOs.size(); i++){
            if (id == setsDTOs.get(i).getId())
                return setsDTOs.get(i);
        }

        return null;
    }

    public static void updateSetDto(SetsDTO setsDTO){
        ArrayList<SetsDTO> setDTOS = SharedPreferencesUtils.getSavedSetList(mContext);
        for (int i = 0; i < setDTOS.size(); i++){
            if (setsDTO.getId() == setDTOS.get(i).getId()){
                setDTOS.set(i, setsDTO);
                SharedPreferencesUtils.saveSetList(mContext, setDTOS);
                return;
            }
        }
    }

    public static void addSetDto(SetsDTO setsDTO){
        ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(mContext);
        setsDTOs.add(setsDTO);
        SharedPreferencesUtils.saveSetList(mContext, setsDTOs);
    }

    public static void deleteSetDto(SetsDTO setsDTO){
        ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(mContext);
        for (int i = 0; i < setsDTOs.size(); i++){
            if (setsDTO.getId() == setsDTOs.get(i).getId()) {
                setsDTOs.remove(i);
                continue;
            }
        }

        SharedPreferencesUtils.saveSetList(mContext, setsDTOs);
    }

    public static WorkoutDTO getWorkoutDtoWithID(int id){
        ArrayList<WorkoutDTO> workoutDTOs = SharedPreferencesUtils.getSavedWorkouts(mContext);
        for (int i = 0; i < workoutDTOs.size(); i++){
            if (id == workoutDTOs.get(i).getId())
                return workoutDTOs.get(i);
        }

        return null;
    }

    public static void updateWorkoutDto(WorkoutDTO workoutDTO){
        ArrayList<WorkoutDTO> workoutDTOs = SharedPreferencesUtils.getSavedWorkouts(mContext);
        for (int i = 0; i < workoutDTOs.size(); i++){
            if (workoutDTO.getId() == workoutDTOs.get(i).getId()){
                workoutDTOs.set(i, workoutDTO);
                SharedPreferencesUtils.saveWorkoutList(mContext, workoutDTOs);
                return;
            }
        }
    }

    public static void addWorkoutDto(WorkoutDTO workoutDTO){
        ArrayList<WorkoutDTO> workoutDTOs = SharedPreferencesUtils.getSavedWorkouts(mContext);
        workoutDTOs.add(workoutDTO);
        SharedPreferencesUtils.saveWorkoutList(mContext, workoutDTOs);
    }

    public static void deleteWorkoutDto(WorkoutDTO workoutDTO){
        ArrayList<WorkoutDTO> workoutDTOs = SharedPreferencesUtils.getSavedWorkouts(mContext);
        for (int i = 0; i < workoutDTOs.size(); i++){
            if (workoutDTO.getId() == workoutDTOs.get(i).getId()) {
                workoutDTOs.remove(i);
                continue;
            }
        }

        SharedPreferencesUtils.saveWorkoutList(mContext, workoutDTOs);
    }

    public static void deleteComboFromAllSets(ComboDTO comboDTO){
        ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(mContext);

        ArrayList<SetsDTO> newSetsDTOS = new ArrayList<>();

        for (int i = 0; i < setsDTOs.size(); i++){
            SetsDTO setsDTO = setsDTOs.get(i);

            if (!setsDTO.getComboIDLists().contains(comboDTO.getId())) {
                newSetsDTOS.add(setsDTO);
                continue;
            }

            ArrayList<Integer> newComboIDList = new ArrayList<>();
            for (int j = 0; j < setsDTO.getComboIDLists().size(); j++){
                if (comboDTO.getId() != setsDTO.getComboIDLists().get(j)){
                    newComboIDList.add(setsDTO.getComboIDLists().get(j));
                }
            }

            SetsDTO newSetDto = new SetsDTO(setsDTO.getName(), newComboIDList, setsDTO.getId());
            newSetsDTOS.add(newSetDto);

//            if (newComboIDList.size() == 0){
//                //delete this set from set list
//            }else {
//                SetsDTO newSetDto = new SetsDTO(setsDTO.getName(), newComboIDList, setsDTO.getId());
//                newSetsDTOS.add(newSetDto);
//            }
        }

        SharedPreferencesUtils.saveSetList(mContext, newSetsDTOS);
    }

    public static void deleteComboFromAllWorkout(ComboDTO comboDTO){
        ArrayList<WorkoutDTO> workoutDTOs = SharedPreferencesUtils.getSavedWorkouts(mContext);

        ArrayList<WorkoutDTO> newWorkoutDtos = new ArrayList<>();

        for (int i = 0; i < workoutDTOs.size(); i++){
            WorkoutDTO workoutDTO = workoutDTOs.get(i);
            ArrayList<ArrayList<Integer>> newRoundComboLists = new ArrayList<>();

            for (int j = 0; j < workoutDTO.getRoundcount(); j++){
                ArrayList<Integer> comboLists = workoutDTO.getRoundsetIDs().get(j);

                if (!comboLists.contains(comboDTO.getId())){
                    newRoundComboLists.add(comboLists);
                    continue;
                }

                ArrayList<Integer> newComboLists = new ArrayList<>();

                for (int k = 0; k < comboLists.size(); k++){
                    if (comboLists.get(k) != comboDTO.getId()){
                        newComboLists.add(comboLists.get(k));
                    }
                }

                newRoundComboLists.add(newComboLists);
            }

            WorkoutDTO newWorkoutDTO = new WorkoutDTO(workoutDTO.getId(), workoutDTO.getName(), workoutDTO.getRoundcount(),
                    newRoundComboLists, workoutDTO.getRound(), workoutDTO.getRest(), workoutDTO.getPrepare(), workoutDTO.getWarning(), workoutDTO.getGlove());

            newWorkoutDtos.add(newWorkoutDTO);
        }

        SharedPreferencesUtils.saveWorkoutList(mContext, newWorkoutDtos);
    }

    public static ArrayList<TrainingResultComboDTO> getCombostatsforDay(DBAdapter dbAdapter, String formatteddate){
        List<String> comboResults = dbAdapter.getTrainingStatswithtype(Integer.parseInt(CommonUtils.getServerUserId(mContext)), EFDConstants.TYPE_COMBO, formatteddate);
        ArrayList<TrainingResultComboDTO> resultComboDTOs = new ArrayList<>();
        Gson gson = new Gson();

        for (int i = 0; i < comboResults.size(); i++){
            TrainingResultComboDTO trainingResultComboDTO;
            Type type = new TypeToken<TrainingResultComboDTO>(){}.getType();
            trainingResultComboDTO = gson.fromJson(comboResults.get(i), type);

            resultComboDTOs.add(trainingResultComboDTO);
        }

        return resultComboDTOs;
    }

    public static ArrayList<TrainingResultSetDTO> getSetstatsforDay(DBAdapter dbAdapter, String formatteddate){
        List<String> setResults = dbAdapter.getTrainingStatswithtype(Integer.parseInt(CommonUtils.getServerUserId(mContext)), EFDConstants.TYPE_SET, formatteddate);

        ArrayList<TrainingResultSetDTO> resultSetDTOs = new ArrayList<>();
        Gson gson = new Gson();

        for (int i = 0; i < setResults.size(); i++){
            TrainingResultSetDTO trainingResultSetDTO;
            Type type = new TypeToken<TrainingResultSetDTO>(){}.getType();
            trainingResultSetDTO = gson.fromJson(setResults.get(i), type);

            resultSetDTOs.add(trainingResultSetDTO);
        }

        return resultSetDTOs;
    }

    public static ArrayList<TrainingResultWorkoutDTO> getWorkoutstatsforDay(DBAdapter dbAdapter, String formatteddate){
        List<String> workoutResults = dbAdapter.getTrainingStatswithtype(Integer.parseInt(CommonUtils.getServerUserId(mContext)), EFDConstants.TYPE_WORKOUT, formatteddate);

        ArrayList<TrainingResultWorkoutDTO> resultWorkoutDTOs = new ArrayList<>();
        Gson gson = new Gson();

        for (int i = 0; i < workoutResults.size(); i++){
            TrainingResultWorkoutDTO trainingResultWorkoutDTO;
            Type type = new TypeToken<TrainingResultWorkoutDTO>(){}.getType();
            trainingResultWorkoutDTO = gson.fromJson(workoutResults.get(i), type);

            resultWorkoutDTOs.add(trainingResultWorkoutDTO);
        }

        return resultWorkoutDTOs;
    }

    public static void saveComboStats(DBAdapter dbAdapter, TrainingResultComboDTO comboresult){
        Gson gson = new Gson();
        String jsonString = gson.toJson(comboresult);
        dbAdapter.insertTrainingPlanRestuls(Integer.parseInt(CommonUtils.getServerUserId(mContext)), EFDConstants.TYPE_COMBO, jsonString);
    }

    public static void saveSetStats(DBAdapter dbAdapter, TrainingResultSetDTO setresult){
        Gson gson = new Gson();
        String jsonString = gson.toJson(setresult);
        dbAdapter.insertTrainingPlanRestuls(Integer.parseInt(CommonUtils.getServerUserId(mContext)),EFDConstants.TYPE_SET, jsonString);
    }

    public static void saveWorkStats(DBAdapter dbAdapter, TrainingResultWorkoutDTO workoutresult){
        Gson gson = new Gson();
        String jsonString = gson.toJson(workoutresult);
        dbAdapter.insertTrainingPlanRestuls(Integer.parseInt(CommonUtils.getServerUserId(mContext)),EFDConstants.TYPE_WORKOUT, jsonString);
    }

    public static String getPunchkeyDetail (TrainingResultComboDTO comboDTO){
        String result = "";
        if (comboDTO.getPunches() == null || comboDTO.getPunches().size() == 0){
            return result;
        }

        for (int i = 0; i < comboDTO.getPunches().size(); i++){
            if (i == 0)
                result = result + comboDTO.getPunches().get(i).getPunchKey();
            else
                result = result + "-" + comboDTO.getPunches().get(i).getPunchKey();
        }

        return result;
    }
}
