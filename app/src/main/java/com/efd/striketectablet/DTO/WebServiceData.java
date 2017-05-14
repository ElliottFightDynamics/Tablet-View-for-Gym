package com.efd.striketectablet.DTO;

import java.io.Serializable;
import java.util.Map;

public class WebServiceData implements Serializable {
    private static final long serialVersionUID = 1L;
    public String webServiceUrl;
    public String httpMethodType;
    public Map<String, String> webServiceParams;

    public WebServiceData(String webServiceUrl, String httpMethodType, Map<String, String> params) {
        super();
        this.webServiceUrl = webServiceUrl;
        this.httpMethodType = httpMethodType;
        this.webServiceParams = params;
    }
}

