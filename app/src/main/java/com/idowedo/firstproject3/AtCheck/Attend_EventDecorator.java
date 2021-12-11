package com.idowedo.firstproject3.AtCheck;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.idowedo.firstproject3.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Attend_EventDecorator extends AppCompatActivity implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;



    //도장 이미지 가져와서 색이랑 날짜를 지정
    public Attend_EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.daily_check);  //도장 이미지 (출석체크를 위한것)
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