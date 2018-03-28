package com.cy.androidpdf.model;

import android.app.Application;

/**
 * Created by lenovo on 2017/9/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);

    }
}
