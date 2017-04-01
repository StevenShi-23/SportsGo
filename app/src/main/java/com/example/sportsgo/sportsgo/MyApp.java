package com.example.sportsgo.sportsgo;

import android.app.Application;
import android.content.Context;


/**
 * A class for holding application context
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
