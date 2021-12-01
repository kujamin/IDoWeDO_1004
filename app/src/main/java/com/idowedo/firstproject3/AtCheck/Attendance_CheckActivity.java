package com.idowedo.firstproject3.AtCheck;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Attendance_CheckActivity extends Activity {
    Button attenBtn;
    MaterialCalendarView calendarView;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String usercode;
    private String strDate;
    private FirebaseFirestore firebaseFirestore;
    private int btnstate = 0;
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_attendance_check);

        attenBtn = findViewById(R.id.checkbutton);  //출석체크하기 버튼

        String sDY = String.valueOf(CalendarDay.today().getYear());     //오늘 날짜에서 년도 얻어옴
        String sDM = String.valueOf(CalendarDay.today().getMonth()+1);  //오늘 날짜에서 월 얻어옴
        String sDD = String.valueOf(CalendarDay.today().getDay());      //오늘 날짜에서 일 얻어옴

        if (sDM.length() != 2) {
            sDM = 0 + sDM;
        }
        if (sDD.length() != 2){
            sDD = 0 + sDD;
        }

        strDate = sDY + "/" + sDM + "/" + sDD;

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        firebaseFirestore = FirebaseFirestore.getInstance();

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        //Material Calendar 커스텀 과정
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)             //달력의 시작 요일은 일요일
                .setMinimumDate(CalendarDay.from(2021, 8, 1))   //달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) //달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setPadding(0, -20, 0, 30);
        calendarView.setArrowColor(Color.rgb(244,56,94));   //다음 달, 이전 달 이동 화살표의 색깔 변경

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

        //DB에서 usercode 얻어오기
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());

                //파이어스토어 컬렉션인 user attendance에서 만들어진 문서들을 검토하면서 checkOX가 있는지 찾아서 조건문 달음.
                //조건에 맞는 해당 날에  addDecorator로 drawable 도장을 찍어줌.
                firebaseFirestore.collection("user").document(usercode).collection("user attendance")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("checkOX") != null) {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                            String strd = document.getString("checkDate");
                                            Date dated = null;

                                            try {
                                                dated = dateFormat.parse(strd);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            CalendarDay day = CalendarDay.from(dated);
                                            calendarView.addDecorator(new Attend_EventDecorator(Color.RED, Collections.singleton(day),Attendance_CheckActivity.this));
                                        }
                                    }
                                }
                                else{
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                //출석체크가 된 경우 문서에 checkDate가 있으므로 , 챌린지 인증 팝업에 있는 출석체크하기 버튼의 background와 Text가 변화됨.
                firebaseFirestore.collection("user").document(usercode).collection("user attendance")
                        .whereEqualTo("checkDate",strDate)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("checkDate") != null){
                                            btnstate = 1;
                                            attenBtn.setBackground(getDrawable(R.drawable.attencheckeddrawble));
                                            attenBtn.setText("출석완료");
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "안됨", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //출석체크 버튼을 클릭했을 때
        attenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnstate == 0) {
                    btnstate = 1;
//                    attenBtn.setEnabled(false); //버튼 비활성화
                    attenBtn.setBackground(getDrawable(R.drawable.attencheckeddrawble));
                    attenBtn.setText("출석완료");
                    calendarView.addDecorator(new Attend_EventDecorator(Color.RED, Collections.singleton(CalendarDay.today()),Attendance_CheckActivity.this));

                    //현재 로그인한 계정의 DB에서 datecnt값을 value로 가져와서 value값을 바탕으로 30일 출석 체크, 100일 출석 체크 업적 달성을 판단함.
                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("datecnt").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int value = snapshot.getValue(Integer.class);
                            value += 1;
                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("datecnt").setValue(value);
                            if (value == 30 || value == 100) {
                                Toast.makeText(getApplicationContext(), "획득한 배지가 있어요! 확인하러 가세요", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    String id = UUID.randomUUID().toString();

                    Map<String, Object> doc = new HashMap<>();
                    doc.put("checkOX", "O");
                    doc.put("id",id);
                    doc.put("usercode", usercode);
                    doc.put("checkDate", strDate);
                    doc.put("checkCount", "1");

                    firebaseFirestore.collection("user").document(usercode).collection("user attendance").document(id).set(doc)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 오늘은 출석체크가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                }
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