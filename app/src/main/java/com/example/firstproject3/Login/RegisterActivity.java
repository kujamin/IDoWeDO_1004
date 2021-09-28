package com.example.firstproject3.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstproject3.R;
import com.example.firstproject3.daily.CalListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity
{

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; //회원가입 입력필드
    private Button mBtnRegister; // 회원가입 버튼
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("idowedo");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //회원가입 처리 진행
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                // Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {


                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            // setValue : database에 insert 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Map<String, Object> data = new HashMap<>();
                            data.put("id", strEmail);
                            data.put("password", strPwd);

                            Map<String, Object> data2 = new HashMap<>();

                                firebaseFirestore = FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("user").document(strEmail).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        firebaseFirestore.collection("user").document(strEmail).collection("user todo").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user habbit").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user timer").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user challenge").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                    }
                                });




                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다",Toast.LENGTH_SHORT).show();
                        }


                        else {
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다",Toast.LENGTH_SHORT).show();
                        }



                    }
                });
            }


        });


    }
}