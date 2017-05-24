package com.efd.striketectablet.util;

import android.content.Context;
import android.util.Log;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by super on 10/05/2016.
 */
public class ComboSetUtil {

    public static HashMap<String, String> punchTypeMap;
    public static ArrayList<String> keyLists;
    public static Context mContext;

    public static void init(Context context){
        mContext = context;
        punchTypeMap = new HashMap<>();
        keyLists = new ArrayList<>();

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

            if (newComboIDList.size() == 0){
                //delete this set from set list
            }else {
                SetsDTO newSetDto = new SetsDTO(setsDTO.getName(), newComboIDList, setsDTO.getId());
                newSetsDTOS.add(newSetDto);
            }
        }

        SharedPreferencesUtils.saveSetList(mContext, newSetsDTOS);
    }
}
