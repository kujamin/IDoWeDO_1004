package com.example.firstproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.bottom_fragment.Fragment_Timer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    TextView timerText;
    Button stopStartButton;
    Timer timer;
    TimerTask timerTask;
    String str_time;
    int time;
    private boolean timerStarted = false, isRunning = false;
    TextView timerPause;
    private FirebaseFirestore firebaseFirestore;
    private Thread timeThread = null;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String userCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        timerText = (TextView) findViewById(R.id.timerText);
        stopStartButton = (Button) findViewById(R.id.startStopButton);
        timerPause = findViewById(R.id.timerPause);

        Intent intent = getIntent();
        String timer_id = intent.getStringExtra("timer_id");

        //String usercode = ((usercode)getApplication()).getUsercode();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                userCode = (group.getEmailid());

                DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user timer").document(timer_id);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            String recordText = (String) doc.getString("timer_record");
                            int timedb = Integer.parseInt(doc.getString("timer_time"));
//                                str_time = doc.getString("timer_time");
//                                time = Integer.parseInt(doc.getString("timer_time"));
                            timerText.setText(recordText);
                            time = timedb;
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //타이머 이미지 누르면
        stopStartButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                ConstraintLayout backGround = (ConstraintLayout) findViewById(R.id.backGround);

                if (timerStarted == false) { //시작 누르면
                    timerStarted = true;
                    isRunning = true;
                    timeThread = new Thread(new timeThread());
                    timeThread.start();

                    setButtonUI("일시정지", R.color.colorwhite);
                    backGround.setBackgroundResource(R.color.colorblack);
                    timerText.setTextColor(getResources().getColor(R.color.colorwhite));
                    ImageView imgTimer = findViewById(R.id.imageView);

                    timerPause.setVisibility(View.VISIBLE);
                    timerPause.setTextColor(getResources().getColor(R.color.colorwhite));
                    timerPause.setText("기록하기");
                } else { //일시정지 누르면
                    timerStarted = false;
                    isRunning = false;
                    setButtonUI("계속", R.color.colorPrimary);
                    timerText.setTextColor(getResources().getColor(R.color.colorPrimary));
                    backGround.setBackgroundResource(R.color.colorwhite);
                    //timerTask.cancel();
                    timerPause.setText("초기화");
                    timerPause.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });


        //초기화 버튼 누르면
        timerPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(timerStarted == false) {

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(userCode)
                            .collection("user timer").document(timer_id);

                    time = 0;
                    timerText.setText("00 : 00 : 00");
                }
                else {
                    String str = timerText.getText().toString();

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(userCode)
                            .collection("user timer").document(timer_id);

                    docRef.update("timer_record", str);
                    docRef.update("timer_time",String.valueOf(time));

                    finish();
                }
            }
        });


    }
    //oncrreater

    private void setButtonUI(String start, int color) {
        stopStartButton.setText(start);
        stopStartButton.setTextColor(ContextCompat.getColor(getApplicationContext(), color));
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int seconds = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;

            String result = String.format("%02d : %02d : %02d", hour, min, seconds);

            timerText.setText(result);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            while(isRunning) {
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message msg = new Message();
                msg.arg1 = time;
                time++;

                handler.sendMessage(msg);
            }
        }
    }

}
