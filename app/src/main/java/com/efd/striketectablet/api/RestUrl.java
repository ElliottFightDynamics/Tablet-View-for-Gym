package com.efd.striketectablet.api;

import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;

public class RestUrl {
    public static final int ENV = 1; // 0 local, 1 dev, 2 prod

//    public static String BASE_URL = "http://fe1-1088333348.us-west-2.elb.amazonaws.com:8090/EFD/";//PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();
    public static String BASE_URL = "http://54.213.226.127:8090/EFD/";//PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();

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
}
