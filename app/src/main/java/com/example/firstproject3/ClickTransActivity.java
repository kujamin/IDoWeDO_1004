package com.example.firstproject3;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.daily.CalListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClickTransActivity extends AppCompatActivity {
    ImageView imgarrow;
    TextView textChallAttend;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String usercode, chall_Text, id, dateR, endDate;
    public void onClickBack(View v) {
        finish();
    }
    private Calendar calendar;
    private int Eyear, Emonth, Edayy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_trans);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        ImageView imgChall = findViewById(R.id.imageViewChall);
        TextView textChallname = findViewById(R.id.textViewChallname);
        TextView textChallplan = findViewById(R.id.textViewChallexplan);
        TextView textChallnotice = findViewById(R.id.textViewChallnotice);

        Intent intent = getIntent();
        chall_Text = intent.getStringExtra("chall_title");
        String chall_Img = intent.getStringExtra("chall_img");

        firebaseFirestore = FirebaseFirestore.getInstance();

        imgarrow = findViewById(R.id.imageViewarrow);
        textChallAttend = findViewById(R.id.textViewAttend);

        imgarrow.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);

        //usercode 얻어오기
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());

                /////참가 여부 알아보기////
                firebaseFirestore.collection("challenge").document(chall_Text).collection("challenge list")
                        .whereEqualTo("challenge_id", usercode)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        id = document.getString("challenge_id");
                                    }
                                    if (usercode.equals(id)) {
                                        textChallAttend.setText("참가 중...");
                                        textChallAttend.setEnabled(false);
                                    } else {
                                        textChallAttend.setText("참가하기");
                                        textChallAttend.setEnabled(true);
                                    }

                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textChallname.setText(chall_Text);

        if(chall_Text.equals("자격증 취득하기")){
            textChallplan.setText("한 달 동안 자격증 하나 취득해보세요!\n자격증 취득 후 취득결과를 사진으로 찍어주세요.\n\n챌린지 기간 : 30일");
            textChallnotice.setText("보상 : 100코인 지급\n");
        }
        else if(chall_Text.equals("아침 6시 기상하기")){
            textChallplan.setText("한 달 동안 아침 6시에 뜨는 알림 팝업으로 어플에 들어와서\n미션 완료 버튼을 클릭해보세요.\n\n챌린지 기간 : 30일");
            textChallnotice.setText("보상 : 100코인 + 레어 의상(선택 O) 지급");
        }
        else {
            textChallplan.setText("한 달 동안 매일 만보씩 걸어보세요!\n만보를 채운 후 미션 완료 버튼을 클릭해주세요.\n\n챌린지 기간 : 30일");
            textChallnotice.setText("보상 : 100코인 지급");
        }
        Glide.with(getApplicationContext()).load(chall_Img).into(imgChall);

        //코인 색상
        TextView textSendTonotice = findViewById(R.id.textViewChallnotice);

        String content = textSendTonotice.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word_coin = "코인";
        int start = content.indexOf(word_coin);
        int end = start + word_coin.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ece342")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textSendTonotice.setText(spannableString);

        //참가하기 버튼 누르면
        textChallAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("challengepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int value = snapshot.getValue(Integer.class);
                        value += 1;
                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("challengepoint").setValue(value);
                        if(value == 1)
                        {
                            Toast.makeText(getApplicationContext(), "획득한 배지가 있어요! 확인하러 가세요",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String year = String.valueOf(CalendarDay.today().getYear());
                String month = String.valueOf(CalendarDay.today().getMonth() + 1);
                String day = String.valueOf(CalendarDay.today().getDay());

                if (month.length() != 2) {
                    month = 0 + month;
                }
                if (day.length() != 2){
                    day = 0 + day;
                }

                //시작 날짜
                dateR = year + "-" + month + "-" + day;

                //종료 날짜
                calendar = Calendar.getInstance();

                String[] time = dateR.split("-");
                Eyear = Integer.parseInt(time[0]);
                Emonth = Integer.parseInt(time[1]);
                Edayy = Integer.parseInt(time[2]);

                calendar.set(Eyear, Emonth-1, Edayy);
                calendar.add(Calendar.DATE, 30);

                Date getTime = calendar.getTime();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                endDate = simpleDateFormat.format(getTime);

                Map<String, Object> doc = new HashMap<>();
                doc.put("challenge_id", usercode);
                doc.put("chall_StartDate", dateR);
                doc.put("chall_EndDate", endDate);

                firebaseFirestore.collection("challenge").document(chall_Text).collection("challenge list").document(usercode).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });

                Map<String, Object> userChall = new HashMap<>();
                userChall.put("userChall_title", chall_Text);
                userChall.put("userCode", usercode);

                firebaseFirestore.collection("user").document(usercode).collection("user challenge").document(chall_Text).set(userChall)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                startActivity(new Intent(ClickTransActivity.this, PopupActivity.class));

                textChallAttend.setText("참가 중...");
            }
        });

    }//onCreate

}