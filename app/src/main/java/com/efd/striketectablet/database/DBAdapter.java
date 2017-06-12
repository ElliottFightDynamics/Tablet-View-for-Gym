package com.efd.striketectablet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efd.striketectablet.DTO.AuthenticationDTO;
import com.efd.striketectablet.DTO.BoxerProfileDTO;
import com.efd.striketectablet.DTO.CalendarSummaryDTO;
import com.efd.striketectablet.DTO.DBTrainingDataDTO;
import com.efd.striketectablet.DTO.DBTrainingDataDetailsDTO;
import com.efd.striketectablet.DTO.DBTrainingPunchDataDTO;
import com.efd.striketectablet.DTO.DBTrainingPunchDataPeakSummaryDTO;
import com.efd.striketectablet.DTO.DBTrainingSessionDTO;
import com.efd.striketectablet.DTO.ProgressSummaryDTO;
import com.efd.striketectablet.DTO.PunchCountSummaryDTO;
import com.efd.striketectablet.DTO.ResultSummaryDTO;
import com.efd.striketectablet.DTO.SyncResponseDTO;
import com.efd.striketectablet.DTO.TrainingPunchDTO;
import com.efd.striketectablet.DTO.TrainingStatsPunchTypeInfoDTO;
import com.efd.striketectablet.DTO.UserDTO;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * A class for connecting to SQLiteDatabase in Android.
 *
 * @version: 1 $Date: 2013/06/14
 */
public class DBAdapter {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");



    // A initialization for database version.
    private static final int DATABASE_VERSION = 4; //TODO:- Note- When ever you do changes/modification in your database. Please upgrade the version.

    // A string tag to display message in log file.
    private static final String TAG = "DBAdapter";


    // *************training_data*******************

    // A TRAINING_DATA_TABLE initializing to training_data
    private static final String TRAINING_DATA_TABLE = "training_data";

    public static final String KEY_TRAINING_DATA_ID = "id";
    public static final String KEY_TRAINING_DATA_LEFT_HAND = "left_hand";
    public static final String KEY_TRAINING_DATA_TRAINING_SESSION_ID = "training_session_id";
    public static final String KEY_TRAINING_DATA_USER_ID = "user_id";
    public static final String KEY_TRAINING_DATA_SYNC = "sync";
    public static final String KEY_TRAINING_DATA_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_TRAINING_DATA_ID = "server_id";

    public static String getTrainingDataTable() {
        return TRAINING_DATA_TABLE;
    }

    // *************training_data_details*******************

    // A TRAINING_DATA_DETAILS_TABLE initializing to training_data_details
    private static final String TRAINING_DATA_DETAILS_TABLE = "training_data_details";

    public static final String KEY_TRAINING_DATA_DETAILS_ID = "id";
    public static final String KEY_TRAINING_DATA_DETAILS_AX = "ax";
    public static final String KEY_TRAINING_DATA_DETAILS_AY = "ay";
    public static final String KEY_TRAINING_DATA_DETAILS_AZ = "az";
    public static final String KEY_TRAINING_DATA_DETAILS_CURRENT_FORCE = "current_force";
    public static final String KEY_TRAINING_DATA_DETAILS_CURRENT_VELOCITY = "current_velocity";
    public static final String KEY_TRAINING_DATA_DETAILS_DATA_RECIEVED_TIME = "data_recieved_time";
    public static final String KEY_TRAINING_DATA_DETAILS_GX = "gx";
    public static final String KEY_TRAINING_DATA_DETAILS_GY = "gy";
    public static final String KEY_TRAINING_DATA_DETAILS_GZ = "gz";
    public static final String KEY_TRAINING_DATA_DETAILS_HEAD_TRAUMA = "head_trauma";
    public static final String KEY_TRAINING_DATA_DETAILS_MILLI_SECOND = "milli_second";
    public static final String KEY_TRAINING_DATA_DETAILS_TEMP = "temp";
    public static final String KEY_TRAINING_DATA_DETAILS_TRAINING_DATA_ID = "training_data_id";
    public static final String KEY_TRAINING_DATA_DETAILS_SYNC = "sync";
    public static final String KEY_TRAINING_DATA_DETAILS_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_TRAINING_DATA_DETAILS_ID = "server_id";

    public static String getTrainingDataDetailsTable() {
        return TRAINING_DATA_DETAILS_TABLE;
    }

    // *************training_punch_data*******************

    // A TRAINING_PUNCH_DATA_TABLE initializing to training_punch_data
    private static final String TRAINING_PUNCH_DATA_TABLE = "training_punch_data";

    public static final String KEY_TRAINING_PUNCH_DATA_ID = "id";
    public static final String KEY_TRAINING_PUNCH_DATA_MAX_FORCE = "max_force";
    public static final String KEY_TRAINING_PUNCH_DATA_MAX_SPEED = "max_speed";
    public static final String KEY_TRAINING_PUNCH_DATA_PUNCH_DATA_DATE = "punch_data_date";
    public static final String KEY_TRAINING_PUNCH_DATA_PUNCH_TYPE = "punch_type";
    public static final String KEY_TRAINING_PUNCH_DATA_TRAINING_DATA_ID = "training_data_id";
    public static final String KEY_TRAINING_PUNCH_DATA_SYNC = "sync";
    public static final String KEY_TRAINING_PUNCH_DATA_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_TRAINING_PUNCH_DATA_ID = "server_id";

    public static String getTrainingPunchDataTable() {
        return TRAINING_PUNCH_DATA_TABLE;
    }

    // *************training_punch_data_peak_summary*******************

    // A TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE initializing to
    // training_punch_data_peak_summary
    private static final String TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE = "training_punch_data_peak_summary";

    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_ID = "id";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_HOOK = "hook";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_JAB = "jab";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_PUNCH_DATA_PEAK_SUMMARY_DATE = "punch_data_peak_summary_date";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SPEED_FLAG = "speed_flag";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_STRAIGHT = "straight";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_TRAINING_DATA_ID = "training_data_id";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_UPPER_CUT = "upper_cut";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC = "sync";
    public static final String KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_TRAINING_PUNCH_DATA_PEAK_SUMMARY_ID = "server_id";

    public static String getTrainingPunchDataPeakSummaryTable() {
        return TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE;
    }

    // A String DATABASE_NAME contains database EFD_TrainerApp_DB
    private static final String DATABASE_NAME = EFDConstants.DATABASE_NAME;


    // *************training_session*******************

    // A TRAINING_SESSION_TABLE initializing to training_session
    private static final String TRAINING_SESSION_TABLE = "training_session";

    public static final String KEY_TRAINING_SESSION_ID = "id";
    public static final String KEY_TRAINING_SESSION_END_TIME = "end_time";
    public static final String KEY_TRAINING_SESSION_START_TIME = "start_time";
    public static final String KEY_TRAINING_SESSION_TRAINING_SESSION_DATE = "training_session_date";
    public static final String KEY_TRAINING_SESSION_TRAINING_TYPE = "training_type";
    public static final String KEY_TRAINING_SESSION_USER_ID = "user_id";
    public static final String KEY_TRAINING_SESSION_SYNC = "sync";
    public static final String KEY_TRAINING_SESSION_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_TRAINING_SESSION_ID = "server_id";

    public static String getTrainingSessionTable() {
        return TRAINING_SESSION_TABLE;
    }

    // *************user*******************

    // A USER_TABLE initializing to user
    private static final String USER_TABLE = "user";

    public static String getUserTable() {
        return USER_TABLE;
    }

    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_VERSION = "version";
    public static final String KEY_USER_ACCOUNT_EXPIRED = "account_expired";
    public static final String KEY_USER_ACCOUNT_LOCKED = "account_locked";
    public static final String KEY_USER_BOXERFIRSTNAME = "first_name";
    public static final String KEY_USER_BOXERLASTNAME = "last_name";
    public static final String KEY_USER_DATE_OF_BIRTH = "date_of_birth";
    public static final String KEY_USER_ENABLED = "enabled";
    public static final String KEY_USER_GENDER = "gender";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_PASSWORD_EXPIRED = "password_expired";
    public static final String KEY_USER_PHOTO = "photo";
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_COUNTRY_ID = "country_id";
    public static final String KEY_USER_EMAIL_ID = "email_id";
    public static final String KEY_USER_ZIP_CODE = "zipcode";
    public static final String KEY_USER_SYNC = "sync";
    public static final String KEY_USER_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_USER_ID = "server_id";


    // *************user_access*******************

    // A USER_ACCESS_TABLE initializing to user
    private static final String USER_ACCESS_TABLE = "user_access";

    public static String getUserAccessTable() {
        return USER_ACCESS_TABLE;
    }

    public static final String KEY_USER_ACCESS_ID = "id";
    public static final String KEY_USER_ACCESS_TOKEN = "access_token";
    public static final String KEY_USER_ACCESS_SERVER_ID = "user_id";

    // *************role*******************

    // A ROLE_TABLE initializing to role
    private static final String ROLE_TABLE = "role";

    public static final String KEY_ROLE_ID = "id";
    public static final String KEY_ROLE_VERSION = "version";
    public static final String KEY_ROLE_AUTHORITY = "authority";
    public static final String KEY_ROLE_SYNC = "sync";
    public static final String KEY_ROLE_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_ROLE_ID = "server_id";

    // *************user_role*******************

    // A USER_TABLE initializing to user_role
    private static final String USER_ROLE_TABLE = "user_role";

    public static final String KEY_USER_ROLE_ROLE_ID = "role_id";
    public static final String KEY_USER_ROLE_USER_ID = "user_id";
    public static final String KEY_USER_ROLE_SYNC = "sync";
    public static final String KEY_USER_ROLE_SYNC_DATE = "sync_date";
    public static final String KEY_SERVER_USER_ROLE_ID = "server_id";

    //super added 2 tables for training stats page

    // ************** gym training_session *************
    // A GYM TRAINING_DATA_TABLE initializing to gym training_data
    private static final String GYM_TRAINING_SESSION_TABLE = "gym_training_session";

    public static final String KEY_GYM_TRAINING_SESSION_ID = "id";
    public static final String KEY_GYM_TRAINING_SESSION_TRAINING_SESSION_DATE = "gym_training_session_date";
    public static final String KEY_GYM_TRAINING_SESSION_START_TIME = "start_time";
    public static final String KEY_GYM_TRAINING_SESSION_END_TIME = "end_time";

    public static String getGymTrainingSessionTable(){
        return GYM_TRAINING_SESSION_TABLE;
    }

//     Query string for creating table GYM_TRAINING_SESSION_TABLE .
    private static final String DATABASE_CREATE_GYM_TRAINING_SESSION = "create table "
            + GYM_TRAINING_SESSION_TABLE
            + " (id integer NOT NULL primary key autoincrement, "
            + " gym_training_session_date text DEFAULT null, "
            + " start_time integer default NULL, "
            + " end_time integer default NULL); ";

//    private static final String DATABASE_CREATE_GYM_TRAINING_SESSION = "create table "
//            + GYM_TRAINING_SESSION_TABLE + " ( " + KEY_GYM_TRAINING_SESSION_ID + " integer NOT NULL primary key autoincrement, "
//            + KEY_GYM_TRAINING_SESSION_TRAINING_SESSION_DATE + " text NOT NULL, "
//            + KEY_GYM_TRAINING_SESSION_START_TIME + " integer DEFAULT NULL, "
//            + KEY_GYM_TRAINING_SESSION_END_TIME + " integer DEFAULT NULL"
//            + " );";

    // **************gym training stats type infos ****************
    // A GYM STAS DATA TABLE initializing
    private static final String GYM_TRAINING_STATS_TABLE = "gym_training_stats";
    public static final String KEY_GYM_TRAINING_STATS_ID = "id";
    public static final String KEY_GYM_TRAINING_STATS_DATE = "punched_date";
    public static final String KEY_GYM_TRAINING_STATS_PUNCH_TYPE = "punch_type";
    public static final String KEY_GYM_TRAINING_STATS_AVG_SPEED = "avg_speed";
    public static final String KEY_GYM_TRAINING_STATS_AVG_FORCE = "avg_force";
    public static final String KEY_GYM_TRAINING_STATS_PUNCH_COUNT = "punch_count";
    public static final String KEY_GYM_TRAINING_STATS_TOTAL_TIME = "total_time";

    public static String getGymTrainingStatsTable(){
        return GYM_TRAINING_STATS_TABLE;
    }

    // Query string for creating table GYM_TRAINING_STATS_TABLE .
    private static final String DATABASE_CREATE_GYM_TRAINING_STATS = "create table "
            + GYM_TRAINING_STATS_TABLE
            + " (id integer NOT NULL primary key autoincrement, "
            + " punch_type text DEFAULT NULL, "
            + " punched_date text DEFAULT NULL, "
            + " avg_speed double DEFAULT NULL, "
            + " avg_force double DEFAULT NULL, "
            + " punch_count integer(11) DEFAULT NULL, "
            + " total_time double DEFAULT NULL);";

//    private static final String DATABASE_CREATE_GYM_TRAINING_STATS = "create table "
//            + GYM_TRAINING_STATS_TABLE
//            + " (id integer NOT NULL primary key autoincrement, "
//            + " punch_type text DEFAULT NULL, "
//            + " punched_date text DEFAULT NULL, "
//            + " avg_speed double DEFAULT NULL, "
//            + " avg_force double DEFAULT NULL, "
//            + " punch_count integer(11) DEFAULT NULL, "
//            + " total_time double DEFAULT NULL;";

    // *************boxer_profile*******************

    // A BOXER_PROFILE_TABLE initializing to boxer_profile
    private static final String BOXER_PROFILE_TABLE = "boxer_profile";

    public static final String KEY_BOXER_ID = "id";
    public static final String KEY_BOXER_VERSION = "version";
    public static final String KEY_BOXER_CHEST = "chest";
    public static final String KEY_BOXER_INSEAM = "inseam";
    public static final String KEY_BOXER_REACH = "reach";
    public static final String KEY_BOXER_STANCE = "stance";
    public static final String KEY_BOXER_USER_ID = "user_id";
    public static final String KEY_BOXER_WAIST = "waist";
    public static final String KEY_BOXER_WEIGHT = "weight";
    public static final String KEY_BOXER_HEIGHT = "height";
    public static final String KEY_BOXER_LEFT_DEVICE = "left_device";
    public static final String KEY_BOXER_RIGHT_DEVICE = "right_device";
    public static final String KEY_BOXER_GLOVE_TYPE = "glove_type";
    public static final String KEY_BOXER_SKILL_LEVEL = "skill_level";
    public static final String KEY_BOXER_SERVER_ID = "boxer_server_id";

    // Query string for creating table USER .
    private static final String CREATE_TABLE_BOXER_PROFILE = "create table "
            + BOXER_PROFILE_TABLE + " ( " + KEY_BOXER_ID + " integer NOT NULL primary key autoincrement, "
            + KEY_BOXER_VERSION + " integer(20) NOT NULL, "
            + KEY_BOXER_CHEST + " double(19,2) DEFAULT NULL, "
            + KEY_BOXER_INSEAM + " double(19,2) DEFAULT NULL, "
            + KEY_BOXER_REACH + " double(19,2) DEFAULT NULL, "
            + KEY_BOXER_STANCE + " text(50) NOT NULL, "
            + KEY_BOXER_USER_ID + " integer(20) references " + USER_TABLE + "(" + KEY_USER_ID + ") NOT NULL, "
            + KEY_BOXER_WAIST + " double(19,2) DEFAULT NULL, "
            + KEY_BOXER_WEIGHT + " double(19,2) DEFAULT NULL, "
            + KEY_BOXER_HEIGHT + " double(19,2) DEFAULT NULL, "
            + KEY_BOXER_LEFT_DEVICE + " text(50), "
            + KEY_BOXER_RIGHT_DEVICE + " text(50), "
            + KEY_BOXER_GLOVE_TYPE + " text(50), "
            + KEY_BOXER_SKILL_LEVEL + " text(50), "
            + KEY_BOXER_SERVER_ID + " integer(20) NOT NULL"
            + " );";

    // *************calendar_summary******************************

    private static final String CALENDAR_SUMMARY_TABLE = "calendar_summary";

    public static final String KEY_CALENDAR_ROW_ID = "id";
    public static final String KEY_CALENDAR_TOTAL_TRAINING_TIME = "total_training_time";
    public static final String KEY_CALENDAR_TOTAL_PUNCHES = "total_punches";
    public static final String KEY_CALENDAR_MAX_SPEED = "max_speed";
    public static final String KEY_CALENDAR_MAX_FORCE = "max_force";
    public static final String KEY_CALENDAR_DATE = "trainingSessionDate";
    public static final String KEY_CALENDAR_USER_ID = "user_id";

    private static final String CREATE_TABLE_CALENDAR_SUMMARY =
            "create table "
                    + CALENDAR_SUMMARY_TABLE
                    + " (" + KEY_CALENDAR_ROW_ID + " INTEGER NOT NULL primary key autoincrement, "
                    + KEY_CALENDAR_TOTAL_TRAINING_TIME + " text default null, "
                    + KEY_CALENDAR_TOTAL_PUNCHES + " integer(20), "
                    + KEY_CALENDAR_MAX_SPEED + " integer(20), "
                    + KEY_CALENDAR_MAX_FORCE + " integer(20), "
                    + KEY_CALENDAR_DATE + " text not null, "
                    + KEY_CALENDAR_USER_ID + " integer(20) not null, "
                    + "FOREIGN KEY (" + KEY_CALENDAR_USER_ID + ") references user (id));";

    // *************result_summary******************************

    private static final String RESULT_SUMMARY_TABLE = "result_summary";

    public static final String KEY_RESULT_SUMMARY_ROW_ID = "id";

    //OTHER COLOUMNS
    public static final String KEY_RESULT_SUMMARY_USER_ID = "user_id";
    public static final String KEY_RESULT_SUMMARY_DATE = "trainingSessionDate";
    public static final String KEY_RESULT_SUMMARY_TYPE = "type";

    //LEFT JAB
    public static final String KEY_RESULT_SUMMARY_LJ_SPEED = "lj_speed";
    public static final String KEY_RESULT_SUMMARY_LJ_FORCE = "lj_force";
    public static final String KEY_RESULT_SUMMARY_LJ_TOTAL = "lj_total";

    //LEFT STRAIGHT
    public static final String KEY_RESULT_SUMMARY_LS_SPEED = "ls_speed";
    public static final String KEY_RESULT_SUMMARY_LS_FORCE = "ls_force";
    public static final String KEY_RESULT_SUMMARY_LS_TOTAL = "ls_total";

    //LEFT HOOK
    public static final String KEY_RESULT_SUMMARY_LH_SPEED = "lh_speed";
    public static final String KEY_RESULT_SUMMARY_LH_FORCE = "lh_force";
    public static final String KEY_RESULT_SUMMARY_LH_TOTAL = "lh_total";

    //LEFT UPPERCUT
    public static final String KEY_RESULT_SUMMARY_LU_SPEED = "lu_speed";
    public static final String KEY_RESULT_SUMMARY_LU_FORCE = "lu_force";
    public static final String KEY_RESULT_SUMMARY_LU_TOTAL = "lu_total";

    //LEFT UNRECOGNIZED
    public static final String KEY_RESULT_SUMMARY_LR_SPEED = "lr_speed";
    public static final String KEY_RESULT_SUMMARY_LR_FORCE = "lr_force";
    public static final String KEY_RESULT_SUMMARY_LR_TOTAL = "lr_total";

    //RIGHT JAB
    public static final String KEY_RESULT_SUMMARY_RJ_SPEED = "rj_speed";
    public static final String KEY_RESULT_SUMMARY_RJ_FORCE = "rj_force";
    public static final String KEY_RESULT_SUMMARY_RJ_TOTAL = "rj_total";

    //RIGHT STRAIGHT
    public static final String KEY_RESULT_SUMMARY_RS_SPEED = "rs_speed";
    public static final String KEY_RESULT_SUMMARY_RS_FORCE = "rs_force";
    public static final String KEY_RESULT_SUMMARY_RS_TOTAL = "rs_total";

    //RIGHT HOOK
    public static final String KEY_RESULT_SUMMARY_RH_SPEED = "rh_speed";
    public static final String KEY_RESULT_SUMMARY_RH_FORCE = "rh_force";
    public static final String KEY_RESULT_SUMMARY_RH_TOTAL = "rh_total";

    //RIGHT UPPERCUT
    public static final String KEY_RESULT_SUMMARY_RU_SPEED = "ru_speed";
    public static final String KEY_RESULT_SUMMARY_RU_FORCE = "ru_force";
    public static final String KEY_RESULT_SUMMARY_RU_TOTAL = "ru_total";

    //RIGHT UNRECOGNIZED
    public static final String KEY_RESULT_SUMMARY_RR_SPEED = "rr_speed";
    public static final String KEY_RESULT_SUMMARY_RR_FORCE = "rr_force";
    public static final String KEY_RESULT_SUMMARY_RR_TOTAL = "rr_total";

    private static final String CREATE_TABLE_RESULT_SUMMARY =
            "create table " + RESULT_SUMMARY_TABLE + "("
                    + KEY_RESULT_SUMMARY_ROW_ID + " INTEGER NOT NULL primary key autoincrement, "
                    + KEY_RESULT_SUMMARY_USER_ID + " integer(20) not null, "
                    + KEY_RESULT_SUMMARY_DATE + " text not null, "
                    + KEY_RESULT_SUMMARY_TYPE + " text not null, "

                    //LEFT JAB
                    + KEY_RESULT_SUMMARY_LJ_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_LJ_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_LJ_TOTAL + " double, "

                    //LEFT STRAIGHT
                    + KEY_RESULT_SUMMARY_LS_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_LS_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_LS_TOTAL + " double, "

                    //LEFT HOOK
                    + KEY_RESULT_SUMMARY_LH_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_LH_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_LH_TOTAL + " double, "

                    //LEFT UPPERCUT
                    + KEY_RESULT_SUMMARY_LU_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_LU_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_LU_TOTAL + " double, "


                    //LEFT UNRECOGNIZED
                    + KEY_RESULT_SUMMARY_LR_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_LR_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_LR_TOTAL + " double, "

                    //RIGHT JAB
                    + KEY_RESULT_SUMMARY_RJ_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_RJ_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_RJ_TOTAL + " double, "

                    //RIGHT STRAIGHT
                    + KEY_RESULT_SUMMARY_RS_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_RS_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_RS_TOTAL + " double, "

                    //RIGHT HOOK
                    + KEY_RESULT_SUMMARY_RH_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_RH_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_RH_TOTAL + " double, "

                    //RIGHT UPPERCUT
                    + KEY_RESULT_SUMMARY_RU_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_RU_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_RU_TOTAL + " double, "

                    //RIGHT UNRECOGNIZED
                    + KEY_RESULT_SUMMARY_RR_SPEED + " double, "
                    + KEY_RESULT_SUMMARY_RR_FORCE + " double, "
                    + KEY_RESULT_SUMMARY_RR_TOTAL + " double, "
                    + "FOREIGN KEY (" + KEY_RESULT_SUMMARY_USER_ID + ") references user (id));";


    // *************progress_summary******************************

    private static final String PROGRESS_SUMMARY_TABLE = "progress_summary";

    public static final String KEY_PROGRESS_SUMMARY_ROW_ID = "id";

    //OTHER COLOUMNS
    public static final String KEY_PROGRESS_SUMMARY_USER_ID = "user_id";
    public static final String KEY_PROGRESS_SUMMARY_DATE = "trainingSessionDate";
    public static final String KEY_PROGRESS_SUMMARY_TYPE = "type";

    //LEFT JAB
    public static final String KEY_PROGRESS_SUMMARY_LJ_SPEED = "lj_speed";
    public static final String KEY_PROGRESS_SUMMARY_LJ_FORCE = "lj_force";

    //LEFT STRAIGHT
    public static final String KEY_PROGRESS_SUMMARY_LS_SPEED = "ls_speed";
    public static final String KEY_PROGRESS_SUMMARY_LS_FORCE = "ls_force";

    //LEFT HOOK
    public static final String KEY_PROGRESS_SUMMARY_LH_SPEED = "lh_speed";
    public static final String KEY_PROGRESS_SUMMARY_LH_FORCE = "lh_force";

    //LEFT UPPERCUT
    public static final String KEY_PROGRESS_SUMMARY_LU_SPEED = "lu_speed";
    public static final String KEY_PROGRESS_SUMMARY_LU_FORCE = "lu_force";

    //LEFT UNRECOGNIZED
    public static final String KEY_PROGRESS_SUMMARY_LR_SPEED = "lr_speed";
    public static final String KEY_PROGRESS_SUMMARY_LR_FORCE = "lr_force";

    //RIGHT JAB
    public static final String KEY_PROGRESS_SUMMARY_RJ_SPEED = "rj_speed";
    public static final String KEY_PROGRESS_SUMMARY_RJ_FORCE = "rj_force";

    //RIGHT STRAIGHT
    public static final String KEY_PROGRESS_SUMMARY_RS_SPEED = "rs_speed";
    public static final String KEY_PROGRESS_SUMMARY_RS_FORCE = "rs_force";

    //RIGHT HOOK
    public static final String KEY_PROGRESS_SUMMARY_RH_SPEED = "rh_speed";
    public static final String KEY_PROGRESS_SUMMARY_RH_FORCE = "rh_force";

    //RIGHT UPPERCUT
    public static final String KEY_PROGRESS_SUMMARY_RU_SPEED = "ru_speed";
    public static final String KEY_PROGRESS_SUMMARY_RU_FORCE = "ru_force";

    //RIGHT UNRECOGNIZED
    public static final String KEY_PROGRESS_SUMMARY_RR_SPEED = "rr_speed";
    public static final String KEY_PROGRESS_SUMMARY_RR_FORCE = "rr_force";

    private static final String CREATE_TABLE_PROGRESS_SUMMARY =
            "create table " + PROGRESS_SUMMARY_TABLE + "("
                    + KEY_PROGRESS_SUMMARY_ROW_ID + " INTEGER NOT NULL primary key autoincrement, "
                    + KEY_PROGRESS_SUMMARY_USER_ID + " integer(20) not null, "
                    + KEY_PROGRESS_SUMMARY_DATE + " text not null, "
                    + KEY_PROGRESS_SUMMARY_TYPE + " text not null, "

                    //LEFT JAB
                    + KEY_PROGRESS_SUMMARY_LJ_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_LJ_FORCE + " double, "

                    //LEFT STRAIGHT
                    + KEY_PROGRESS_SUMMARY_LS_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_LS_FORCE + " double, "

                    //LEFT HOOK
                    + KEY_PROGRESS_SUMMARY_LH_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_LH_FORCE + " double, "

                    //LEFT UPPERCUT
                    + KEY_PROGRESS_SUMMARY_LU_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_LU_FORCE + " double, "

                    //LEFT UNRECOGNIZED
                    + KEY_PROGRESS_SUMMARY_LR_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_LR_FORCE + " double, "

                    //RIGHT JAB
                    + KEY_PROGRESS_SUMMARY_RJ_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_RJ_FORCE + " double, "

                    //RIGHT STRAIGHT
                    + KEY_PROGRESS_SUMMARY_RS_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_RS_FORCE + " double, "

                    //RIGHT HOOK
                    + KEY_PROGRESS_SUMMARY_RH_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_RH_FORCE + " double, "

                    //RIGHT UPPERCUT
                    + KEY_PROGRESS_SUMMARY_RU_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_RU_FORCE + " double, "

                    //RIGHT UNRECOGNIZED
                    + KEY_PROGRESS_SUMMARY_RR_SPEED + " double, "
                    + KEY_PROGRESS_SUMMARY_RR_FORCE + " double, "
                    + "FOREIGN KEY (" + KEY_PROGRESS_SUMMARY_USER_ID + ") references user (id));";


    // *************punch_count_summary******************************

    private static final String PUNCH_COUNT_SUMMARY_TABLE = "punch_count_summary";

    public static final String KEY_PUNCH_COUNT_SUMMARY_ROW_ID = "id";

    //OTHER COLOUMNS
    public static final String KEY_PUNCH_COUNT_SUMMARY_USER_ID = "user_id";
    public static final String KEY_PUNCH_COUNT_SUMMARY_DATE = "trainingSessionDate";
    public static final String KEY_PUNCH_COUNT_SUMMARY_TYPE = "type";
    public static final String KEY_PUNCH_COUNT_TOTAL = "total_punch_count";

    //LEFT JAB
    public static final String KEY_PUNCH_COUNT_SUMMARY_LJ_AVG = "lj_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_LJ_TODAY = "lj_today";

    //LEFT STRAIGHT
    public static final String KEY_PUNCH_COUNT_SUMMARY_LS_AVG = "ls_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_LS_TODAY = "ls_today";

    //LEFT HOOK
    public static final String KEY_PUNCH_COUNT_SUMMARY_LH_AVG = "lh_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_LH_TODAY = "lh_today";

    //LEFT UPPERCUT
    public static final String KEY_PUNCH_COUNT_SUMMARY_LU_AVG = "lu_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_LU_TODAY = "lu_today";

    //LEFT UNRECOGNIZED
    public static final String KEY_PUNCH_COUNT_SUMMARY_LR_AVG = "lr_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_LR_TODAY = "lr_today";

    //RIGHT JAB
    public static final String KEY_PUNCH_COUNT_SUMMARY_RJ_AVG = "rj_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_RJ_TODAY = "rj_today";

    //RIGHT STRAIGHT
    public static final String KEY_PUNCH_COUNT_SUMMARY_RS_AVG = "rs_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_RS_TODAY = "rs_today";

    //RIGHT HOOK
    public static final String KEY_PUNCH_COUNT_SUMMARY_RH_AVG = "rh_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_RH_TODAY = "rh_today";

    //RIGHT UPPERCUT
    public static final String KEY_PUNCH_COUNT_SUMMARY_RU_AVG = "ru_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_RU_TODAY = "ru_today";

    //RIGHT UNRECOGNIZED
    public static final String KEY_PUNCH_COUNT_SUMMARY_RR_AVG = "rr_avg";
    public static final String KEY_PUNCH_COUNT_SUMMARY_RR_TODAY = "rr_today";

    private static final String CREATE_TABLE_PUNCH_COUNT_SUMMARY =
            "create table " + PUNCH_COUNT_SUMMARY_TABLE + "("
                    + KEY_PUNCH_COUNT_SUMMARY_ROW_ID + " INTEGER NOT NULL primary key autoincrement, "
                    + KEY_PUNCH_COUNT_SUMMARY_USER_ID + " integer(20) not null, "
                    + KEY_PUNCH_COUNT_SUMMARY_DATE + " text not null, "
                    + KEY_PUNCH_COUNT_SUMMARY_TYPE + " text default null, "

                    // LEFT JAB
                    + KEY_PUNCH_COUNT_SUMMARY_LJ_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_LJ_TODAY + " double, "

                    // LEFT STRAIGHT
                    + KEY_PUNCH_COUNT_SUMMARY_LS_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_LS_TODAY + " double, "

                    // LEFT HOOK
                    + KEY_PUNCH_COUNT_SUMMARY_LH_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_LH_TODAY + " double, "

                    // LEFT UPPERCUT
                    + KEY_PUNCH_COUNT_SUMMARY_LU_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_LU_TODAY + " double, "

                    // LEFT UNRECOGNIZED
                    + KEY_PUNCH_COUNT_SUMMARY_LR_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_LR_TODAY + " double, "

                    // RIGHT JAB
                    + KEY_PUNCH_COUNT_SUMMARY_RJ_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_RJ_TODAY + " double, "

                    // RIGHT STRAIGHT
                    + KEY_PUNCH_COUNT_SUMMARY_RS_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_RS_TODAY + " double, "

                    // RIGHT HOOK
                    + KEY_PUNCH_COUNT_SUMMARY_RH_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_RH_TODAY + " double, "

                    // RIGHT UPPERCUT
                    + KEY_PUNCH_COUNT_SUMMARY_RU_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_RU_TODAY + " double, "

                    // RIGHT UNRECOGNIZED
                    + KEY_PUNCH_COUNT_SUMMARY_RR_AVG + " double, "
                    + KEY_PUNCH_COUNT_SUMMARY_RR_TODAY + " double, "

                    + KEY_PUNCH_COUNT_TOTAL + " integer(20), "
                    + "FOREIGN KEY (" + KEY_PUNCH_COUNT_SUMMARY_USER_ID + ") references " + USER_TABLE + " (" + KEY_USER_ID + "));";


    // Query string for creating table TRAINING_DATA_TABLE .
    private static final String DATABASE_CREATE_TRAINING_DATA = "create table "
            + TRAINING_DATA_TABLE
            + " (id INTEGER NOT NULL primary key autoincrement, "
            + " left_hand integer(1) NOT null, "
            + " training_session_id integer(20) NOT null, "
            + " user_id integer(20) NOT null, "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, "
            + " FOREIGN KEY (user_id) references user (id),"
            + " FOREIGN KEY (training_session_id) references training_session (id));";

    // Query string for creating table TRAINING_DATA_DETAILS_TABLE .
    private static final String DATABASE_CREATE_TRAINING_DATA_DETAILS = "create table "
            + TRAINING_DATA_DETAILS_TABLE
            + " (id INTEGER NOT NULL primary key autoincrement, "
            + " ax integer(11) DEFAULT null, "
            + " ay integer(11) DEFAULT null, "
            + " az integer(11) DEFAULT null, "
            + " current_force double DEFAULT null, "
            + " current_velocity double DEFAULT null, "
            + " data_recieved_time text DEFAULT null, "
            + " gx integer(11) DEFAULT NULL, "
            + " gy integer(11) DEFAULT NULL, "
            + " gz integer(11) DEFAULT NULL, "
            + " head_trauma double DEFAULT NULL, "
            + " milli_second double(19,1) NOT NULL, "
            + " temp integer(11) DEFAULT NULL, "
            + " training_data_id integer(20) NOT NULL, "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, "
            + " FOREIGN KEY (training_data_id) references training_data (id));";

    // Query string for creating table TRAINING_PUNCH_DATA_TABLE .
    private static final String DATABASE_CREATE_TRAINING_PUNCH_DATA = "create table "
            + TRAINING_PUNCH_DATA_TABLE
            + " (id integer NOT NULL primary key autoincrement, "
            + " max_force double DEFAULT NULL, "
            + " max_speed double DEFAULT NULL, "
            + " punch_data_date text NOT NULL, "
            + " punch_type text(2) NOT NULL, "
            + " training_data_id integer(20) NOT NULL, "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, "
            + " FOREIGN KEY (training_data_id) references training_data (id));";

    // Query string for creating table TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE .
    private static final String DATABASE_CREATE_TRAINING_PUNCH_DATA_PEAK_SUMMARY = "create table "
            + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE
            + " (id integer NOT NULL primary key autoincrement,"
            + " hook integer(11) NOT NULL, "
            + " jab integer(11) NOT NULL, "
            + " punch_data_peak_summary_date text NOT NULL, "
            + " speed_flag integer(1) NOT NULL, "
            + " straight integer(11) NOT NULL, "
            + " training_data_id integer(20) NOT NULL, "
            + " upper_cut integer(11) NOT NULL, "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, "
            + " FOREIGN KEY (training_data_id) references training_data (id));";

    // Query string for creating table TRAINING_SESSION_TABLE .
    private static final String DATABASE_CREATE_TRAINING_SESSION = "create table "
            + TRAINING_SESSION_TABLE
            + " (id integer NOT NULL primary key autoincrement, "
            + " end_time text DEFAULT NULL, "
            + " start_time text NOT NULL, "
            + " training_session_date text NOT NULL, "
            + " training_type text(50) NOT NULL, "
            + " user_id integer(20) NOT NULL, "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, "
            + " FOREIGN KEY (user_id) references user (id));";

    // Query string for creating table DATABASE_CREATE_USER .
    private static final String DATABASE_CREATE_USER = "create table "
            + USER_TABLE + " (" + KEY_USER_ID + " integer NOT NULL primary key autoincrement, "
            + KEY_USER_VERSION + " integer(20) NOT NULL, "
            + KEY_USER_ACCOUNT_EXPIRED + " integer(1) NOT NULL, "
            + KEY_USER_ACCOUNT_LOCKED + " integer(1) NOT NULL, "
            + KEY_USER_DATE_OF_BIRTH + " text(20) DEFAULT NULL,"
            + KEY_USER_ENABLED + " integer(1) NOT NULL, "
            + KEY_USER_BOXERFIRSTNAME + " text(50) NOT NULL, "
            + KEY_USER_BOXERLASTNAME + " text(50) NOT NULL, "
            + KEY_USER_GENDER + " text(1) NOT NULL, "
            + KEY_USER_PASSWORD + " text(255) NOT NULL, "
            + KEY_USER_PASSWORD_EXPIRED + " integer(1) NOT NULL, "
            + KEY_USER_PHOTO + " blob, "
            + KEY_USER_USERNAME + " text(255) NOT NULL, "
            + KEY_USER_COUNTRY_ID + " integer(20) NOT NULL, "
            + KEY_USER_EMAIL_ID + " text(255) NOT NULL, "
            + KEY_USER_ZIP_CODE + " text(10) NOT NULL, "
            + KEY_USER_SYNC + " integer(1) default 0, "
            + KEY_USER_SYNC_DATE + " text DEFAULT null, "
            + KEY_SERVER_USER_ID + " integer default NULL, "
            + " UNIQUE(" + KEY_USER_USERNAME + "));";

    // Query string for creating table DATABASE_CREATE_USER_ACCESS .
    private static final String DATABASE_CREATE_USER_ACCESS = "create table "
            + USER_ACCESS_TABLE + " (" + KEY_USER_ACCESS_ID + " integer NOT NULL primary key autoincrement, "
            + KEY_USER_ACCESS_TOKEN + " text(50) DEFAULT NULL, "
            + KEY_USER_ACCESS_SERVER_ID + " integer DEFAULT NULL, "
            + " UNIQUE(" + KEY_USER_ACCESS_SERVER_ID + "));";

    // Query string for creating table ROLE .
    private static final String DATABASE_CREATE_ROLE = "create table "
            + ROLE_TABLE + " (id integer NOT NULL primary key autoincrement, "
            + " version integer(20) NOT NULL, "
            + " authority string(255) NOT NULL, "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, " + " UNIQUE (authority));";

    // Query string for creating table USER_ROLE .
    private static final String DATABASE_CREATE_USER_ROLE = "create table "
            + USER_ROLE_TABLE + "(role_id integer NOT NULL , "
            + " user_id integer(20) NOT NULL , "
            + " sync integer(1) default 0, "
            + " sync_date text DEFAULT null, "
            + " server_id integer default NULL, "
            + " PRIMARY KEY (role_id,user_id), "
            + " FOREIGN KEY (user_id) references user (id),"
            + " FOREIGN KEY (role_id) references role (id));";


    // Object reference for context.
    private final Context context;

    // Object reference for DatabaseHelper.
    private static DatabaseHelper DBHelper;

    // Object reference for SQLiteDatabase.
    private SQLiteDatabase db;

    private static DBAdapter instance = null;
    private static String path = null;

    /**
     * A private Constructor for DBAdapter class prevents any other class from instantiating.
     *
     * @param ctx
     */
    private DBAdapter(Context ctx) {
        this.context = ctx;
        path = EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.DATABASE_DIRECTORY + File.separator;
        File mydir = new File(path);
        if (!mydir.exists()) {
            mydir.mkdirs();
        } else {
            Log.i(TAG, "directory already exists");
        }
        DBHelper = new DatabaseHelper(context);
    }

    public static DBAdapter getInstance(Context ctx) {
        if (instance == null) {
            instance = new DBAdapter(ctx);
            instance.open();
        }
        return instance;
    }

    public static void clearDBAdapter() {
        instance = null;
    }

    /**
     * A helper class to manage database creation and version management.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Constructor for database helper class.
         *
         * @param context
         */
        DatabaseHelper(Context context) {
            super(context, path + DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Called when the database is created for the first time. This is where
         * the creation of tables and the initial population of the tables
         * should happen. Parameters: db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE_TRAINING_DATA);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_DATA);

            db.execSQL(DATABASE_CREATE_TRAINING_DATA_DETAILS);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_DATA_DETAILS);

            db.execSQL(DATABASE_CREATE_TRAINING_PUNCH_DATA);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_PUNCH_DATA);

            db.execSQL(DATABASE_CREATE_TRAINING_PUNCH_DATA_PEAK_SUMMARY);
            Log.d(TAG, "created table "
                    + DATABASE_CREATE_TRAINING_PUNCH_DATA_PEAK_SUMMARY);

            db.execSQL(DATABASE_CREATE_TRAINING_SESSION);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_SESSION);

            db.execSQL(DATABASE_CREATE_USER);
            Log.d(TAG, "created table " + DATABASE_CREATE_USER);

            db.execSQL(DATABASE_CREATE_ROLE);
            Log.d(TAG, "created table " + DATABASE_CREATE_ROLE);

            //Insert role manually
            ContentValues values = new ContentValues();
            values.put(KEY_ROLE_VERSION, 0);
            values.put(KEY_ROLE_AUTHORITY, "ROLE_BOXER");
            db.insert(ROLE_TABLE, null, values);
            values = new ContentValues();
            values.put(KEY_ROLE_VERSION, 0);
            values.put(KEY_ROLE_AUTHORITY, "ROLE_ADMIN");
            db.insert(ROLE_TABLE, null, values);

            db.execSQL(DATABASE_CREATE_USER_ROLE);
            Log.d(TAG, "created table " + DATABASE_CREATE_USER_ROLE);

            db.execSQL(CREATE_TABLE_CALENDAR_SUMMARY);
            Log.d(TAG, "created table " + CREATE_TABLE_CALENDAR_SUMMARY);

            db.execSQL(CREATE_TABLE_PROGRESS_SUMMARY);
            Log.d(TAG, "created table " + CREATE_TABLE_PROGRESS_SUMMARY);

            db.execSQL(CREATE_TABLE_PUNCH_COUNT_SUMMARY);
            Log.d(TAG, "created table " + CREATE_TABLE_PUNCH_COUNT_SUMMARY);

            db.execSQL(CREATE_TABLE_RESULT_SUMMARY);
            Log.d(TAG, "created table " + CREATE_TABLE_RESULT_SUMMARY);

            db.execSQL(CREATE_TABLE_BOXER_PROFILE);
            Log.d(TAG, "created table " + CREATE_TABLE_BOXER_PROFILE);

            db.execSQL(DATABASE_CREATE_USER_ACCESS);
            Log.d(TAG, "created table " + DATABASE_CREATE_USER_ACCESS);

            //super added 2 create table
            db.execSQL(DATABASE_CREATE_GYM_TRAINING_SESSION);
            db.execSQL(DATABASE_CREATE_GYM_TRAINING_STATS);
        }

        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything
         * else it needs to upgrade to the new schema version. The SQLite ALTER
         * TABLE documentation can be found here. If you add new columns you can
         * use ALTER TABLE to insert them into a live table. If you rename or
         * remove columns you can use ALTER TABLE to rename the old table, then
         * create the new table and then populate the new table with the
         * contents of the old table. Parameters: db The database. oldVersion
         * The old database version. newVersion: The new database version.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // TODO: For production version we need to write alter table & data migration scripts here for according to version numbers instead of dropping table & recreating it.

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_DATA_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_DATA_DETAILS_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_PUNCH_DATA_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "
                    + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_SESSION_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + ROLE_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + USER_ROLE_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + CALENDAR_SUMMARY_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + RESULT_SUMMARY_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + PROGRESS_SUMMARY_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + PUNCH_COUNT_SUMMARY_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + BOXER_PROFILE_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + USER_ACCESS_TABLE);

            //super added 2 db create
            db.execSQL("DROP TABLE IF EXISTS" + GYM_TRAINING_SESSION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS" + GYM_TRAINING_STATS_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            onCreate(db);
        }
    }

    /**
     * Opens the database connection.
     *
     * @return
     * @throws SQLException
     */
    public DBAdapter open() throws SQLException {
        try {

            db = DBHelper.getWritableDatabase();
            Log.i(TAG, db.getPath());

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        DBHelper.close();
    }

    /**
     * Insert a Role values into the database.
     */
    public long insertRole(int version, String authority) {
        Date date = new Date();
        ContentValues values = new ContentValues();

        values.put(KEY_ROLE_VERSION, version);
        values.put(KEY_ROLE_AUTHORITY, authority);
        values.put(KEY_ROLE_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(ROLE_TABLE, null, values);
        return result;
    }

    /**
     * Checks for end_time null values in training_session & updates those with the last punch time if available else with the start_time.
     *
     * @return No. of rows updated
     */
    public int endAllPreviousTrainingSessions() {

        String sqlQuery = "SELECT " + KEY_TRAINING_SESSION_ID + ", " + KEY_TRAINING_SESSION_START_TIME + " FROM " + TRAINING_SESSION_TABLE
                + " WHERE TRIM(" + KEY_TRAINING_SESSION_END_TIME + ")='' OR " + KEY_TRAINING_SESSION_END_TIME + " IS NULL;";
        Cursor cursor = db.rawQuery(sqlQuery, null);

        int updateResult = 0;

        try {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
            }
            // Maintain list of elements for which end time is 0 or null
            String startTime, endTime;
            Cursor endTimeCursor;
            ContentValues values;

            for (int i = 0; i < count; i++) {
                int id = cursor.getInt(0);
                startTime = cursor.getString(1);
                sqlQuery = "select (case when max(tpd.punch_data_date)='' or max(tpd.punch_data_date) is null then '"
                        + startTime + "' else max(tpd.punch_data_date) end) as endTime "
                        + "from training_session as ts INNER JOIN training_data as td ON ts.id=td.training_session_id "
                        + "INNER JOIN training_punch_data as tpd ON tpd.training_data_id=td.id where ts.id=" + id + ";";

                endTimeCursor = db.rawQuery(sqlQuery, null);
                endTimeCursor.moveToFirst();
                endTime = endTimeCursor.getString(0);
                values = new ContentValues();
                values.put(KEY_TRAINING_SESSION_END_TIME, endTime);
                values.put(KEY_TRAINING_SESSION_SYNC, "0");
                updateResult += db.update(TRAINING_SESSION_TABLE, values, KEY_TRAINING_SESSION_ID + " = " + id, null);
                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return updateResult;
    }

    public int userLogin(String userName, String password) {

        int userID;
        int returnValue;
        Log.d(TAG, "userName=" + userName + "  password=" + password);
        String query = "SELECT id from user where upper(username) = upper('" + userName + "') and password ='" + password + "'";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                userID = cursor.getInt(0);
                returnValue = userID;
            } else {
                returnValue = -1;
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return returnValue;
    }

    public int checkUserAccessDetails(int userId) {
        int userAccessRecordCount;
        String query = "SELECT id from " + USER_ACCESS_TABLE + " where " + KEY_USER_ACCESS_SERVER_ID + " ='" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                userAccessRecordCount = cursor.getInt(0);
            } else {
                userAccessRecordCount = -1;
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return userAccessRecordCount;
    }

    public Boolean isUserAvailable() {
        Boolean returnValue;
        String query = "SELECT * FROM user";
        Cursor cursor = db.rawQuery(query, null);
        Log.d(TAG, "Number of Users : " + cursor.getCount());
        try {
            if (cursor.getCount() > 0) {
                returnValue = true;
            } else {
                returnValue = false;
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return returnValue;
    }

    /**
     * Gets the boxer details.
     *
     * @param userId the user id
     * @return the boxer details
     */
    public HashMap<String, String> getBoxerDetails(Integer userId) {

        HashMap<String, String> boxerDetails;
        boxerDetails = new HashMap<String, String>();
        String query = "SELECT * from user where id ='" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                boxerDetails.put("boxerName", cursor.getString(cursor.getColumnIndex("first_name")));
                boxerDetails.put(KEY_BOXER_STANCE, getUsersBoxerDetails(userId).get(KEY_BOXER_STANCE));
            } else
                boxerDetails = null;


        } finally {
            if (cursor != null)
                cursor.close();
        }

        return boxerDetails;
    }

    public String getUserAccessDetails(int userServerId) {
        String userAccessToken;
        String query = "SELECT * from " + USER_ACCESS_TABLE + " where " + KEY_USER_ACCESS_SERVER_ID + "='" + userServerId + "'";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                userAccessToken = cursor.getString(cursor.getColumnIndex(KEY_USER_ACCESS_TOKEN));
            } else {
                userAccessToken = null;
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return userAccessToken;
    }

    /**
     * Gets the boxer details from Server user id.
     *
     * @param userId the user id
     * @return the boxer details
     */
    public HashMap<String, String> getUsersBoxerDetails(Integer userId) {

        HashMap<String, String> boxerDetails;
        boxerDetails = new HashMap<String, String>();
        String query = "SELECT * from " + BOXER_PROFILE_TABLE + " where " + KEY_BOXER_USER_ID + " ='" + userId + "'";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                boxerDetails.put(KEY_BOXER_CHEST, cursor.getString(cursor.getColumnIndex(KEY_BOXER_CHEST)));
                boxerDetails.put(KEY_BOXER_INSEAM, cursor.getString(cursor.getColumnIndex(KEY_BOXER_INSEAM)));
                boxerDetails.put(KEY_BOXER_REACH, cursor.getString(cursor.getColumnIndex(KEY_BOXER_REACH)));
                boxerDetails.put(KEY_BOXER_STANCE, cursor.getString(cursor.getColumnIndex(KEY_BOXER_STANCE)));
                boxerDetails.put(KEY_BOXER_USER_ID, cursor.getString(cursor.getColumnIndex(KEY_BOXER_USER_ID)));
                boxerDetails.put(KEY_BOXER_WAIST, cursor.getString(cursor.getColumnIndex(KEY_BOXER_WAIST)));
                boxerDetails.put(KEY_BOXER_WEIGHT, cursor.getString(cursor.getColumnIndex(KEY_BOXER_WEIGHT)));
                boxerDetails.put(KEY_BOXER_HEIGHT, cursor.getString(cursor.getColumnIndex(KEY_BOXER_HEIGHT)));
                boxerDetails.put(KEY_BOXER_LEFT_DEVICE, cursor.getString(cursor.getColumnIndex(KEY_BOXER_LEFT_DEVICE)));
                boxerDetails.put(KEY_BOXER_RIGHT_DEVICE, cursor.getString(cursor.getColumnIndex(KEY_BOXER_RIGHT_DEVICE)));
                boxerDetails.put(KEY_BOXER_GLOVE_TYPE, cursor.getString(cursor.getColumnIndex(KEY_BOXER_GLOVE_TYPE)));
                boxerDetails.put(KEY_BOXER_SKILL_LEVEL, cursor.getString(cursor.getColumnIndex(KEY_BOXER_SKILL_LEVEL)));

            } else {
                boxerDetails = null;
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return boxerDetails;
    }

    public int insertUser(AuthenticationDTO authenticationDTO) {

        UserDTO userDTO = authenticationDTO.getUser();

        ContentValues values = new ContentValues();

        values.put(KEY_USER_VERSION, 1);
        values.put(KEY_USER_ACCOUNT_EXPIRED, userDTO.getAccountExpired());
        values.put(KEY_USER_ACCOUNT_LOCKED, userDTO.getAccountLocked());
        values.put(KEY_USER_BOXERFIRSTNAME, userDTO.getFirstName());
        values.put(KEY_USER_BOXERLASTNAME, userDTO.getLastName());
        values.put(KEY_USER_DATE_OF_BIRTH, userDTO.getDateOfBirth());
        values.put(KEY_USER_ENABLED, userDTO.getEnabled());
        values.put(KEY_USER_GENDER, userDTO.getGender());
        values.put(KEY_USER_PASSWORD, userDTO.getPassword());
        values.put(KEY_USER_PASSWORD_EXPIRED, userDTO.getPasswordExpired());
        values.put(KEY_USER_PHOTO, "");
        values.put(KEY_USER_USERNAME, userDTO.getUsername());
        values.put(KEY_USER_EMAIL_ID, userDTO.getEmailId());
        values.put(KEY_USER_COUNTRY_ID, userDTO.getCountry().getId());
        values.put(KEY_USER_ZIP_CODE, userDTO.getZipcode());
        values.put(KEY_SERVER_USER_ID, userDTO.getId());
        int result = (int) db.insert(USER_TABLE, null, values);
        return result;
    }


    public int insertUser(int version, int accountExpired,
                          int accountLocked, String firstName, String lastName, String bdate,
                          int enabled, String gender, String password, int passwordExpired,
                          byte[] photo, String username, String emailId, int countryId, String zipcode,
                          int serverUserId) {

        ContentValues values = new ContentValues();

        values.put(KEY_USER_VERSION, version);
        values.put(KEY_USER_ACCOUNT_EXPIRED, accountExpired);
        values.put(KEY_USER_ACCOUNT_LOCKED, accountLocked);
        values.put(KEY_USER_BOXERFIRSTNAME, firstName);
        values.put(KEY_USER_BOXERLASTNAME, lastName);
        values.put(KEY_USER_DATE_OF_BIRTH, bdate);
        values.put(KEY_USER_ENABLED, enabled);
        values.put(KEY_USER_GENDER, gender);
        values.put(KEY_USER_PASSWORD, password);
        values.put(KEY_USER_PASSWORD_EXPIRED, passwordExpired);
        values.put(KEY_USER_PHOTO, photo);
        values.put(KEY_USER_USERNAME, username);
        values.put(KEY_USER_EMAIL_ID, emailId);
        values.put(KEY_USER_COUNTRY_ID, countryId);
        values.put(KEY_USER_ZIP_CODE, zipcode);
        values.put(KEY_SERVER_USER_ID, serverUserId);
        int result = (int) db.insert(USER_TABLE, null, values);
        return result;
    }

    /**
     * Insert User Access Data
     *
     * @param traineeAccessToken
     * @param userAccessServerId
     * @return inserted or not status
     */
    public int insertUserAccessData(String traineeAccessToken, int userAccessServerId) {
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ACCESS_TOKEN, traineeAccessToken);
        values.put(KEY_USER_ACCESS_SERVER_ID, userAccessServerId);
        int result = (int) db.insert(USER_ACCESS_TABLE, null, values);
        return result;
    }

    public int insertUserAccessData(AuthenticationDTO authenticationDTO) {
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ACCESS_TOKEN, authenticationDTO.getSecureAccessToken());
        values.put(KEY_USER_ACCESS_SERVER_ID, authenticationDTO.getUser().getId());
        int result = (int) db.insert(USER_ACCESS_TABLE, null, values);
        return result;
    }

    /**
     * Insert a Boxer Profile values into the database.
     */
    public int insertBoxerProfile(int version, double chest, double inseam, double reach, String stance,
                                  int userID, double waist, double weight, double height, String leftDevice, String rightDevice,
                                  String gloveType, String skillLevel, int boxerServerId) {
        ContentValues values = new ContentValues();
        values.put(KEY_BOXER_VERSION, version);
        values.put(KEY_BOXER_CHEST, chest);
        values.put(KEY_BOXER_INSEAM, inseam);
        values.put(KEY_BOXER_LEFT_DEVICE, leftDevice);
        values.put(KEY_BOXER_RIGHT_DEVICE, rightDevice);
        values.put(KEY_BOXER_REACH, reach);
        values.put(KEY_BOXER_STANCE, stance);
        values.put(KEY_BOXER_USER_ID, userID);
        values.put(KEY_BOXER_WAIST, waist);
        values.put(KEY_BOXER_WEIGHT, weight);
        values.put(KEY_BOXER_HEIGHT, height);
        values.put(KEY_BOXER_GLOVE_TYPE, gloveType);
        values.put(KEY_BOXER_SKILL_LEVEL, skillLevel);
        values.put(KEY_BOXER_SERVER_ID, boxerServerId);
        int result = (int) db.insert(BOXER_PROFILE_TABLE, null, values);
        return result;
    }

    /**
     * Insert a Boxer Profile values into the database.
     */
    public int insertBoxerProfile(AuthenticationDTO authenticationDTO) {
        BoxerProfileDTO boxerProfileDTO = authenticationDTO.getBoxerProfile();

        ContentValues values = new ContentValues();
        values.put(KEY_BOXER_VERSION, 1);
        values.put(KEY_BOXER_CHEST, boxerProfileDTO.getChest());
        values.put(KEY_BOXER_INSEAM, boxerProfileDTO.getInseam());
        values.put(KEY_BOXER_LEFT_DEVICE, boxerProfileDTO.getLeftDevice());
        values.put(KEY_BOXER_RIGHT_DEVICE, boxerProfileDTO.getRightDevice());
        values.put(KEY_BOXER_REACH, boxerProfileDTO.getReach());
        values.put(KEY_BOXER_STANCE, boxerProfileDTO.getStance());
        values.put(KEY_BOXER_USER_ID, authenticationDTO.getUser().getId());
        values.put(KEY_BOXER_WAIST, boxerProfileDTO.getWaist());
        values.put(KEY_BOXER_WEIGHT, boxerProfileDTO.getWeight());
        values.put(KEY_BOXER_HEIGHT, boxerProfileDTO.getHeight());
        values.put(KEY_BOXER_GLOVE_TYPE, boxerProfileDTO.getGloveType());
        values.put(KEY_BOXER_SKILL_LEVEL, boxerProfileDTO.getSkillLevel());
        values.put(KEY_BOXER_SERVER_ID, authenticationDTO.getUser().getId());
        int result = (int) db.insert(BOXER_PROFILE_TABLE, null, values);
        return result;
    }

    /**
     * Insert a User_Role values into the database.
     */
    public long insertUserRole(int roleId, int userId) {
        Date date = new Date();
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ROLE_ROLE_ID, roleId);
        values.put(KEY_USER_ROLE_USER_ID, userId);
        values.put(KEY_USER_ROLE_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(USER_ROLE_TABLE, null, values);
        return result;
    }

    /**
     * Insert a Training_data values into the database.
     */
    public long insertTrainingData(int leftHand, int trainingSessionId, int userId) {
        Date date = new Timestamp(System.currentTimeMillis());
        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_DATA_LEFT_HAND, leftHand);
        values.put(KEY_TRAINING_DATA_TRAINING_SESSION_ID, trainingSessionId);
        values.put(KEY_TRAINING_DATA_USER_ID, userId);
        values.put(KEY_TRAINING_DATA_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(TRAINING_DATA_TABLE, null, values);
        return result;
    }

    /**
     * Insert a Training_data_details values into the database.
     */
    public long insertTrainingDataDetails(int ax, int ay, int az,
                                          double currentForce, double currentVelocity,
                                          Timestamp dataRecievedTime, int gx, int gy, int gz,
                                          double headTrauma, double milliSecond, int temp,
                                          int trainingDataId) {
        Date date = new Date();
        dateFormat.setTimeZone(utcTimeZone);

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_DATA_DETAILS_AX, ax);
        values.put(KEY_TRAINING_DATA_DETAILS_AY, ay);
        values.put(KEY_TRAINING_DATA_DETAILS_AZ, az);
        values.put(KEY_TRAINING_DATA_DETAILS_CURRENT_FORCE, currentForce);
        values.put(KEY_TRAINING_DATA_DETAILS_CURRENT_VELOCITY, currentVelocity);
        values.put(KEY_TRAINING_DATA_DETAILS_DATA_RECIEVED_TIME, dateFormat.format(dataRecievedTime));
        values.put(KEY_TRAINING_DATA_DETAILS_GX, gx);
        values.put(KEY_TRAINING_DATA_DETAILS_GY, gy);
        values.put(KEY_TRAINING_DATA_DETAILS_GZ, gz);
        values.put(KEY_TRAINING_DATA_DETAILS_HEAD_TRAUMA, headTrauma);
        values.put(KEY_TRAINING_DATA_DETAILS_MILLI_SECOND, milliSecond);
        values.put(KEY_TRAINING_DATA_DETAILS_TEMP, temp);
        values.put(KEY_TRAINING_DATA_DETAILS_TRAINING_DATA_ID, trainingDataId);
        values.put(KEY_TRAINING_DATA_DETAILS_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(TRAINING_DATA_DETAILS_TABLE, null, values);
        return result;
    }

    /**
     * Insert a training_punch_data values into the database.
     */
    public long insertTrainingPunchData(double maxForce, double maxSpeed,
                                        Timestamp punchDataDate, String punchType, int trainingDataId) {
        Date date = new Date();
        dateFormat.setTimeZone(utcTimeZone);

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_PUNCH_DATA_MAX_FORCE, maxForce);
        values.put(KEY_TRAINING_PUNCH_DATA_MAX_SPEED, maxSpeed);
        values.put(KEY_TRAINING_PUNCH_DATA_PUNCH_DATA_DATE, dateFormat.format(punchDataDate));
        values.put(KEY_TRAINING_PUNCH_DATA_PUNCH_TYPE, punchType);
        values.put(KEY_TRAINING_PUNCH_DATA_TRAINING_DATA_ID, trainingDataId);
        values.put(KEY_TRAINING_PUNCH_DATA_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(TRAINING_PUNCH_DATA_TABLE, null, values);
        return result;
    }

    /**
     * Insert a training_punch_data_peak_summary values into the database.
     */
    public long insertTrainingPunchDataPeakSummary(int hook, int jab,
                                                   String punchDataPeakSummaryDate, int speedFlag, int straight,
                                                   int trainingDataId, int upperCut) {
        Date date = new Date();
        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_HOOK, hook);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_JAB, jab);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_PUNCH_DATA_PEAK_SUMMARY_DATE, punchDataPeakSummaryDate);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SPEED_FLAG, speedFlag);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_STRAIGHT, straight);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_TRAINING_DATA_ID, trainingDataId);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_UPPER_CUT, upperCut);
        values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE, null,
                values);
        return result;
    }

    //super added this function for insert gym training session
    /**
     * Insert a gym training_session values into the database.
     */
    public long insertGymTrainingSession() {
        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date sessionDate = new Date();

        ContentValues values = new ContentValues();

        values.put(KEY_GYM_TRAINING_SESSION_START_TIME, (int)(System.currentTimeMillis() / 1000));
        values.put(KEY_GYM_TRAINING_SESSION_TRAINING_SESSION_DATE, sessionDateFormat.format(sessionDate));

        long result = db.insert(GYM_TRAINING_SESSION_TABLE, null, values);
        return result;
    }

    /**
     * Checks for end_time null values in training_session & updates those with the last punch time if available else with the start_time.
     *
     * @return No. of rows updated
     */
    public int endAllPreviousGymTrainingSessions() {

        // Select All Query
        String sqlQuery = "SELECT * FROM " + GYM_TRAINING_SESSION_TABLE + " WHERE TRIM(" + KEY_GYM_TRAINING_SESSION_END_TIME + ")='' OR " + KEY_GYM_TRAINING_SESSION_END_TIME + " IS NULL;";

        Cursor cursor = db.rawQuery(sqlQuery, null);

        int updateResult = 0;

        try {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
            }
            // Maintain list of elements for which end time is 0 or null
            String startTime, endTime;
            Cursor endTimeCursor;
            ContentValues values;

            for (int i = 0; i < count; i++) {
                int id = cursor.getInt(0);

                values = new ContentValues();
                values.put(KEY_GYM_TRAINING_SESSION_END_TIME, (int)(System.currentTimeMillis() / 1000));
                updateResult += db.update(GYM_TRAINING_SESSION_TABLE, values, KEY_GYM_TRAINING_SESSION_ID + " = " + id, null);
                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return updateResult;
    }

    public void deleteGymTrainingSession(){
        String selectQuery = "SELECT * FROM " + GYM_TRAINING_SESSION_TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();

            if (count > 0) {
                cursor.moveToFirst();
            }

            for (int i = 0; i < count; i++) {
                db.delete(GYM_TRAINING_SESSION_TABLE, KEY_GYM_TRAINING_SESSION_ID + "=?" , new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_ID)))});
                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public int getTodayTotalTime(){
        int totaltime = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatteddate = simpleDateFormat.format(new Date());

        String selectQuery = "SELECT * FROM " + GYM_TRAINING_SESSION_TABLE + " WHERE " + KEY_GYM_TRAINING_SESSION_TRAINING_SESSION_DATE + "='" + formatteddate + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();

            if (count > 0) {
                cursor.moveToFirst();
            }

            for (int i = 0; i < count; i++) {
                int startTime = cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_SESSION_START_TIME));
                int endTime = cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_SESSION_END_TIME));

                int duration = endTime - startTime;

                totaltime += duration;

                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return totaltime;
    }

    //super added this function for insert training stats table when get punch info from sensor
    public void addPunchtoStats(TrainingPunchDTO punchDTO){

        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Select All Query
        String selectQuery = "SELECT * FROM " + GYM_TRAINING_STATS_TABLE + " WHERE TRIM(" + KEY_GYM_TRAINING_STATS_PUNCH_TYPE + ")='"  + punchDTO.getPunchtype() +
                "' AND " + KEY_GYM_TRAINING_STATS_DATE + "='" + sessionDateFormat.format(new Date()) + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                updateGymTrainingStats(punchDTO, cursor);
            } else
                insertGymTrainingStats(punchDTO);
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
    public long insertGymTrainingStats(TrainingPunchDTO punchDTO) {
        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        ContentValues values = new ContentValues();

        values.put(KEY_GYM_TRAINING_STATS_PUNCH_TYPE, punchDTO.getPunchtype());
        values.put(KEY_GYM_TRAINING_STATS_AVG_SPEED, punchDTO.getSpeed());
        values.put(KEY_GYM_TRAINING_STATS_AVG_FORCE, punchDTO.getForce());
        values.put(KEY_GYM_TRAINING_STATS_DATE, sessionDateFormat.format(new Date()));
        values.put(KEY_GYM_TRAINING_STATS_PUNCH_COUNT, 1);
        values.put(KEY_GYM_TRAINING_STATS_TOTAL_TIME, punchDTO.getPunchtime());

        long result = db.insert(GYM_TRAINING_STATS_TABLE, null, values);
        return result;
    }

    public void updateGymTrainingStats(TrainingPunchDTO punchDTO, Cursor cursor){
        double avgspeed = cursor.getDouble(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_AVG_SPEED));
        double avgforce = cursor.getDouble(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_AVG_FORCE));
        double totaltime = cursor.getDouble(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_TOTAL_TIME));
        int punchcount = cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_PUNCH_COUNT));

        avgspeed = ((avgspeed * punchcount) + punchDTO.getSpeed()) / (punchcount + 1);
        avgforce = ((avgforce * punchcount) + punchDTO.getForce()) / (punchcount + 1);
        totaltime = totaltime + punchDTO.getPunchtime();
        punchcount = punchcount + 1;

        ContentValues values = new ContentValues();
        values.put(KEY_GYM_TRAINING_STATS_AVG_SPEED, avgspeed);
        values.put(KEY_GYM_TRAINING_STATS_AVG_FORCE, avgforce);
        values.put(KEY_GYM_TRAINING_STATS_PUNCH_COUNT, punchcount);
        values.put(KEY_GYM_TRAINING_STATS_TOTAL_TIME, totaltime);

        db.update(GYM_TRAINING_STATS_TABLE, values, KEY_GYM_TRAINING_STATS_ID + " = " + cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_ID)), null);
    }

    public void deleteGymTrainingStats(){
        String selectQuery = "SELECT * FROM " + GYM_TRAINING_STATS_TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();

            if (count > 0) {
                cursor.moveToFirst();
            }

            for (int i = 0; i < count; i++) {
                db.delete(GYM_TRAINING_STATS_TABLE, KEY_GYM_TRAINING_STATS_ID + "=?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_ID)))});
                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public ArrayList<TrainingStatsPunchTypeInfoDTO> getTrainingStats(){

        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        ArrayList<TrainingStatsPunchTypeInfoDTO> punchTypeInfoDTOs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + GYM_TRAINING_STATS_TABLE + " WHERE " + KEY_GYM_TRAINING_STATS_DATE + "='" + sessionDateFormat.format(new Date()) + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
            }
            // Maintain list of elements for which end time is 0 or null

            for (int i = 0; i < count; i++) {
                TrainingStatsPunchTypeInfoDTO punchTypeInfoDTO = new TrainingStatsPunchTypeInfoDTO();
                punchTypeInfoDTO.punchtype = (cursor.getString(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_PUNCH_TYPE)));
                punchTypeInfoDTO.avgspeed = (cursor.getDouble(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_AVG_SPEED)));
                punchTypeInfoDTO.avgforce = (cursor.getDouble(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_AVG_FORCE)));
                punchTypeInfoDTO.totaltime = (cursor.getDouble(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_TOTAL_TIME)));
                punchTypeInfoDTO.punchcount = (cursor.getInt(cursor.getColumnIndex(KEY_GYM_TRAINING_STATS_PUNCH_COUNT)));

                punchTypeInfoDTOs.add(punchTypeInfoDTO);

                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return punchTypeInfoDTOs;
    }


    /**
     * Insert a training_session values into the database.
     */
    public long insertTrainingSession(String training_type, int userId) {
        Date date = new Timestamp(System.currentTimeMillis());
        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date sessionDate = new Date();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_SESSION_START_TIME, dateFormat.format(date));
        values.put(KEY_TRAINING_SESSION_TRAINING_SESSION_DATE, sessionDateFormat.format(sessionDate));
        values.put(KEY_TRAINING_SESSION_TRAINING_TYPE, training_type);
        values.put(KEY_TRAINING_SESSION_USER_ID, userId);
        values.put(KEY_TRAINING_SESSION_SYNC_DATE, dateFormat.format(date));

        long result = db.insert(TRAINING_SESSION_TABLE, null, values);
        return result;
    }

    public void getAllAsync_session() {
        String session = "";
        int i = 1;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TRAINING_SESSION_TABLE
                + " WHERE " + KEY_TRAINING_SESSION_SYNC + " = 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                session = session + "id" + i + "=" + cursor.getInt(0)
                        + ",end_time=" + cursor.getString(1) + ",start_time="
                        + cursor.getString(2) + ",training_session_date="
                        + cursor.getString(3) + ",training_type="
                        + cursor.getString(4) + ",user_id=" + cursor.getInt(5)
                        + ",sync=" + cursor.getInt(6) + ",trainingSessionDate="
                        + cursor.getString(7) + ",server_training_session_id="
                        + cursor.getInt(8) + "\n";

            } else
                // resultJSON.put("success", false);
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    /* display all Training_session data from SQLite */
    public JSONObject getAllAsyncTrainingSession() {
        JSONObject sessionJSON = null, resultJSON = null;
        String session = "";

        resultJSON = new JSONObject();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_SYNC + " = 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    session = "{" + "\"id\":\"" + cursor.getInt(0) + "\","
                            + "\"end_time\":\"" + cursor.getString(1) + "\","
                            + "\"start_time\":\"" + cursor.getString(2) + "\","
                            + "\"training_session_date\":\"" + cursor.getString(3)
                            + "\"," + "\"training_type\":\"" + cursor.getString(4)
                            + "\"," + "\"user_id\":\"" + cursor.getInt(5) + "\","
                            + "\"sync\":\"" + cursor.getInt(6) + "\","
                            + "\"sync_date\":\"" + cursor.getString(7) + "\","
                            + "\"server_training_session_id\":\""
                            + cursor.getInt(8) + "\"" + "}";


                    sessionJSON = new JSONObject(session);
                    resultJSON.put("success", true);
                    resultJSON.put("session", sessionJSON);
                } else
                    resultJSON.put("success", false);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return resultJSON;
    }

    /* display all Training_data from SQLite */
    public JSONObject getAllAsyncTrainingData() {
        JSONObject trainingDataJSON = null, resultJSON = new JSONObject();

        String trainingData = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TRAINING_DATA_TABLE
                + " WHERE " + KEY_TRAINING_DATA_SYNC + " = 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    trainingData = "{" + "\"id\":\"" + cursor.getInt(0) + "\","
                            + "\"left_hand\":\"" + cursor.getInt(1) + "\","
                            + "\"training_session_id\":\"" + cursor.getInt(2)
                            + "\"," + "\"user_id\":\"" + cursor.getInt(3) + "\","
                            + "\"sync\":\"" + cursor.getInt(4) + "\","
                            + "\"sync_date\":\"" + cursor.getString(5) + "\","
                            + "\"server_id\":\"" + cursor.getInt(6) + "\"" + "}";

                    trainingDataJSON = new JSONObject(trainingData);
                    resultJSON.put("success", true);
                    resultJSON.put("TrainingData", trainingDataJSON);

                } else
                    resultJSON.put("success", false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultJSON;
    }

    /* display all Training_data from SQLite */
    public JSONObject getAllAsyncTrainingDataDetails() {
        JSONObject trainingDataDetailsJSON = null, resultJSON = new JSONObject();

        String TrainingDataDetails = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TRAINING_DATA_DETAILS_TABLE
                + " WHERE " + KEY_TRAINING_DATA_DETAILS_SYNC + " = 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    TrainingDataDetails = "{" + "\"id\":\"" + cursor.getInt(0)
                            + "\"," + "\"ax\":\"" + cursor.getInt(1) + "\","
                            + "\"ay\":\"" + cursor.getInt(2) + "\"," + "\"az\":\""
                            + cursor.getInt(3) + "\"," + "\"current_force\":\""
                            + cursor.getDouble(4) + "\","
                            + "\"current_velocity\":\"" + cursor.getDouble(5)
                            + "\"," + "\"data_recievd_time\":\""
                            + cursor.getString(6) + "\"," + "\"gx\":\""
                            + cursor.getInt(7) + "\"," + "\"gy\":\""
                            + cursor.getInt(8) + "\"," + "\"gz\":\""
                            + cursor.getInt(9) + "\"," + "\"head_trauma\":\""
                            + cursor.getDouble(10) + "\"," + "\"milli_second\":\""
                            + cursor.getDouble(11) + "\"," + "\"temp\":\""
                            + cursor.getInt(12) + "\"," + "\"training_data_id\":\""
                            + cursor.getInt(13) + "\"," + "\"sync\":\""
                            + cursor.getInt(14) + "\"," + "\"sync_date\":\""
                            + cursor.getString(15) + "\"," + "\"server_id\":\""
                            + cursor.getInt(16) + "\"" + "}";

                    trainingDataDetailsJSON = new JSONObject(TrainingDataDetails);
                    resultJSON.put("success", true);
                    resultJSON.put("TrainingDataDetails", trainingDataDetailsJSON);

                } else
                    resultJSON.put("success", false);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultJSON;
    }

    /* display all Training_PunchData from SQLite */
    public JSONObject getAllAsyncTrainingPunchData() {
        JSONObject trainingPunchDataJSON = null, resultJSON = new JSONObject();
        String PunchData = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TRAINING_PUNCH_DATA_TABLE
                + " WHERE " + KEY_TRAINING_PUNCH_DATA_SYNC + " = 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    PunchData = "{" + "\"id\":\"" + cursor.getInt(0) + "\","
                            + "\"max_force\":\"" + cursor.getDouble(1) + "\","
                            + "\"max_speed\":\"" + cursor.getDouble(2) + "\","
                            + "\"punch_data_date\":\"" + cursor.getString(3)
                            + "\"," + "\"punch_type\":\"" + cursor.getString(4)
                            + "\"," + "\"training_data_id\":\"" + cursor.getInt(5)
                            + "\"," + "\"sync\":\"" + cursor.getInt(6) + "\","
                            + "\"sync_date\":\"" + cursor.getString(7) + "\","
                            + "\"server_id\":\"" + cursor.getInt(8) + "\"" + "}";

                    trainingPunchDataJSON = new JSONObject(PunchData);
                    resultJSON.put("success", true);
                    resultJSON.put("PunchData", trainingPunchDataJSON);

                } else
                    resultJSON.put("success", false);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultJSON;
    }

    /* display all Training_PunchDataPeakSummary from SQLite */
    public JSONObject getAllAsyncTrainingPunchDataPeakSummary() {
        JSONObject trainingPunchDataPeakSummaryJSON = null, resultJSON = new JSONObject();
        String punchDataPeakSummary = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM "
                + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE + " WHERE "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC + " = 0 ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    punchDataPeakSummary = "{" + "\"id\":\"" + cursor.getInt(0)
                            + "\"," + "\"hook\":\"" + cursor.getInt(1) + "\","
                            + "\"jab\":\"" + cursor.getInt(2) + "\","
                            + "\"punch_data_peak_summary_date\":\""
                            + cursor.getString(3) + "\"," + "\"speed_flag\":\""
                            + cursor.getInt(4) + "\"," + "\"straight\":\""
                            + cursor.getInt(5) + "\"," + "\"training_data_id\":\""
                            + cursor.getInt(6) + "\"," + "\"upper_cut\":\""
                            + cursor.getInt(7) + "\"," + "\"sync\":\""
                            + cursor.getInt(8) + "\"," + "\"sync_date\":\""
                            + cursor.getString(9) + "\"," + "\"server_id\":\""
                            + cursor.getInt(10) + "\"" + "}";

                    trainingPunchDataPeakSummaryJSON = new JSONObject(
                            punchDataPeakSummary);
                    resultJSON.put("success", true);
                    resultJSON.put("PunchDataPeakSummary",
                            trainingPunchDataPeakSummaryJSON);

                } else
                    resultJSON.put("success", false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultJSON;
    }

    /* display all User data from SQLite */
    public JSONObject getAllUser() {
        JSONObject userJSON = null, resultJSON = new JSONObject();

        String user = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    user = "{" + "\"id\":\"" + cursor.getInt(0) + "\","
                            + "\"version\":\"" + cursor.getInt(1) + "\","
                            + "\"account_expired\":\"" + cursor.getInt(2) + "\","
                            + "\"account_locked\":\"" + cursor.getInt(3) + "\","
                            + "\"boxername\":\"" + cursor.getString(4) + "\","
                            + "\"chest\":\"" + cursor.getDouble(5) + "\","
                            + "\"date_of_birth\":\"" + cursor.getString(6) + "\","
                            + "\"enabled\":\"" + cursor.getInt(7) + "\","
                            + "\"gender\":\"" + cursor.getString(8) + "\","
                            + "\"inseam\":\"" + cursor.getDouble(9) + "\","
                            + "\"password\":\"" + cursor.getString(10) + "\","
                            + "\"password_expired\":\"" + cursor.getInt(11) + "\","
                            + "\"photo\":\"" + cursor.getBlob(12) + "\","
                            + "\"reach\":\"" + cursor.getDouble(13) + "\","
                            + "\"stance\":\"" + cursor.getString(14) + "\","
                            + "\"username\":\"" + cursor.getString(15) + "\","
                            + "\"waist\":\"" + cursor.getDouble(16) + "\","
                            + "\"weight\":\"" + cursor.getDouble(17) + "\","
                            + "\"left_device\":\"" + cursor.getString(18) + "\","
                            + "\"right_device\":\"" + cursor.getString(19) + "\","
                            + "\"sync\":\"" + cursor.getInt(20) + "\","
                            + "\"sync_date\":\"" + cursor.getString(21) + "\","
                            + "\"server_id\":\"" + cursor.getInt(22) + "\"" + "}";

                    userJSON = new JSONObject(user);
                    resultJSON.put("success", true);
                    resultJSON.put("User", userJSON);

                } else
                    resultJSON.put("success", false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultJSON;
    }

    /**
     * save a training_session values into the database.
     */
    public JSONObject trainingSessionSave(int boxerId, String trainingType) {
        JSONObject trainingDataJson, resultJSON;
        long trainingSessionId, trainingRightHandId, trainingLeftHandId;
        String trainingDataString = "";

        resultJSON = new JSONObject();


        try {

            trainingSessionId = insertTrainingSession(trainingType, boxerId);

            if (trainingSessionId == -1)
                resultJSON.put("success", false);
            else {

                trainingRightHandId = insertTrainingData(0,
                        (int) trainingSessionId, boxerId);

                if (trainingRightHandId == -1)
                    resultJSON.put("success", false);
                else {
                    System.out
                            .println("SQLite training Data RightHandId created "
                                    + trainingRightHandId);

                    trainingLeftHandId = insertTrainingData(1,
                            (int) trainingSessionId, boxerId);

                    if (trainingLeftHandId == -1)
                        resultJSON.put("success", false);
                    else {

                        System.out
                                .println("SQLite training Data LeftHandId created "
                                        + trainingLeftHandId);

                        trainingDataString = "{" + "\"trainingSessionId\":"
                                + trainingSessionId + ","
                                + "\"trainingLeftHandId\":"
                                + trainingLeftHandId + ","
                                + "\"trainingRightHandId\":"
                                + trainingRightHandId + "}";
                        trainingDataJson = new JSONObject(trainingDataString);

                        resultJSON.put("success", true);
                        resultJSON.put("trainingData", trainingDataJson);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //getAllAsyncTraining_session(); // changes by #1037
        //getAllAsyncTraining_Data(); // changes by #1037
        return resultJSON;
    }

    public JSONObject trainingSessionEnd(long trainingSessionId) {
        JSONObject resultJSON = new JSONObject();

        Date date = new Timestamp(System.currentTimeMillis());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Log.d(TAG, " KEY_TRAINING_SESSION_END_TIME :- "
                    + dateFormat.format(date));
            ContentValues values = new ContentValues();
            values.put(KEY_TRAINING_SESSION_END_TIME, dateFormat.format(date));
            values.put(KEY_TRAINING_SESSION_SYNC, 0);
            values.put(KEY_TRAINING_SESSION_SYNC_DATE, dateFormat.format(date));

            int updated_id = db.update(TRAINING_SESSION_TABLE, values,
                    KEY_TRAINING_SESSION_ID + " = " + trainingSessionId, null);

            if (updated_id != -1) {
                Log.d(TAG, "SQLite Training Session Ended ");
                resultJSON.put("success", true);
            } else
                resultJSON.put("success", false);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    public JSONObject trainingPunchDataSave(String punchtype,
                                            double forceValue, double currentVelocity, Timestamp punchTime,
                                            int trainingId) {

        JSONObject resultJSON = new JSONObject();
        Date date = new Date();
        try {
            long trainingPunchDataId = insertTrainingPunchData(forceValue, currentVelocity, punchTime, punchtype, trainingId);

            if (trainingPunchDataId == -1) {
                resultJSON.put("success", false);
            } else {
                resultJSON.put("success", true);
                System.out
                        .println("SQLite save of Training PunchData finished ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //getAllAsyncTraining_PunchData(); // changes by #1037
        return resultJSON;
    }

    public JSONObject trainingUserInfo(String userId) {

        JSONObject userInfoJson, resultJSON;
        resultJSON = new JSONObject();

        Cursor cursor = db.query(USER_TABLE, null, "id=" + userId, null, null, null, null);
        Date date = new Timestamp(System.currentTimeMillis());

        try {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    String userBirthdate = null;
                    if (cursor.getString(cursor.getColumnIndex("date_of_birth")) == null) {
                        userBirthdate = "";
                    } else {
                        userBirthdate = cursor.getString(cursor.getColumnIndex("date_of_birth"));
                    }
                    getUsersBoxerDetails(cursor.getInt(cursor.getColumnIndex(KEY_SERVER_USER_ID)));
                    String userInfoString = "";

                    userInfoString = "{" + "\"user_name\":\"" + cursor.getString(cursor.getColumnIndex(KEY_USER_USERNAME)) + "\","
                            + "\"first_name\":\"" + cursor.getString(cursor.getColumnIndex(KEY_USER_BOXERFIRSTNAME)) + "\","
                            + "\"last_name\":\"" + cursor.getString(cursor.getColumnIndex(KEY_USER_BOXERLASTNAME)) + "\","
                            + "\"user_birthdate\":\"" + userBirthdate + "\","
                            + "\"user_weight\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_WEIGHT) + "\","
                            + "\"left_device\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_LEFT_DEVICE) + "\","
                            + "\"right_device\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_RIGHT_DEVICE) + "\","
                            + "\"user_gender\":\"" + cursor.getString(cursor.getColumnIndex("gender")) + "\","
                            + "\"user_reach\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_REACH) + "\","
                            + "\"user_waist\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_WAIST) + "\","
                            + "\"user_chest\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_CHEST) + "\","
                            + "\"user_inseam\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_INSEAM) + "\","
                            + "\"user_stance\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_STANCE) + "\","
                            + "\"user_height\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_HEIGHT) + "\","
                            + "\"user_skill_level\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_SKILL_LEVEL) + "\","
                            + "\"user_glove_type\":\"" + getUsersBoxerDetails(Integer.parseInt(userId)).get(KEY_BOXER_GLOVE_TYPE) + "\","
                            + "\"user_email\":\"" + cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL_ID)) + "\""
                            + "}";

                    userInfoJson = new JSONObject(userInfoString);
                    resultJSON.put("success", true);
                    resultJSON.put("userInfo", userInfoJson);
                } else {
                    resultJSON.put("success", false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultJSON;
    }


    public JSONObject trainingPunchDataPeakSummarySave(String straight,
                                                       String hook, String jab, String uppercut, String speed,
                                                       Integer trainingId) {

        JSONObject resultJSON = new JSONObject();
        Date date = new Timestamp(System.currentTimeMillis());

        try {
            long trainingPunchDataPeakSummaryId = insertTrainingPunchDataPeakSummary(
                    Integer.parseInt(hook), Integer.parseInt(jab),
                    dateFormat.format(date), Integer.parseInt(speed),
                    Integer.parseInt(straight), trainingId,
                    Integer.parseInt(uppercut));

            if (trainingPunchDataPeakSummaryId == -1) {
                resultJSON.put("success", false);
            } else {
                resultJSON.put("success", true);
                System.out
                        .println("SQLite save of Training punchData PeakSummary  finished ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getAllAsyncTrainingPunchDataPeakSummary(); // changes by #1037
        return resultJSON;
    }

    public JSONObject trainingDataDetailsSave(int ax, int ay, int az,
                                              double forcevalue, double currentvelocity, Timestamp datarecievedtime,
                                              int gx, int gy, int gz, int headtrauma,
                                              double millisecond, short temp, Integer trainingId) {

        JSONObject resultJSON = new JSONObject();

        try {
            long trainingDataDetailsId = insertTrainingDataDetails(ax, ay, az, forcevalue, currentvelocity,
                    datarecievedtime, gx, gy, gz, headtrauma, millisecond, temp, trainingId);

            if (trainingDataDetailsId == -1) {
                resultJSON.put("success", false);
            } else {
                resultJSON.put("success", true);
                System.out
                        .println("SQLite save of Training DataDetails finished ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //getAllAsyncTraining_DataDetails(); // changes by #1037
        return resultJSON;
    }

    /**
     * Get the data for training result screen from local db.
     *
     * @param userId            userId of the current logged in trainee.
     * @param aggregateFunction String "avg" or "max" depending on what button is clicked.
     * @return JSONObject containing the data returned from query. If the data is not present it will return 0 for those records.
     */
    public JSONObject getTrainingResultData(String userId, String aggregateFunction) {
        final boolean DEBUG = false;
        JSONObject resultJsonData = new JSONObject();
        int sessionId = -1;
//			final String STR_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        try {
            //query to get last completed session id
            sessionId = getCompletedSessionId(userId);

            //query with unrecognized punch
            String strQuery =
                    "select substr(tpd.punch_type, 1, 1) as hand, substr(tpd.punch_type, 2) as punchType, count(tpd.punch_type) as totalPunch, " + aggregateFunction +
                            "(tpd.max_force) as " + aggregateFunction + "Force, " + aggregateFunction + "(tpd.max_speed) as " + aggregateFunction +
                            "Speed from training_data td, training_punch_data tpd, training_session TS  where tpd.training_data_id = td.id AND TD.training_session_id = TS.id and TS.id = " + sessionId + " and TS.user_id = " + userId +
                            " group by tpd.punch_type;";
            Cursor resultCursor = db.rawQuery(strQuery, null);

            Map<String, Object> mapResultData = new HashMap<String, Object>();

            final String[] PUNCH_TYPES = {"hook", "jab", "straight", "uppercut", "unrecognized"};
            final String[] HANDS = {"lh", "rh"};
            final String FORCE = "force";
            final String SPEED = "speed";
            final String TOTAL = "total";

            try {
                if (resultCursor.getCount() > 0) {
                    resultCursor.moveToFirst();
                    String hand, punch = "";

                    do {
                        hand = ("L".equals(resultCursor.getString(0))) ? HANDS[0] : HANDS[1];
                        if ("H".equals(resultCursor.getString(1))) {
                            punch = PUNCH_TYPES[0];
                        } else if ("J".equals(resultCursor.getString(1))) {
                            punch = PUNCH_TYPES[1];
                        } else if ("S".equals(resultCursor.getString(1))) {
                            punch = PUNCH_TYPES[2];
                        } else if ("U".equals(resultCursor.getString(1))) {
                            punch = PUNCH_TYPES[3];
                        } else if ("R".equals(resultCursor.getString(1))) {
                            punch = PUNCH_TYPES[4];
                        }
                        mapResultData.put(hand + "_" + punch + "_" + TOTAL, resultCursor.getString(2));    // total count value
                        mapResultData.put(hand + "_" + punch + "_" + FORCE, resultCursor.getString(3));    // force value
                        mapResultData.put(hand + "_" + punch + "_" + SPEED, resultCursor.getString(4));    // speed value

                    } while (resultCursor.moveToNext());

                } else {
                    resultJsonData.put("success", false);
                }
            } finally {
                if (resultCursor != null)
                    resultCursor.close();
            }


            StringBuilder sb = new StringBuilder("{");
            String key, value;

            for (int i = 0; i < HANDS.length; i++) {
                for (int j = 0; j < PUNCH_TYPES.length; j++) {    // length because we want unrecognized punch

                    // for speed
                    key = HANDS[i] + "_" + PUNCH_TYPES[j] + "_" + SPEED;
                    value = (String) mapResultData.get(key);
                    sb.append("\"" + key + "\":");
                    sb.append(value == null ? 0 : value);
                    sb.append(",");

                    // for force
                    key = HANDS[i] + "_" + PUNCH_TYPES[j] + "_" + FORCE;
                    value = (String) mapResultData.get(key);
                    sb.append("\"" + key + "\":");
                    sb.append(value == null ? 0 : value);
                    sb.append(",");

                    // for total
                    key = HANDS[i] + "_" + PUNCH_TYPES[j] + "_" + TOTAL;
                    value = (String) mapResultData.get(key);
                    sb.append("\"" + key + "\":");
                    sb.append(value == null ? 0 : value);

                    sb.append((j != PUNCH_TYPES.length - 1) ? "," : "");   //length-1 to enable unrecognized punches else length-2 to avoid unrecognized punches
                }
                sb.append((i != HANDS.length - 1) ? "," : "}");
            }
            JSONObject jsonPunchData = new JSONObject(sb.toString());
            resultJsonData.put("success", true);
            resultJsonData.put("jsonPunchData", jsonPunchData);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return resultJsonData;
    }

    public int getCompletedSessionId(String userId) {
        int punchCountSessionId = -1;
        String punchCountSessionIdQuery =
                "select id from training_session where end_time =(select MAX(end_time) from training_session where user_id=" + userId + ") and end_time not null;";

        Cursor currentEndedSession = db.rawQuery(punchCountSessionIdQuery, null);

        if (currentEndedSession.getCount() > 0) {
            currentEndedSession.moveToFirst();
            punchCountSessionId = currentEndedSession.getInt(0);
        }
        return punchCountSessionId;
    }

    public int getCurrentRunningSessionId(String userId) {
        int punchCountSessionId = -1;
        String punchCountSessionIdQuery =
                "select id from training_session where start_time =(select MAX(start_time) from training_session where user_id=" + userId + " and end_time is null);";

        Cursor currentEndedSession = db.rawQuery(punchCountSessionIdQuery, null);

        if (currentEndedSession.getCount() > 0) {
            currentEndedSession.moveToFirst();
            punchCountSessionId = currentEndedSession.getInt(0);
        }
        return punchCountSessionId;
    }

    public Integer trainingTotalPunchCountDataOfBagTrainingOne(String userId, Integer sessionId) {
        JSONObject resultJSON = null;
        int currentRunningsessionId = -1;
        Integer totalPunchCount = 0;
        try {
            Map<String, Object> dataHoldMap = new HashMap();
            resultJSON = new JSONObject();
            String getTotalPunchCountValueQuery = "";
            if (sessionId == null)
                currentRunningsessionId = getCompletedSessionId(userId);
            else
                currentRunningsessionId = sessionId;//getCurrentRunningSessionId(userId);	
            getTotalPunchCountValueQuery = "SELECT COUNT( * ) AS totalPunchCount FROM training_punch_data AS TPD, training_data AS TD, training_session AS TS " +
                    "WHERE TPD.training_data_id = TD.id AND TD.training_session_id = TS.id AND  TS.id=" + currentRunningsessionId + " AND TS.user_id=" + userId + "; ";

            Cursor resultantTotalPunchCount = db.rawQuery(getTotalPunchCountValueQuery, null);
            if (resultantTotalPunchCount.getCount() > 0) {
                resultantTotalPunchCount.moveToFirst();
                do {
                    totalPunchCount = resultantTotalPunchCount.getInt(0);

                } while (resultantTotalPunchCount.moveToNext());
            } else
                resultJSON.put("success", false);

            resultantTotalPunchCount.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return totalPunchCount;
    }


    // today, avg and total punch count data getting queries
    public JSONObject getPunchCountScreenDetails(String userId, String startDate, String endDate, Integer currentSessionId) {

        JSONObject punchCountJson, resultJSON = null;
        try {
            Map<String, Object> dataHoldMap = new HashMap<String, Object>();
            resultJSON = new JSONObject();

            String totalPunchCountQuery = "";
            Integer totalPunchCount = 0;

            totalPunchCountQuery = "SELECT COUNT( * ) AS totalPunchCount FROM training_punch_data AS TPD, training_data AS TD, training_session AS TS WHERE TPD.training_data_id = TD.id AND TD.training_session_id = TS.id AND TS.user_id= "
                    + userId + " and TS.id != " + currentSessionId + " and TS.training_session_date between '" + endDate + "' and '" + endDate + "'";

            Cursor resultantTotalPunchCount = db.rawQuery(totalPunchCountQuery, null);
            if (resultantTotalPunchCount.getCount() > 0) {
                resultantTotalPunchCount.moveToFirst();
                do {
                    totalPunchCount = resultantTotalPunchCount.getInt(0);

                } while (resultantTotalPunchCount.moveToNext());
            } else {
                resultJSON.put("success", false);
            }

            dataHoldMap.put("Punch_Count_Total", totalPunchCount);   //to store total punch count value(sum of all punches)
            resultantTotalPunchCount.close();

            String getCountForAvgQuery = "";

            // for display "7 day" data as per new requirement. 
            getCountForAvgQuery = "SELECT punch_type AS punchType, count(*) AS avgTotalPunch FROM training_punch_data AS TPD, training_data AS TD, training_session AS TS WHERE TPD.training_data_id = TD.id AND TD.training_session_id = TS.id AND TS.id != " + currentSessionId + " AND TS.training_session_date between \'" + startDate + "\' and \'" + endDate + "\' AND TS.user_id= "
                    + userId + " GROUP BY TPD.punch_type";

            Cursor resultgetCountForAvg = db.rawQuery(getCountForAvgQuery, null);

            if (resultgetCountForAvg.getCount() > 0) {

                resultgetCountForAvg.moveToFirst();
                do {

                    String punchType = resultgetCountForAvg.getString(0);
                    Integer averageTotalPunch = resultgetCountForAvg.getInt(1);

                    if (punchType.equals("LJ")) {
                        dataHoldMap.put("LJ_Avg", averageTotalPunch);
                    } else if (punchType.equals("RJ")) {
                        dataHoldMap.put("RJ_Avg", averageTotalPunch);
                    } else if (punchType.equals("LS")) {
                        dataHoldMap.put("LS_Avg", averageTotalPunch);                  // LS_Avg added
                    } else if (punchType.equals("RS")) {
                        dataHoldMap.put("RS_Avg", averageTotalPunch);
                    } else if (punchType.equals("LH")) {
                        dataHoldMap.put("LH_Avg", averageTotalPunch);
                    } else if (punchType.equals("RH")) {
                        dataHoldMap.put("RH_Avg", averageTotalPunch);
                    } else if (punchType.equals("LU")) {
                        dataHoldMap.put("LU_Avg", averageTotalPunch);
                    } else if (punchType.equals("RU")) {
                        dataHoldMap.put("RU_Avg", averageTotalPunch);
                    } else if (punchType.equals("LR")) {
                        dataHoldMap.put("LR_Avg", averageTotalPunch);
                    } else if (punchType.equals("RR")) {
                        dataHoldMap.put("RR_Avg", averageTotalPunch);
                    }

                } while (resultgetCountForAvg.moveToNext());

                resultgetCountForAvg.close();
            } else {
                resultJSON.put("success", false);
            }

            String getTodaysPunchQuery = "";
            getTodaysPunchQuery = "SELECT punch_type AS punchType, count(*) AS todayTotalPunch FROM training_punch_data AS TPD, training_data AS TD, training_session AS TS WHERE TPD.training_data_id = TD.id AND TD.training_session_id = TS.id  AND TS.user_id= "
                    + userId + " and TS.id != " + currentSessionId + " and TS.training_session_date between '" + endDate + "' and '" + endDate + "'" + " GROUP BY TPD.punch_type";

            Cursor resultTodaysPunch = db.rawQuery(getTodaysPunchQuery, null);

            if (resultTodaysPunch.getCount() > 0) {
                resultTodaysPunch.moveToFirst();
                do {
                    String punchType = resultTodaysPunch.getString(0);
                    Integer todaysTotalPunch = resultTodaysPunch.getInt(1);

                    if (punchType.equals("LJ")) {
                        dataHoldMap.put("LJ_Today", todaysTotalPunch);
                    } else if (punchType.equals("RJ")) {
                        dataHoldMap.put("RJ_Today", todaysTotalPunch);
                    } else if (punchType.equals("LS")) {
                        dataHoldMap.put("LS_Today", todaysTotalPunch);                  // LS_Today added
                    } else if (punchType.equals("RS")) {
                        dataHoldMap.put("RS_Today", todaysTotalPunch);
                    } else if (punchType.equals("LH")) {
                        dataHoldMap.put("LH_Today", todaysTotalPunch);
                    } else if (punchType.equals("RH")) {
                        dataHoldMap.put("RH_Today", todaysTotalPunch);
                    } else if (punchType.equals("LU")) {
                        dataHoldMap.put("LU_Today", todaysTotalPunch);
                    } else if (punchType.equals("RU")) {
                        dataHoldMap.put("RU_Today", todaysTotalPunch);
                    } else if (punchType.equals("LR")) {
                        dataHoldMap.put("LR_Today", todaysTotalPunch);
                    } else if (punchType.equals("RR")) {
                        dataHoldMap.put("RR_Today", todaysTotalPunch);
                    }

                } while (resultTodaysPunch.moveToNext());

                resultTodaysPunch.close();
            } else {
                resultJSON.put("success", false);
            }

            // punchCountJson(dataHoldMap)
            Integer rjToday = (Integer) ((dataHoldMap.get("RJ_Today") == null) ? 0 : dataHoldMap.get("RJ_Today"));
            Integer rjAvg = (Integer) ((dataHoldMap.get("RJ_Avg") == null) ? 0 : dataHoldMap.get("RJ_Avg"));
            Integer rhToday = (Integer) ((dataHoldMap.get("RH_Today") == null) ? 0 : dataHoldMap.get("RH_Today"));
            Integer rhAvg = (Integer) ((dataHoldMap.get("RH_Avg") == null) ? 0 : dataHoldMap.get("RH_Avg"));
            Integer rsToday = (Integer) ((dataHoldMap.get("RS_Today") == null) ? 0 : dataHoldMap.get("RS_Today"));
            Integer rsAvg = (Integer) ((dataHoldMap.get("RS_Avg") == null) ? 0 : dataHoldMap.get("RS_Avg"));
            Integer ruToday = (Integer) ((dataHoldMap.get("RU_Today") == null) ? 0 : dataHoldMap.get("RU_Today"));
            Integer ruAvg = (Integer) ((dataHoldMap.get("RU_Avg") == null) ? 0 : dataHoldMap.get("RU_Avg"));
            //right unrecognized punch data
            Integer rrToday = (Integer) ((dataHoldMap.get("RR_Today") == null) ? 0 : dataHoldMap.get("RR_Today"));
            Integer rrAvg = (Integer) ((dataHoldMap.get("RR_Avg") == null) ? 0 : dataHoldMap.get("RR_Avg"));

            Integer ljToday = (Integer) ((dataHoldMap.get("LJ_Today") == null) ? 0 : dataHoldMap.get("LJ_Today"));
            Integer ljAvg = (Integer) ((dataHoldMap.get("LJ_Avg") == null) ? 0 : dataHoldMap.get("LJ_Avg"));
            Integer lhToday = (Integer) ((dataHoldMap.get("LH_Today") == null) ? 0 : dataHoldMap.get("LH_Today"));
            Integer lhAvg = (Integer) ((dataHoldMap.get("LH_Avg") == null) ? 0 : dataHoldMap.get("LH_Avg"));
            Integer lsToday = (Integer) ((dataHoldMap.get("LS_Today") == null) ? 0 : dataHoldMap.get("LS_Today"));
            Integer lsAvg = (Integer) ((dataHoldMap.get("LS_Avg") == null) ? 0 : dataHoldMap.get("LS_Avg"));
            Integer luToday = (Integer) ((dataHoldMap.get("LU_Today") == null) ? 0 : dataHoldMap.get("LU_Today"));
            Integer luAvg = (Integer) ((dataHoldMap.get("LU_Avg") == null) ? 0 : dataHoldMap.get("LU_Avg"));
            //left unrecognized punch data
            Integer lrToday = (Integer) ((dataHoldMap.get("LR_Today") == null) ? 0 : dataHoldMap.get("LR_Today"));
            Integer lrAvg = (Integer) ((dataHoldMap.get("LR_Avg") == null) ? 0 : dataHoldMap.get("LR_Avg"));

            //sum of all punches store in totalPunchCountSum var
            Integer resultantPunchCountValue = (Integer) ((dataHoldMap.get("Punch_Count_Total") == null) ? 0 : dataHoldMap.get("Punch_Count_Total"));

            String punchCountString = "";

            punchCountString = "{" + "\"RJ_Today\":" + rjToday + "," + "\"RJ_Avg\":" + rjAvg + "," + "\"RH_Today\":" + rhToday + "," + "\"RH_Avg\":"
                    + rhAvg + "," + "\"RS_Today\":" + rsToday + "," + "\"RS_Avg\":" + rsAvg + "," + "\"RU_Today\":" + ruToday + "," + "\"RU_Avg\":"
                    + ruAvg + "," + "\"RR_Today\":" + rrToday + "," + "\"RR_Avg\":" + rrAvg + "," + "\"LJ_Today\":" + ljToday + "," + "\"LJ_Avg\":"
                    + ljAvg + "," + "\"LH_Today\":" + lhToday + "," + "\"LH_Avg\":" + lhAvg + "," + "\"LS_Today\":" + lsToday + "," + "\"LS_Avg\":"
                    + lsAvg + "," + "\"LU_Today\":" + luToday + "," + "\"LU_Avg\":" + luAvg + "," + "\"LR_Today\":" + lrToday + "," + "\"LR_Avg\":"
                    + lrAvg + "," + "\"Punch_Count_Total\":" + resultantPunchCountValue + "}";

            punchCountJson = new JSONObject(punchCountString);
            resultJSON.put("success", true);
            resultJSON.put("punchCount", punchCountJson);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultJSON;
    }

    public JSONObject getTrainingPunchCountDataSummary(String userId) {
        JSONObject result = new JSONObject();
        final String sqlQuery = "SELECT * FROM " + PUNCH_COUNT_SUMMARY_TABLE + " WHERE " + KEY_PUNCH_COUNT_SUMMARY_USER_ID + "='" + userId + "';";
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            // do not jumble up the element sequence. Its as per values returned by respective column nos.
            String[] keyArr = {"LJ_Avg", "LJ_Today", "LS_Avg", "LS_Today", "LH_Avg", "LH_Today", "LU_Avg", "LU_Today", "LR_Avg", "LR_Today",
                    "RJ_Avg", "RJ_Today", "RS_Avg", "RS_Today", "RH_Avg", "RH_Today", "RU_Avg", "RU_Today", "RR_Avg", "RR_Today", "Punch_Count_Total"};
            try {
                for (int i = 0; i < keyArr.length; i++) {
                    result.put(keyArr[i], cursor.getString(i + 4));    // +4 to skip 1st 4 columns
                }
                result.put("success", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result.put("success", false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Map<String, Integer> getTrainingProgressData(String userId, String startDate, String endDate, Integer currentSessionId) {
        Map<String, Integer> progressDataMap = new HashMap<String, Integer>();

        String progressDataQuery = "select tpd.punch_type as punchType,avg(tpd.max_speed) as avgVelocity, avg(tpd.max_force) as avgForce " +
                "from training_punch_data tpd, training_data td, user u,training_session ts where td.user_id=" + userId + " and tpd.training_data_id = td.id " +
                "and  ts.user_id=" + userId + " and ts.id=td.training_session_id and ts.id != " + currentSessionId + " and ts.training_session_date between \'" + startDate + "\' and \'" + endDate + "\' group by 1";

        try {
            Cursor cursor = db.rawQuery(progressDataQuery, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String punchType = cursor.getString(0);
                    Double avgSpeed = cursor.getDouble(1);
                    Double avgForce = cursor.getDouble(2);

                    progressDataMap.put(punchType + "Speed", (int) Math.round(avgSpeed));
                    progressDataMap.put(punchType + "Force", (int) Math.round(avgForce));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDataMap;
    }

    Integer returnMaxValue(Integer jabMax, Integer hookMax,
                           Integer straightMax, Integer upperCutMax) {

        Integer maxValue = jabMax;
        if (maxValue < hookMax) {
            maxValue = hookMax;
        }
        if (maxValue < straightMax) {
            maxValue = straightMax;
        }
        if (maxValue < upperCutMax) {
            maxValue = upperCutMax;
        }
        return maxValue;
    }

    /**
     * Shows totals and max for the given values over the past n days- regardless of the total number of training sessions
     *
     * @param userId     User id of the logged in user.
     * @param stringDate Current selected trainingSessionDate.
     * @param nDaysBack  No. of days back
     * @return {@link JSONObject} containing data
     */
    public JSONObject trainingDataDetailsCalendarSummary(long userId, String stringDate, int nDaysBack, Integer currentSessionId) {

        JSONObject resultJSON = null, resultCalendarSummaryJSON = null;

        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat1.parse(stringDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            cal.setTime(df.parse(stringDate));
            cal.add(Calendar.DATE, -nDaysBack);
            String startDate = df.format(cal.getTime());
            String endDate = stringDate;
            resultJSON = new JSONObject();
            JSONArray monthlySummaryJSONArray = new JSONArray();

            // Get Calendar maxSpeed, maxForce, totalPunches for nDaysBack
            String getCalendarSummaryQuery = "SELECT " +
                    "MAX(TPD.max_force) AS maxForce, " +
                    "MAX(TPD.max_speed) AS maxSpeed, " +
                    "AVG(TPD.max_force) AS averageForce, " +
                    "AVG(TPD.max_speed) AS averageSpeed, " +
                    "COUNT(TPD.punch_type) AS totalPunch "
                    + "FROM training_punch_data AS TPD, " +
                    "training_data AS TD," +
                    "training_session AS TS " +
                    "WHERE "
                    + "TPD.training_data_id = TD.id " +
                    " AND TS.user_id= " + userId +
                    " AND TS.id=TD.training_session_id " +
                    " AND TS.id != " + currentSessionId +
                    " AND TS.training_session_date BETWEEN '" + startDate + "' AND '" + endDate + "'";

            Cursor resultCalendarSummary = db.rawQuery(getCalendarSummaryQuery, null);
            String resultCalendarSummaryString = "";

            if (resultCalendarSummary.getCount() > 0) {
                resultCalendarSummary.moveToFirst();

                resultCalendarSummaryString = "{" +
                        "\"maxForce\":" + resultCalendarSummary.getDouble(0) + "," +
                        "\"maxSpeed\":" + resultCalendarSummary.getDouble(1) + "," +
                        "\"averageForce\":" + resultCalendarSummary.getDouble(2) + "," +
                        "\"averageSpeed\":" + resultCalendarSummary.getDouble(3) + "," +
                        "\"totalPunch\":" + resultCalendarSummary.getInt(4) +
                        "}";
            } else {
                resultCalendarSummaryString = "{" + "\"maxSpeed\":" + null + "," + "\"maxForce\":" + null + "," + "\"totalPunch\":" + 0 + "}";
                resultJSON.put("success", false);
            }
            resultCalendarSummary.close();
            resultCalendarSummaryJSON = new JSONObject(resultCalendarSummaryString);
            monthlySummaryJSONArray.put(resultCalendarSummaryJSON);

            String getDatesQuery = "", getCalendarDurationQuery = "";
            getDatesQuery = "Select * FROM training_session WHERE user_id = " + userId + " and training_session_date between '" + startDate + "' and '" + endDate + "' and id != " + currentSessionId + ";";
            Cursor resultDatesQuery = db.rawQuery(getDatesQuery, null);
            String endTimeNew, startTimeNew;
            int diffInSeconds = 0, Duration = 0;

            if (resultDatesQuery.getCount() > 0) {
                resultDatesQuery.moveToFirst();
                do {
                    if (resultDatesQuery.getString(1) != null && resultDatesQuery.getString(2) != null && resultDatesQuery.getString(3) != null) {

                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        endTimeNew = resultDatesQuery.getString(1);
                        startTimeNew = resultDatesQuery.getString(2);
                        getCalendarDurationQuery = "SELECT strftime('%s','" + endTimeNew + "') - strftime('%s','" + startTimeNew + "')";
                        Cursor resultCalendarDurationQuery = db.rawQuery(getCalendarDurationQuery, null);
                        if (resultCalendarDurationQuery.getCount() > 0) {
                            resultCalendarDurationQuery.moveToFirst();
                            do {
                                if (resultCalendarDurationQuery.getString(0) != null) {
                                    diffInSeconds = Integer.parseInt(resultCalendarDurationQuery.getString(0));
                                    Duration += diffInSeconds;
                                }
                            } while (resultCalendarDurationQuery.moveToNext());
                            resultCalendarDurationQuery.close();
                        } else
                            Duration = 0;
                    } else {
                        //Duration = 0;
                    }
//							} else
//								resultJSON.put("success", false);
                } while (resultDatesQuery.moveToNext());

            } else
                resultJSON.put("success", false);
            resultDatesQuery.close();

            Log.d(TAG, "----------------  Duration := " + Duration + "  ----------------");

            long totalDiffHours = Duration / (60 * 60) % 24;
            long totalDiffMinutes = (Duration / 60) % 60;
            //totalDiffMinutes = (totalDiffHours*(60))+totalDiffMinutes;
            long totalDiffSeconds = Duration % 60;

            String duration = String.format("%02d", totalDiffHours) + ":" + String.format("%02d", totalDiffMinutes) + ":" + String.format("%02d", totalDiffSeconds);

            resultJSON.put("success", true);
            resultJSON.put("calendarSummary", monthlySummaryJSONArray);
            resultJSON.put("duration", duration);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    public int updateUserFields(ContentValues userData, int userId) {
        int result = db.update(USER_TABLE, userData, KEY_USER_ID + " = " + userId, null);
        return result;
    }

    public int updateUsersBoxer(ContentValues boxerData, int userId) {
        int result = db.update(BOXER_PROFILE_TABLE, boxerData, KEY_BOXER_USER_ID + " = " + userId, null);
        return result;
    }

    public int updateUser(int userId, int version, int accountExpired,
                          int accountLocked, String firstName, String lastName, String bdate,
                          int enabled, String gender, String password, int passwordExpired,
                          byte[] photo, String username, String emailId, int countryId, String zipcode,
                          int sync, int serverUserId) {

        Date date = new Date();
        Log.d(TAG, "*****updateUser Date:- " + date);
        ContentValues values = new ContentValues();

        values.put(KEY_USER_VERSION, version);
        values.put(KEY_USER_ACCOUNT_EXPIRED, accountExpired);
        values.put(KEY_USER_ACCOUNT_LOCKED, accountLocked);
        values.put(KEY_USER_BOXERFIRSTNAME, firstName);
        values.put(KEY_USER_BOXERLASTNAME, lastName);
        values.put(KEY_USER_DATE_OF_BIRTH, bdate);
        values.put(KEY_USER_ENABLED, enabled);
        values.put(KEY_USER_GENDER, gender);
        values.put(KEY_USER_PASSWORD, password);
        values.put(KEY_USER_PASSWORD_EXPIRED, passwordExpired);
        values.put(KEY_USER_PHOTO, photo);
        values.put(KEY_USER_USERNAME, username);
        values.put(KEY_USER_EMAIL_ID, emailId);
        values.put(KEY_USER_COUNTRY_ID, countryId);
        values.put(KEY_USER_ZIP_CODE, zipcode);
        values.put(KEY_USER_SYNC, sync);
        values.put(KEY_USER_SYNC_DATE, dateFormat.format(date));    // TODO: check if this is required also check if sync flag needs to be updated
        values.put(KEY_SERVER_USER_ID, serverUserId);
        Log.d(TAG, "Database obj=-" + db);

        // updating row
        int result = db.update(USER_TABLE, values, KEY_USER_ID + " = " + userId, null);
        return result;
    }


    public int updateUser(AuthenticationDTO authenticationDTO, boolean sync) {

        Date date = new Date();
        Log.d(TAG, "*****updateUser Date:- " + date);
        ContentValues values = new ContentValues();

        UserDTO userDTO = authenticationDTO.getUser();

        values.put(KEY_USER_VERSION, 1);
        values.put(KEY_USER_ACCOUNT_EXPIRED, userDTO.getAccountExpired());
        values.put(KEY_USER_ACCOUNT_LOCKED, userDTO.getAccountLocked());
        values.put(KEY_USER_BOXERFIRSTNAME, userDTO.getFirstName());
        values.put(KEY_USER_BOXERLASTNAME, userDTO.getLastName());
        values.put(KEY_USER_DATE_OF_BIRTH, userDTO.getDateOfBirth());
        values.put(KEY_USER_ENABLED, userDTO.getEnabled());
        values.put(KEY_USER_GENDER, userDTO.getGender());
        values.put(KEY_USER_PASSWORD, userDTO.getPassword());
        values.put(KEY_USER_PASSWORD_EXPIRED, userDTO.getPasswordExpired());
        values.put(KEY_USER_PHOTO, "");
        values.put(KEY_USER_USERNAME, userDTO.getUsername());
        values.put(KEY_USER_EMAIL_ID, userDTO.getEmailId());
        values.put(KEY_USER_COUNTRY_ID, userDTO.getCountry().getId());
        values.put(KEY_USER_ZIP_CODE, userDTO.getId());
        values.put(KEY_USER_SYNC, sync);
        values.put(KEY_USER_SYNC_DATE, dateFormat.format(date));    // TODO: check if this is required also check if sync flag needs to be updated
        values.put(KEY_SERVER_USER_ID, userDTO.getId());
        Log.d(TAG, "Database obj=-" + db);

        // updating row
        int result = db.update(USER_TABLE, values, KEY_USER_ID + " = " + userDTO.getId(), null);
        return result;
    }

    /**
     * update a User Access Data values from the database.
     */
    public int updateUserAccessData(int userAccessId, String traineeAccessToken, int userAccessServerId) {
        ContentValues values = new ContentValues();

        values.put(KEY_USER_ACCESS_TOKEN, traineeAccessToken);
        values.put(KEY_USER_ACCESS_SERVER_ID, userAccessServerId);
        Log.d(TAG, "Database obj=-" + db);

        // updating user access table row
        int result = db.update(USER_ACCESS_TABLE, values, KEY_USER_ACCESS_ID + " = " + userAccessId, null);
        return result;
    }

    /**
     * update a Boxer Profile values from the database.
     */
    public int updateBoxerProfile(int version, double chest, double inseam, double reach, String stance,
                                  int userID, double waist, double weight, double height, String leftDevice, String rightDevice,
                                  String gloveType, String skillLevel, int boxerServerId) {

        ContentValues values = new ContentValues();
        values.put(KEY_BOXER_VERSION, version);
        values.put(KEY_BOXER_CHEST, chest);
        values.put(KEY_BOXER_INSEAM, inseam);
        values.put(KEY_BOXER_LEFT_DEVICE, leftDevice);
        values.put(KEY_BOXER_RIGHT_DEVICE, rightDevice);
        values.put(KEY_BOXER_REACH, reach);
        values.put(KEY_BOXER_STANCE, stance);
        values.put(KEY_BOXER_USER_ID, userID);
        values.put(KEY_BOXER_WAIST, waist);
        values.put(KEY_BOXER_WEIGHT, weight);
        values.put(KEY_BOXER_HEIGHT, height);
        values.put(KEY_BOXER_GLOVE_TYPE, gloveType);
        values.put(KEY_BOXER_SKILL_LEVEL, skillLevel);
        values.put(KEY_BOXER_SERVER_ID, boxerServerId);
        int result = (int) db.update(BOXER_PROFILE_TABLE, values, KEY_BOXER_USER_ID + " = " + userID, null);
        return result;
    }

    /**
     * update a Boxer Profile values from the database.
     */
    public int updateBoxerProfile(AuthenticationDTO authenticationDTO) {
        BoxerProfileDTO boxerProfileDTO = authenticationDTO.getBoxerProfile();

        ContentValues values = new ContentValues();
        values.put(KEY_BOXER_VERSION, 1);
        values.put(KEY_BOXER_CHEST, boxerProfileDTO.getChest());
        values.put(KEY_BOXER_INSEAM, boxerProfileDTO.getInseam());
        values.put(KEY_BOXER_LEFT_DEVICE, boxerProfileDTO.getLeftDevice());
        values.put(KEY_BOXER_RIGHT_DEVICE, boxerProfileDTO.getRightDevice());
        values.put(KEY_BOXER_REACH, boxerProfileDTO.getReach());
        values.put(KEY_BOXER_STANCE, boxerProfileDTO.getStance());
        values.put(KEY_BOXER_USER_ID, authenticationDTO.getUser().getId());
        values.put(KEY_BOXER_WAIST, boxerProfileDTO.getWaist());
        values.put(KEY_BOXER_WEIGHT, boxerProfileDTO.getWeight());
        values.put(KEY_BOXER_HEIGHT, boxerProfileDTO.getHeight());
        values.put(KEY_BOXER_GLOVE_TYPE, boxerProfileDTO.getGloveType());
        values.put(KEY_BOXER_SKILL_LEVEL, boxerProfileDTO.getSkillLevel());
        values.put(KEY_BOXER_SERVER_ID, authenticationDTO.getUser().getId());
        int result = (int) db.update(BOXER_PROFILE_TABLE, values, KEY_BOXER_USER_ID + " = " + authenticationDTO.getUser().getId(), null);
        return result;
    }

    public int getServerID(int userId) {
        int serverID;
        int returnValue;

        Cursor cursor = db.query(true, USER_TABLE, new String[]{KEY_SERVER_USER_ID}, KEY_USER_ID + "=" + userId, null, null, null, null, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                serverID = cursor.getInt(0);
                returnValue = serverID;
            } else
                returnValue = -1;


        } finally {
            if (cursor != null)
                cursor.close();
        }
        return returnValue;
    }

    public int getServerSessionID(int sessionId) {
        int serverSessionID;
        int returnValue;

        Cursor cursor = db.query(true, TRAINING_SESSION_TABLE, new String[]{KEY_SERVER_TRAINING_SESSION_ID}, KEY_TRAINING_SESSION_ID + "=" + sessionId, null, null, null, null, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                serverSessionID = cursor.getInt(0);
                returnValue = serverSessionID;
            } else
                returnValue = -1;


        } finally {
            if (cursor != null)
                cursor.close();
        }
        return returnValue;
    }

    public int getServerTrainingDataID(int trainingDataId) {
        int serverTrainingDataID;
        int returnValue;

        Cursor cursor = db.query(true, TRAINING_DATA_TABLE, new String[]{KEY_SERVER_TRAINING_DATA_ID}, KEY_TRAINING_DATA_ID + "=" + trainingDataId, null, null, null, null, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                serverTrainingDataID = cursor.getInt(0);
                returnValue = serverTrainingDataID;
            } else
                returnValue = -1;

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return returnValue;
    }

    public int getServerUserID(int userId) {
        int serverUserId;
        int returnValue;

        Cursor cursor = db.query(true, USER_TABLE, new String[]{KEY_SERVER_USER_ID}, KEY_USER_ID + "=" + userId, null, null, null, null, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                serverUserId = cursor.getInt(0);
                returnValue = serverUserId;
            } else
                returnValue = -1;
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return returnValue;
    }

    public int updateServerIDSync(String tableName, int id, int serverId) {

        Date date = new Date();
        Log.d(TAG, "*****updateSync Date:- " + date);

        String table = null, column = null;

        ContentValues values = new ContentValues();

        if (tableName == "session") {
            values.put(KEY_SERVER_TRAINING_SESSION_ID, serverId);
            values.put(KEY_TRAINING_SESSION_SYNC, 1);
            values.put(KEY_TRAINING_SESSION_SYNC_DATE, dateFormat.format(date));

            table = TRAINING_SESSION_TABLE;
            column = KEY_TRAINING_SESSION_ID;
        } else if (tableName == "TrainingData") {
            values.put(KEY_SERVER_TRAINING_DATA_ID, serverId);
            values.put(KEY_TRAINING_DATA_SYNC, 1);
            values.put(KEY_TRAINING_DATA_SYNC_DATE, dateFormat.format(date));

            table = TRAINING_DATA_TABLE;
            column = KEY_TRAINING_DATA_ID;
        } else if (tableName == "TrainingDataDetails") {
            values.put(KEY_SERVER_TRAINING_DATA_DETAILS_ID, serverId);
            values.put(KEY_TRAINING_DATA_DETAILS_SYNC, 1);
            values.put(KEY_TRAINING_DATA_DETAILS_SYNC_DATE, dateFormat.format(date));

            table = TRAINING_DATA_DETAILS_TABLE;
            column = KEY_TRAINING_DATA_DETAILS_ID;
        } else if (tableName == "TrainingPunchData") {
            values.put(KEY_SERVER_TRAINING_PUNCH_DATA_ID, serverId);
            values.put(KEY_TRAINING_PUNCH_DATA_SYNC, 1);
            values.put(KEY_TRAINING_PUNCH_DATA_SYNC_DATE, dateFormat.format(date));

            table = TRAINING_PUNCH_DATA_TABLE;
            column = KEY_TRAINING_PUNCH_DATA_ID;
        } else if (tableName == "TrainingPunchDataPeakSummary") {
            values.put(KEY_SERVER_TRAINING_PUNCH_DATA_PEAK_SUMMARY_ID, serverId);
            values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC, 1);
            values.put(KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC_DATE, dateFormat.format(date));

            table = TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE;
            column = KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_ID;
        }

        int result = db.update(table, values, column + " = " + id, null);
        return result;
    }

    public long insertCalendarSummaryRecord(CalendarSummaryDTO userData) {
        ContentValues values = new ContentValues();

        values.put(KEY_CALENDAR_TOTAL_TRAINING_TIME, userData.getTotalTrainingTime());
        values.put(KEY_CALENDAR_TOTAL_PUNCHES, userData.getTotalPunches());
        values.put(KEY_CALENDAR_MAX_SPEED, userData.getMaxSpeed());
        values.put(KEY_CALENDAR_MAX_FORCE, userData.getMaxForce());
        values.put(KEY_CALENDAR_DATE, userData.getDate());
        values.put(KEY_CALENDAR_USER_ID, userData.getUserID());

        long result = db.insert(CALENDAR_SUMMARY_TABLE, null, values);
        return result;
    }

    public int updateCalendarSummaryRecord(CalendarSummaryDTO userData) {
        ContentValues values = new ContentValues();
        int success = 0;
        values.put(KEY_CALENDAR_TOTAL_TRAINING_TIME, userData.getTotalTrainingTime());
        values.put(KEY_CALENDAR_TOTAL_PUNCHES, userData.getTotalPunches());
        values.put(KEY_CALENDAR_MAX_SPEED, userData.getMaxSpeed());
        values.put(KEY_CALENDAR_MAX_FORCE, userData.getMaxForce());

        try {
            success = db.update(CALENDAR_SUMMARY_TABLE, values,
                    KEY_CALENDAR_DATE + " = ? and " + KEY_CALENDAR_USER_ID + " = ? ",
                    new String[]{userData.getDate(), "" + userData.getUserID()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<RoundDto> getRoundSummaryData(Date startDate, Date endDate, int userId) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = simpleDateFormat.format(startDate);
        String formattedEndDate = simpleDateFormat.format(endDate);

        String query = "SELECT \n" +
                " TS.id as sessionId," +
                " MAX(TPD.max_force) AS maxForce," +
                " MAX(TPD.max_speed) AS maxSpeed," +
                " AVG(TPD.max_force) AS averageForce," +
                " AVG(TPD.max_speed) AS averageSpeed," +
                " COUNT(TPD.punch_type) AS totalPunch, " +
                " TS.training_session_date as sessionDate, " +
                " SUM((julianday(TS.end_time) - julianday(TS.start_time)) * 1440.0 ) as elapsedSeconds " +
                " FROM training_punch_data AS TPD," +
                " training_data AS TD," +
                " training_session AS TS" +
                " WHERE TPD.training_data_id = TD.id " +
                " AND TS.user_id= " + userId +
                " AND TS.id = TD.training_session_id " +
                " AND sessionDate BETWEEN '" + formattedStartDate + "' AND '" + formattedEndDate + "' " +
                " GROUP BY sessionId, sessionDate";

        Cursor cursor = db.rawQuery(query, null);
        List<RoundDto> rounds = new ArrayList<>();

        try {
            if (cursor.getCount() >= 1) {
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    int sessionId = cursor.getInt(cursor.getColumnIndex("sessionId"));
                    float maxForce = cursor.getInt(cursor.getColumnIndex("maxForce"));
                    float maxSpeed = cursor.getInt(cursor.getColumnIndex("maxSpeed"));
                    float averageForce = cursor.getInt(cursor.getColumnIndex("averageForce"));
                    float averageSpeed = cursor.getInt(cursor.getColumnIndex("averageSpeed"));
                    int totalPunches = cursor.getInt(cursor.getColumnIndex("totalPunch"));
                    int elapsedSeconds = cursor.getInt(cursor.getColumnIndex("elapsedSeconds"));

                    RoundDto round = new RoundDto(userId, sessionId, totalPunches, maxForce, maxSpeed, averageForce, averageSpeed, elapsedSeconds);
                    rounds.add(round);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return rounds;
    }

    public CalendarSummaryDTO getCalendarSummaryData(String fromCurrentSelectedDate, int numberOfPastDays, int userID) {
        // Returns calendar summary for daily, weekly, monthly records for given trainingSessionDate range and user id
        // Returns null otherwise
        // Date format yyyy-mm-dd

        CalendarSummaryDTO calendarDTO = null;

        String query = "SELECT * from calendar_summary where "
                + KEY_CALENDAR_DATE + " between '"
                + CommonUtils.calculatePastDate(fromCurrentSelectedDate, numberOfPastDays, EFDConstants.MEASURE_DATE) + "' and '" + fromCurrentSelectedDate
                + "' and " + KEY_CALENDAR_USER_ID + " ='" + userID + "'";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);

            int totalPunches = 0;
            int maxSpeed = 0;
            int maxForce = 0;
            long totalSeconds = 0;
            double[] speeds = new double[cursor.getCount()];
            double[] forces = new double[cursor.getCount()];

            int rowNumber = 0;
            if (cursor.getCount() > 1) {
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    totalSeconds += CommonUtils.strTimeToSeconds(cursor.getString(1));
                    totalPunches += cursor.getInt(2);
                    int speed = cursor.getInt(3);
                    if (maxSpeed < speed) {
                        maxSpeed = speed;
                    }
                    int force = cursor.getInt(4);
                    if (maxForce < force) {
                        maxForce = force;
                    }
                    speeds[rowNumber] = speed;
                    forces[rowNumber] = force;

                    cursor.moveToNext();
                    rowNumber++;
                }

                float averageForce = (float) StatisticUtil.average(forces);
                float averageSpeed = (float) StatisticUtil.average(speeds);

                calendarDTO = new CalendarSummaryDTO(userID, totalPunches, maxForce, maxSpeed, averageForce, averageSpeed, CommonUtils.secondsToTime(totalSeconds), fromCurrentSelectedDate);
                cursor.close();
            }

            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                int punches = cursor.getInt(2);
                int force = cursor.getInt(4);
                int speed = cursor.getInt(3);
                String trainingTime = cursor.getString(1);
                String date = cursor.getString(5);
                calendarDTO = new CalendarSummaryDTO(userID, punches, force, speed, force, speed, trainingTime, date);
                cursor.close();
            }
        }finally {
            if(cursor != null)
                cursor.close();
        }

        return calendarDTO;
    }

    public boolean isCalendarSummaryRecordExist(String date, int userID) {
        String query = "SELECT " + KEY_CALENDAR_ROW_ID + " from " + CALENDAR_SUMMARY_TABLE + " where "
                + KEY_CALENDAR_DATE + " = '" + date + "' and "
                + KEY_CALENDAR_USER_ID + " ='" + userID + "'";
        Cursor cursor = db.rawQuery(query, null);
        boolean result = false;
        try {
            if (cursor.getCount() > 0)
                result = true;
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return result;
    }

    public long insertResultSummaryRecord(ResultSummaryDTO resultSummaryDTO) {

        ContentValues values = new ContentValues();

        values.put(KEY_RESULT_SUMMARY_DATE, resultSummaryDTO.getDate());
        values.put(KEY_RESULT_SUMMARY_USER_ID, resultSummaryDTO.getUserID());
        values.put(KEY_RESULT_SUMMARY_TYPE, resultSummaryDTO.getSummaryType());

        // LEFT JAB
        values.put(KEY_RESULT_SUMMARY_LJ_SPEED, resultSummaryDTO.getLeftJabSpeed());
        values.put(KEY_RESULT_SUMMARY_LJ_FORCE, resultSummaryDTO.getLeftJabForce());
        values.put(KEY_RESULT_SUMMARY_LJ_TOTAL, resultSummaryDTO.getLeftJabTotal());

        // LEFT STRAIGHT
        values.put(KEY_RESULT_SUMMARY_LS_SPEED, resultSummaryDTO.getLeftStraightSpeed());
        values.put(KEY_RESULT_SUMMARY_LS_FORCE, resultSummaryDTO.getLeftStraightForce());
        values.put(KEY_RESULT_SUMMARY_LS_TOTAL, resultSummaryDTO.getLeftStraightTotal());

        // LEFT HOOK
        values.put(KEY_RESULT_SUMMARY_LH_SPEED, resultSummaryDTO.getLeftHookSpeed());
        values.put(KEY_RESULT_SUMMARY_LH_FORCE, resultSummaryDTO.getLeftHookForce());
        values.put(KEY_RESULT_SUMMARY_LH_TOTAL, resultSummaryDTO.getLeftHookTotal());

        // LEFT UPPERCUT
        values.put(KEY_RESULT_SUMMARY_LU_SPEED, resultSummaryDTO.getLeftUppercutSpeed());
        values.put(KEY_RESULT_SUMMARY_LU_FORCE, resultSummaryDTO.getLeftUppercutForce());
        values.put(KEY_RESULT_SUMMARY_LU_TOTAL, resultSummaryDTO.getLeftUppercutTotal());

        // LEFT UNRECOGNIZED
        values.put(KEY_RESULT_SUMMARY_LR_SPEED, resultSummaryDTO.getLeftUnrecognizedSpeed());
        values.put(KEY_RESULT_SUMMARY_LR_FORCE, resultSummaryDTO.getLeftUnrecognizedForce());
        values.put(KEY_RESULT_SUMMARY_LR_TOTAL, resultSummaryDTO.getLeftUnrecognizedTotal());

        // RIGHT JAB
        values.put(KEY_RESULT_SUMMARY_RJ_SPEED, resultSummaryDTO.getRightJabSpeed());
        values.put(KEY_RESULT_SUMMARY_RJ_FORCE, resultSummaryDTO.getRightJabForce());
        values.put(KEY_RESULT_SUMMARY_RJ_TOTAL, resultSummaryDTO.getRightJabTotal());

        // RIGHT STRAIGHT
        values.put(KEY_RESULT_SUMMARY_RS_SPEED, resultSummaryDTO.getRightStraightSpeed());
        values.put(KEY_RESULT_SUMMARY_RS_FORCE, resultSummaryDTO.getRightStraightForce());
        values.put(KEY_RESULT_SUMMARY_RS_TOTAL, resultSummaryDTO.getRightStraightTotal());

        // RIGHT HOOK
        values.put(KEY_RESULT_SUMMARY_RH_SPEED, resultSummaryDTO.getRightHookSpeed());
        values.put(KEY_RESULT_SUMMARY_RH_FORCE, resultSummaryDTO.getRightHookForce());
        values.put(KEY_RESULT_SUMMARY_RH_TOTAL, resultSummaryDTO.getRightHookTotal());

        // RIGHT UPPERCUT
        values.put(KEY_RESULT_SUMMARY_RU_SPEED, resultSummaryDTO.getRightUppercutSpeed());
        values.put(KEY_RESULT_SUMMARY_RU_FORCE, resultSummaryDTO.getRightUppercutForce());
        values.put(KEY_RESULT_SUMMARY_RU_TOTAL, resultSummaryDTO.getRightUppercutTotal());

        // RIGHT UNRECOGNIZED
        values.put(KEY_RESULT_SUMMARY_RR_SPEED, resultSummaryDTO.getRightUnrecognizedSpeed());
        values.put(KEY_RESULT_SUMMARY_RR_FORCE, resultSummaryDTO.getRightUnrecognizedForce());
        values.put(KEY_RESULT_SUMMARY_RR_TOTAL, resultSummaryDTO.getRightUnrecognizedTotal());

        long result = db.insert(RESULT_SUMMARY_TABLE, null, values);
        return result;
    }

    public int updateResultSummaryRecord(ResultSummaryDTO resultSummaryDTO) {
        ContentValues values = new ContentValues();
        int success = 0;

        values.put(KEY_RESULT_SUMMARY_DATE, resultSummaryDTO.getDate());
        values.put(KEY_RESULT_SUMMARY_USER_ID, resultSummaryDTO.getUserID());
        values.put(KEY_RESULT_SUMMARY_TYPE, resultSummaryDTO.getSummaryType());

        // LEFT JAB
        values.put(KEY_RESULT_SUMMARY_LJ_SPEED, resultSummaryDTO.getLeftJabSpeed());
        values.put(KEY_RESULT_SUMMARY_LJ_FORCE, resultSummaryDTO.getLeftJabForce());
        values.put(KEY_RESULT_SUMMARY_LJ_TOTAL, resultSummaryDTO.getLeftJabTotal());

        // LEFT STRAIGHT
        values.put(KEY_RESULT_SUMMARY_LS_SPEED, resultSummaryDTO.getLeftStraightSpeed());
        values.put(KEY_RESULT_SUMMARY_LS_FORCE, resultSummaryDTO.getLeftStraightForce());
        values.put(KEY_RESULT_SUMMARY_LS_TOTAL, resultSummaryDTO.getLeftStraightTotal());

        // LEFT HOOK
        values.put(KEY_RESULT_SUMMARY_LH_SPEED, resultSummaryDTO.getLeftHookSpeed());
        values.put(KEY_RESULT_SUMMARY_LH_FORCE, resultSummaryDTO.getLeftHookForce());
        values.put(KEY_RESULT_SUMMARY_LH_TOTAL, resultSummaryDTO.getLeftHookTotal());

        // LEFT UPPERCUT
        values.put(KEY_RESULT_SUMMARY_LU_SPEED, resultSummaryDTO.getLeftUppercutSpeed());
        values.put(KEY_RESULT_SUMMARY_LU_FORCE, resultSummaryDTO.getLeftUppercutForce());
        values.put(KEY_RESULT_SUMMARY_LU_TOTAL, resultSummaryDTO.getLeftUppercutTotal());

        // LEFT UNRECOGNIZED
        values.put(KEY_RESULT_SUMMARY_LR_SPEED, resultSummaryDTO.getLeftUnrecognizedSpeed());
        values.put(KEY_RESULT_SUMMARY_LR_FORCE, resultSummaryDTO.getLeftUnrecognizedForce());
        values.put(KEY_RESULT_SUMMARY_LR_TOTAL, resultSummaryDTO.getLeftUnrecognizedTotal());

        // RIGHT JAB
        values.put(KEY_RESULT_SUMMARY_RJ_SPEED, resultSummaryDTO.getRightJabSpeed());
        values.put(KEY_RESULT_SUMMARY_RJ_FORCE, resultSummaryDTO.getRightJabForce());
        values.put(KEY_RESULT_SUMMARY_RJ_TOTAL, resultSummaryDTO.getRightJabTotal());

        // RIGHT STRAIGHT
        values.put(KEY_RESULT_SUMMARY_RS_SPEED, resultSummaryDTO.getRightStraightSpeed());
        values.put(KEY_RESULT_SUMMARY_RS_FORCE, resultSummaryDTO.getRightStraightForce());
        values.put(KEY_RESULT_SUMMARY_RS_TOTAL, resultSummaryDTO.getRightStraightTotal());

        // RIGHT HOOK
        values.put(KEY_RESULT_SUMMARY_RH_SPEED, resultSummaryDTO.getRightHookSpeed());
        values.put(KEY_RESULT_SUMMARY_RH_FORCE, resultSummaryDTO.getRightHookForce());
        values.put(KEY_RESULT_SUMMARY_RH_TOTAL, resultSummaryDTO.getRightHookTotal());

        // RIGHT UPPERCUT
        values.put(KEY_RESULT_SUMMARY_RU_SPEED, resultSummaryDTO.getRightUppercutSpeed());
        values.put(KEY_RESULT_SUMMARY_RU_FORCE, resultSummaryDTO.getRightUppercutForce());
        values.put(KEY_RESULT_SUMMARY_RU_TOTAL, resultSummaryDTO.getRightUppercutTotal());

        // RIGHT UNRECOGNIZED
        values.put(KEY_RESULT_SUMMARY_RR_SPEED, resultSummaryDTO.getRightUnrecognizedSpeed());
        values.put(KEY_RESULT_SUMMARY_RR_FORCE, resultSummaryDTO.getRightUnrecognizedForce());
        values.put(KEY_RESULT_SUMMARY_RR_TOTAL, resultSummaryDTO.getRightUnrecognizedTotal());

        try {
            success = db.update(RESULT_SUMMARY_TABLE, values,
                    KEY_RESULT_SUMMARY_USER_ID + " = ? and " +
                            KEY_RESULT_SUMMARY_TYPE + " = ?",
                    new String[]{"" + resultSummaryDTO.getUserID(), resultSummaryDTO.getSummaryType()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public HashMap<String, ResultSummaryDTO> getResultSummaryData(int userID) {
        HashMap<String, ResultSummaryDTO> resultSummaryHashMap = new HashMap<String, ResultSummaryDTO>();

        String query = "SELECT * from " + RESULT_SUMMARY_TABLE + " where "
                + KEY_RESULT_SUMMARY_USER_ID + " ='" + userID + "';";

        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                while (cursor.isAfterLast() == false) {
                    ResultSummaryDTO resultSummaryDTO = new ResultSummaryDTO(cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                            // LEFT JAB - speed, force, total
                            cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6),
                            // LEFT STRAIGHT
                            cursor.getDouble(7), cursor.getDouble(8), cursor.getDouble(9),
                            // LEFT HOOK
                            cursor.getDouble(10), cursor.getDouble(11), cursor.getDouble(12),
                            // LEFT UPPERCUT
                            cursor.getDouble(13), cursor.getDouble(14), cursor.getDouble(15),
                            // LEFT UNRECOGNIZED
                            cursor.getDouble(16), cursor.getDouble(17), cursor.getDouble(18),
                            // RIGHT JAB
                            cursor.getDouble(19), cursor.getDouble(20), cursor.getDouble(21),
                            // RIGHT STRAIGHT
                            cursor.getDouble(22), cursor.getDouble(23), cursor.getDouble(24),
                            // RIGHT HOOK
                            cursor.getDouble(25), cursor.getDouble(26), cursor.getDouble(27),
                            // RIGHT UPPERCUT
                            cursor.getDouble(28), cursor.getDouble(29), cursor.getDouble(30),
                            // RIGHT UNRECOGNIZED
                            cursor.getDouble(31), cursor.getDouble(32), cursor.getDouble(33)
                    );

                    resultSummaryHashMap.put(resultSummaryDTO.getSummaryType(), resultSummaryDTO);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return resultSummaryHashMap;
    }

    public boolean isResultSummaryRecordExist(int userID, String summaryType) {
        String query = "SELECT * from " + RESULT_SUMMARY_TABLE + " where "
                + KEY_RESULT_SUMMARY_USER_ID + " ='" + userID + "' and "
                + KEY_RESULT_SUMMARY_TYPE + " ='" + summaryType + "';";
        Cursor cursor = db.rawQuery(query, null);

        boolean result = false;

        try {
            if (cursor.moveToFirst())
                result = true;
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return result;
    }

    public long insertProgressSummaryRecord(ProgressSummaryDTO progressSummaryDTO) {
        ContentValues values = new ContentValues();

        values.put(KEY_PROGRESS_SUMMARY_USER_ID, progressSummaryDTO.getUserID());
        values.put(KEY_PROGRESS_SUMMARY_DATE, progressSummaryDTO.getDate());
        values.put(KEY_PROGRESS_SUMMARY_TYPE, progressSummaryDTO.getSummaryType());

        //LEFT JAB
        values.put(KEY_PROGRESS_SUMMARY_LJ_SPEED, progressSummaryDTO.getLeftJabSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LJ_FORCE, progressSummaryDTO.getLeftJabForce());

        //LEFT STRAIGHT
        values.put(KEY_PROGRESS_SUMMARY_LS_SPEED, progressSummaryDTO.getLeftStraightSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LS_FORCE, progressSummaryDTO.getLeftStraightForce());

        //LEFT HOOK
        values.put(KEY_PROGRESS_SUMMARY_LH_SPEED, progressSummaryDTO.getLeftHookSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LH_FORCE, progressSummaryDTO.getLeftHookForce());

        //LEFT UPPERCUT
        values.put(KEY_PROGRESS_SUMMARY_LU_SPEED, progressSummaryDTO.getLeftUppercutSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LU_FORCE, progressSummaryDTO.getLeftUppercutForce());

        //LEFT UNRECOGNIZED
        values.put(KEY_PROGRESS_SUMMARY_LR_SPEED, progressSummaryDTO.getLeftUnrecognizedSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LR_FORCE, progressSummaryDTO.getLeftUnrecognizedForce());

        //RIGHT JAB
        values.put(KEY_PROGRESS_SUMMARY_RJ_SPEED, progressSummaryDTO.getRightJabSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RJ_FORCE, progressSummaryDTO.getRightJabForce());

        //RIGHT STRAIGHT
        values.put(KEY_PROGRESS_SUMMARY_RS_SPEED, progressSummaryDTO.getRightStraightSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RS_FORCE, progressSummaryDTO.getRightStraightForce());

        //RIGHT HOOK
        values.put(KEY_PROGRESS_SUMMARY_RH_SPEED, progressSummaryDTO.getRightHookSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RH_FORCE, progressSummaryDTO.getRightHookForce());

        //RIGHT UPPERCUT
        values.put(KEY_PROGRESS_SUMMARY_RU_SPEED, progressSummaryDTO.getRightUppercutSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RU_FORCE, progressSummaryDTO.getRightUppercutForce());

        //RIGHT UNRECOGNIZED
        values.put(KEY_PROGRESS_SUMMARY_RR_SPEED, progressSummaryDTO.getRightUnrecognizedSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RR_FORCE, progressSummaryDTO.getRightUnrecognizedForce());

        long result = db.insert(PROGRESS_SUMMARY_TABLE, null, values);
        return result;
    }

    public int updateProgressSummaryRecord(ProgressSummaryDTO progressSummaryDTO) {
        ContentValues values = new ContentValues();
        int success = 0;

        values.put(KEY_PROGRESS_SUMMARY_DATE, progressSummaryDTO.getDate());

        //LEFT JAB
        values.put(KEY_PROGRESS_SUMMARY_LJ_SPEED, progressSummaryDTO.getLeftJabSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LJ_FORCE, progressSummaryDTO.getLeftJabForce());

        //LEFT STRAIGHT
        values.put(KEY_PROGRESS_SUMMARY_LS_SPEED, progressSummaryDTO.getLeftStraightSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LS_FORCE, progressSummaryDTO.getLeftStraightForce());

        //LEFT HOOK
        values.put(KEY_PROGRESS_SUMMARY_LH_SPEED, progressSummaryDTO.getLeftHookSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LH_FORCE, progressSummaryDTO.getLeftHookForce());

        //LEFT UPPERCUT
        values.put(KEY_PROGRESS_SUMMARY_LU_SPEED, progressSummaryDTO.getLeftUppercutSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LU_FORCE, progressSummaryDTO.getLeftUppercutForce());

        //LEFT UNRECOGNIZED
        values.put(KEY_PROGRESS_SUMMARY_LR_SPEED, progressSummaryDTO.getLeftUnrecognizedSpeed());
        values.put(KEY_PROGRESS_SUMMARY_LR_FORCE, progressSummaryDTO.getLeftUnrecognizedForce());

        //RIGHT JAB
        values.put(KEY_PROGRESS_SUMMARY_RJ_SPEED, progressSummaryDTO.getRightJabSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RJ_FORCE, progressSummaryDTO.getRightJabForce());

        //RIGHT STRAIGHT
        values.put(KEY_PROGRESS_SUMMARY_RS_SPEED, progressSummaryDTO.getRightStraightSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RS_FORCE, progressSummaryDTO.getRightStraightForce());

        //RIGHT HOOK
        values.put(KEY_PROGRESS_SUMMARY_RH_SPEED, progressSummaryDTO.getRightHookSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RH_FORCE, progressSummaryDTO.getRightHookForce());

        //RIGHT UPPERCUT
        values.put(KEY_PROGRESS_SUMMARY_RU_SPEED, progressSummaryDTO.getRightUppercutSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RU_FORCE, progressSummaryDTO.getRightUppercutForce());

        //RIGHT UNRECOGNIZED
        values.put(KEY_PROGRESS_SUMMARY_RR_SPEED, progressSummaryDTO.getRightUnrecognizedSpeed());
        values.put(KEY_PROGRESS_SUMMARY_RR_FORCE, progressSummaryDTO.getRightUnrecognizedForce());

        try {
            success = db.update(PROGRESS_SUMMARY_TABLE, values,
                    KEY_PROGRESS_SUMMARY_USER_ID + " = ? and " +
                            KEY_PROGRESS_SUMMARY_TYPE + " = ?",
                    new String[]{"" + progressSummaryDTO.getUserID(), progressSummaryDTO.getSummaryType()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public Map<String, ProgressSummaryDTO> getProgressSummaryData(int userID) {
        Map<String, ProgressSummaryDTO> progressSummaryHashMap = new HashMap<String, ProgressSummaryDTO>();
        ProgressSummaryDTO progressSummaryDTO = null;

        String query = "SELECT * from " + PROGRESS_SUMMARY_TABLE + " where "
                + KEY_PROGRESS_SUMMARY_USER_ID + " ='" + userID + "';";// and "
        //+ KEY_PROGRESS_SUMMARY_TYPE + " ='" + summaryType + "';";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    progressSummaryDTO = new ProgressSummaryDTO(
                            userID, cursor.getString(2), cursor.getString(3),
                            cursor.getDouble(4), cursor.getDouble(5),
                            cursor.getDouble(6), cursor.getDouble(7),
                            cursor.getDouble(8), cursor.getDouble(9),
                            cursor.getDouble(10), cursor.getDouble(11),
                            cursor.getDouble(12), cursor.getDouble(13),
                            cursor.getDouble(14), cursor.getDouble(15),
                            cursor.getDouble(16), cursor.getDouble(17),
                            cursor.getDouble(18), cursor.getDouble(19),
                            cursor.getDouble(20), cursor.getDouble(21),
                            cursor.getDouble(22), cursor.getDouble(23));

                    progressSummaryHashMap.put(progressSummaryDTO.getSummaryType(), progressSummaryDTO);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return progressSummaryHashMap;
    }

    public boolean isProgressSummaryRecordExist(String date, int userID, String summaryType) {
        String query = "SELECT * from " + PROGRESS_SUMMARY_TABLE + " where "
                //+ KEY_PROGRESS_SUMMARY_DATE + " = '" + trainingSessionDate + "' and "
                + KEY_PROGRESS_SUMMARY_USER_ID + " ='" + userID + "' and "
                + KEY_PROGRESS_SUMMARY_TYPE + " ='" + summaryType + "';";
        Cursor cursor = db.rawQuery(query, null);

        boolean result = false;

        try {
            if (cursor.getCount() > 0)
                result = true;
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return result;
    }

    public Integer isPunchCountSummaryRecordExist(String userID) {
        Integer id = null;
        String query = "SELECT " + KEY_PUNCH_COUNT_SUMMARY_ROW_ID + " from " + PUNCH_COUNT_SUMMARY_TABLE + " where "
                + KEY_PUNCH_COUNT_SUMMARY_USER_ID + " ='" + userID + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }

    public long insertPunchCountRecord(PunchCountSummaryDTO userData) {
        ContentValues values = new ContentValues();

        values.put(KEY_PUNCH_COUNT_SUMMARY_USER_ID, userData.getUserID());
        values.put(KEY_PUNCH_COUNT_SUMMARY_DATE, userData.getDate());
        values.put(KEY_PUNCH_COUNT_SUMMARY_TYPE, userData.getSummaryType());
        values.put(KEY_PUNCH_COUNT_TOTAL, userData.getTotalPunchCount());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LJ_AVG, userData.getLeftJabAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LJ_TODAY, userData.getLeftJabToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LS_AVG, userData.getLeftStraightAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LS_TODAY, userData.getLeftStraightToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LH_AVG, userData.getLeftHookAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LH_TODAY, userData.getLeftHookToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LU_AVG, userData.getLeftUppercutAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LU_TODAY, userData.getLeftUppercutToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LR_AVG, userData.getLeftUnrecognizedAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LR_TODAY, userData.getLeftUnrecognizedToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RJ_AVG, userData.getRightJabAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RJ_TODAY, userData.getRightJabToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RS_AVG, userData.getRightStraightAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RS_TODAY, userData.getRightStraightToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RH_AVG, userData.getRightHookAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RH_TODAY, userData.getRightHookToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RU_AVG, userData.getRightUppercutAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RU_TODAY, userData.getRightUppercutToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RR_AVG, userData.getRightUnrecognizedAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RR_TODAY, userData.getRightUnrecognizedToday());

        long result = db.insert(PUNCH_COUNT_SUMMARY_TABLE, null, values);
        return result;
    }

    public int updatePunchCountRecord(PunchCountSummaryDTO userData) {
        ContentValues values = new ContentValues();
        int success = 0;

        values.put(KEY_PUNCH_COUNT_SUMMARY_USER_ID, userData.getUserID());
        values.put(KEY_PUNCH_COUNT_SUMMARY_DATE, userData.getDate());
        values.put(KEY_PUNCH_COUNT_SUMMARY_TYPE, userData.getSummaryType());
        values.put(KEY_PUNCH_COUNT_TOTAL, userData.getTotalPunchCount());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LJ_AVG, userData.getLeftJabAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LJ_TODAY, userData.getLeftJabToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LS_AVG, userData.getLeftStraightAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LS_TODAY, userData.getLeftStraightToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LH_AVG, userData.getLeftHookAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LH_TODAY, userData.getLeftHookToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LU_AVG, userData.getLeftUppercutAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LU_TODAY, userData.getLeftUppercutToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LR_AVG, userData.getLeftUnrecognizedAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_LR_TODAY, userData.getLeftUnrecognizedToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RJ_AVG, userData.getRightJabAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RJ_TODAY, userData.getRightJabToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RS_AVG, userData.getRightStraightAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RS_TODAY, userData.getRightStraightToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RH_AVG, userData.getRightHookAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RH_TODAY, userData.getRightHookToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RU_AVG, userData.getRightUppercutAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RU_TODAY, userData.getRightUppercutToday());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RR_AVG, userData.getRightUnrecognizedAverage());
        values.put(KEY_PUNCH_COUNT_SUMMARY_RR_TODAY, userData.getRightUnrecognizedToday());

        try {
            success = db.update(PUNCH_COUNT_SUMMARY_TABLE, values,
                    KEY_PUNCH_COUNT_SUMMARY_ROW_ID + " = ?",
                    new String[]{String.valueOf(userData.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Delete completely synced training session records for no. of back days specified
     *
     * @param nDaysBack No. of days back starting from today
     * @return Count of training session records deleted
     */
    public int deleteCompletelySyncedTrainingSessionRecords(int nDaysBack) {

        String date;
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, -nDaysBack);
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime());
        }

        String query = "SELECT " + KEY_TRAINING_SESSION_ID + " FROM " + TRAINING_SESSION_TABLE
                + " WHERE " + KEY_TRAINING_SESSION_SYNC + "=1 AND " + KEY_TRAINING_SESSION_TRAINING_SESSION_DATE
                + "<='" + date + "';";
        Cursor syncedTrainingSessionCur = db.rawQuery(query, null);

        int rec = 0;

        if (syncedTrainingSessionCur.getCount() > 0) {

            syncedTrainingSessionCur.moveToFirst();
            int syncedTrainingSessionId;
            int td, tdd, tpd, tpdps;
            int[] trainingDataId;
            String[] values = new String[1];
            do {

                syncedTrainingSessionId = syncedTrainingSessionCur.getInt(0);
                trainingDataId = isTrainingDataSynced(syncedTrainingSessionId);

                // check for synced records in training_data
                if (null != trainingDataId) {
                    // check for synced records in training_data_details
                    if (isTableSyncedForTrainingData(TRAINING_DATA_DETAILS_TABLE,
                            KEY_TRAINING_DATA_DETAILS_SYNC, KEY_TRAINING_DATA_DETAILS_TRAINING_DATA_ID, trainingDataId)) {
                        // check for synced records in training_punch_data
                        if (isTableSyncedForTrainingData(TRAINING_PUNCH_DATA_TABLE,
                                KEY_TRAINING_PUNCH_DATA_SYNC, KEY_TRAINING_PUNCH_DATA_TRAINING_DATA_ID, trainingDataId)) {
                            // check for synced records in training_punch_data_peak_summary
                            if (isTableSyncedForTrainingData(TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE,
                                    KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC, KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_TRAINING_DATA_ID, trainingDataId)) {

                                values[0] = String.valueOf(trainingDataId[0]) + ", " + String.valueOf(trainingDataId[1]);
                                db.beginTransaction();
                                try {
                                    // delete from training_punch_data_peak_summary table
                                    tpdps = db.delete(TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE, KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_TRAINING_DATA_ID + " IN (?)", values);

                                    // delete from training_punch_data table
                                    tpd = db.delete(TRAINING_PUNCH_DATA_TABLE, KEY_TRAINING_PUNCH_DATA_TRAINING_DATA_ID + " IN (?)", values);

                                    // delete from training_data_details table
                                    tdd = db.delete(TRAINING_DATA_DETAILS_TABLE, KEY_TRAINING_DATA_DETAILS_TRAINING_DATA_ID + " IN (?)", values);

                                    // delete from training_data table
                                    values[0] = String.valueOf(syncedTrainingSessionId);
                                    td = db.delete(TRAINING_DATA_TABLE, KEY_TRAINING_DATA_TRAINING_SESSION_ID + " IN (?)", values);

                                    // delete from training_session
                                    values[0] = String.valueOf(syncedTrainingSessionId);
                                    rec += db.delete(TRAINING_SESSION_TABLE, KEY_TRAINING_SESSION_ID + "=?", values);
                                    db.setTransactionSuccessful();

                                } catch (Exception e) {
                                    Log.i(TAG, "Delete Transaction failed!");
                                    e.printStackTrace();
                                } finally {
                                    db.endTransaction();
                                }
                            }
                        }
                    }
                }
            } while (syncedTrainingSessionCur.moveToNext());
            if (rec > 0) {
                Log.i(TAG, "Deleted " + rec + " records from " + TRAINING_SESSION_TABLE);
            }
        }
        return rec;
    }

    /**
     * Checks if the TrainingData table is synced for a particular trainingSession
     *
     * @param trainingSessionId
     * @return
     */
    private int[] isTrainingDataSynced(int trainingSessionId) {

        int[] result = null;
        String query = "SELECT " + KEY_TRAINING_DATA_ID + " FROM " + TRAINING_DATA_TABLE
                + " WHERE " + KEY_TRAINING_DATA_TRAINING_SESSION_ID + "=" + trainingSessionId + " AND " + KEY_TRAINING_DATA_SYNC + "=1;";
        Cursor sTrainingDataCur = db.rawQuery(query, null);
        if (2 == sTrainingDataCur.getCount()) {    // 2 for left + right hand
            sTrainingDataCur.moveToFirst();
            result = new int[2];
            result[0] = sTrainingDataCur.getInt(0);
            sTrainingDataCur.moveToNext();
            result[1] = sTrainingDataCur.getInt(0);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Checks for the count(*) of records where sync flag is 0
     *
     * @param TABLE_NAME     Name of the table to be checked for sync
     * @param COL_SYNC       Name of the column where to check the sync flag
     * @param COL_ID         Name of the column which contains the foreign key of trainingDataId
     * @param trainingDataId Array containing 2 trainingDataIds for left & right hand respectively.
     * @return
     */
    private boolean isTableSyncedForTrainingData(final String TABLE_NAME, final String COL_SYNC, final String COL_ID, int[] trainingDataId) {

        boolean result = false;

        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COL_SYNC + "=0 AND "
                + COL_ID + " IN (" + trainingDataId[0] + ", " + trainingDataId[1] + ");";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public List<DBTrainingSessionDTO> getAllNonSynchronizedTrainingSessionRecords(int limit) {

        DBTrainingSessionDTO dbTrainingSessionDTO = null;
        List<DBTrainingSessionDTO> list = new ArrayList<DBTrainingSessionDTO>();

        String query = "SELECT "
                + TRAINING_SESSION_TABLE + "." + KEY_TRAINING_SESSION_ID + " , "
                + KEY_TRAINING_SESSION_START_TIME + " , "
                + KEY_TRAINING_SESSION_END_TIME + " , "
                + KEY_TRAINING_SESSION_TRAINING_SESSION_DATE + " , "
                + KEY_TRAINING_SESSION_TRAINING_TYPE + " , "
                + KEY_TRAINING_SESSION_USER_ID + " , "
                + TRAINING_SESSION_TABLE + "." + KEY_SERVER_TRAINING_SESSION_ID + " , "
                + USER_TABLE + "." + KEY_SERVER_USER_ID
                + " from " + TRAINING_SESSION_TABLE
                + " join " + USER_TABLE
                + " on " + TRAINING_SESSION_TABLE + "." + KEY_TRAINING_SESSION_USER_ID + " = " + USER_TABLE + "." + KEY_USER_ID
                + " where " + TRAINING_SESSION_TABLE + "." + KEY_TRAINING_SESSION_SYNC + " ='" + 0 + "' "
                + "limit " + limit + ";";

        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {
                    dbTrainingSessionDTO = new DBTrainingSessionDTO(
                            cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getInt(7),
                            null, null, cursor.getLong(6));

                    list.add(dbTrainingSessionDTO);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingDataDTO> getAllNonSynchronizedTrainingDataRecords(int limit) {

        DBTrainingDataDTO dbTrainingDataDTO = null;
        List<DBTrainingDataDTO> list = new ArrayList<DBTrainingDataDTO>();

        String query = "SELECT "
                + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_ID + " , "
                + KEY_TRAINING_DATA_LEFT_HAND + " , "
                + TRAINING_SESSION_TABLE + "." + KEY_SERVER_TRAINING_SESSION_ID + " , "
                + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_USER_ID + " , "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID + " , "
                + USER_TABLE + "." + KEY_SERVER_USER_ID
                + " from " + TRAINING_DATA_TABLE
                + " join " + TRAINING_SESSION_TABLE
                + " on " + TRAINING_SESSION_TABLE + "." + KEY_TRAINING_SESSION_ID + " = " + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_TRAINING_SESSION_ID
                + " join " + USER_TABLE
                + " on " + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_USER_ID + " = " + USER_TABLE + "." + KEY_USER_ID
                + " where " + TRAINING_DATA_TABLE + "." + KEY_TRAINING_SESSION_SYNC + " ='" + 0 + "' and "
                + TRAINING_SESSION_TABLE + "." + KEY_SERVER_TRAINING_SESSION_ID + " != '' "
                + "limit " + limit + ";";

        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {

                while (cursor.isAfterLast() == false) {
                    dbTrainingDataDTO = new DBTrainingDataDTO(
                            cursor.getInt(0), cursor.getInt(1),
                            cursor.getInt(2), cursor.getInt(5),
                            0, CommonUtils.getCurrentDateStringYMD(), cursor.getLong(4));

                    list.add(dbTrainingDataDTO);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingDataDetailsDTO> getAllNonSynchronizedTrainingDataDetailsRecords(int limit) {

        DBTrainingDataDetailsDTO dbTrainingDataDetailsDTO = null;
        List<DBTrainingDataDetailsDTO> list = new ArrayList<DBTrainingDataDetailsDTO>();

        String query = "SELECT "
                + TRAINING_DATA_DETAILS_TABLE + "." + KEY_TRAINING_DATA_DETAILS_ID + " , "
                + KEY_TRAINING_DATA_DETAILS_AX + " , "
                + KEY_TRAINING_DATA_DETAILS_AY + " , "
                + KEY_TRAINING_DATA_DETAILS_AZ + " , "
                + KEY_TRAINING_DATA_DETAILS_CURRENT_FORCE + " , "
                + KEY_TRAINING_DATA_DETAILS_CURRENT_VELOCITY + " , "
                + KEY_TRAINING_DATA_DETAILS_DATA_RECIEVED_TIME + " , "
                + KEY_TRAINING_DATA_DETAILS_GX + " , "
                + KEY_TRAINING_DATA_DETAILS_GY + " , "
                + KEY_TRAINING_DATA_DETAILS_GZ + " , "
                + KEY_TRAINING_DATA_DETAILS_HEAD_TRAUMA + " , "
                + KEY_TRAINING_DATA_DETAILS_MILLI_SECOND + " , "
                + KEY_TRAINING_DATA_DETAILS_TEMP + " , "
                + TRAINING_DATA_DETAILS_TABLE + "." + KEY_TRAINING_DATA_DETAILS_TRAINING_DATA_ID + " , "
                + TRAINING_DATA_DETAILS_TABLE + "." + KEY_SERVER_TRAINING_DATA_DETAILS_ID + " , "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID
                + " from " + TRAINING_DATA_DETAILS_TABLE
                + " join " + TRAINING_DATA_TABLE
                + " on " + TRAINING_DATA_DETAILS_TABLE + "." + KEY_TRAINING_DATA_DETAILS_TRAINING_DATA_ID + " = " + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_ID
                + " where " + TRAINING_DATA_DETAILS_TABLE + "." + KEY_TRAINING_DATA_DETAILS_SYNC + " ='" + 0 + "' and "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID + " != '' "
                + "limit " + limit + ";";

        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {

                while (cursor.isAfterLast() == false) {
                    dbTrainingDataDetailsDTO = new DBTrainingDataDetailsDTO(
                            cursor.getInt(0),
                            cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
                            cursor.getDouble(4), cursor.getDouble(5), cursor.getString(6),
                            cursor.getInt(7), cursor.getInt(8), cursor.getInt(9),
                            cursor.getDouble(10), cursor.getDouble(11), cursor.getInt(12), cursor.getInt(15),
                            0, CommonUtils.getCurrentDateStringYMD(), cursor.getLong(14));

                    list.add(dbTrainingDataDetailsDTO);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingPunchDataDTO> getAllNonSynchronizedTrainingPunchDataRecords(int limit) {

        DBTrainingPunchDataDTO dbTrainingPunchDataDTO = null;
        List<DBTrainingPunchDataDTO> list = new ArrayList<DBTrainingPunchDataDTO>();

        String query = "SELECT "
                + TRAINING_PUNCH_DATA_TABLE + "." + KEY_TRAINING_PUNCH_DATA_ID + " , "
                + KEY_TRAINING_PUNCH_DATA_MAX_FORCE + " , "
                + KEY_TRAINING_PUNCH_DATA_MAX_SPEED + " , "
                + KEY_TRAINING_PUNCH_DATA_PUNCH_DATA_DATE + " , "
                + KEY_TRAINING_PUNCH_DATA_PUNCH_TYPE + " , "
                + TRAINING_PUNCH_DATA_TABLE + "." + KEY_TRAINING_PUNCH_DATA_TRAINING_DATA_ID + " , "
                + TRAINING_PUNCH_DATA_TABLE + "." + KEY_SERVER_TRAINING_PUNCH_DATA_ID + " , "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID
                + " from " + TRAINING_PUNCH_DATA_TABLE
                + " join " + TRAINING_DATA_TABLE
                + " on " + TRAINING_PUNCH_DATA_TABLE + "." + KEY_TRAINING_PUNCH_DATA_TRAINING_DATA_ID + " = " + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_ID
                + " where " + TRAINING_PUNCH_DATA_TABLE + "." + KEY_TRAINING_PUNCH_DATA_SYNC + " ='" + 0 + "' and "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID + " != '' "
                + "limit " + limit + ";";

        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {

                while (cursor.isAfterLast() == false) {
                    dbTrainingPunchDataDTO = new DBTrainingPunchDataDTO(
                            cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2),
                            cursor.getString(3), cursor.getString(4), cursor.getInt(7),
                            0, CommonUtils.getCurrentDateStringYMD(), cursor.getLong(6));

                    list.add(dbTrainingPunchDataDTO);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingPunchDataPeakSummaryDTO> getAllNonSynchronizedTrainingPunchDataPeakSummaryRecords(int limit) {

        DBTrainingPunchDataPeakSummaryDTO dbTrainingPunchDataPeakSummary = null;
        List<DBTrainingPunchDataPeakSummaryDTO> list = new ArrayList<DBTrainingPunchDataPeakSummaryDTO>();

        String query = "SELECT "
                + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE + "." + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_ID + " , "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_HOOK + " , "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_JAB + " , "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_PUNCH_DATA_PEAK_SUMMARY_DATE + " , "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SPEED_FLAG + " , "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_STRAIGHT + " , "
                + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE + "." + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_TRAINING_DATA_ID + " , "
                + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_UPPER_CUT + " , "
                + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE + "." + KEY_SERVER_TRAINING_PUNCH_DATA_PEAK_SUMMARY_ID + " , "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID
                + " from " + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE
                + " join " + TRAINING_DATA_TABLE
                + " on " + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE + "." + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_TRAINING_DATA_ID + " = " + TRAINING_DATA_TABLE + "." + KEY_TRAINING_DATA_ID
                + " where " + TRAINING_PUNCH_DATA_PEAK_SUMMARY_TABLE + "." + KEY_TRAINING_PUNCH_DATA_PEAK_SUMMARY_SYNC + " ='" + 0 + "' and "
                + TRAINING_DATA_TABLE + "." + KEY_SERVER_TRAINING_DATA_ID + " != '' "
                + "limit " + limit + ";";

        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {

                while (cursor.isAfterLast() == false) {
                    dbTrainingPunchDataPeakSummary = new DBTrainingPunchDataPeakSummaryDTO(
                            cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                            cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(9), cursor.getInt(7),
                            0, CommonUtils.getCurrentDateStringYMD(), cursor.getLong(8));

                    list.add(dbTrainingPunchDataPeakSummary);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public int updateSynchronizedRecordsForTable(String tableName, ArrayList<SyncResponseDTO> dtos) {

        ContentValues values = new ContentValues();
        int success = 0;

        for (int i = 0; i < dtos.size(); i++) {
            values.put("server_id", dtos.get(i).getServerID());
            values.put("sync", "1");
            values.put("sync_date", CommonUtils.getCurrentDateStringYMD());

            try {
                success = db.update(tableName, values, "id = ? ", new String[]{dtos.get(i).getId()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public Map<String, String> getNonSyncedUserInformation() {
        Map<String, String> map = null;

        final String query = "SELECT "
                + USER_TABLE + "." + KEY_SERVER_USER_ID + ", "
                + USER_TABLE + "." + KEY_USER_BOXERFIRSTNAME + ", "
                + USER_TABLE + "." + KEY_USER_BOXERLASTNAME + ", "
                + USER_TABLE + "." + KEY_USER_DATE_OF_BIRTH + ", "
                + USER_TABLE + "." + KEY_USER_GENDER + ", "
                + USER_TABLE + "." + KEY_USER_EMAIL_ID + ", "
                + BOXER_PROFILE_TABLE + "." + KEY_BOXER_REACH + ", "
                + BOXER_PROFILE_TABLE + "." + KEY_BOXER_STANCE + ", "
                + BOXER_PROFILE_TABLE + "." + KEY_BOXER_HEIGHT + ", "
                + BOXER_PROFILE_TABLE + "." + KEY_BOXER_WEIGHT + ", "
                + BOXER_PROFILE_TABLE + "." + KEY_BOXER_GLOVE_TYPE + ", "
                + BOXER_PROFILE_TABLE + "." + KEY_BOXER_SKILL_LEVEL
                //+ USER_TABLE + "." + KEY_USER_PASSWORD + ", "
                //+ USER_TABLE + "." + KEY_USER_ZIP_CODE + ", "
                //+ BOXER_PROFILE_TABLE + "." + KEY_BOXER_CHEST + ", "
                //+ BOXER_PROFILE_TABLE + "." + KEY_BOXER_INSEAM + ", "
                //+ BOXER_PROFILE_TABLE + "." + KEY_BOXER_LEFT_DEVICE + ", "
                //+ BOXER_PROFILE_TABLE + "." + KEY_BOXER_RIGHT_DEVICE + ", "
                + " FROM " + USER_TABLE
                + " INNER JOIN " + BOXER_PROFILE_TABLE
                + " ON ( " + BOXER_PROFILE_TABLE + "." + KEY_BOXER_USER_ID + " = " + USER_TABLE + "." + KEY_USER_ID + " ) "
                + " WHERE " + USER_TABLE + "." + KEY_USER_SYNC + " = " + EFDConstants.SYNC_FALSE + ";";

        Cursor cursor = db.rawQuery(query, null);
        map = new HashMap<String, String>();

        try {
            if (cursor.moveToFirst()) {
                map.put("userId", cursor.getString(0));
                map.put("firstName", cursor.getString(1));
                map.put("lastName", cursor.getString(2));
                map.put("dateOfBirth", cursor.getString(3));
                map.put("gender", cursor.getString(4));
                map.put("emailId", cursor.getString(5));
                map.put("reach", cursor.getString(6));
                map.put("stance", cursor.getString(7));
                map.put("height", cursor.getString(8));
                map.put("weight", cursor.getString(9));
                map.put("gloveType", cursor.getString(10));
                map.put("skillLevel", cursor.getString(11));
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return map;
    }

    public int setSyncFlag(int id, String tableName, int sync) {
        ContentValues values = new ContentValues();
        int success = 0;
        values.put("sync", sync);
        try {
            success = db.update(tableName, values,
                    "server_id" + " = ?",
                    new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * deleteCalendarSummaryBeforeDate
     *
     * @param date before which calendar summary data should be deleted.
     * @return
     */
    public int deleteCalendarSummaryBeforeDate(String date) {
        Log.d(TAG, "inside deleteCalendarOnDate date :" + date);
        int deleteResponse = db.delete(CALENDAR_SUMMARY_TABLE, KEY_CALENDAR_DATE + " < ?", new String[]{date});
        Log.d(TAG, "number of records deleted :" + deleteResponse);
        return deleteResponse;
    }
}