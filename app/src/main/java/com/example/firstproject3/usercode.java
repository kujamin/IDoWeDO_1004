package com.example.firstproject3;

import android.app.Application;

public class usercode extends Application {

    private String usercode;

    @Override
    public void onCreate() {
        usercode = "";
        super.onCreate();
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }
}
