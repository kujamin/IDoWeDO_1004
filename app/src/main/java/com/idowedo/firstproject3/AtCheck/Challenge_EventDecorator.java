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


/**
 * Decorate several days with a dot
 */
public class Challenge_EventDecorator extends AppCompatActivity implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;



    public Challenge_EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.chall_check);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(color));

    }
}