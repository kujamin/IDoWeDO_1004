package com.idowedo.firstproject3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private String string1 = "참가 중...";

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

        ImageView chall_check = findViewById(R.id.chall_check);

        Intent intent = getIntent();
        chall_Text = intent.getStringExtra("chall_title");
        String chall_Img = intent.getStringExtra("chall_img");

        firebaseFirestore = FirebaseFirestore.getInstance();

        imgarrow = findViewById(R.id.imageViewarrow);
        imgarrow.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);   //imgarrow 이미지의 색상 변경

        textChallAttend = findViewById(R.id.textViewAttend);

        //usercode 얻어오기
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());

                //참가 여부 알아보기
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
                                        textChallAttend.setText(string1);
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
            textChallplan.setText("한 달 안에 자격증을 취득하세요!\n\n* 어떤 자격증이든 상관 없습니다.\n\n자격증 취득 후 취득 결과를 사진으로 찍어 인증\n\n챌린지 기간 : 30일");
            textChallnotice.setText("* 주의사항 : 사진을 찍은 후 X를 누르고 나오면 인증이 되지 않아요. \n\n 보상 : 500코인 + 레어 의상(선택 가능) 지급");
        }
        else if(chall_Text.equals("아침 6시 기상하기")){
            textChallplan.setText("휴대폰 내 메모장 어플 open → \n\n주어진 문장을 메모장에 입력 후 화면 캡쳐 → \n\n다시 어플로 돌아온 후 이미지 업로드 버튼을 눌러 캡쳐 화면을 선택 →\n\n인식 버튼 클릭 후 인증 버튼을 누르세요!\n\n챌린지 기간 : 30일");
            textChallnotice.setText("* 주의사항 : 인증 버튼을 누르지 않으면 인증이 되지 않아요. \n\n 보상 : 500코인 + 레어 의상(선택 가능) 지급");
        }
        else {
            textChallplan.setText("한 달 동안 매일 만보 걷기에 도전하세요!\n\n만보를 채우고 인증하기 버튼을 눌러 인증하세요.\n\n챌린지 기간 : 30일");
            textChallnotice.setText("* 걸음 수를 측정 가능한 어플과 함께하면\n더 좋아요!\n\n보상 : 100코인 지급 + 레어 의상(선택 가능) 지급");
        }
        Glide.with(getApplicationContext()).load(chall_Img).into(imgChall);


        chall_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chall_Text.equals("자격증 취득하기")) {
                    Intent intent = new Intent(ClickTransActivity.this, Challenge_Study_Activity.class);
                    startActivity(intent);
                } else if (chall_Text.equals("아침 6시 기상하기")) {
                    Intent intent = new Intent(ClickTransActivity.this, Challenge_Wakeup_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ClickTransActivity.this, Challenge_CheckActivity.class);
                    startActivity(intent);
                }
            }
        });


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
                            startActivity(new Intent(ClickTransActivity.this, AchievementPopupActivity.class));                        }
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
                userChall.put("show", "false");

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