package com.efd.striketectablet.utilities;

import android.util.Log;

import com.efd.striketectablet.DTO.CalendarSummaryDTO;
import com.efd.striketectablet.DTO.ProgressSummaryDTO;
import com.efd.striketectablet.DTO.PunchCountSummaryDTO;
import com.efd.striketectablet.DTO.ResultSummaryDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class JSONParsers {
    private static final String TAG = "JSONParsers";

    public static ResultSummaryDTO parseTrainingResultJSON(JSONObject jsonData, int userID, String date, String summaryType) {

        JSONObject data;
        ResultSummaryDTO resultSummaryDTO = null;

        try {
            data = jsonData.getJSONObject("jsonPunchData");

            resultSummaryDTO = new ResultSummaryDTO(userID, date, summaryType.toLowerCase(Locale.getDefault()), data.getDouble("lh_jab_speed"), data.getDouble("lh_jab_force"),
                    data.getDouble("lh_jab_total"), data.getDouble("lh_straight_speed"), data.getDouble("lh_straight_force"),
                    data.getDouble("lh_straight_total"), data.getDouble("lh_hook_speed"), data.getDouble("lh_hook_force"), data.getDouble("lh_hook_total"),
                    data.getDouble("lh_uppercut_speed"), data.getDouble("lh_uppercut_force"), data.getDouble("lh_uppercut_total"),
                    data.getDouble("lh_unrecognized_speed"), data.getDouble("lh_unrecognized_force"), data.getDouble("lh_unrecognized_total"),
                    data.getDouble("rh_jab_speed"), data.getDouble("rh_jab_force"), data.getDouble("rh_jab_total"), data.getDouble("rh_straight_speed"),
                    data.getDouble("rh_straight_force"), data.getDouble("rh_straight_total"), data.getDouble("rh_hook_speed"),
                    data.getDouble("rh_hook_force"), data.getDouble("rh_hook_total"), data.getDouble("rh_uppercut_speed"),
                    data.getDouble("rh_uppercut_force"), data.getDouble("rh_uppercut_total"), data.getDouble("rh_unrecognized_speed"),
                    data.getDouble("rh_unrecognized_force"), data.getDouble("rh_unrecognized_total"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultSummaryDTO;
    }

    public static CalendarSummaryDTO parseCalendarResultJSON(JSONObject calendarSummaryResult, int userID, String date) {
        CalendarSummaryDTO calendarDTO = null;

        try {
            if (calendarSummaryResult.getString("success").equals("true")) {

                JSONArray monthlySummary = calendarSummaryResult.getJSONArray("calendarSummary");
                JSONObject childJSONObject = monthlySummary.getJSONObject(0);

                Log.d(TAG, "Summary : " + childJSONObject.toString());

                long maxForce = 0;
                long maxSpeed = 0;
                float averageSpeed = 0;
                float averageForce= 0;
                String duration = "00.00";

                if (childJSONObject.get("maxForce") != null) {
                    maxForce = Math.round(Double.parseDouble(childJSONObject.getString("maxForce")));
                }
                if (childJSONObject.get("maxSpeed") != null) {
                    maxSpeed = Math.round(Double.parseDouble(childJSONObject.getString("maxSpeed")));
                }
                if (childJSONObject.get("averageForce") != null) {
                    averageForce = Math.round(Double.parseDouble(childJSONObject.getString("averageForce")));
                }
                if (childJSONObject.get("averageSpeed") != null) {
                    averageSpeed = Math.round(Double.parseDouble(childJSONObject.getString("averageSpeed")));
                }
                if (calendarSummaryResult.get("duration") != null) {
                    duration = (String) calendarSummaryResult.get("duration");
                }

                String durationValue = (String) ((duration == null) ? 0 : duration);
                Log.d(TAG, "durationValue =" + durationValue);

                int totalPunch = Integer.parseInt(childJSONObject.getString("totalPunch"));

                calendarDTO = new CalendarSummaryDTO(userID, totalPunch, (int) maxForce, (int) maxSpeed, averageForce, averageSpeed, durationValue, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return calendarDTO;
    }

    public static PunchCountSummaryDTO parsePunchCountSummaryResultJSON(JSONObject punchCountSummary, String userId, String date) {
        PunchCountSummaryDTO punchCountSummaryDTO = null;

        try {
            punchCountSummaryDTO = new PunchCountSummaryDTO(userId, date, null, punchCountSummary.getInt("Punch_Count_Total"),
                    punchCountSummary.getDouble("LJ_Avg"), punchCountSummary.getDouble("LJ_Today"), punchCountSummary.getDouble("LS_Avg"),
                    punchCountSummary.getDouble("LS_Today"), punchCountSummary.getDouble("LH_Avg"), punchCountSummary.getDouble("LH_Today"),
                    punchCountSummary.getDouble("LU_Avg"), punchCountSummary.getDouble("LU_Today"), punchCountSummary.getDouble("LR_Avg"),
                    punchCountSummary.getDouble("LR_Today"), punchCountSummary.getDouble("RJ_Avg"), punchCountSummary.getDouble("RJ_Today"),
                    punchCountSummary.getDouble("RS_Avg"), punchCountSummary.getDouble("RS_Today"), punchCountSummary.getDouble("RH_Avg"),
                    punchCountSummary.getDouble("RH_Today"), punchCountSummary.getDouble("RU_Avg"), punchCountSummary.getDouble("RU_Today"),
                    punchCountSummary.getDouble("RR_Avg"), punchCountSummary.getDouble("RR_Today"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return punchCountSummaryDTO;
    }

    public static ProgressSummaryDTO parseProgressResultJSON(JSONObject jsonObject, int userID, String date, String summaryType) {
        ProgressSummaryDTO progressSummaryDTO = null;

        double leftJabSpeed = 0.0, leftJabForce = 0.0, leftStraightSpeed = 0.0, leftStraightForce = 0.0;
        double leftHookSpeed = 0.0, leftHookForce = 0.0, leftUppercutSpeed = 0.0, leftUppercutForce = 0.0;
        double leftUnrecognizedSpeed = 0.0, leftUnrecognizedForce = 0.0;
        double rightJabSpeed = 0.0, rightJabForce = 0.0, rightStraightSpeed = 0.0, rightStraightForce = 0.0;
        double rightHookSpeed = 0.0, rightHookForce = 0.0, rightUppercutSpeed = 0.0, rightUppercutForce = 0.0;
        double rightUnrecognizedSpeed = 0.0, rightUnrecognizedForce = 0.0;

        try {

            leftJabSpeed = jsonObject.has("LJSpeed") ? jsonObject.getDouble("LJSpeed") : 0.0;
            leftJabForce = jsonObject.has("LJForce") ? jsonObject.getDouble("LJForce") : 0.0;
            leftStraightSpeed = jsonObject.has("LSSpeed") ? jsonObject.getDouble("LSSpeed") : 0.0;
            leftStraightForce = jsonObject.has("LSForce") ? jsonObject.getDouble("LSForce") : 0.0;
            leftHookSpeed = jsonObject.has("LHSpeed") ? jsonObject.getDouble("LHSpeed") : 0.0;
            leftHookForce = jsonObject.has("LHForce") ? jsonObject.getDouble("LHForce") : 0.0;
            leftUppercutSpeed = jsonObject.has("LUSpeed") ? jsonObject.getDouble("LUSpeed") : 0.0;
            leftUppercutForce = jsonObject.has("LUForce") ? jsonObject.getDouble("LUForce") : 0.0;
            leftUnrecognizedSpeed = jsonObject.has("LRSpeed") ? jsonObject.getDouble("LRSpeed") : 0.0;
            leftUnrecognizedForce = jsonObject.has("LRForce") ? jsonObject.getDouble("LRForce") : 0.0;
            rightJabSpeed = jsonObject.has("RJSpeed") ? jsonObject.getDouble("RJSpeed") : 0.0;
            rightJabForce = jsonObject.has("RJForce") ? jsonObject.getDouble("RJForce") : 0.0;
            rightStraightSpeed = jsonObject.has("RSSpeed") ? jsonObject.getDouble("RSSpeed") : 0.0;
            rightStraightForce = jsonObject.has("RSForce") ? jsonObject.getDouble("RSForce") : 0.0;
            rightHookSpeed = jsonObject.has("RHSpeed") ? jsonObject.getDouble("RHSpeed") : 0.0;
            rightHookForce = jsonObject.has("RHForce") ? jsonObject.getDouble("RHForce") : 0.0;
            rightUppercutSpeed = jsonObject.has("RUSpeed") ? jsonObject.getDouble("RUSpeed") : 0.0;
            rightUppercutForce = jsonObject.has("RUForce") ? jsonObject.getDouble("RUForce") : 0.0;
            rightUnrecognizedSpeed = jsonObject.has("RRSpeed") ? jsonObject.getDouble("RRSpeed") : 0.0;
            rightUnrecognizedForce = jsonObject.has("RRForce") ? jsonObject.getDouble("RRForce") : 0.0;

            progressSummaryDTO = new ProgressSummaryDTO(userID, date, summaryType, leftJabSpeed, leftJabForce, leftStraightSpeed, leftStraightForce, leftHookSpeed, leftHookForce, leftUppercutSpeed, leftUppercutForce, leftUnrecognizedSpeed, leftUnrecognizedForce, rightJabSpeed, rightJabForce, rightStraightSpeed, rightStraightForce, rightHookSpeed, rightHookForce, rightUppercutSpeed, rightUppercutForce, rightUnrecognizedSpeed, rightUnrecognizedForce);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return progressSummaryDTO;
    }

}
