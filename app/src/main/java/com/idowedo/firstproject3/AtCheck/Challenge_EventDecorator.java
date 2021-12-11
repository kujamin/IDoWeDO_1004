package com.idowedo.firstproject3.AtCheck;

import android.app.Activity;

import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;

import com.idowedo.firstproject3.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;


public class Challenge_EventDecorator extends AppCompatActivity implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;


    //도장 이미지 가져와서 색이랑 날짜를 지정
    public Challenge_EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.chall_check);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    //해당 날짜가 정말 오늘이 맞는지 확인
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    //daily_check 도장을 캘린더 해당날짜에 background로 적용
    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(color));

    }
}