package com.idowedo.firstproject3;

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


/**
 * Decorate several days with a dot
 */
//챌린지 인증 켈린더뷰의 출석한 날짜에 출석인증마크를 붙여주는 클래스
public class Challenge_EventDecorator extends AppCompatActivity implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;


    //생성자로 꾸밀 떄 필요한 값 얻어오기
    public Challenge_EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.chall_check);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }
    //꾸밀 날짜 지정
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }
    //어떻게 꾸밀지
    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(color));

    }
}