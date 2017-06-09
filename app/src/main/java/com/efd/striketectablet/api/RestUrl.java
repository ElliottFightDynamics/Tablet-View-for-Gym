package com.efd.striketectablet.api;

import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;

public class RestUrl {
    public static final int ENV = 1; // 0 local, 1 dev, 2 prod

    public static String BASE_URL = "http://fe1-1088333348.us-west-2.elb.amazonaws.com:8090/EFD/";//PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();

    //credential url
    public static final String LOGIN = "user/traineeLogin";
}
