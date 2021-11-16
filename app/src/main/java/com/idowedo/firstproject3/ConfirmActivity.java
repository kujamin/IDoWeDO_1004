package com.idowedo.firstproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmActivity extends AppCompatActivity {
    private Button challBtn;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private FirebaseFirestore firebaseFirestore;
    private String usercode, dateR, startDate, endDate;
    private int year, month, dayy, count = 0;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        challBtn = findViewById(R.id.challConfirmBtn);

        //usercode 얻어오기
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());

                firebaseFirestore.collection("challenge").document("자격증 취득하기").collection("challenge list").document(usercode)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            startDate = document.getString("chall_StartDate");
                            endDate = document.getString("chall_EndDate");

                            calendar = Calendar.getInstance();

                            String[] time = endDate.split("-");
                            int year = Integer.parseInt(time[0]);
                            int month = Integer.parseInt(time[1]);
                            int dayy = Integer.parseInt(time[2]);

                            calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.set(year, month + 1, dayy + 1);

                            Toast.makeText(getApplicationContext(), year + "/" +month+1 +"/"+dayy+1, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("자격증 취득하기").collection("OX")
                        .whereEqualTo("userChallStudy_OX", "O").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count += 1;//사용자의 챌린지 참여 횟수
                            }//for
                            Toast.makeText(getApplicationContext(), Calendar.getInstance().after(calendar) + "", Toast.LENGTH_LONG).show();
                            //시작 기간으로부터 30일이 지나면 참여 완료 버튼 막기
                            if(Calendar.getInstance().after(calendar)) {
                                challBtn.setEnabled(false);
                                challBtn.setText("챌린지 기간 종료");
                                if(count == 30) { //챌린지 성공 시
                                    Toast.makeText(getApplicationContext(), "축하합니다! 자격증 취득하기 챌린지를 성공했습니다!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "아쉽게도 챌린지 성공에 실패했습니다!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }//if
                    }//onComplete
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //참여 완료 버튼 클릭 시
        challBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), startDate+"/"+ endDate, Toast.LENGTH_LONG).show();
                AlertDialog.Builder myAlertBuilder =
                        new AlertDialog.Builder(v.getContext());
                // alert의 Messege 세팅
                myAlertBuilder.setMessage("정말 달성하셨나요?!");
                // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
                myAlertBuilder.setPositiveButton("네!",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        // OK 버튼을 눌렸을 경우
                        String year = String.valueOf(CalendarDay.today().getYear());
                        String month = String.valueOf(CalendarDay.today().getMonth() + 1);
                        String day = String.valueOf(CalendarDay.today().getDay());

                        if (month.length() != 2) {
                            month = 0 + month;
                        }
                        if (day.length() != 2){
                            day = 0 + day;
                        }

                        dateR = year + "-" + month + "-" + day;

                        Map<String, Object> doc = new HashMap<>();
                        doc.put("userChallStudy_OX", "O");
                        doc.put("userCode", usercode);
                        doc.put("today_date", dateR);

                        firebaseFirestore.collection("user").document(usercode)
                                .collection("user challenge").document("자격증 취득하기").collection("OX").document(dateR).set(doc)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "오늘의 참여 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }
                });
                myAlertBuilder.setNegativeButton("아니요..", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
                myAlertBuilder.show();


            }
        });

    }
}