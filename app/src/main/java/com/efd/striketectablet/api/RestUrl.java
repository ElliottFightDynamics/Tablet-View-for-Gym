package com.efd.striketectablet.api;

public class RestUrl {
    public static final int ENV = 1; // 0 local, 1 dev, 2 prod

//    public static String BASE_URL = "http://fe1-1088333348.us-west-2.elb.amazonaws.com:8090/EFD/";//PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();
//    public static String BASE_URL = "http://54.213.226.127:8090/EFD/";//PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();

//    public static String BASE_URL = "http://34.212.48.125:8090/EFD/";//PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();
    public static String BASE_URL = "http://34.213.85.121:8090/EFD/";
    public static String CSV_BASE_URL = "http://ec2-34-213-85-121.us-west-2.compute.amazonaws.com:3000/";

    //credential url
    public static final String LOGIN = "user/traineeLogin";

    //securite question list
    public static final String QUESTION_LIST = "question/list";

    //country list
    public static final String COUNTRY_LIST = "country/list";

    //registration
    public static final String REGIST = "user/doTraineeRegistration";

    //update user  profile
    public static final String UPDATE_USER = "user/updateUserInfo";

    //reset pwd with email
    public static final String RESETPWD_EMAIL = "user/recovery/email";

    //reset pwd with security question
    public static final String RESETPWD_QUESTION = "user/recovery/question";

    //update new password
    public static final String CHANGEPWD = "user/updatePassword";

    //check user is registered or not
    public static final String CHECK_UNREGISTERED_USER = "user/isUnRegisteredUser";

    //update weight and glove type
    public static final String UPDATE_WEIGHTGLOVE = "user/changeParams";



    /******************************* Training Rest ***************************************/

    public static final String SAVE_COMBO_PLAN = "combo/save";
    public static final String RETRIEVE_PLANS = "comboSetsWorkout/retrieve";
    public static final String SAVE_SETS_PLAN = "sets/save";
    public static final String SAVE_WORKOUT_PLAN = "workout/save";
    public static final String SAVE_PRESET = "preset/save";

    //save/retreive training data
    public static final String SAVE_TRAINING_DATA = "trainingData/saveBulkLocalData";
    public static final String SAVE_TRAINING_DATA_DETAIL = "trainingDataDetails/saveBulkLocalData";
    public static final String SAVE_TRAINING_PUNCH_DATA = "trainingPunchData/saveBulkLocalData";
    public static final String SAVE_TRAINING_PUNCH_PEAK_SUMMARY = "trainingPunchDataPeakSummary/saveBulkLocalData";
    public static final String RETRIEVE_TRAINING_DATAS = "getBulkLocalData";

    public static final String SAVE_TRAINING_SESSION = "trainingSession/saveBulkLocalData";
    public static final String SAVE_TRAINING_PUNCH_STATS = "trainingPunchStats/saveBulkLocalData";
    public static final String SAVE_TRAINING_PUNCH_DETAIL = "trainingPunchDetail/saveBulkLocalData";
    public static final String SAVE_TRAINING_PLAN_RESULTS = "trainingPlanResults/saveBulkLocalData";

    public static final String RETRIEVE_TRAINING_SESSION = "getBulkLocalData";
    public static final String RETRIEVE_TRAINING_PUNCH_DETAIL = "getTrainingPunchDetail";
    public static final String RETRIEVE_TRAINING_PUNCH_STATS = "getTrainingPunchStats";
    public static final String RETRIEVE_TRAINING_PLAN_RESULTS = "getTrainingPlanResults";


    /************************* CSV REST **********************************/
    public static final String UPLOAD_CSV = "analyze";
    public static final String GET_INFO = "info";
    public static final String GET_PLOTTING_LOG = "plottingLog";
}
