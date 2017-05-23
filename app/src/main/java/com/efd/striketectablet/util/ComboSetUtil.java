package com.efd.striketectablet.util;

import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by super on 10/05/2016.
 */
public class ComboSetUtil {

    public static HashMap<String, String> punchTypeMap;
    public static ArrayList<String> keyLists;

    public static void init(){
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
}
