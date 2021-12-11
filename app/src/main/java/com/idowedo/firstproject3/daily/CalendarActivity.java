package com.idowedo.firstproject3.daily;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idowedo.firstproject3.EventDecorator;
import com.idowedo.firstproject3.Login.ProgressDialog;
import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView listcal;
    private ArrayList<CalListActivity> cal_list;
    private CustomCalendar calAdapter;
    private MaterialCalendarView calendarView;
    private String strDate;
    final String TAG = "MainActivity";
    private String userCode;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    ProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Toolbar mToolbar = (Toolbar) findViewById(R.id.Calendar_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        String sDY = String.valueOf(CalendarDay.today().getYear());
        String sDM = String.valueOf(CalendarDay.today().getMonth()+1);
        String sDD = String.valueOf(CalendarDay.today().getDay());

        //오늘 날짜 얻어오는 구문
        if (sDM.length() != 2) {
            sDM = 0 + sDM;
        }
        if (sDD.length() != 2){
            sDD = 0 + sDD;
        }

        strDate = sDY + "/" + sDM + "/" + sDD;

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        //커스텀 달력에서 주말에만 커스텀 적용하기
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2021, 8, 1))   //달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) //달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setArrowColor(Color.rgb(244,56,94));


        //달력 실행될 때 오늘 날짜가 select되게 함
        calendarView.setSelectedDate(CalendarDay.today());


        listcal = findViewById(R.id.listcal);
        listcal.setHasFixedSize(true);
        listcal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        cal_list = new ArrayList<CalListActivity>();
        calAdapter = new CustomCalendar(cal_list, getApplicationContext());

        customProgressDialog.show();
        customProgressDialog.setCancelable(false);

        //firebase에 존재하는 유저계정에서 지금 로그인 중인 유저의 이메일 하위 목록에 데이터를 가져옴
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                userCode = (group.getEmailid());

                //해당 계정에 todo_date에서 오늘 날짜랑 같은 date에 존재하는 문서만 보여줄수 있도록 함
                firebaseFirestore.collection("user").document(userCode).collection("user todo")
                        .whereEqualTo("todo_date", strDate)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    customProgressDialog.dismiss();

                                    cal_list.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        cal_list.add(0, new CalListActivity((String)document.getData().get("todo_title"),(String)document.getData().get("todo_time")));
                                    }
                                    calAdapter.notifyDataSetChanged();
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                //달력에서 보이는 날짜 중에서 옛날이나 미래에 예정된 날짜에 해당하는 todo가 존재하면 빨간점과 함께 일정의 제목, 시간을 띄워주는 구문
                firebaseFirestore.collection("user").document(userCode).collection("user todo")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("todo_title") != null) {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                            String strd = document.getString("todo_date");
                                            Date dated = null;

                                            try {
                                                dated = dateFormat.parse(strd);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            CalendarDay day = CalendarDay.from(dated);
                                            calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(day)));
                                        }
                                    }
                                }
                                else{
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listcal.setAdapter(calAdapter);

        //캘린더뷰 클릭 시 이벤트
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String month = String.valueOf(date.getMonth() + 1);
                String day = String.valueOf(date.getDay());

                if (month.length() != 2) {
                    month = 0 + month;
                }
                if (day.length() != 2){
                    day = 0 + day;
                }

                strDate = date.getYear() + "/" + month + "/" + day;

                //달력에서 날짜를 클릭하면 해당날짜에 존재하는 todo가 제목과 시간으로 list에 뜨게됨
                firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("user").document(userCode).collection("user todo")
                        .whereEqualTo("todo_date", strDate)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    cal_list.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("todo_title"));
                                        cal_list.add(0, new CalListActivity((String)document.getData().get("todo_title"),(String)document.getData().get("todo_time")));
                                    }
                                    calAdapter.notifyDataSetChanged();
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                listcal.setAdapter(calAdapter);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}