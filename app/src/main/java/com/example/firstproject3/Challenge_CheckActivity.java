package com.example.firstproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstproject3.AtCheck.Attend_EventDecorator;
import com.example.firstproject3.AtCheck.Attendance_CheckActivity;
import com.example.firstproject3.AtCheck.SaturdayDecorator;
import com.example.firstproject3.AtCheck.SundayDecorator;
import com.example.firstproject3.Login.ProgressDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Collections;

public class Challenge_CheckActivity extends Activity {
    private TextView monthYearText;
    String time, kcal, menu;
    private CalendarDay date;
    Cursor cursor;
    Button chall_checkBtn;
    MaterialCalendarView calendarView;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String usercode, dateR, datee;
    private String strDate;
    private FirebaseFirestore firebaseFirestore;
    int sumCount = 0;
    String[] dateqd;
    int i = 0;
    private int btnstate = 0;
    final String TAG = "MainActivity";
    ProgressDialog customProgressDialog;
    ImageView imageViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_challenge_check);

        chall_checkBtn = findViewById(R.id.chall_checkbutton);

        imageViewX = (ImageView) findViewById(R.id.imageView3);
        imageViewX.setColorFilter(Color.parseColor("#132F7E"), PorterDuff.Mode.SRC_IN);


        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2021, 8, 1))   //달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) //달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setPadding(0, -20, 0, 30);
        calendarView.setArrowColor(Color.rgb(19, 47, 126));

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

//        customProgressDialog.show();
//        customProgressDialog.setCancelable(false);

        //챌린지 인증 버튼
        chall_checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}