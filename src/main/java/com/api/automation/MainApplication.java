package com.api.automation;

import com.api.automation.constants.Constants;
import com.api.automation.util.PackageUtil;

import java.io.File;
import java.util.logging.Logger;

public class MainApplication {
    private static final Logger LOGGER = Logger.getLogger(MainApplication.class.getName());
    private static final String DEFAULT_ENVIRONMENT = "dev";
    public static String ENVIRONMENT;
    public static String APPLICATION_URL;
    public static String USER_NAME;
    public static String PASSWORD;

    static {cleanUP();}
    public  static void cleanUP(){
        File logs = new File("./logs");
        if(logs.exists())
            PackageUtil.recursiveDelete(logs);
    }

    public static void init() {
        if(System.getProperty(Constants.ENVIRONMENT)==null)
            System.setProperty(Constants.ENVIRONMENT, DEFAULT_ENVIRONMENT);
        ENVIRONMENT = System.getProperty(Constants.ENVIRONMENT);
        APPLICATION_URL = System.getProperty(String.join(Constants.DOT,ENVIRONMENT,Constants.URL));
        USER_NAME = System.getProperty(String.join(Constants.DOT,ENVIRONMENT,Constants.USER_NAME));
        PASSWORD = System.getProperty(String.join(Constants.DOT,ENVIRONMENT,Constants.PASSWORD));
    }
}
