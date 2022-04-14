package com.sky.lux.utils;

import android.app.Application;

/**
 * created on 2020-04-07
 * description：
 */
public class Lux {
    private static Application application;

    /**
     * 初始化lux context
     */
    public static void init(Application app) {
        application = app;
    }

    public static Application getAppContext() {

        return application;
    }
}

