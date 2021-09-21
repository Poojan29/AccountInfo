package com.example.angelbank.preference;

import android.app.Application;

public class Bank extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DayNight.setDefaultMode(this);
    }
}
