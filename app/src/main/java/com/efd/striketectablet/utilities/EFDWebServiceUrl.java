package com.efd.striketectablet.utilities;

import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;

public class EFDWebServiceUrl {

    // web service relevant url's
    public static String COUNTRY_LIST_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/country/list";
    }

    public static String QUESTION_LIST_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/question/list";
    }

    public static String USER_EMAIL_REGISTRATION_STATUS_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/user/isUnRegisteredUser?";
    }

    public static String SENSOR_REGISTRATION_STATUS_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/sensor/isUnclaimedSensors?";
    }

    public static String USER_ACCOUNT_REGISTRATION_STATUS_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/user/doTraineeRegistration?";
    }

    public static String UPDATE_TRAINEE_PROFILE_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/boxerProfile/updateTraineeProfile?";
    }

    public static String TRAINEE_LOGIN_URL() {
        return PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN() + "/user/traineeLogin?";
    }

    // web service relevant methods
    public static final String IS_UNREGISTERED_USER = "isUnRegisteredUser";
    public static final String GET_QUESTION_LIST = "getQuestionList";
    public static final String IS_UNCLAIMED_SENSORS = "isUnclaimedSensors";
    public static final String UPDATE_TRAINEE_PROFILE = "updateTraineeProfile";
    public static final String DO_TRAINEE_REGISTRATION = "doTraineeRegistration";
    public static final String GET_COUNTRY_LIST = "getCountryList";
    public static final String DO_TRAINEE_LOGIN = "traineeLogin";
}
