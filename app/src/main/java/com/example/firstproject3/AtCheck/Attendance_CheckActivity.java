package com.example.firstproject3.AtCheck;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstproject3.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class Attendance_CheckActivity extends Activity {
    private TextView monthYearText;
    String time, kcal, menu;
    private CalendarDay date;
    Cursor cursor;
    MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_attendance_check);

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2021, 8, 1))   //달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) //달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setPadding(0, -20, 0, 30);
        calendarView.setWeekDayTextAppearance(R.style.CustomTextAppearenceWeek);
        calendarView.setDateTextAppearance(R.style.CustomTextAppearanceDay);

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
                );

//        String[] result = {"2021,09,18", "2021,09,06", "2021,09,12", "2021,09,25"};

//        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        //출석체크 버튼
        Button attenBtn = (Button) findViewById(R.id.checkbutton);
        attenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setSelectedDate(CalendarDay.today());
            }
        });
    }


    public void onClikcPopupClose(View v) {
        Intent intent = new Intent();
        intent.putExtra("result", true);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안 닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }

//    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
//
//        String[] Time_Result;
//
//        ApiSimulator(String[] Time_Result) {
//            this.Time_Result = Time_Result;
//        }
//
//        @Override
//        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            Calendar calendar = Calendar.getInstance();
//            ArrayList<CalendarDay> dates = new ArrayList<>();
//
//            /*특정날짜 달력에 점표시해주는곳*/
//            /*월은 0이 1월 년,일은 그대로*/
//            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
//            for (int i = 0; i < Time_Result.length; i++) {
//                CalendarDay day = CalendarDay.from(calendar);
//                String[] time = Time_Result[i].split(",");
//                int year = Integer.parseInt(time[0]);
//                int month = Integer.parseInt(time[1]);
//                int dayy = Integer.parseInt(time[2]);
//
//                dates.add(day);
//                calendar.set(year, month - 1, dayy);
//            }
//            return dates;
//        }

//        @Override
//        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
//            super.onPostExecute(calendarDays);
//
//            if (isFinishing()) {
//                return;
//            }
//
//            calendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays, Attendance_CheckActivity.this));
//        }
//    }
}