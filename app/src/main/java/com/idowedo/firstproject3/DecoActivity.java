package com.idowedo.firstproject3;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.R;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DecoActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String userCode;
    private FirebaseFirestore firebaseFirestore;
    private TextView saveText;
    private String cloHead, cloTorso, cloLeg, cloArm,
            rwdSleepH1 = "0", rwdSleepT1 = "0", rwdSleepL1 = "0", rwdGolfL2 = "0", rwdStuL3 = "0";
    private int imgId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deco);

        androidx.appcompat.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.Deco_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveText = findViewById(R.id.deco_saveText);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        final ImageButton imgbtnHead = findViewById(R.id.imageBtnHead);
        final ImageButton imgbtnTorso = findViewById(R.id.imageBtnTorso);
        final ImageButton imgbtnLeg = findViewById(R.id.imageBtnLeg);
        final ImageButton imgbtnArm = findViewById(R.id.imageBtnArm);

        final ImageView imgHead = findViewById(R.id.imageHead);
        final ImageView imgTorso = findViewById(R.id.imageTorso);
        final ImageView imgLeg = findViewById(R.id.imageLeg);
        final ImageView imgArm = findViewById(R.id.imageArm);

        final ImageButton imgbtnHead1 = findViewById(R.id.imageButtonHead1);
        final ImageButton imgbtnHead2 = findViewById(R.id.imageButtonHead2);
        final ImageButton imgbtnHead3 = findViewById(R.id.imageButtonHead3);
        final ImageButton imgbtnHead4 = findViewById(R.id.imageButtonHead4);
        final ImageButton imgbtnHead5 = findViewById(R.id.imageButtonHead5);
        final ImageButton imgbtnHead6 = findViewById(R.id.imageButtonHead6);
        final ImageButton imgbtnHead7 = findViewById(R.id.imageButtonHead7);
        final ImageButton imgbtnHead8 = findViewById(R.id.imageButtonHead8);
        final ImageButton imgbtnHead9 = findViewById(R.id.imageButtonHead9);
        final ImageButton imgbtnHead10 = findViewById(R.id.imageButtonHead10);
        final ImageButton imgbtnHead11 = findViewById(R.id.imageButtonHead11);
        final ImageButton imgbtnHead12 = findViewById(R.id.imageButtonHead12);

        final ImageButton imgbtnTorse1 = findViewById(R.id.imageButtonTorso1);
        final ImageButton imgbtnTorse2 = findViewById(R.id.imageButtonTorso2);
        final ImageButton imgbtnTorse3 = findViewById(R.id.imageButtonTorso3);
        final ImageButton imgbtnTorse4 = findViewById(R.id.imageButtonTorso4);
        final ImageButton imgbtnTorse5 = findViewById(R.id.imageButtonTorso5);
        final ImageButton imgbtnTorse6 = findViewById(R.id.imageButtonTorso6);
        final ImageButton imgbtnTorse7 = findViewById(R.id.imageButtonTorso7);
        final ImageButton imgbtnTorse8 = findViewById(R.id.imageButtonTorso8);
        final ImageButton imgbtnTorse9 = findViewById(R.id.imageButtonTorso9);
        final ImageButton imgbtnTorse10 = findViewById(R.id.imageButtonTorso10);
        final ImageButton imgbtnTorse11 = findViewById(R.id.imageButtonTorso11);
        final ImageButton imgbtnTorse12 = findViewById(R.id.imageButtonTorso12);

        final ImageButton imgbtnLeg1 = findViewById(R.id.imageButtonLeg1);
        final ImageButton imgbtnLeg2 = findViewById(R.id.imageButtonLeg2);
        final ImageButton imgbtnLeg3 = findViewById(R.id.imageButtonLeg3);
        final ImageButton imgbtnLeg4 = findViewById(R.id.imageButtonLeg4);
        final ImageButton imgbtnLeg5 = findViewById(R.id.imageButtonLeg5);
        final ImageButton imgbtnLeg6 = findViewById(R.id.imageButtonLeg6);
        final ImageButton imgbtnLeg7 = findViewById(R.id.imageButtonLeg7);
        final ImageButton imgbtnLeg8 = findViewById(R.id.imageButtonLeg8);
        final ImageButton imgbtnLeg9 = findViewById(R.id.imageButtonLeg9);
        final ImageButton imgbtnLeg10 = findViewById(R.id.imageButtonLeg10);
        final ImageButton imgbtnLeg11 = findViewById(R.id.imageButtonLeg11);
        final ImageButton imgbtnLeg12 = findViewById(R.id.imageButtonLeg12);

        final ImageButton imgbtnArm1 = findViewById(R.id.imageButtonArm1);
        final ImageButton imgbtnArm2 = findViewById(R.id.imageButtonArm2);
        final ImageButton imgbtnArm3 = findViewById(R.id.imageButtonArm3);
        final ImageButton imgbtnArm4 = findViewById(R.id.imageButtonArm4);
        final ImageButton imgbtnArm5 = findViewById(R.id.imageButtonArm5);
        final ImageButton imgbtnArm6 = findViewById(R.id.imageButtonArm6);
        final ImageButton imgbtnArm7 = findViewById(R.id.imageButtonArm7);
        final ImageButton imgbtnArm8 = findViewById(R.id.imageButtonArm8);
        final ImageButton imgbtnArm9 = findViewById(R.id.imageButtonArm9);
        final ImageButton imgbtnArm10 = findViewById(R.id.imageButtonArm10);
        final ImageButton imgbtnArm11 = findViewById(R.id.imageButtonArm11);
        final ImageButton imgbtnArm12 = findViewById(R.id.imageButtonArm12);

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                userCode = (group.getEmailid());

                //사용자가 저장했던 옷 띄워주기
                firebaseFirestore.collection("user").document(userCode).collection("user character").document("deco")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            cloHead = document.getString("cloHead");
                            cloTorso = document.getString("cloTorso");
                            cloLeg = document.getString("cloLeg");
                            cloArm = document.getString("cloArm");

                            Glide.with(getApplicationContext()).load(document.getString("cloHead")).into(imgHead);
                            Glide.with(getApplicationContext()).load(document.getString("cloTorso")).into(imgTorso);
                            Glide.with(getApplicationContext()).load(document.getString("cloLeg")).into(imgLeg);
                            Glide.with(getApplicationContext()).load(document.getString("cloArm")).into(imgArm);

                            if(document.getString("cloHead") != null)
                                imgHead.setVisibility(View.VISIBLE);

                            if(document.getString("cloTorso") != null)
                                imgTorso.setVisibility(View.VISIBLE);

                            if(document.getString("cloLeg") != null)
                                imgLeg.setVisibility(View.VISIBLE);

                            if(document.getString("cloArm") != null)
                                imgArm.setVisibility(View.VISIBLE);

                        }
                    }
                });

                //버튼 비활성화
                firebaseFirestore.collection("user").document(userCode).collection("user character")
                        .document("state").collection("store")
                        .whereEqualTo("buy","X")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cloName = (String) document.getData().get("name");

                                switch (cloName) {
                                    //첫번째 버튼
                                    case "business_head_01" :
                                        imgbtnHead1.setEnabled(false);
                                        imgbtnHead1.setImageResource(R.drawable.lock);
                                        imgbtnHead1.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "basic_torso_01" :
                                        imgbtnTorse1.setEnabled(false);
                                        imgbtnTorse1.setImageResource(R.drawable.lock);
                                        imgbtnTorse1.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "basic_leg_01" :
                                        imgbtnLeg1.setEnabled(false);
                                        imgbtnLeg1.setImageResource(R.drawable.lock);
                                        imgbtnLeg1.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "claw_hand_01" :
                                        imgbtnArm1.setEnabled(false);
                                        imgbtnArm1.setImageResource(R.drawable.lock);
                                        imgbtnArm1.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //두번째 버튼
                                    case "formal_head_01" :
                                        imgbtnHead2.setEnabled(false);
                                        imgbtnHead2.setImageResource(R.drawable.lock);
                                        imgbtnHead2.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "business_torso_01" :
                                        imgbtnTorse2.setEnabled(false);
                                        imgbtnTorse2.setImageResource(R.drawable.lock);
                                        imgbtnTorse2.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "business_leg_01" :
                                        imgbtnLeg2.setEnabled(false);
                                        imgbtnLeg2.setImageResource(R.drawable.lock);
                                        imgbtnLeg2.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "knife_hand_01" :
                                        imgbtnArm2.setEnabled(false);
                                        imgbtnArm2.setImageResource(R.drawable.lock);
                                        imgbtnArm2.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //세번째 버튼
                                    case "knight_head_01" :
                                        imgbtnHead3.setEnabled(false);
                                        imgbtnHead3.setImageResource(R.drawable.lock);
                                        imgbtnHead3.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "formal_torso_01" :
                                        imgbtnTorse3.setEnabled(false);
                                        imgbtnTorse3.setImageResource(R.drawable.lock);
                                        imgbtnTorse3.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "formal_leg_01" :
                                        imgbtnLeg3.setEnabled(false);
                                        imgbtnLeg3.setImageResource(R.drawable.lock);
                                        imgbtnLeg3.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "book_hand_01" :
                                        imgbtnArm3.setEnabled(false);
                                        imgbtnArm3.setImageResource(R.drawable.lock);
                                        imgbtnArm3.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //네번째 버튼
                                    case "magician_head_01" :
                                        imgbtnHead4.setEnabled(false);
                                        imgbtnHead4.setImageResource(R.drawable.lock);
                                        imgbtnHead4.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "hood_torso_01" :
                                        imgbtnTorse4.setEnabled(false);
                                        imgbtnTorse4.setImageResource(R.drawable.lock);
                                        imgbtnTorse4.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "hood_leg_01" :
                                        imgbtnLeg4.setEnabled(false);
                                        imgbtnLeg4.setImageResource(R.drawable.lock);
                                        imgbtnLeg4.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "staff_hand_01" :
                                        imgbtnArm4.setEnabled(false);
                                        imgbtnArm4.setImageResource(R.drawable.lock);
                                        imgbtnArm4.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //다섯번째 버튼
                                    case "rogue_head_01" :
                                        imgbtnHead5.setEnabled(false);
                                        imgbtnHead5.setImageResource(R.drawable.lock);
                                        imgbtnHead5.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "magician_torso_01" :
                                        imgbtnTorse5.setEnabled(false);
                                        imgbtnTorse5.setImageResource(R.drawable.lock);
                                        imgbtnTorse5.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "magician_leg_01" :
                                        imgbtnLeg5.setEnabled(false);
                                        imgbtnLeg5.setImageResource(R.drawable.lock);
                                        imgbtnLeg5.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //여섯번째 버튼
                                    case "alien_head_02" :
                                        imgbtnHead6.setEnabled(false);
                                        imgbtnHead6.setImageResource(R.drawable.lock);
                                        imgbtnHead6.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "rogue_torso_01" :
                                        imgbtnTorse6.setEnabled(false);
                                        imgbtnTorse6.setImageResource(R.drawable.lock);
                                        imgbtnTorse6.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "rogue_leg_01" :
                                        imgbtnLeg6.setEnabled(false);
                                        imgbtnLeg6.setImageResource(R.drawable.lock);
                                        imgbtnLeg6.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //일곱번째 버튼
                                    case "alien_head_01" :
                                        imgbtnHead7.setEnabled(false);
                                        imgbtnHead7.setImageResource(R.drawable.lock);
                                        imgbtnHead7.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "alien_torso_01" :
                                        imgbtnTorse7.setEnabled(false);
                                        imgbtnTorse7.setImageResource(R.drawable.lock);
                                        imgbtnTorse7.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "alien_leg_01" :
                                        imgbtnLeg7.setEnabled(false);
                                        imgbtnLeg7.setImageResource(R.drawable.lock);
                                        imgbtnLeg7.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //여덟번째 버튼
                                    case "sleep_head_01" :
                                        imgbtnHead8.setEnabled(false);
                                        imgbtnHead8.setImageResource(R.drawable.lock);
                                        imgbtnHead8.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "alien_torso_02" :
                                        imgbtnTorse8.setEnabled(false);
                                        imgbtnTorse8.setImageResource(R.drawable.lock);
                                        imgbtnTorse8.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "alien_leg_02" :
                                        imgbtnLeg8.setEnabled(false);
                                        imgbtnLeg8.setImageResource(R.drawable.lock);
                                        imgbtnLeg8.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //아홉번째 버튼
                                    case "sleep_head_02" :
                                        imgbtnHead9.setEnabled(false);
                                        imgbtnHead9.setImageResource(R.drawable.lock);
                                        imgbtnHead9.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "knight_torso_01" :
                                        imgbtnTorse9.setEnabled(false);
                                        imgbtnTorse9.setImageResource(R.drawable.lock);
                                        imgbtnTorse9.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "knight_leg_01" :
                                        imgbtnLeg9.setEnabled(false);
                                        imgbtnLeg9.setImageResource(R.drawable.lock);
                                        imgbtnLeg9.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //열번째 버튼

                                }//switch
                            }//for
                        }//if
                    }//oncomplete
                });

                //버튼 활성화
                firebaseFirestore.collection("user").document(userCode).collection("user character")
                        .document("state").collection("store")
                        .whereEqualTo("buy","O")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cloName = (String) document.getData().get("name");

                                switch (cloName) {
                                    //첫번째 버튼
                                    case "business_head_01" :
                                        imgbtnHead1.setEnabled(true);
                                        break;
                                    case "basic_torso_01" :
                                        imgbtnTorse1.setEnabled(true);
                                        break;
                                    case "basic_leg_01" :
                                        imgbtnLeg1.setEnabled(true);
                                        break;
                                    case "claw_hand_01" :
                                        imgbtnArm1.setEnabled(true);
                                        break;
                                    //두번째 버튼
                                    case "formal_head_01" :
                                        imgbtnHead2.setEnabled(true);
                                        break;
                                    case "business_torso_01" :
                                        imgbtnTorse2.setEnabled(true);
                                        break;
                                    case "business_leg_01" :
                                        imgbtnLeg2.setEnabled(true);
                                        break;
                                    case "knife_hand_01" :
                                        imgbtnArm2.setEnabled(true);
                                        break;
                                    //세번째 버튼
                                    case "knight_head_01" :
                                        imgbtnHead3.setEnabled(true);
                                        break;
                                    case "formal_torso_01" :
                                        imgbtnTorse3.setEnabled(true);
                                        break;
                                    case "formal_leg_01" :
                                        imgbtnLeg3.setEnabled(true);
                                        break;
                                    case "book_hand_01" :
                                        imgbtnArm3.setEnabled(true);
                                        break;
                                    //네번째 버튼
                                    case "magician_head_01" :
                                        imgbtnHead4.setEnabled(true);
                                        break;
                                    case "hood_torso_01" :
                                        imgbtnTorse4.setEnabled(true);
                                        break;
                                    case "hood_leg_01" :
                                        imgbtnLeg4.setEnabled(true);
                                        break;
                                    case "staff_hand_01" :
                                        imgbtnArm4.setEnabled(true);
                                        break;
                                    //다섯번째 버튼
                                    case "rogue_head_01" :
                                        imgbtnHead5.setEnabled(true);
                                        break;
                                    case "magician_torso_01" :
                                        imgbtnTorse5.setEnabled(true);
                                        break;
                                    case "magician_leg_01" :
                                        imgbtnLeg5.setEnabled(true);
                                        break;
                                    //여섯번째 버튼
                                    case "alien_head_02" :
                                        imgbtnHead6.setEnabled(true);
                                        break;
                                    case "rogue_torso_01" :
                                        imgbtnTorse6.setEnabled(true);
                                        break;
                                    case "rogue_leg_01" :
                                        imgbtnLeg6.setEnabled(true);
                                        break;
                                    //일곱번째 버튼
                                    case "alien_head_01" :
                                        imgbtnHead7.setEnabled(true);
                                        break;
                                    case "alien_torso_01" :
                                        imgbtnTorse7.setEnabled(true);
                                        break;
                                    case "alien_leg_01" :
                                        imgbtnLeg7.setEnabled(true);
                                        break;
                                    //여덟번째 버튼
                                    case "alien_torso_02" :
                                        imgbtnTorse8.setEnabled(true);
                                        break;
                                    case "alien_leg_02" :
                                        imgbtnLeg8.setEnabled(true);
                                        break;
                                    //아홉번째 버튼
                                    case "knight_torso_01" :
                                        imgbtnTorse9.setEnabled(true);
                                        break;
                                    case "knight_leg_01" :
                                        imgbtnLeg9.setEnabled(true);
                                        break;
                                }//switch
                            }//for
                        }//if
                    }//oncomplete
                });

                //reward 버튼 비활성화
                firebaseFirestore.collection("user").document(userCode).collection("user character")
                        .document("state").collection("reward")
                        .whereEqualTo("buy","X").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cloName = (String) document.getData().get("name");



                                switch (cloName) {
                                    //다섯번째
                                    case "student_hand_01" :
                                        imgbtnArm5.setEnabled(false);
                                        imgbtnArm5.setImageResource(R.drawable.lock);
                                        imgbtnArm5.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //여덟번째
                                    case "sleep_head_01" :
                                    case "sleep_head_02" :
                                        imgbtnHead8.setEnabled(false);
                                        imgbtnHead8.setImageResource(R.drawable.lock);
                                        imgbtnHead8.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //열번째
                                    case "sleep_torso_01" :
                                    case "sleep_torso_02" :
                                        imgbtnTorse10.setEnabled(false);
                                        imgbtnTorse10.setImageResource(R.drawable.lock);
                                        imgbtnTorse10.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "sleep_leg_01" :
                                    case "sleep_leg_02" :
                                        imgbtnLeg10.setEnabled(false);
                                        imgbtnLeg10.setImageResource(R.drawable.lock);
                                        imgbtnLeg10.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //열한번째
                                    case "golf_torso_01" :
                                        imgbtnTorse11.setEnabled(false);
                                        imgbtnTorse11.setImageResource(R.drawable.lock);
                                        imgbtnTorse11.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "golf_leg_01" :
                                    case "golf_leg_02" :
                                        imgbtnLeg11.setEnabled(false);
                                        imgbtnLeg11.setImageResource(R.drawable.lock);
                                        imgbtnLeg11.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    //열두번째
                                    case "student_torso_01" :
                                        imgbtnTorse12.setEnabled(false);
                                        imgbtnTorse12.setImageResource(R.drawable.lock);
                                        imgbtnTorse12.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;
                                    case "student_leg_01" :
                                    case "student_leg_02" :
                                        imgbtnLeg12.setEnabled(false);
                                        imgbtnLeg12.setImageResource(R.drawable.lock);
                                        imgbtnLeg12.setBackgroundColor(Color.parseColor("#ECECEC"));
                                        break;

                                }
                            }
                        }
                    }
                });

                //reward 버튼 활성화
                firebaseFirestore.collection("user").document(userCode).collection("user character")
                        .document("state").collection("reward")
                        .whereEqualTo("buy","O").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cloName = (String) document.getData().get("name");

                                switch (cloName) {
                                    //다섯번째
                                    case "student_hand_01" :
                                        imgbtnArm5.setEnabled(true);
                                        break;
                                    //여덟번째
                                    case "sleep_head_01" :
                                        imgbtnHead8.setEnabled(true);
                                        imgbtnHead8.setImageResource(R.drawable.sleep_head_01);
                                        imgbtnHead8.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdSleepH1 = "1";
                                        break;
                                    case "sleep_head_02" :
                                        imgbtnHead8.setEnabled(true);
                                        imgbtnHead8.setImageResource(R.drawable.sleep_head_02);
                                        imgbtnHead8.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdSleepH1 = "2";
                                        break;
                                    //열번째
                                    case "sleep_torso_01" :
                                        imgbtnTorse10.setEnabled(true);
                                        imgbtnTorse10.setImageResource(R.drawable.sleep_torso_01);
                                        imgbtnTorse10.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdSleepT1 = "1";
                                        break;
                                    case "sleep_torso_02" :
                                        imgbtnTorse10.setEnabled(true);
                                        imgbtnTorse10.setImageResource(R.drawable.sleep_torso_02);
                                        imgbtnTorse10.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdSleepT1 = "2";
                                        break;
                                    case "sleep_leg_01" :
                                        imgbtnLeg10.setEnabled(true);
                                        imgbtnLeg10.setImageResource(R.drawable.sleep_leg_01);
                                        imgbtnLeg10.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdSleepL1 = "1";
                                        break;
                                    case "sleep_leg_02" :
                                        imgbtnLeg10.setEnabled(true);
                                        imgbtnLeg10.setImageResource(R.drawable.sleep_leg_02);
                                        imgbtnLeg10.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdSleepL1 = "2";
                                        break;
                                    //열한번째
                                    case "golf_torso_01" :
                                        imgbtnTorse11.setEnabled(true);
                                        imgbtnTorse11.setImageResource(R.drawable.golf_torso_01);
                                        break;
                                    case "golf_leg_01" :
                                        imgbtnLeg11.setEnabled(true);
                                        imgbtnLeg11.setImageResource(R.drawable.golf_leg_01);
                                        imgbtnLeg11.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdGolfL2 = "1";
                                        break;
                                    case "golf_leg_02" :
                                        imgbtnLeg11.setEnabled(true);
                                        imgbtnLeg11.setImageResource(R.drawable.golf_leg_02);
                                        imgbtnLeg11.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdGolfL2 = "2";
                                        break;
                                    //열두번째
                                    case "student_torso_01" :
                                        imgbtnTorse12.setEnabled(true);
                                        imgbtnTorse12.setImageResource(R.drawable.student_torso_01);
                                        imgbtnTorse12.setBackgroundColor(Color.parseColor("#ffffff"));
                                        break;
                                    case "student_leg_01" :
                                        imgbtnLeg12.setEnabled(true);
                                        imgbtnLeg12.setImageResource(R.drawable.student_leg_01);
                                        imgbtnLeg12.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdStuL3 = "1";
                                        break;
                                    case "student_leg_02" :
                                        imgbtnLeg12.setEnabled(true);
                                        imgbtnLeg12.setImageResource(R.drawable.student_leg_02);
                                        imgbtnLeg12.setBackgroundColor(Color.parseColor("#ffffff"));
                                        rwdStuL3 = "2";
                                        break;

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

        TableRow tableRow3 = findViewById(R.id.tabletow3);
        TableRow tableRow4 = findViewById(R.id.tabletow4);

        //각각의 버튼 클릭 시 색상 변경 및 이미지 변경
        imgbtnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#ffffff"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#CACACA"));

                tableRow3.setVisibility(View.VISIBLE);
                tableRow4.setVisibility(View.GONE);

                imgbtnHead1.setVisibility(View.VISIBLE);
                imgbtnHead2.setVisibility(View.VISIBLE);
                imgbtnHead3.setVisibility(View.VISIBLE);
                imgbtnHead4.setVisibility(View.VISIBLE);
                imgbtnHead5.setVisibility(View.VISIBLE);
                imgbtnHead6.setVisibility(View.VISIBLE);
                imgbtnHead7.setVisibility(View.VISIBLE);
                imgbtnHead8.setVisibility(View.VISIBLE);
                imgbtnHead9.setVisibility(View.VISIBLE);

                imgbtnTorse1.setVisibility(View.INVISIBLE);
                imgbtnTorse2.setVisibility(View.INVISIBLE);
                imgbtnTorse3.setVisibility(View.INVISIBLE);
                imgbtnTorse4.setVisibility(View.INVISIBLE);
                imgbtnTorse5.setVisibility(View.INVISIBLE);
                imgbtnTorse6.setVisibility(View.INVISIBLE);
                imgbtnTorse7.setVisibility(View.INVISIBLE);
                imgbtnTorse8.setVisibility(View.INVISIBLE);
                imgbtnTorse9.setVisibility(View.INVISIBLE);

                imgbtnLeg1.setVisibility(View.INVISIBLE);
                imgbtnLeg2.setVisibility(View.INVISIBLE);
                imgbtnLeg3.setVisibility(View.INVISIBLE);
                imgbtnLeg4.setVisibility(View.INVISIBLE);
                imgbtnLeg5.setVisibility(View.INVISIBLE);
                imgbtnLeg6.setVisibility(View.INVISIBLE);
                imgbtnLeg7.setVisibility(View.INVISIBLE);
                imgbtnLeg8.setVisibility(View.INVISIBLE);
                imgbtnLeg9.setVisibility(View.INVISIBLE);

                imgbtnArm1.setVisibility(View.INVISIBLE);
                imgbtnArm2.setVisibility(View.INVISIBLE);
                imgbtnArm3.setVisibility(View.INVISIBLE);
                imgbtnArm4.setVisibility(View.INVISIBLE);
                imgbtnArm5.setVisibility(View.INVISIBLE);
                imgbtnArm6.setVisibility(View.INVISIBLE);
                imgbtnArm7.setVisibility(View.INVISIBLE);
                imgbtnArm8.setVisibility(View.INVISIBLE);
                imgbtnArm9.setVisibility(View.INVISIBLE);
            }
        });

        imgbtnTorso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#ffffff"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#CACACA"));

                tableRow3.setVisibility(View.VISIBLE);
                tableRow4.setVisibility(View.VISIBLE);

                imgbtnTorse1.setVisibility(View.VISIBLE);
                imgbtnTorse2.setVisibility(View.VISIBLE);
                imgbtnTorse3.setVisibility(View.VISIBLE);
                imgbtnTorse4.setVisibility(View.VISIBLE);
                imgbtnTorse5.setVisibility(View.VISIBLE);
                imgbtnTorse6.setVisibility(View.VISIBLE);
                imgbtnTorse7.setVisibility(View.VISIBLE);
                imgbtnTorse8.setVisibility(View.VISIBLE);
                imgbtnTorse9.setVisibility(View.VISIBLE);
                imgbtnTorse10.setVisibility(View.VISIBLE);
                imgbtnTorse11.setVisibility(View.VISIBLE);
                imgbtnTorse12.setVisibility(View.VISIBLE);

                imgbtnHead1.setVisibility(View.INVISIBLE);
                imgbtnHead2.setVisibility(View.INVISIBLE);
                imgbtnHead3.setVisibility(View.INVISIBLE);
                imgbtnHead4.setVisibility(View.INVISIBLE);
                imgbtnHead5.setVisibility(View.INVISIBLE);
                imgbtnHead6.setVisibility(View.INVISIBLE);
                imgbtnHead7.setVisibility(View.INVISIBLE);
                imgbtnHead8.setVisibility(View.INVISIBLE);
                imgbtnHead9.setVisibility(View.INVISIBLE);
                imgbtnHead10.setVisibility(View.INVISIBLE);
                imgbtnHead11.setVisibility(View.INVISIBLE);
                imgbtnHead12.setVisibility(View.INVISIBLE);

                imgbtnLeg1.setVisibility(View.INVISIBLE);
                imgbtnLeg2.setVisibility(View.INVISIBLE);
                imgbtnLeg3.setVisibility(View.INVISIBLE);
                imgbtnLeg4.setVisibility(View.INVISIBLE);
                imgbtnLeg5.setVisibility(View.INVISIBLE);
                imgbtnLeg6.setVisibility(View.INVISIBLE);
                imgbtnLeg7.setVisibility(View.INVISIBLE);
                imgbtnLeg8.setVisibility(View.INVISIBLE);
                imgbtnLeg9.setVisibility(View.INVISIBLE);
                imgbtnLeg10.setVisibility(View.INVISIBLE);
                imgbtnLeg11.setVisibility(View.INVISIBLE);
                imgbtnLeg12.setVisibility(View.INVISIBLE);

                imgbtnArm1.setVisibility(View.INVISIBLE);
                imgbtnArm2.setVisibility(View.INVISIBLE);
                imgbtnArm3.setVisibility(View.INVISIBLE);
                imgbtnArm4.setVisibility(View.INVISIBLE);
                imgbtnArm5.setVisibility(View.INVISIBLE);
                imgbtnArm6.setVisibility(View.INVISIBLE);
                imgbtnArm7.setVisibility(View.INVISIBLE);
                imgbtnArm8.setVisibility(View.INVISIBLE);
                imgbtnArm9.setVisibility(View.INVISIBLE);
                imgbtnArm10.setVisibility(View.INVISIBLE);
                imgbtnArm11.setVisibility(View.INVISIBLE);
                imgbtnArm12.setVisibility(View.INVISIBLE);
            }
        });

        imgbtnLeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#ffffff"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#CACACA"));

                tableRow3.setVisibility(View.VISIBLE);
                tableRow4.setVisibility(View.VISIBLE);

                imgbtnLeg1.setVisibility(View.VISIBLE);
                imgbtnLeg2.setVisibility(View.VISIBLE);
                imgbtnLeg3.setVisibility(View.VISIBLE);
                imgbtnLeg4.setVisibility(View.VISIBLE);
                imgbtnLeg5.setVisibility(View.VISIBLE);
                imgbtnLeg6.setVisibility(View.VISIBLE);
                imgbtnLeg7.setVisibility(View.VISIBLE);
                imgbtnLeg8.setVisibility(View.VISIBLE);
                imgbtnLeg9.setVisibility(View.VISIBLE);
                imgbtnLeg10.setVisibility(View.VISIBLE);
                imgbtnLeg11.setVisibility(View.VISIBLE);
                imgbtnLeg12.setVisibility(View.VISIBLE);

                imgbtnHead1.setVisibility(View.INVISIBLE);
                imgbtnHead2.setVisibility(View.INVISIBLE);
                imgbtnHead3.setVisibility(View.INVISIBLE);
                imgbtnHead4.setVisibility(View.INVISIBLE);
                imgbtnHead5.setVisibility(View.INVISIBLE);
                imgbtnHead6.setVisibility(View.INVISIBLE);
                imgbtnHead7.setVisibility(View.INVISIBLE);
                imgbtnHead8.setVisibility(View.INVISIBLE);
                imgbtnHead9.setVisibility(View.INVISIBLE);
                imgbtnHead10.setVisibility(View.INVISIBLE);
                imgbtnHead11.setVisibility(View.INVISIBLE);
                imgbtnHead12.setVisibility(View.INVISIBLE);

                imgbtnTorse1.setVisibility(View.INVISIBLE);
                imgbtnTorse2.setVisibility(View.INVISIBLE);
                imgbtnTorse3.setVisibility(View.INVISIBLE);
                imgbtnTorse4.setVisibility(View.INVISIBLE);
                imgbtnTorse5.setVisibility(View.INVISIBLE);
                imgbtnTorse6.setVisibility(View.INVISIBLE);
                imgbtnTorse7.setVisibility(View.INVISIBLE);
                imgbtnTorse8.setVisibility(View.INVISIBLE);
                imgbtnTorse9.setVisibility(View.INVISIBLE);
                imgbtnTorse10.setVisibility(View.VISIBLE);
                imgbtnTorse11.setVisibility(View.VISIBLE);
                imgbtnTorse12.setVisibility(View.VISIBLE);

                imgbtnArm1.setVisibility(View.INVISIBLE);
                imgbtnArm2.setVisibility(View.INVISIBLE);
                imgbtnArm3.setVisibility(View.INVISIBLE);
                imgbtnArm4.setVisibility(View.INVISIBLE);
                imgbtnArm5.setVisibility(View.INVISIBLE);
                imgbtnArm6.setVisibility(View.INVISIBLE);
                imgbtnArm7.setVisibility(View.INVISIBLE);
                imgbtnArm8.setVisibility(View.INVISIBLE);
                imgbtnArm9.setVisibility(View.INVISIBLE);
                imgbtnArm10.setVisibility(View.INVISIBLE);
                imgbtnArm11.setVisibility(View.INVISIBLE);
                imgbtnArm12.setVisibility(View.INVISIBLE);
            }
        });

        imgbtnArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#ffffff"));

                tableRow3.setVisibility(View.GONE);
                tableRow4.setVisibility(View.GONE);

                imgbtnArm1.setVisibility(View.VISIBLE);
                imgbtnArm2.setVisibility(View.VISIBLE);
                imgbtnArm3.setVisibility(View.VISIBLE);
                imgbtnArm4.setVisibility(View.VISIBLE);
                imgbtnArm5.setVisibility(View.VISIBLE);
                imgbtnArm6.setVisibility(View.VISIBLE);
                imgbtnArm7.setVisibility(View.VISIBLE);
                imgbtnArm8.setVisibility(View.VISIBLE);
                imgbtnArm9.setVisibility(View.VISIBLE);

                imgbtnHead1.setVisibility(View.INVISIBLE);
                imgbtnHead2.setVisibility(View.INVISIBLE);
                imgbtnHead3.setVisibility(View.INVISIBLE);
                imgbtnHead4.setVisibility(View.INVISIBLE);
                imgbtnHead5.setVisibility(View.INVISIBLE);
                imgbtnHead6.setVisibility(View.INVISIBLE);
                imgbtnHead7.setVisibility(View.INVISIBLE);
                imgbtnHead8.setVisibility(View.INVISIBLE);
                imgbtnHead9.setVisibility(View.INVISIBLE);

                imgbtnTorse1.setVisibility(View.INVISIBLE);
                imgbtnTorse2.setVisibility(View.INVISIBLE);
                imgbtnTorse3.setVisibility(View.INVISIBLE);
                imgbtnTorse4.setVisibility(View.INVISIBLE);
                imgbtnTorse5.setVisibility(View.INVISIBLE);
                imgbtnTorse6.setVisibility(View.INVISIBLE);
                imgbtnTorse7.setVisibility(View.INVISIBLE);
                imgbtnTorse8.setVisibility(View.INVISIBLE);
                imgbtnTorse9.setVisibility(View.INVISIBLE);

                imgbtnLeg1.setVisibility(View.INVISIBLE);
                imgbtnLeg2.setVisibility(View.INVISIBLE);
                imgbtnLeg3.setVisibility(View.INVISIBLE);
                imgbtnLeg4.setVisibility(View.INVISIBLE);
                imgbtnLeg5.setVisibility(View.INVISIBLE);
                imgbtnLeg6.setVisibility(View.INVISIBLE);
                imgbtnLeg7.setVisibility(View.INVISIBLE);
                imgbtnLeg8.setVisibility(View.INVISIBLE);
                imgbtnLeg9.setVisibility(View.INVISIBLE);
            }
        });

        ImageButton btnReset = findViewById(R.id.imageReset);

        //새로고침 메소드
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHead.setVisibility(View.INVISIBLE);
                imgTorso.setVisibility(View.INVISIBLE);
                imgLeg.setVisibility(View.INVISIBLE);
                imgArm.setVisibility(View.INVISIBLE);

                cloHead = "";
                cloTorso = "";
                cloLeg = "";
                cloArm = "";
            }
        });

        //옷 갈아입히기 이벤트 리스너
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = v.getId();
                Drawable drawH, drawT, drawL, drawA;
                switch (imgId) {
                    //1번 Row
                    case R.id.imageButtonHead1 :
                        drawH = imgbtnHead1.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/business_head_01.png?alt=media&token=52d39453-728b-40c0-86f7-ef40018d12ff";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso1 :
                        drawT = imgbtnTorse1.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/basic_torso_01.png?alt=media&token=906aa5a2-fca7-4a3a-8d6f-11fbf4992a5b";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg1 :
                        drawL = imgbtnLeg1.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/basic_leg_01.png?alt=media&token=b3522aef-f43e-46a3-ac1b-3d9ed2020fd6";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonArm1 :
                        drawA = imgbtnArm1.getDrawable();
                        cloArm = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/claw_arm_01.png?alt=media&token=0dbd0e5c-3f28-415f-8ea6-80d7d13a57b0";
                        imgArm.setImageDrawable(drawA);
                        imgArm.setVisibility(View.VISIBLE);
                        break;

                    //2번 Row
                    case R.id.imageButtonHead2 :
                        drawH = imgbtnHead2.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/formal_head_01.png?alt=media&token=61b34547-ef73-4d3b-905b-7aeb1076d736";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso2 :
                        drawT = imgbtnTorse2.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/business_torso_01.png?alt=media&token=5ec8c372-dc9b-44d2-b6d5-41921df8d027";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg2 :
                        drawL = imgbtnLeg2.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/business_leg_01.png?alt=media&token=426c17d3-917e-4758-a959-ccc29973da53";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonArm2 :
                        drawA = imgbtnArm2.getDrawable();
                        cloArm = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/knife_hand_01.png?alt=media&token=a90b099e-a7c7-44de-9482-892b26285fcb";
                        imgArm.setImageDrawable(drawA);
                        imgArm.setVisibility(View.VISIBLE);
                        break;

                    //3번 Row
                    case R.id.imageButtonHead3 :
                        drawH = imgbtnHead3.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/knight_head_01.png?alt=media&token=8b21a272-54a9-4fe1-a3c2-92708d34ee4f";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso3 :
                        drawT = imgbtnTorse3.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/formal_torso_01.png?alt=media&token=6734f7b7-37ed-4547-8db7-2480a11a77d6";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg3 :
                        drawL = imgbtnLeg3.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/formal_leg_01.png?alt=media&token=5aa56e1d-18b6-40b8-babe-4d2e09c80143";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonArm3 :
                        drawA = imgbtnArm3.getDrawable();
                        cloArm = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/book_hand_01.png?alt=media&token=8f8e4dab-fee2-4f07-ad3f-14f627a958fa";
                        imgArm.setImageDrawable(drawA);
                        imgArm.setVisibility(View.VISIBLE);
                        break;

                    //4번 Row
                    case R.id.imageButtonHead4 :
                        drawH = imgbtnHead4.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/magician_head_01.png?alt=media&token=d04b4319-1f71-4b97-84c4-e9e1e7d61f9a";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso4 :
                        drawT = imgbtnTorse4.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/hood_torso_01.png?alt=media&token=37e0bffa-abcf-40f3-ba44-76eca5caa4e1";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg4 :
                        drawL = imgbtnLeg4.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/hood_leg_01.png?alt=media&token=5daf5e48-6a5a-4f00-91d0-23030085e082";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonArm4 :
                        drawA = imgbtnArm4.getDrawable();
                        cloArm = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/staff_hand_01.png?alt=media&token=e15e7548-9d6e-4860-9913-3479922f492d";
                        imgArm.setImageDrawable(drawA);
                        imgArm.setVisibility(View.VISIBLE);
                        break;

                    //5번 Row
                    case R.id.imageButtonHead5 :
                        drawH = imgbtnHead5.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/rogue_head_01.png?alt=media&token=c349637a-333a-49b9-81e0-725d82903426";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso5 :
                        drawT = imgbtnTorse5.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/magician_torso_01.png?alt=media&token=1256c333-5eaa-407e-92a5-7ccc9394bea3";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg5 :
                        drawL = imgbtnLeg5.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/magician_leg_01.png?alt=media&token=a3e9edac-e375-4131-9675-e2ff45c7452c";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonArm5 :
                        drawA = imgbtnArm5.getDrawable();
                        cloArm = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/student_hand_01.png?alt=media&token=5c82d7a8-3250-4211-863e-68963d7e3c21";
                        imgArm.setImageDrawable(drawA);
                        imgArm.setVisibility(View.VISIBLE);
                        break;
                    //6번 Row
                    case R.id.imageButtonHead6:
                        drawH = imgbtnHead6.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/alien_head_02.png?alt=media&token=3881075b-1528-45f4-b5d4-51d0a7179ce1";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso6 :
                        drawT = imgbtnTorse6.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/rogue_torso_01.png?alt=media&token=70ae343c-01a9-4663-b235-be222e998d1e";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg6 :
                        drawL = imgbtnLeg6.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/rogue_leg_01.png?alt=media&token=8fc22700-2140-405e-9c10-6a13d88d9885";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;

                    //7번 Row
                    case R.id.imageButtonHead7 :
                        drawH = imgbtnHead7.getDrawable();
                        cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/alien_head_01.png?alt=media&token=10929595-ef3c-4899-be74-2928f07b9d8f";
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso7 :
                        drawT = imgbtnTorse7.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/alien_torso_01.png?alt=media&token=cabd9b15-84c8-414a-b5c9-a57c883c5675";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg7 :
                        drawL = imgbtnLeg7.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/alien_leg_01.png?alt=media&token=b750874d-43de-4df6-b9ea-aa3235c560cb";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;

                    //8번 Row
                    case R.id.imageButtonHead8 :
                        drawH = imgbtnHead8.getDrawable();
                        if(rwdSleepH1 == "1")
                            cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/sleep_head_01.png?alt=media&token=fbb4bf18-540a-4a43-b44c-01ae657155dd";
                        else if(rwdSleepH1 == "2")
                            cloHead = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/sleep_head_02.png?alt=media&token=87b60b1e-95ba-4241-aa1e-6a7aaea792d9";

                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso8 :
                        drawT = imgbtnTorse8.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/alien_torso_02.png?alt=media&token=1feac206-4ff1-4f85-9144-a48d9ff1df61";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg8 :
                        drawL = imgbtnLeg8.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/alien_leg_02.png?alt=media&token=d258be48-309f-401b-bc19-8b721f9e53ec";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;

                    //9번 Row
                    case R.id.imageButtonTorso9 :
                        drawT = imgbtnTorse9.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/knight_torso_01.png?alt=media&token=436ffb5e-ce0c-45c2-b2e4-94e21dfd8291";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg9 :
                        drawL = imgbtnLeg9.getDrawable();
                        cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/knight_leg_01.png?alt=media&token=59539f93-38c7-42af-b6bb-dfca471b9a35";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;

                    //10번 Row
                    case R.id.imageButtonTorso10 :
                        drawT = imgbtnTorse10.getDrawable();
                        if(rwdSleepT1 == "1")
                            cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/sleep_torso_01.png?alt=media&token=2af055cf-1a1d-4d9d-95dc-235784739527";
                        else if(rwdSleepT1 == "2")
                            cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/sleep_torso_02.png?alt=media&token=7beb9081-9242-4837-a4bf-c71f13379fa1";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg10 :
                        drawL = imgbtnLeg10.getDrawable();
                        if(rwdSleepL1 == "1")
                            cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/sleep_leg_01.png?alt=media&token=346d6c8e-ae39-4585-b71d-5391caf218e9";
                        else if(rwdSleepL1 == "2")
                            cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/sleep_leg_02.png?alt=media&token=79e4f807-7f45-426b-8ce2-4e916e1d9413";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;

                    //11번 Row
                    case R.id.imageButtonTorso11:
                        drawT = imgbtnTorse11.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/golf_torso_01.png?alt=media&token=6dcd4a02-66de-4d4a-9c2e-12ec86b5c17b";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg11 :
                        drawL = imgbtnLeg11.getDrawable();
                        if(rwdGolfL2 == "1")
                            cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/golf_leg_01.png?alt=media&token=ee070111-dcd7-44fd-854f-7170c368d983";
                        else if(rwdGolfL2 == "2")
                            cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/golf_leg_02.png?alt=media&token=06089a19-7972-4cbc-9780-ee155e712610";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;

                    //12번 Row
                    case R.id.imageButtonTorso12 :
                        drawT = imgbtnTorse12.getDrawable();
                        cloTorso = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/student_torso_01.png?alt=media&token=cfec737a-e061-4071-bc2b-ce027215b4b9";
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg12 :
                        drawL = imgbtnLeg12.getDrawable();
                        if(rwdStuL3 == "1")
                            cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/student_leg_01.png?alt=media&token=0465b9cf-60d5-433c-a473-3ef6ebf975d4";
                        else if(rwdStuL3 == "2")
                            cloLeg = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/student_leg_02.png?alt=media&token=47f97a7f-a6d9-4f15-a759-2133b8f77959";
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                }//switch
            }//onClick
        };//ocl

        imgbtnHead1.setOnClickListener(ocl);
        imgbtnTorse1.setOnClickListener(ocl);
        imgbtnLeg1.setOnClickListener(ocl);
        imgbtnArm1.setOnClickListener(ocl);

        imgbtnHead2.setOnClickListener(ocl);
        imgbtnTorse2.setOnClickListener(ocl);
        imgbtnLeg2.setOnClickListener(ocl);
        imgbtnArm2.setOnClickListener(ocl);

        imgbtnHead3.setOnClickListener(ocl);
        imgbtnTorse3.setOnClickListener(ocl);
        imgbtnLeg3.setOnClickListener(ocl);
        imgbtnArm3.setOnClickListener(ocl);

        imgbtnHead4.setOnClickListener(ocl);
        imgbtnTorse4.setOnClickListener(ocl);
        imgbtnLeg4.setOnClickListener(ocl);
        imgbtnArm4.setOnClickListener(ocl);

        imgbtnHead5.setOnClickListener(ocl);
        imgbtnTorse5.setOnClickListener(ocl);
        imgbtnLeg5.setOnClickListener(ocl);
        imgbtnArm5.setOnClickListener(ocl);

        imgbtnHead6.setOnClickListener(ocl);
        imgbtnTorse6.setOnClickListener(ocl);
        imgbtnLeg6.setOnClickListener(ocl);

        imgbtnHead7.setOnClickListener(ocl);
        imgbtnTorse7.setOnClickListener(ocl);
        imgbtnLeg7.setOnClickListener(ocl);

        imgbtnHead8.setOnClickListener(ocl);
        imgbtnTorse8.setOnClickListener(ocl);
        imgbtnLeg8.setOnClickListener(ocl);

        imgbtnTorse9.setOnClickListener(ocl);
        imgbtnLeg9.setOnClickListener(ocl);


        imgbtnTorse10.setOnClickListener(ocl);
        imgbtnLeg10.setOnClickListener(ocl);

        imgbtnTorse11.setOnClickListener(ocl);
        imgbtnLeg11.setOnClickListener(ocl);

        imgbtnTorse12.setOnClickListener(ocl);
        imgbtnLeg12.setOnClickListener(ocl);

        //툴바 저장 버튼
        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user character").document("deco");

                docRef.update("cloHead", cloHead);
                docRef.update("cloTorso", cloTorso);
                docRef.update("cloLeg", cloLeg);
                docRef.update("cloArm", cloArm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });
            }
        });
    }//onCreate

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