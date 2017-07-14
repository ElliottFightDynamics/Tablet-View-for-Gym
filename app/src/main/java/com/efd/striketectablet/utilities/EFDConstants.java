package com.efd.striketectablet.utilities;

import android.os.Environment;

import java.io.File;

public class EFDConstants {

    public static final double GUEST_TRAINING_EFFECTIVE_PUNCH_MASS = 3.0;
    //----------------------------- Application credentials ---------------------
    public static String TRAINING_TYPE_BOXER = "BOXER";
    public static String TRAINING_TYPE_BAG = "BAG";
    public static String USER_ROLE_BOXER = "ROLE_BOXER";
    public static String USER_ROLE_ADMIN = "ROLE_ADMIN";

    public static final String DATABASE_NAME_OLD = "EFD_TrainerApp_DBold.db";
    public static final String DATABASE_NAME = "EFD_TrainerApp_DB.db";

    public static final String[] MMAGLOVE_PREFIX = {"MMAGlove-", "STRIKTEC-"};    // "MMAGlove-";

    //----------------------------- Default date and time ----------------------
    public static final String DATE_FORMAT = "yyyy_MMM_dd_HH_mm_ss_SSS";
    public static final String DEFAULT_START_TIME = "00:00:00";

    //country list
    public static final String COUNTRY1 = "USA";

    //question list
    public static final String QUESTION1 = "What is the name of your favorite pet?";
    public static final String QUESTION2 = "What is the name of the company where you held your first job?";
    public static final String QUESTION3 = "What is your mother\'s maiden name?";
    public static final String QUESTION4 = "What is your father\'s middle name?";
    public static final String QUESTION5 = "What was the name of your high school?";


    //-----------------------------File path------------------------------------
    private static final String ANDROID_DATA_DIRECTORY = "Android/data";
    public static final String EFD_COMMON_DATA_DIRECTORY = Environment.getExternalStorageDirectory() + File.separator + ANDROID_DATA_DIRECTORY + File.separator + "com.efd.striketectablet";// MainActivity.context.getPackageName();
    public static final String APP_DIRECTORY = "EFD Training";
    public static final String SCREENSHOTS_DIRECTORY = "screenshots";
    public static final String CONFIG_DIRECTORY = "config";
    public static final String DATABASE_DIRECTORY = "Database";
    public static final String LOGS_DIRECTORY = "logs";
    public static final String ERROR_LOGS_DIRECTORY = "ErrorLogs";
    public static final String PROPERTIESFILEPATH = "config.properties";
    public static final String TEMPLATEFILEPATH = "templates.csv";
    public static final String FORCE_TABLE_TEMPLATE_FILEPATH = "forceTable.csv";
    public static final String BOXER1RIGHTFILEPATH = "loadDataFromCSVFile\\Boxer1Right.csv";
    public static final String BOXER1LEFTFILEPATH = "loadDataFromCSVFile\\Boxer1Left.csv";
    public static final String BOXER2RIGHTFILEPATH = "loadDataFromCSVFile\\Boxer2Right.csv";
    public static final String BOXER2LEFTFILEPATH = "loadDataFromCSVFile\\Boxer2Left.csv";
    public static final String ERROR_LOGS_FILE = "error.log";


    //---------------------------- Device constants Start ----------------------
    public static final int START_BYTE = 170;
    public static final int LOW_G_MODE = 64;
    public static final int HIGH_G_MODE = 65;
    public static final int GYRO_MODE = 66;
    public static final int BATTERY_MODE = 67;
    public static final int MESSAGE_LENGTH_LSB = 104;
    public static final int MESSAGE_LENGTH_BATTERY = 6;
    public static final int MESSAGE_LENGTH_MSB = 0;
    public static final int SAMPLE_PACKET_SIZE = 16;

    //--------------------------------------------------------------------------
    public static final double LOWG_SAMPLE_TIME_DIFFERENCE = 2.5;

    //----------------------------- Stance Type --------------------------------
    public static final String NON_TRADITIONAL_TEXT = "Non-Traditional";
    public static final String TRADITIONAL_TEXT = "Traditional";
    public static final String NON_TRADITIONAL = "Non-Traditional (right foot front)";
    public static final String TRADITIONAL = "Traditional- Left foot front";

    //----------------------------- Punch Types --------------------------------
    public static final String LEFT_JAB = "LJ";
    public static final String RIGHT_JAB = "RJ";
    public static final String LEFT_STRAIGHT = "LS";
    public static final String RIGHT_STRAIGHT = "RS";
    public static final String LEFT_HOOK = "LH";
    public static final String RIGHT_HOOK = "RH";
    public static final String LEFT_UPPERCUT = "LU";
    public static final String RIGHT_UPPERCUT = "RU";
    public static final String LEFT_UNRECOGNIZED = "LR";
    public static final String RIGHT_UNRECOGNIZED = "RR";
    public static final String JAB = "JAB";
    public static final String STRAIGHT = "STRAIGHT";
    public static final String HOOK = "HOOK";
    public static final String UPPERCUT = "UPPERCUT";
    public static final String UNRECOGNIZED = "UNRECOGNIZED";
    public static final String UNIDENTIFIED = "UNIDENTIFIED";

    //-------------------------------- Boxer Hand ------------------------------
    public static final String LEFT_HAND = "left";
    public static final String RIGHT_HAND = "right";

    //----------------------------- Admin credentials --------------------------
    public static final String ADMIN_USERNAME = "admin2521";
    public static final String ADMIN_PASSWORD = "!!Efd293688!!";


    //------------------ Force Formula calculation type -------------------------
    public static final String OLD_FORCE_FORMULA = "0";
    public static final String CSV_FORCE_FORMULA = "1";

    //----------------------------------------------------------------------------
    public static final int PHONE_HISTORY_GRAPH_LENGTH = 15;
    public static final int TABLET_HISTORY_GRAPH_LENGTH = 24;

    //----------------------- Punch Type Abbreviation texts ----------------------
    public static final String JAB_ABBREVIATION_TEXT = "J";
    public static final String STRAIGHT_ABBREVIATION_TEXT = "S";
    public static final String HOOK_ABBREVIATION_TEXT = "H";
    public static final String UPPERCUT_ABBREVIATION_TEXT = "U";
    public static final String UNRECOGNIZED_ABBREVIATION_TEXT = "UR";

    //----------------------------------------------------------------------------
    public static final String BLANK_TEXT = "";
    public static final String REDIRECTED_FROM_LOGIN = "redirectedFromLogin";
    public static final String ZERO_VALUE = "0";
    public static final String START_TRAINING_TEXT = "START TRAINING";
    public static final String STOP_TRAINING_TEXT = "STOP TRAINING";
    public static final String START_CALIBRATING_TEXT = "START CALIBRATING";
    public static final String STOP_CALIBRATING_TEXT = "STOP CALIBRATING";
    public static final String MPH_TEXT = "MPH";
    public static final String LBF_TEXT = "LBF";

    // ------------------------ Social Media Share message------------------------
    public static final String EMAIL_SUBJECT = "EFD Training App";
    public static final String EMAIL_BODY = "";

    // ------------------------ Calendar and Progress screen tab name ------------
    public static final String TWENTY_FOUR_HOURS = "24 HRS";
    public static final String SEVEN_DAYS = "7 DAYS";
    public static final String THIRTY_DAYS = "30 DAYS";

    // ------------------------ Guest Boxer Constants ----------------------------
    public static final String GUEST_BOXER_USERNAME = "guestboxer";
    public static final Integer GUEST_BOXER_ID = -1;
    public static final int GUEST_TRAINING_SESSION_ID = -1;
    public static final Integer GUEST_TRAINING_DATA_LEFT_HAND_ID = -1;
    public static final Integer GUEST_TRAINING_DATA_RIGHT_HAND_ID = -2;

    // ------------------------ Result Summary Constants -------------------------
    public static final String SUMMARY_TYPE_MAX = "max";
    public static final String SUMMARY_TYPE_AVG = "avg";
    public static final String SUMMARY_TYPE_DAILY = "1";
    public static final String SUMMARY_TYPE_WEEKLY = "6";
    public static final String SUMMARY_TYPE_MONTHLY = "29";

    // ------------------------ HTTP Method Type ---------------------------------
    public static final String HTTP_POST_METHOD = "POST";
    public static final String HTTP_GET_METHOD = "GET";

    // ------------------------Toast and AlertDialog messages --------------------
    public static final String SENSOR_EDIT_TEXT_ERROR = "You cannot edit sensor id when match is in progress.";
    public static final String SENSOR_SEARCH_BUTTON_ERROR = "You cannot search sensor when match is in progress.";
    public static final String CHANGE_BOXER_PROFILE_ERROR = "You cannot change boxer when match is in progress.";
    public static final String REGISTRATION_PROCESS_ERROR_DUE_TO_NETWORK_FAIL = "Unable to begin registration process. Please check network.";
    public static final String DEVICE_ID_MUST_NOT_BE_BLANK = "Sensor id cannot be blank.";
    public static final String DEVICE_ID_MUST_NOT_BE_SAME = "Sensor ids cannot be same.";
    public static final String DEVICE_ID_BLANK = "Please enter sensor id.";
    public static final String SERVER_ERROR = "Unable to process your request due to server error. Please try again later.";
    public static final String REGISTRATION_PROCESS_PROCEED_ERROR = "Unable to process screen. Please check network.";
    public static final String NETWORK_ERROR = "Please check your network connection and try again.";
    public static final String LOGIN_USERNAME_PASSWORD_INCORRECT_ERROR = "The username or password you entered is incorrect.";
    public static final String DEVICE_ID_MUST_NOT_BE_BLANK_ERROR = "No sensors assigned for selected boxer.";
    public static final String OFFLINE_USER_INFO_UPDATE_MESSAGE = "Network not available to update user information on server.";
    public static final String USER_INFO_UPDATE_MESSAGE = "User details updated successfully.";
    public static final String USER_INFO_UPDATE_MESSAGE_ERROR = "Unable to update user details due to some error.";
    public static final String TRAINEE_PROFILE_UPDATE_MESSAGE = "Trainee profile updated successfully.";
    public static final String TRAINEE_ACCOUNT_SUBMIT_MESSAGE = "Trainee account submitted successfully.";
    public static final String USER_INFO_ACCESS_DENIED_MESSAGE = "You cannot access and edit user information while training session is in progress.";
    public static final String COUNTRY_SELECTION_ERROR = "Please select country from the list.";
    public static final String SECURITY_QUESTION_SELECTION_ERROR = "Please select security question from the list.";
    public static final String APPLICATION_CRASH_ERROR = "Unfortunately the application has stopped. Please login again.";
    public static final String ALREADY_CONNECTED_MESSAGE_TEXT = "Sensor already connected.";
    public static final String UNABLE_TO_CONNECT_WITH_SENSOR_MESSAGE_TEXT = "Unable to connect sensor.";
    public static final String SENSOR_CONNECTION_LOST_MESSAGE_TEXT = "Sensor connection was lost.";
    public static final String EMPTY_FIELD_CONNECTION_ERROR_MESSAGE = "Unable to connect with empty field.";
    public static final String INCORRECT_SENSOR_ID_MESSAGE = "Sensor id is not correct = ";
    public static final String SENSOR_CONNECTION_ESTABLISHED_MESSAGE = "Connection established with sensor ";
    public static final String END_USER_AGREEMENT_TEXT = "Please accept the terms of end user agreement.";
    public static final String REGISTRATION_SENSORES_SEARCH_ERROR = "Please search active sensors.";
    public static final String REGISTRATION_BIRTHDATE_ERROR = "Birthdate cannot be blank.";
    public static final String PLEASE_ENTER_DEVICE_ID = "Please go to boxer screen and enter device id\'s to start a match.";
    public static final String LOGOUT_CONFIRMATION_MESSAGE = "Are you sure you want to logout?";
    public static final String CONFIG_PROPERTIES_SAVED_MESSAGE = "Config properties saved successfully.";
    public static final String DATABASE_NOT_FOUND_ERROR_MESSAGE = "Database not found, please login again.";
    public static final String GO_BACK_TO_MENU_CONFIRM_MESSAGE = "Are you sure you want to go back to menu? Your data will not be saved.";
    public static final String INSTALL_TWITTER_APPLICATION_MESSAGE = "Please install twitter application on this device.";
    public static final String INSTALL_FACEBOOK_APPLICATION_MESSAGE = "Please install facebook application on this device.";
    public static final String LEFT_SENSOR_CONNECTING_MESSAGE = "Connecting to left hand sensor.";
    public static final String RIGHT_SENSOR_CONNECTING_MESSAGE = "Connecting to right hand sensor.";
    public static final String BLUETOOTH_ACCESS_ERROR = "Sorry, having problem with bluetooth.";
    public static final String SCANNING_FINISHED = "Scanning finished.";
    public static final String SENSOR_NOT_FOUND_MESSAGE = "Sensor not found.";
    public static final String SENSOR_FOUND_MESSAGE = "Sensor :- ";
    public static final String DETECTED_TEXT = " detected.";
    public static final String INTERNATE_UNAVAILABLE_ERRROR = "Internet not available on phone....";
    public static final String FACEBOOK_SCREENSHOT_UPLOAD_ERRROR = "Something wrong happened. Please try again.";
    public static final String SENSOR_CONNECTION_FAILED_MESSAGE = "Failed to connect with sensor = ";
    public static final String BLUETOOTH_SOCKET_CONNECTION_FAILED_MESSAGE = "Failed to connect with bluetooth socket.";
    public static final String UNABLE_TO_POST_ON_WALL_ERROR = "Could not post to wall.";
    public static final String SUCCESSFULLY_POSTED_ON_WALL_MESSAGE = "Message posted to your facebook wall.";
    public static final String FETCHING_TEXT = "Fetching ";
    public static final String SENSOR_INFO_TEXT = " sensor information.";
    public static final String TRAINEE_SESSION_EXPIRED_ERROR = "Trainee session expired. Please login again.";
    public static final String TRAINEE_PROFILE_UPDATION_ERROR = "Problem with profile updation. Please login and update your profile from User Information.";
    public static final String LEAVE_REGISTRATION_CONFIRM_MESSAGE = "You selected NOT TO ACCEPT the terms of agreement. Are you sure you want to leave registration?";

    // ------------------------Boxer Profile details ------------------------------
    public static final String GLOVE_TYPE_TEXT = "MMA";
    public static final String SKILL_LEVEL_TEXT = "NOVICE";

    // ------------------------Boxers Skill Level values --------------------------
    public static final String SKILL_LEVEL_NOVICE_TEXT = "Novice";
    public static final String SKILL_LEVEL_AMATEUR_TEXT = "Amateur";
    public static final String SKILL_LEVEL_PROFESSIONAL_TEXT = "Professional";

    // ------------------------Boxers Glove Type values ---------------------------
    public static final String GLOVE_TYPE_MMA_TEXT = "mma";
    public static final String GLOVE_TYPE_12_OZ_TEXT = "12";
    public static final String GLOVE_TYPE_14_OZ_TEXT = "14";
    public static final String GLOVE_TYPE_16_OZ_TEXT = "16";

    // ------------------------Boxers Gender values -------------------------------
    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";

    //-------------------------Sync Constants -------------------------------------
    public static final int NUM_SECONDS_IN_ONE_MINUTE = 60;
    public static final int NUM_MILLISECONDS_IN_ONE_SECOND = 1000;
    public static final int SYNC_DELAY = 30;
    public static final int SYNC_RECORDS_LIMIT = 100;
    public static final int SYNC_TRUE = 1;
    public static final int SYNC_FALSE = 0;

    //-------------------------calendar summary data deletion constants -----------
    public static final int MEASURE_YEAR = 1;
    public static final int MEASURE_DATE = 0;
    public static final int CALENDAR_SUMMARY_NUMBER_OF_YEARS = 2;

    //---------------------------connection thread constants ---------------------
    public static final String CONNECTED_DEVICE_TEXT = "ConnectedDevice";
    public static final String DISCONNECTED_DEVICE_TEXT = "DisconnectedDevice";

    //-------------------------- About Efd Share links(urls) ------------------------
    public static final String ABOUT_EFD_FOLLOW_ON_FB_URL = "http://m.facebook.com/ELLIOTTFIGHTDYNAMICSFAN";
    public static final String ABOUT_EFD_YOUTUBE_URL = "http://m.youtube.com/watch?v=A32ng-NkM1E";
    public static final String ABOUT_EFD_FOLLOW_ON_TWITTER_URL = "http://m.twitter.com/elliottfightdyn";
    public static final String ABOUT_EFD_EULA_URL = "http://efdstriketec.com/terms-and-conditions/";
    //-------------------------- Secure Access token  ------------------------
    public static final String KEY_SECURE_ACCESS_TOKEN = "secureAccessToken";

    public static final String KEY_SERVER_USER_ID = "serverUserId";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_ACCESS = "access";

    //-------------------------- Spinner kind  ------------------------
    public static final int SPINNER_WHITE = 0;
    public static final int SPINNER_GREEN = 1;
    public static final int SPINNER_RED = 2;
    public static final int SPINNER_PUNCH = 3;
    public static final int SPINNER_DIGIT_ORANGE = 4;
    public static final int SPINNER_TEXT_ORANGE = 5;

    //max, min value of preset
    public static final int GLOVE_MIN = 4;
    public static final int GLOVE_MAX = 16;
    public static final int GLOVE_INTERVAL = 2;

    public static final int ROUNDS_MIN = 1;
    public static final int ROUNDS_MAX = 12;
    public static final int ROUNDS_INTERVAL = 1;

    public static final int PRESET_MIN_TIME = 30;  //30 secs
    public static final int PRESET_MAX_TIME = 600; //10 mins
    public static final int PRESET_INTERVAL_TIME = 30;

    public static final int WARNING_MIN_TIME = 5;
    public static final int WARNING_MAX_TIME = 60;
    public static final int WARNING_INTERVAL_TIME = 5;

    public static final int WEIGHT_MIN = 50; //lbs
    public static final int WEIGHT_MAX = 350;
    public static final int WEIGHT_INTERVAL = 1;

    public static final int HEIGHT_MIN = 50; //lbs
    public static final int HEIGHT_MAX = 100;
    public static final int HEIGHT_INTERVAL = 1;

    public static final int REACH_MIN = 30;
    public static final int REACH_MAX = 100;
    public static final int REACH_INTERVAL = 1;

    public static final int SPEED_MAX = 45;
    public static final int POWER_MAX = 1250;
    public static final int GRID_COUNT = 15;

    public static final String EDIT_COMBINATION = "editcombination";
    public static final String EDIT_SETS = "editsets";
    public static final String EDIT_WORKOUT = "editworkout";
    public static final String EDIT_COMBOID = "editcomboid";
    public static final String EDIT_SETID = "editsetid";
    public static final String EDIT_WORKOUTID = "editworkoutid";

    public static final String COMBO_ID = "comboid";
    public static final String SET_ID = "setid";
    public static final String WORKOUT_ID = "workoutid";

    public static final String TRAININGTYPE = "trainingtype";
    public static final String COMBINATION = "combination";
    public static final String SETS = "sets";
    public static final String WORKOUT = "workout";

    public static final int MAX_NUM_FORPUNCH = 7;

    public static final String ROUNDTRAINING = "roundtraining";

    public static final String TYPE_COMBO = "combo";
    public static final String TYPE_SET = "set";
    public static final String TYPE_WORKOUT = "workout";
}
