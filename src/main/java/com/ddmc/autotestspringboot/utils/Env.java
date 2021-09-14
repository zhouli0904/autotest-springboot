package com.ddmc.autotestspringboot.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Env {

    private static final ResourceBundle boundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getEnv() {
        String env = System.getProperty("env");

        if (env == null) {
            env = "t0";
        }
        System.out.println(env);
        return env;
    }

    public static String getUrl() {
        String env = getEnv();
        String url = "";
        if (env.equals("t0") || env.equals("T0")) {
            url = boundle.getString("t0.Host");
        } else if (env.equals("t1") || env.equals("T1")) {
            url = boundle.getString("t1.Host");
        }

        return url;
    }


}
