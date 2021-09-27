package com.example.firstproject3.daily;

import androidx.appcompat.app.AppCompatActivity;

public class CalListActivity extends AppCompatActivity {

    private String cal_title, cal_time ;

    public CalListActivity(String cal_title, String cal_time) {
        this.cal_title = cal_title;
        this.cal_time = cal_time;
    }

    public String getCal_title() {
        return cal_title;
    }

    public void setCal_title(String cal_title) {
        this.cal_title = cal_title;
    }

    public String getCal_time() {
        return cal_time;
    }

    public void setCal_memo(String cal_time) {
        this.cal_time = cal_time;
    }
}