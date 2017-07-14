package com.efd.striketectablet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efd.striketectablet.DTO.CalendarSummaryDTO;
import com.efd.striketectablet.DTO.TrainingPunchDTO;
import com.efd.striketectablet.DTO.TrainingStatsPunchTypeInfoDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingPlanResultDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingPunchDetailDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingPunchStatDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingSessionDTO;
import com.efd.striketectablet.DTO.responsedto.SyncResponseDTO;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Super on 6/30/2017.
 */

public class DBAdapter {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

    // A initialization for database version.
    private static final int DATABASE_VERSION = 4; //TODO:- Note- When ever you do changes/modification in your database. Please upgrade the version.

    // A string tag to display message in log file.
    private static final String TAG = "DBAdapter";

    // A String DATABASE_NAME contains database EFD_TrainerApp_DB
    private static final String DATABASE_NAME = EFDConstants.DATABASE_NAME;

    // A TRAINING_SESSION_TABLE initializing to training_session
    private static final String TRAINING_SESSION_TABLE = "training_session";
    //start_time is unique value, and server will update this.

    public static final String KEY_TRAINING_SESSION_ID = "id";
    public static final String KEY_TRAINING_SESSION_END_TIME = "end_time";  //long value
    public static final String KEY_TRAINING_SESSION_START_TIME = "start_time"; //long value
    public static final String KEY_TRAINING_SESSION_TRAINING_SESSION_DATE = "training_session_date"; //yyyy-MM-dd
    public static final String KEY_TRAINING_SESSION_TRAINING_TYPE = "training_type";  //boxing
    public static final String KEY_TRAINING_SESSION_AVG_SPEED = "avg_speed";
    public static final String KEY_TRAINING_SESSION_AVG_FORCE = "avg_force";
    public static final String KEY_TRAINING_SESSION_MAX_SPEED = "max_speed";
    public static final String KEY_TRAINING_SESSION_MAX_FORCE = "max_force";
    public static final String KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT = "total_punch_count";
    public static final String KEY_TRAINING_SESSION_USER_ID = "user_id";  //user_id
    public static final String KEY_TRAINING_SESSION_SERVER_TIMESTAMP = "server_time";
    public static final String KEY_TRAINING_SESSION_FINISHED = "finished"; //0: training, 1:finished
    public static final String KEY_TRAINING_SESSION_SYNC = "sync";  // o : unsynced

    // Query string for creating table TRAINING_SESSION_TABLE .
    private static final String DATABASE_CREATE_TRAINING_SESSION = "create table "
            + TRAINING_SESSION_TABLE
            + " (id integer NOT NULL primary key autoincrement, "
            + " end_time text DEFAULT NULL, "
            + " start_time text NOT NULL, "
            + " training_session_date text NOT NULL, "
            + " training_type text(50) DEFAULT NULL, "
            + " avg_speed double DEFAULT 0, "
            + " avg_force double DEFAULT 0, "
            + " max_speed double DEFAULT 0, "
            + " max_force double DEFAULT 0, "
            + " total_punch_count integer(5) default 0, "
            + " user_id integer(20) NOT NULL, "
            + " sync integer(1) default 0, "
            + " finished integer(1) default 0, "
            + " server_time text default NULL, "
            + " FOREIGN KEY (user_id) references user (id)); ";

    public static String getTrainingSessionTable() {
        return TRAINING_SESSION_TABLE;
    }


    // **************training stats type infos ****************
    // A STATS DATA TABLE initializing
    private static final String TRAINING_PUNCH_STATS_TABLE = "punch_stats";

    //this table can be created
    public static final String KEY_TRAINING_PUNCH_STATS_ID = "id";
    public static final String KEY_TRAINING_PUNCH_STATS_DATE = "punched_date";
    public static final String KEY_TRAINING_PUNCH_STATS_PUNCH_TYPE = "punch_type";
    public static final String KEY_TRAINING_PUNCH_STATS_AVG_SPEED = "avg_speed";
    public static final String KEY_TRAINING_PUNCH_STATS_AVG_FORCE = "avg_force";
    public static final String KEY_TRAINING_PUNCH_STATS_MAX_SPEED = "max_speed";
    public static final String KEY_TRAINING_PUNCH_STATS_MAX_FORCE = "max_force";
    public static final String KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT = "punch_count";
    public static final String KEY_TRAINING_PUNCH_STATS_TOTAL_TIME = "total_time";
    public static final String KEY_TRAINING_PUNCH_STATS_USER_ID = "user_id";
    public static final String KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP = "server_time";
    public static final String KEY_TRAINING_PUNCH_STATS_SYNC = "sync";  // o : unsynced

    // Query string for creating table GYM_TRAINING_STATS_TABLE .
    private static final String DATABASE_CREATE_STAT_TRAINING_STATS = "create table "
            + TRAINING_PUNCH_STATS_TABLE
            + " (id integer NOT NULL primary key autoincrement, "
            + " punch_type text DEFAULT NULL, "
            + " punched_date text DEFAULT NULL, "
            + " avg_speed double DEFAULT 0, "
            + " avg_force double DEFAULT 0, "
            + " max_speed double DEFAULT 0, "
            + " max_force double DEFAULT 0, "
            + " punch_count integer(11) DEFAULT 0, "
            + " total_time double DEFAULT 0, "
            + " user_id integer(20) NOT NULL, "
            + " server_time text DEFAULT NULL, "
            + " sync integer(1) default 0); ";

    public static String getTrainingPunchStatsTable(){
        return TRAINING_PUNCH_STATS_TABLE;
    }

    //**************** training punch results stats table combo/set/workout*****************//
    //this table is for combo/stat/workout page
    private static final String TRAINING_PLAN_TRAINING_RESULTS_TABLE = "training_plan_training_results";
    public static final String KEY_TRAINING_PLAN_TRAINING_RESULTS_ID = "id";
    public static final String KEY_TRAINING_PLAN_TRAINING_TYPE = "plantype";//combo, sets, workout
    public static final String KEY_TRAINING_PLAN_TRAINING_RESULT = "trainingresult"; //result for combo, sets, workout
    public static final String KEY_TRAINING_PLAN_TRAINING_DATE = "trainingdate"; //yyyy-mm-dd
    public static final String KEY_TRAINING_PLAN_TRAINING_USER_ID = "user_id";  //user_id
    public static final String KEY_TRAINING_PLAN_TRAINING_SERVER_TIME = "server_time";
    public static final String KEY_TRAINING_PLAN_TRAINING_SYNC = "sync";  // o : unsynced

    public static String getTrainingPlanResultsTable() {
        return TRAINING_PLAN_TRAINING_RESULTS_TABLE;
    }

    private static final String DATABASE_CREATE_TRAINING_PLAN_TRAINING_RESULTS_TABLE = "create table "
            + TRAINING_PLAN_TRAINING_RESULTS_TABLE
            + " ( id integer NOT NULL primary key autoincrement, "
            + " plantype text DEFAULT null, "
            + " trainingresult text default NULL, "
            + " trainingdate text default NULL, "
            + " user_id integer(20) NOT NULL, "
            + " server_time text default NULL, "
            + " sync integer(1) default 0); ";

    //************************** training punch detail, for graph for previous training **************//
    private static final String TRAINING_PUNCH_DETAIL = "training_punch_detail";
    private static final String KEY_TRAINING_PUNCH_DETAIL_ID = "id";
    private static final String KEY_TRAINING_PUNCH_DETAIL_PUNCHTYPE = "punch_type";
    private static final String KEY_TRAINING_PUNCH_DETAIL_FORCE = "force";
    private static final String KEY_TRAINING_PUNCH_DETAIL_SPEED = "speed";
    private static final String KEY_TRAINING_PUNCH_DETAIL_PUNCHED_TIME_MILESECONDS = "punchedtime";
    private static final String KEY_TRAINING_PUNCH_DETAIL_USER_ID = "user_id";
    private static final String KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME = "server_time";
    private static final String KEY_TRAINING_PUNCH_DETAIL_SYNC = "sync";

    public static String getTrainingPunchDetailTable(){
        return TRAINING_PUNCH_DETAIL;
    }

    private static final String DATABASE_CREATE_TRAINING_PUNCH_DETAIL_TABLE = "create table "
            + TRAINING_PUNCH_DETAIL
            + " (id integer NOT NULL primary key autoincrement, "
            + " punch_type text DEFAULT null, "
            + " force double default 0, "
            + " speed double default 0, "
            + " punchedtime text DEFAULT null, "
            + " user_id interger(20) NOT NULL, "
            + " server_time text DEFAULT null, "
            + " sync integer(1) default 0 ); ";

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


    // *************user_access*******************

    // A USER_ACCESS_TABLE initializing to user
    private static final String USER_ACCESS_TABLE = "user_access";

    public static String getUserAccessTable() {
        return USER_ACCESS_TABLE;
    }

    public static final String KEY_USER_ACCESS_ID = "id";
    public static final String KEY_USER_ACCESS_TOKEN = "access_token";
    public static final String KEY_USER_ACCESS_SERVER_ID = "user_id";

    // Query string for creating table DATABASE_CREATE_USER_ACCESS .
    private static final String DATABASE_CREATE_USER_ACCESS = "create table "
            + USER_ACCESS_TABLE + " (" + KEY_USER_ACCESS_ID + " integer NOT NULL primary key autoincrement, "
            + KEY_USER_ACCESS_TOKEN + " text(50) DEFAULT NULL, "
            + KEY_USER_ACCESS_SERVER_ID + " integer DEFAULT NULL, "
            + " UNIQUE(" + KEY_USER_ACCESS_SERVER_ID + "));";


    // Object reference for context.
    private final Context context;

    // Object reference for DatabaseHelper.
    private static DBAdapter.DatabaseHelper DBHelper;

    // Object reference for SQLiteDatabase.
    private SQLiteDatabase db;

    private static DBAdapter instance = null;
    private static String path = null;

    private DBAdapter(Context ctx) {
        this.context = ctx;
        path = EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.DATABASE_DIRECTORY + File.separator;
        File mydir = new File(path);
        if (!mydir.exists()) {
            mydir.mkdirs();
        } else {
            Log.i(TAG, "directory already exists");
        }
        DBHelper = new DBAdapter.DatabaseHelper(context);
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

    private static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Constructor for database helper class.
         *
         * @param context
         */
        DatabaseHelper(Context context) {
            super(context, path + DATABASE_NAME, null, DATABASE_VERSION);
            setWriteAheadLoggingEnabled(true);
        }

        /**
         * Called when the database is created for the first time. This is where
         * the creation of tables and the initial population of the tables
         * should happen. Parameters: db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE_TRAINING_SESSION);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_SESSION);

            db.execSQL(DATABASE_CREATE_STAT_TRAINING_STATS);
            Log.d(TAG, "created table " + DATABASE_CREATE_STAT_TRAINING_STATS);

            db.execSQL(DATABASE_CREATE_TRAINING_PLAN_TRAINING_RESULTS_TABLE);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_PLAN_TRAINING_RESULTS_TABLE);

            db.execSQL(DATABASE_CREATE_TRAINING_PUNCH_DETAIL_TABLE);
            Log.d(TAG, "created table " + DATABASE_CREATE_TRAINING_PUNCH_DETAIL_TABLE);

            db.execSQL(DATABASE_CREATE_USER);
            Log.d(TAG, "created table " + DATABASE_CREATE_USER);

            db.execSQL(CREATE_TABLE_BOXER_PROFILE);
            Log.d(TAG, "created table " + CREATE_TABLE_BOXER_PROFILE);

            db.execSQL(DATABASE_CREATE_USER_ACCESS);
            Log.d(TAG, "created table " + DATABASE_CREATE_USER_ACCESS);
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

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_SESSION_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_PUNCH_STATS_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_PLAN_TRAINING_RESULTS_TABLE);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TRAINING_PUNCH_DETAIL);

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + BOXER_PROFILE_TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + USER_ACCESS_TABLE);

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


//            db.enableWriteAheadLogging();
//
//            db.enableWriteAheadLogging();


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



    /*       super added these functions   */

    //****************** super added insert or update functions when user punched ******************************//

    /**
     * Checks for finished null values in training_session & updates those with the last punch time if available else with the start_time.
     *
     * @return No. of rows updated
     */
    public int endAllPreviousTrainingSessions() {

        String selectQuery = "SELECT  * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_FINISHED + "='" + 0 + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        int updateResult = 0;

        try {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();

                ContentValues values = new ContentValues();
                values.put(KEY_TRAINING_SESSION_FINISHED, 1);
                updateResult += db.update(TRAINING_SESSION_TABLE, values, KEY_TRAINING_SESSION_ID + " = " + cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_ID)), null);
                cursor.moveToNext();
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return updateResult;
    }

    public long insertTrainingSession(String training_type, int userId) {
        Date date = new java.sql.Timestamp(System.currentTimeMillis());
        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date sessionDate = new Date();

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_SESSION_START_TIME, String.valueOf(System.currentTimeMillis()));
        values.put(KEY_TRAINING_SESSION_END_TIME, String.valueOf(System.currentTimeMillis()));
        values.put(KEY_TRAINING_SESSION_TRAINING_SESSION_DATE, sessionDateFormat.format(sessionDate));
        values.put(KEY_TRAINING_SESSION_TRAINING_TYPE, training_type);
        values.put(KEY_TRAINING_SESSION_USER_ID, userId);
        values.put(KEY_TRAINING_SESSION_SERVER_TIMESTAMP, "0");

        long result = db.insert(TRAINING_SESSION_TABLE, null, values);
        return result;
    }

    public void insertPunchedData(int trainingId, TrainingPunchDTO punchDataDTO){
        updateTrainingSession(trainingId, punchDataDTO);
    }

    private void updateTrainingSession(int trainingId, TrainingPunchDTO punchDTO){
        String selectQuery = "SELECT  * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_ID + "='" + trainingId + "'";
        Log.e("Super", "training id = " + trainingId);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();
            if (count > 0) {
                //trainig session is already in local db
                cursor.moveToFirst();
                double avgspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_SPEED));
                double avgforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_FORCE));
                double maxspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_SPEED));
                double maxforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_FORCE));
                int punchcount = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT));

                avgspeed = ((avgspeed * punchcount) + punchDTO.getSpeed()) / (punchcount + 1);
                avgforce = ((avgforce * punchcount) + punchDTO.getForce()) / (punchcount + 1);
                maxspeed = Math.max(maxspeed, punchDTO.getSpeed());
                maxforce = Math.max(maxforce, punchDTO.getForce());

                punchcount = punchcount + 1;

                ContentValues values = new ContentValues();
                values.put(KEY_TRAINING_SESSION_AVG_SPEED, avgspeed);
                values.put(KEY_TRAINING_SESSION_AVG_FORCE, avgforce);
                values.put(KEY_TRAINING_SESSION_MAX_SPEED, maxspeed);
                values.put(KEY_TRAINING_SESSION_MAX_FORCE, maxforce);
                values.put(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT, punchcount);
                values.put(KEY_TRAINING_SESSION_END_TIME, String.valueOf(System.currentTimeMillis()));

                db.update(TRAINING_SESSION_TABLE, values, KEY_TRAINING_SESSION_ID + " = " + cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_ID)), null);

                updateOrInsertTrainingPunchResults(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_USER_ID)), punchDTO);
                insertTrainingPunchDetail(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_USER_ID)), punchDTO);

            }else {
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private void updateOrInsertTrainingPunchResults(int userId, TrainingPunchDTO punchDTO){
        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Select All Query
        String selectQuery = "SELECT * FROM " + TRAINING_PUNCH_STATS_TABLE + " WHERE " + KEY_TRAINING_PUNCH_STATS_PUNCH_TYPE + "='"  + punchDTO.getPunchtype() +
                "' AND " + KEY_TRAINING_PUNCH_STATS_DATE + "='" + sessionDateFormat.format(new Date()) +
                "' AND " + KEY_TRAINING_PUNCH_STATS_USER_ID +"='" + userId + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                updateTrainingPunchResults(punchDTO, cursor);
            } else
                insertTrainingPunchResults(userId, punchDTO);
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private void updateTrainingPunchResults(TrainingPunchDTO punchDTO, Cursor cursor){
        double avgspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_AVG_SPEED));
        double avgforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_AVG_FORCE));
        double maxspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_MAX_SPEED));
        double maxforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_MAX_FORCE));
        int punchcount = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT));
        double totaltime = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_TOTAL_TIME));

        avgspeed = ((avgspeed * punchcount) + punchDTO.getSpeed()) / (punchcount + 1);
        avgforce = ((avgforce * punchcount) + punchDTO.getForce()) / (punchcount + 1);
        maxspeed = Math.max(maxspeed, punchDTO.getSpeed());
        maxforce = Math.max(maxforce, punchDTO.getForce());
        totaltime = totaltime + punchDTO.getPunchtime();
        punchcount = punchcount + 1;

        Log.e("Super", "totaltime = " + punchcount + "    " + totaltime);

        ContentValues values = new ContentValues();
        values.put(KEY_TRAINING_PUNCH_STATS_AVG_SPEED, avgspeed);
        values.put(KEY_TRAINING_PUNCH_STATS_AVG_FORCE, avgforce);
        values.put(KEY_TRAINING_PUNCH_STATS_MAX_SPEED, maxspeed);
        values.put(KEY_TRAINING_PUNCH_STATS_MAX_FORCE, maxforce);
        values.put(KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT, punchcount);
        values.put(KEY_TRAINING_PUNCH_STATS_TOTAL_TIME, totaltime);
        values.put(KEY_TRAINING_PUNCH_STATS_SYNC, 0);
        values.put(KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP, "0");

        db.update(TRAINING_PUNCH_STATS_TABLE, values, KEY_TRAINING_PUNCH_STATS_ID + " = " + cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_ID)), null);
    }

    private long insertTrainingPunchResults(int userId, TrainingPunchDTO punchDTO) {
        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_PUNCH_STATS_DATE, sessionDateFormat.format(new Date()));
        values.put(KEY_TRAINING_PUNCH_STATS_PUNCH_TYPE, punchDTO.getPunchtype());
        values.put(KEY_TRAINING_PUNCH_STATS_AVG_SPEED, punchDTO.getSpeed());
        values.put(KEY_TRAINING_PUNCH_STATS_AVG_FORCE, punchDTO.getForce());
        values.put(KEY_TRAINING_PUNCH_STATS_MAX_SPEED, punchDTO.getSpeed());
        values.put(KEY_TRAINING_PUNCH_STATS_MAX_FORCE, punchDTO.getForce());
        values.put(KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT, 1);
        values.put(KEY_TRAINING_PUNCH_STATS_TOTAL_TIME, punchDTO.getPunchtime());
        values.put(KEY_TRAINING_PUNCH_STATS_USER_ID, userId);
        values.put(KEY_TRAINING_PUNCH_STATS_SYNC, 0);
        values.put(KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP, "0");

        long result = db.insert(TRAINING_PUNCH_STATS_TABLE, null, values);
        return result;
    }

    private void insertTrainingPunchDetail(int userId, TrainingPunchDTO punchDTO){
        ContentValues values = new ContentValues();

        values.put(KEY_TRAINING_PUNCH_DETAIL_PUNCHTYPE, punchDTO.getPunchtype());
        values.put(KEY_TRAINING_PUNCH_DETAIL_SPEED, punchDTO.getSpeed());
        values.put(KEY_TRAINING_PUNCH_DETAIL_FORCE, punchDTO.getForce());
        values.put(KEY_TRAINING_PUNCH_DETAIL_PUNCHED_TIME_MILESECONDS, String.valueOf(System.currentTimeMillis()));
        values.put(KEY_TRAINING_PUNCH_DETAIL_USER_ID, userId);
        values.put(KEY_TRAINING_PUNCH_DETAIL_SYNC, 0);
        values.put(KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME, "0");

        long result = db.insert(TRAINING_PUNCH_DETAIL, null, values);
    }

    public long insertTrainingPlanRestuls(int userId, String trainingtype, String trainingresult){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date today = new Date();

        ContentValues values = new ContentValues();
        values.put(KEY_TRAINING_PLAN_TRAINING_TYPE, trainingtype);
        values.put(KEY_TRAINING_PLAN_TRAINING_RESULT, trainingresult);
        values.put(KEY_TRAINING_PLAN_TRAINING_DATE, format.format(today));
        values.put(KEY_TRAINING_PLAN_TRAINING_USER_ID, userId);
        values.put(KEY_TRAINING_PLAN_TRAINING_SYNC, 0);
        values.put(KEY_TRAINING_PLAN_TRAINING_SERVER_TIME, "0");

        long result = db.insert(TRAINING_PLAN_TRAINING_RESULTS_TABLE, null, values);
        return result;
    }

    //************************************** ****************************   //

    //********************** super added get nonsynced records for each table ******************************//
    public List<DBTrainingSessionDTO> getAllNonSynchronizedTrainingSessionRecords(int limit, int userid) {

        DBTrainingSessionDTO dbTrainingSessionDTO = null;
        List<DBTrainingSessionDTO> list = new ArrayList<DBTrainingSessionDTO>();

        String selectQuery = "SELECT * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_USER_ID + "='"  + userid
                + "' AND " + KEY_TRAINING_SESSION_SYNC + "='" + 0
                + "' AND " + KEY_TRAINING_SESSION_FINISHED + "='" + 1 + "'"
                + " limit " + limit + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {
                    dbTrainingSessionDTO = new DBTrainingSessionDTO(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_START_TIME)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_END_TIME)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_TRAINING_SESSION_DATE)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_TRAINING_TYPE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_SPEED)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_FORCE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_SPEED)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_FORCE)),
                            cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT)),
                            cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_USER_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_SERVER_TIMESTAMP)));

                    list.add(dbTrainingSessionDTO);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingPunchDetailDTO> getAllNonSynchronizedTrainingDetailRecords(int limit, int userid) {

        DBTrainingPunchDetailDTO dbTrainingPunchDetailDTO = null;
        List<DBTrainingPunchDetailDTO> list = new ArrayList<DBTrainingPunchDetailDTO>();

        String selectQuery = "SELECT * FROM " + TRAINING_PUNCH_DETAIL + " WHERE " + KEY_TRAINING_PUNCH_DETAIL_USER_ID + "='"  + userid +
                "' AND " + KEY_TRAINING_PUNCH_DETAIL_SYNC + "='" + 0 + "'"
                + " limit " + limit + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {
                    dbTrainingPunchDetailDTO = new DBTrainingPunchDetailDTO(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_PUNCHTYPE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_SPEED)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_FORCE)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_PUNCHED_TIME_MILESECONDS)),
                            cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_USER_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME)));

                    list.add(dbTrainingPunchDetailDTO);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingPlanResultDTO> getAllNonSynchronizedTrainingPlanResultRecords(int limit, int userid) {

        DBTrainingPlanResultDTO dbTrainingPlanResultDTO = null;
        List<DBTrainingPlanResultDTO> list = new ArrayList<DBTrainingPlanResultDTO>();

        String selectQuery = "SELECT * FROM " + TRAINING_PLAN_TRAINING_RESULTS_TABLE + " WHERE " + KEY_TRAINING_PLAN_TRAINING_USER_ID + "='"  + userid +
                "' AND " + KEY_TRAINING_PLAN_TRAINING_SYNC + "='" + 0 + "'"
                + " limit " + limit + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {
                    dbTrainingPlanResultDTO = new DBTrainingPlanResultDTO(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_RESULTS_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_TYPE)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_RESULT)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_DATE)),
                            cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_USER_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_SERVER_TIME)));

                    list.add(dbTrainingPlanResultDTO);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public List<DBTrainingPunchStatDTO> getAllNonSynchronizedTrainingPunchStatRecords(int limit, int userid) {

        DBTrainingPunchStatDTO dbTrainingPunchStatDTO = null;
        List<DBTrainingPunchStatDTO> list = new ArrayList<DBTrainingPunchStatDTO>();

        String selectQuery = "SELECT * FROM " + TRAINING_PUNCH_STATS_TABLE + " WHERE " + KEY_TRAINING_PUNCH_STATS_USER_ID + "='"  + userid +
                "' AND " + KEY_TRAINING_PUNCH_STATS_SYNC + "='" + 0 + "'"
                + " limit " + limit + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                do {
                    dbTrainingPunchStatDTO = new DBTrainingPunchStatDTO(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_DATE)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_PUNCH_TYPE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_AVG_SPEED)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_AVG_FORCE)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_MAX_SPEED)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_MAX_FORCE)),
                            cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT)),
                            cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_TOTAL_TIME)),
                            cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_USER_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP)));

                    list.add(dbTrainingPunchStatDTO);
                } while (cursor.moveToNext());

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return list;
    }

    public int updateSynchronizedRecordsForTable(String tableName, List<SyncResponseDTO> dtos) {

        ContentValues values = new ContentValues();
        int success = 0;

        for (int i = 0; i < dtos.size(); i++) {
            values.put("sync", "1");
            values.put("server_time", dtos.get(i).getServerTime());

            Log.e("Super", "update db = " + tableName + "    " + dtos.get(i).getId() + "      " + dtos.get(i).getServerTime());
            try {
                success = db.update(tableName, values, "id = ? ", new String[]{String.valueOf(dtos.get(i).getId())});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return success;
    }

    /*****************************************************************************************/

    /********************* super added functions for usage **********************************/
    public List<String> getTrainingStatswithtype(int userId, String type, String date){

        List<String> trainingResult = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TRAINING_PLAN_TRAINING_RESULTS_TABLE + " WHERE " + KEY_TRAINING_PLAN_TRAINING_TYPE + "='"  + type +
                "' AND " + KEY_TRAINING_PLAN_TRAINING_DATE + "='" + date +
                "' AND " + KEY_TRAINING_PLAN_TRAINING_USER_ID + "='" + userId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();

            if (count > 0) {
                cursor.moveToFirst();
            }

            for (int i = 0; i < count; i++) {
                String result = cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PLAN_TRAINING_RESULT));
                trainingResult.add(result);
                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return trainingResult;
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

    public int getTodayTotalTime(int userId, String formatteddate){
        int totaltime = 0;

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String formatteddate = simpleDateFormat.format(new Date());

        String selectQuery = "SELECT * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_TRAINING_SESSION_DATE + "='"  + formatteddate +
                "' AND " + KEY_TRAINING_SESSION_USER_ID + "='" + userId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();

            if (count > 0) {
                cursor.moveToFirst();
            }

            for (int i = 0; i < count; i++) {
                int startTime = (int)(Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_START_TIME))) / 1000);
                int endTime = (int)(Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_END_TIME))) / 1000);

                int duration = endTime - startTime;

                Log.e("Super", "duration = " + duration);

                totaltime += duration;

                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return totaltime;
    }

    public ArrayList<TrainingStatsPunchTypeInfoDTO> getTrainingStats(int userId, String formatteddate){

//        DateFormat sessionDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        sessionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        ArrayList<TrainingStatsPunchTypeInfoDTO> punchTypeInfoDTOs = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TRAINING_PUNCH_STATS_TABLE + " WHERE " + KEY_TRAINING_PUNCH_STATS_DATE + "='"  + formatteddate +
                "' AND " + KEY_TRAINING_PUNCH_STATS_USER_ID + "='" + userId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
            }

            for (int i = 0; i < count; i++) {
                TrainingStatsPunchTypeInfoDTO punchTypeInfoDTO = new TrainingStatsPunchTypeInfoDTO();
                punchTypeInfoDTO.punchtype = (cursor.getString(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_PUNCH_TYPE)));
                punchTypeInfoDTO.avgspeed = (cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_AVG_SPEED)));
                punchTypeInfoDTO.avgforce = (cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_AVG_FORCE)));
                punchTypeInfoDTO.totaltime = (cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_TOTAL_TIME)));
                punchTypeInfoDTO.punchcount = (cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT)));

                punchTypeInfoDTOs.add(punchTypeInfoDTO);

                cursor.moveToNext();
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return punchTypeInfoDTOs;
    }

    public JSONObject trainingSessionEnd(long trainingSessionId) {
        JSONObject resultJSON = new JSONObject();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TRAINING_SESSION_END_TIME, String.valueOf(System.currentTimeMillis()));
            values.put(KEY_TRAINING_SESSION_FINISHED, 1);

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

    public JSONObject trainingUserInfo(String userId) {

        JSONObject userInfoJson, resultJSON;
        resultJSON = new JSONObject();

        Cursor cursor = db.query(USER_TABLE, null, "id=" + userId, null, null, null, null);
        Date date = new java.sql.Timestamp(System.currentTimeMillis());

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

    public List<RoundDto> getRoundSummaryData(Date startDate, Date endDate, int userId) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = simpleDateFormat.format(startDate);
        String formattedEndDate = simpleDateFormat.format(endDate);

        String selectQuery = "SELECT * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_USER_ID + "='"  + userId +
                "' AND " + KEY_TRAINING_SESSION_TRAINING_SESSION_DATE + " BETWEEN '" + formattedStartDate + "' AND '" + formattedEndDate + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        List<RoundDto> rounds = new ArrayList<>();

        try {
            if (cursor.getCount() >= 1) {
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    int sessionId = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_ID));
                    float maxForce = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_FORCE));
                    float maxSpeed = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_SPEED));
                    float averageForce = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_FORCE));
                    float averageSpeed = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_SPEED));
                    int totalPunches = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT));
                    int elapsedSeconds = (int)(Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_END_TIME))) -
                            Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_START_TIME))));

                    RoundDto round = new RoundDto(userId, sessionId, totalPunches, maxForce, maxSpeed, averageForce, averageSpeed, elapsedSeconds);
                    rounds.add(round);
                    cursor.moveToNext();
                }

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        Log.e("Super", "start date  = " + formattedStartDate + "   " + formattedEndDate + "   "  +rounds.size());
        return rounds;
    }

    public CalendarSummaryDTO getCalendarSummaryData(String fromCurrentSelectedDate, int numberOfPastDays, int userId) {
        // Returns calendar summary for daily, weekly, monthly records for given trainingSessionDate range and user id
        // Returns null otherwise
        // Date format yyyy-mm-dd

        CalendarSummaryDTO calendarDTO = null;

        String selectQuery = "SELECT * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_USER_ID + "='"  + userId +
                "' AND " + KEY_TRAINING_SESSION_TRAINING_SESSION_DATE + " BETWEEN '" + CommonUtils.calculatePastDate(fromCurrentSelectedDate, numberOfPastDays, EFDConstants.MEASURE_DATE) + "' AND '" + fromCurrentSelectedDate + "'";

//        String query = "SELECT * from calendar_summary where "
//                + KEY_CALENDAR_DATE + " between '"
//                + CommonUtils.calculatePastDate(fromCurrentSelectedDate, numberOfPastDays, EFDConstants.MEASURE_DATE) + "' and '" + fromCurrentSelectedDate
//                + "' and " + KEY_CALENDAR_USER_ID + " ='" + userID + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
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
                    totalSeconds += (int)(Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_END_TIME))) -
                            Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_START_TIME)))) / 1000;
                    totalPunches += cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT));

                    double avgspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_SPEED));
                    double avgforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_FORCE));
                    double maxspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_SPEED));
                    double maxforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_FORCE));

                    if (maxSpeed < (int)(maxspeed)) {
                        maxSpeed = (int)maxspeed;
                    }
                    if (maxForce < (int)maxforce) {
                        maxForce = (int)maxforce;
                    }
                    speeds[rowNumber] = avgspeed;
                    forces[rowNumber] = avgforce;

                    cursor.moveToNext();
                    rowNumber++;
                }

                float averageForce = (float) StatisticUtil.average(forces);
                float averageSpeed = (float) StatisticUtil.average(speeds);

                calendarDTO = new CalendarSummaryDTO(userId, totalPunches, maxForce, maxSpeed, averageForce, averageSpeed, CommonUtils.secondsToTime(totalSeconds), fromCurrentSelectedDate);

            }

            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                int totaltime = (int)(Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_END_TIME))) -
                        Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_START_TIME)))) / 1000;
                int punches = cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT));
                double avgspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_SPEED));
                double avgforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_AVG_FORCE));
                double maxspeed = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_SPEED));
                double maxforce = cursor.getDouble(cursor.getColumnIndex(KEY_TRAINING_SESSION_MAX_FORCE));
                String date = cursor.getString(cursor.getColumnIndex(KEY_TRAINING_SESSION_TRAINING_SESSION_DATE));

                calendarDTO = new CalendarSummaryDTO(userId, punches, (int)maxforce, (int)maxspeed, (float)avgforce, (float)avgspeed, CommonUtils.secondsToTime(totaltime), date);

            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return calendarDTO;
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

    public String getLastSyncSessionServerTime(String userId){
        String result="0";

        String query = "SELECT "
                + KEY_TRAINING_SESSION_SERVER_TIMESTAMP
                + " from " + TRAINING_SESSION_TABLE
                + " where " + KEY_TRAINING_SESSION_USER_ID + " ='" + userId + "' and "
                + TRAINING_SESSION_TABLE + "." + KEY_TRAINING_SESSION_SYNC + " = '1' "
                + "ORDER BY " + TRAINING_SESSION_TABLE + "." + KEY_TRAINING_SESSION_SERVER_TIMESTAMP + " DESC " +  ";";



        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        Log.e("Super", "lastsync server time = " + query + "    " + result);

        return result;
    }

    public String getLastSyncPunchStatsServerTime(String userId){
        String result="0";

        String query = "SELECT "
                + KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP
                + " from " + TRAINING_PUNCH_STATS_TABLE
                + " where " + KEY_TRAINING_PUNCH_STATS_USER_ID + " ='" + userId + "' and "
                + TRAINING_PUNCH_STATS_TABLE + "." + KEY_TRAINING_PUNCH_STATS_SYNC + " = '1' "
                + "ORDER BY " + TRAINING_PUNCH_STATS_TABLE + "." + KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP + " DESC " +  ";";



        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        Log.e("Super", "lastsync punch stats time = " + query + "    " + result);
        return result;
    }

    public String getLastSyncPlanResultsServerTime(String userId){
        String result="0";

        String query = "SELECT "
                + KEY_TRAINING_PLAN_TRAINING_SERVER_TIME
                + " from " + TRAINING_PLAN_TRAINING_RESULTS_TABLE
                + " where " + KEY_TRAINING_PLAN_TRAINING_USER_ID + " ='" + userId + "' and "
                + TRAINING_PLAN_TRAINING_RESULTS_TABLE + "." + KEY_TRAINING_PLAN_TRAINING_SYNC + " = '1' "
                + "ORDER BY " + TRAINING_PLAN_TRAINING_RESULTS_TABLE + "." + KEY_TRAINING_PLAN_TRAINING_SERVER_TIME + " DESC " +  ";";



        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        Log.e("Super", "lastsync plan  time = " + query + "    " + result);
        return result;
    }

    public String getLastTrainingPunchDetailsServerTime(String userId){
        String result="0";

        String query = "SELECT "
                + KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME
                + " from " + TRAINING_PUNCH_DETAIL
                + " where " + KEY_TRAINING_PUNCH_DETAIL_USER_ID + " ='" + userId + "' and "
                + TRAINING_PUNCH_DETAIL + "." + KEY_TRAINING_PUNCH_DETAIL_SYNC + " = '1' "
                + "ORDER BY " + TRAINING_PUNCH_DETAIL + "." + KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME + " DESC " +  ";";



        Cursor cursor = db.rawQuery(query, null);

        try {
            cursor.moveToFirst();

            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        Log.e("Super", "lastsync punch detail time = " + query + "    " + result);

        return result;
    }

    public void insertTrainingSessionFromServer(List<DBTrainingSessionDTO> sessionDTOs){

        for (int i = 0; i < sessionDTOs.size(); i++){
            DBTrainingSessionDTO sessionDTO = sessionDTOs.get(i);

            String selectQuery = "SELECT  * FROM " + TRAINING_SESSION_TABLE + " WHERE " + KEY_TRAINING_SESSION_SERVER_TIMESTAMP + "='" + sessionDTO.getServerTime() + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            Log.e("Super", "session from server 111111= " + sessionDTO.toString() + "    " +  "    " + cursor.getCount());
            try {
                int count = cursor.getCount();
                if (count > 0) {
                    //trainig session is already in local db
                }else {
                    //insert training session
                    ContentValues values = new ContentValues();
                    Log.e("Super", "session from server = " + sessionDTO.toString());
                    values.put(KEY_TRAINING_SESSION_END_TIME, sessionDTO.getEndTime());
                    values.put(KEY_TRAINING_SESSION_START_TIME, sessionDTO.getStartTime());
                    values.put(KEY_TRAINING_SESSION_TRAINING_SESSION_DATE, sessionDTO.getTrainingSessionDate());
                    values.put(KEY_TRAINING_SESSION_TRAINING_TYPE, sessionDTO.getTrainingType());
                    values.put(KEY_TRAINING_SESSION_AVG_SPEED, sessionDTO.getAvgSpeed());
                    values.put(KEY_TRAINING_SESSION_AVG_FORCE, sessionDTO.getAvgForce());
                    values.put(KEY_TRAINING_SESSION_MAX_SPEED, sessionDTO.getMaxSpeed());
                    values.put(KEY_TRAINING_SESSION_MAX_FORCE, sessionDTO.getMaxForce());
                    values.put(KEY_TRAINING_SESSION_TOTAL_PUNCH_COUNT, sessionDTO.getTotalPunchCount());
                    values.put(KEY_TRAINING_SESSION_USER_ID, sessionDTO.getUserID());
                    values.put(KEY_TRAINING_SESSION_SERVER_TIMESTAMP, sessionDTO.getServerTime());
                    values.put(KEY_TRAINING_SESSION_FINISHED, "1");
                    values.put(KEY_TRAINING_SESSION_SYNC, "1");

                    long result = db.insert(TRAINING_SESSION_TABLE, null, values);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
    }

    public void insertTrainingPunchStatsFromServer(List<DBTrainingPunchStatDTO> punchStatDTOs){

        for (int i = 0; i < punchStatDTOs.size(); i++){
            DBTrainingPunchStatDTO punchStatDTO = punchStatDTOs.get(i);

            String selectQuery = "SELECT  * FROM " + TRAINING_PUNCH_STATS_TABLE + " WHERE " + KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP + "='" + punchStatDTO.getServerTime() + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);

            Log.e("Super", "punch stat from server1111111 = " + punchStatDTO.toString()+ "    " + cursor.getCount());
            try {
                int count = cursor.getCount();
                if (count > 0) {
                    //trainig punch stat is already in local db
                }else {
                    //insert training punch stat
                    ContentValues values = new ContentValues();
                    Log.e("Super", "punch stat from server = " + punchStatDTO.toString());
                    values.put(KEY_TRAINING_PUNCH_STATS_DATE, punchStatDTO.getPunchedDate());
                    values.put(KEY_TRAINING_PUNCH_STATS_PUNCH_TYPE, punchStatDTO.getPunchType());
                    values.put(KEY_TRAINING_PUNCH_STATS_AVG_SPEED, punchStatDTO.getAvgSpeed());
                    values.put(KEY_TRAINING_PUNCH_STATS_AVG_FORCE, punchStatDTO.getAvgForce());
                    values.put(KEY_TRAINING_PUNCH_STATS_MAX_SPEED, punchStatDTO.getMaxSpeed());
                    values.put(KEY_TRAINING_PUNCH_STATS_MAX_FORCE, punchStatDTO.getMaxForce());
                    values.put(KEY_TRAINING_PUNCH_STATS_PUNCH_COUNT, punchStatDTO.getPunchCount());
                    values.put(KEY_TRAINING_PUNCH_STATS_TOTAL_TIME, punchStatDTO.getTotalTime());
                    values.put(KEY_TRAINING_PUNCH_STATS_USER_ID, punchStatDTO.getUserID());
                    values.put(KEY_TRAINING_PUNCH_STATS_SERVER_TIMESTAMP, punchStatDTO.getServerTime());
                    values.put(KEY_TRAINING_PUNCH_STATS_SYNC, "1");

                    long result = db.insert(TRAINING_PUNCH_STATS_TABLE, null, values);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
    }

    public void insertTrainingPlansResultsFromServer(List<DBTrainingPlanResultDTO> planResultDTOs){

        for (int i = 0; i < planResultDTOs.size(); i++){
            DBTrainingPlanResultDTO planResultDTO = planResultDTOs.get(i);

            String selectQuery = "SELECT  * FROM " + TRAINING_PLAN_TRAINING_RESULTS_TABLE + " WHERE " + KEY_TRAINING_PLAN_TRAINING_SERVER_TIME + "='" + planResultDTO.getServerTime() + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            Log.e("Super", "plan results from server11111 = " + planResultDTO.toString() + "    " + cursor.getCount());
            try {
                int count = cursor.getCount();
                if (count > 0) {
                    //trainig plan result is already in local db
                }else {
                    //insert training plan result
                    ContentValues values = new ContentValues();

                    Log.e("Super", "plan results from server = " + planResultDTO.toString());
                    values.put(KEY_TRAINING_PLAN_TRAINING_TYPE, planResultDTO.getPlanType());
                    values.put(KEY_TRAINING_PLAN_TRAINING_RESULT, planResultDTO.getTrainingResult());
                    values.put(KEY_TRAINING_PLAN_TRAINING_DATE, planResultDTO.getTrainingDate());
                    values.put(KEY_TRAINING_PLAN_TRAINING_USER_ID, planResultDTO.getUserID());
                    values.put(KEY_TRAINING_PLAN_TRAINING_SERVER_TIME, planResultDTO.getServerTime());
                    values.put(KEY_TRAINING_PLAN_TRAINING_SYNC, "1");

                    long result = db.insert(TRAINING_PLAN_TRAINING_RESULTS_TABLE, null, values);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
    }

    public void insertTrainingDetailsFromServer(List<DBTrainingPunchDetailDTO> punchDetailDTOs){

        for (int i = 0; i < punchDetailDTOs.size(); i++){
            DBTrainingPunchDetailDTO punchDetailDTO = punchDetailDTOs.get(i);

            String selectQuery = "SELECT  * FROM " + TRAINING_PUNCH_DETAIL + " WHERE " + KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME + "='" + punchDetailDTO.getServerTime() + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);

            try {
                int count = cursor.getCount();
                if (count > 0) {
                    //trainig punch detail is already in local db
                }else {
                    //insert training punch detail
                    ContentValues values = new ContentValues();

                    values.put(KEY_TRAINING_PUNCH_DETAIL_PUNCHTYPE, punchDetailDTO.getPunchType());
                    values.put(KEY_TRAINING_PUNCH_DETAIL_FORCE, punchDetailDTO.getForce());
                    values.put(KEY_TRAINING_PUNCH_DETAIL_SPEED, punchDetailDTO.getSpeed());
                    values.put(KEY_TRAINING_PUNCH_DETAIL_PUNCHED_TIME_MILESECONDS, punchDetailDTO.getPunchTime());
                    values.put(KEY_TRAINING_PUNCH_DETAIL_USER_ID, punchDetailDTO.getUserID());
                    values.put(KEY_TRAINING_PUNCH_DETAIL_SERVER_TIME, punchDetailDTO.getServerTime());
                    values.put(KEY_TRAINING_PUNCH_DETAIL_SYNC, "1");

                    long result = db.insert(TRAINING_PUNCH_DETAIL, null, values);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
    }
}


