package com.example.firstproject3.AtCheck;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.MainActivity;
import com.example.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class Attendance_CheckActivity extends Activity {
    private TextView monthYearText;
    String time, kcal, menu;
    private CalendarDay date;
    Cursor cursor;
    MaterialCalendarView calendarView;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String usercode, dateR, datee;
    private FirebaseFirestore firebaseFirestore;
    int sumCount = 0;
    String[] dateqd;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_attendance_check);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        firebaseFirestore = FirebaseFirestore.getInstance();

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2021, 8, 1))   //달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) //달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setPadding(0, -20, 0, 30);
        calendarView.setArrowColor(Color.rgb(244,56,94));
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

        //usercode 얻어오기
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());

                firebaseFirestore.collection("user").document(usercode).collection("user Check")
                        .whereEqualTo("checkCount", "1")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String intNum = document.getString("checkCount");
                                int ant = Integer.parseInt(intNum);

                                sumCount = sumCount + 1;
                            }
                            Toast.makeText(getApplicationContext(),sumCount+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //달력에 동그라미 남기기
                firebaseFirestore.collection("user").document(usercode).collection("user Check")
                        .whereEqualTo("checkOX", "O")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            dateqd = new String[sumCount];

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                datee = document.getString("checkDate");//체크된 날짜 얻어옴

                                if(datee != null) {
                                    dateqd[i] = datee;
                                    i++;
                                }
                            }
                            new ApiSimulator(dateqd).executeOnExecutor(Executors.newSingleThreadExecutor());
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        String [] dateee = {"2021,10,15", "2021,10,16", "2021,10,17", "2021,10,19", "2021,10,20"};
//        new ApiSimulator(dateqd).executeOnExecutor(Executors.newSingleThreadExecutor());

        //출석체크 버튼
        Button attenBtn = (Button) findViewById(R.id.checkbutton);
        attenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setSelectedDate(CalendarDay.today());
//                ArrayList<CalendarDay> dates = new ArrayList<>();
//                dates = CalendarDay.today();
//                calendarView.addDecorator(new EventDecorator(Color.rgb(244,56,94), dates, Attendance_CheckActivity.this));
                String year = String.valueOf(CalendarDay.today().getYear());
                String month = String.valueOf(CalendarDay.today().getMonth() + 1);
                String day = String.valueOf(CalendarDay.today().getDay());

                if (month.length() != 2) {
                    month = 0 + month;
                }
                if (day.length() != 2){
                    day = 0 + day;
                }
                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("datecnt").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int value = snapshot.getValue(Integer.class);
                        value += 1;
                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("datecnt").setValue(value);
                        if(value == 30 || value == 100)
                            {
                                Toast.makeText(getApplicationContext(), "획득한 배지가 있어요! 확인하러 가세요",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                dateR = year + "," + month + "," + day;

                Map<String, Object> doc = new HashMap<>();
                doc.put("checkOX", "O");
                doc.put("usercode", usercode);
                doc.put("checkDate", dateR);
                doc.put("checkCount", "1");

                firebaseFirestore.collection("user").document(usercode).collection("user Check").document(dateR).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

            }
        });
    }

    //특정 날짜에 dot
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();
            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                dates.add(day);
                calendar.set(year,month-1,dayy);
            }
            return dates;
        }
        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, Attendance_CheckActivity.this));
        }
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