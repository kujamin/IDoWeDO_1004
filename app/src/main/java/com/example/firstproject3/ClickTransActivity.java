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
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
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
    private String usercode, chall_Text, id, title, str1= null, str2 = null, str3 = null, getTime;
    public void onClickBack(View v) {
        finish();
    }

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


                                        ///사용자가 참여한 챌린지 이름 가져오기///
                                        firebaseFirestore.collection("user").document(usercode).collection("user challenge")
                                                .whereEqualTo("userCode", usercode)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()) {
                                                            for(QueryDocumentSnapshot document : task.getResult()) {
                                                                title = (String) document.getData().get("userChall_title");
                                                                switch (title) {
                                                                    case "자격증 취득하기" :
                                                                        str1 = "자격증 취득하기";
                                                                        break;
                                                                    case "아침 6시 기상하기" :
                                                                        str2 = "아침 6시 기상하기";
                                                                        break;
                                                                    case "매일 만보 걷기" :
                                                                        str3 = "매일 만보 걷기";
                                                                        break;
                                                                }
                                                            }
                                                       }
                                                    }
                                                });


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
        Glide.with(getApplicationContext()).load(chall_Img).into(imgChall);


        //참가하기 버튼 누르면
        textChallAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> doc = new HashMap<>();
                doc.put("challenge_id", usercode);

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