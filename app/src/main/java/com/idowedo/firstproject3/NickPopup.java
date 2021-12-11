package com.idowedo.firstproject3;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NickPopup extends Activity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase; //실시간 데이터베이스
    private DocumentReference documentReferenceC;
    private String userCode;
    private int myCoin;
    private int nickcoin = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nick_popup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();


        Button btn_nick_yes = findViewById(R.id.btn_nick_yes);
        Button btn_nick_no = findViewById(R.id.btn_nick_no);

        btn_nick_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_nick_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이어베이스에서 유저의 정보를 계정의 고유 아이디로 식별해서 해당 유저의 정보를 추출
                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserAccount group = dataSnapshot.getValue(UserAccount.class);
                        userCode = (group.getEmailid());

                        documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state");
                        //유저의 코인값을 불어와서 코인이 30개가 있어야만 동작 될 수 있도록 함.
                        documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot doc = task.getResult();
                                    myCoin = Integer.parseInt(doc.getString("coin"));
                                    if(myCoin >= 30) {
                                        documentReferenceC.update("coin", String.valueOf(myCoin - nickcoin));
                                        Intent intent = new Intent(NickPopup.this, NickNameActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //코인이 30개 이상이면 30개를 차감하고 닉네임 변경 화면으로 이동
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(),"코인이 부족합니다!", Toast.LENGTH_SHORT).show(); //부족할때 토스트창으로 안내
                                    }
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


}