package com.example.firstproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.bottom_fragment.Fragment_Todo;
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

import java.util.HashMap;
import java.util.Map;

public class RewardChallActivity extends Activity {
    private String rewardTitle;
    private ImageView imgPink, imgPurple;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String userCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reward_chall);

        imgPink = findViewById(R.id.imagePink);
        imgPurple = findViewById(R.id.imagePurple);

        firebaseFirestore = FirebaseFirestore.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        rewardTitle = getIntent().getStringExtra("rewardTitle");

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                userCode = (group.getEmailid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        switch (rewardTitle) {
            case "자격증 취득하기" : //교복
                imgPink.setImageResource(R.drawable.student_01);
                imgPurple.setImageResource(R.drawable.student_02);
                break;
            case "아침 6시 기상하기" : //잠옷
                imgPink.setImageResource(R.drawable.pink_removebg_preview);
                imgPurple.setImageResource(R.drawable.puple_removebg_preview);
                break;
            case "매일 만보 걷기" :   //골프복
                imgPink.setImageResource(R.drawable.golf_01);
                imgPurple.setImageResource(R.drawable.golf_02);
                break;
        }

        imgPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rewardTitle) {
                    case "자격증 취득하기" : //교복
                        DocumentReference docH1 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r4_head");

                        DocumentReference docT1 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r4_torso");

                        DocumentReference docL1 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r4_leg_01");

                        docH1.update("buy", "O");
                        docT1.update("buy", "O");
                        docL1.update("buy", "O");

                        startActivity(new Intent(RewardChallActivity.this, Fragment_Todo.class));
                        break;
                    case "아침 6시 기상하기" : //잠옷
                        DocumentReference docH2 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r1_head");

                        DocumentReference docT2 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r1_torso");

                        DocumentReference docL2 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r1_leg");

                        docH2.update("buy", "O");
                        docT2.update("buy", "O");
                        docL2.update("buy", "O");

                        startActivity(new Intent(RewardChallActivity.this, Fragment_Todo.class));

                        break;
                    case "매일 만보 걷기" :   //골프복
                        DocumentReference docH3 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r3_head");

                        DocumentReference docT3 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r3_torso");

                        DocumentReference docL3 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r3_leg_01");

                        docH3.update("buy", "O");
                        docT3.update("buy", "O");
                        docL3.update("buy", "O");

                        startActivity(new Intent(RewardChallActivity.this, MainActivity.class));
                        break;
                }


            }
        });

        imgPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rewardTitle) {
                    case "자격증 취득하기" : //교복
                        DocumentReference docH1 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r4_head");

                        DocumentReference docT1 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r4_torso");

                        DocumentReference docL1 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r4_leg_02");

                        docH1.update("buy", "O");
                        docT1.update("buy", "O");
                        docL1.update("buy", "O");

                        startActivity(new Intent(RewardChallActivity.this, Fragment_Todo.class));
                        break;
                    case "아침 6시 기상하기" : //잠옷
                        DocumentReference docH2 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r2_head");

                        DocumentReference docT2 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r2_torso");

                        DocumentReference docL2 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r2_leg");

                        docH2.update("buy", "O");
                        docT2.update("buy", "O");
                        docL2.update("buy", "O");

                        startActivity(new Intent(RewardChallActivity.this, Fragment_Todo.class));

                        break;
                    case "매일 만보 걷기" :   //골프복
                        DocumentReference docH3 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r3_head");

                        DocumentReference docT3 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r3_torso");

                        DocumentReference docL3 = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state").collection("reward").document("r3_leg_02");

                        docH3.update("buy", "O");
                        docT3.update("buy", "O");
                        docL3.update("buy", "O");

                        Intent intent = new Intent(RewardChallActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }//onCreate

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