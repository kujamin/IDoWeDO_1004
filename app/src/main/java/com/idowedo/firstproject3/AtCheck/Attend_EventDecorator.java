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

/**
 * Decorate several days with a dot
 */
public class Attend_EventDecorator extends AppCompatActivity implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;



    public Attend_EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.daily_check);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new DotSpan(7,color));
//        view.addSpan(new BackgroundColorSpan(Color.RED));
        view.setBackgroundDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(color));
//        view.setBackgroundDrawable(view2.getContext().getResources().getDrawable(R.drawable.ic_baseline_add_24));
//        view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }
}