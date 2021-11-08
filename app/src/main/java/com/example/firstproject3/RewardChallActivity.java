package com.example.firstproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstproject3.Login.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        imgPink = findViewById(R.id.imagePurple);

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
            case "자격증 취득하기" :
                break;
            case "아침 6시 기상하기" :
                imgPink.setImageResource(R.drawable.pink_removebg_preview);
                imgPurple.setImageResource(R.drawable.puple_removebg_preview);
                break;
            case "매일 만보 걷기" :
                break;
        }

        imgPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                firebaseFirestore.collection("user").document(userCode).collection("user character")
//                        .document("state").collection("store").document("reward").collection("r1_head").set
            }
        });

        imgPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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