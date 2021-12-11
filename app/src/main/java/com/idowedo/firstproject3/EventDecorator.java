package com.idowedo.firstproject3;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
//캘린더 액티비티에서 지정된 날짜를 꾸며주는 클래스
public class EventDecorator extends AppCompatActivity implements DayViewDecorator {

    private View view;
    private final int color;
    private final HashSet<CalendarDay> dates;
    //생성자로 꾸밀 때 필요한 값 얻어오기
    public EventDecorator(int color, Collection<CalendarDay> dates){
        this.color = color;
        this.dates = new HashSet<>(dates);
    }
    //꾸밀 날짜 지정
    @Override
    public boolean shouldDecorate(CalendarDay day){
        return dates.contains(day);
    }
    //어떻게 꾸밀지
    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(7,color));
    }
}
