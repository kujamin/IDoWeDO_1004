package com.example.firstproject3.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtName, mEtRePwd, mEtEmail, mEtPwd; //회원가입 입력필드
    private TextView mTextPwdError;
    private Button mBtnRegister; // 회원가입 버튼
    private FirebaseFirestore firebaseFirestore;
    com.example.firstproject3.Login.ProgressDialog customProgressDialog;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //로딩창 객체 생성
        customProgressDialog = new com.example.firstproject3.Login.ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("idowedo");

        mEtEmail = findViewById(R.id.et_email);
        mEtName = findViewById(R.id.et_name);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtRePwd = findViewById(R.id.et_repwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mTextPwdError = findViewById(R.id.textPwdError);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 진행
                String strName = mEtName.getText().toString();
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strRePwd = mEtRePwd.getText().toString();


                if (strPwd.equals(strRePwd)) {
                    customProgressDialog.show();
                    customProgressDialog.setCancelable(false);

                    // Firebase Auth 진행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                customProgressDialog.dismiss();

                                //realtimer database
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();
                                account.setIdtoken(firebaseUser.getUid());
                                account.setEmailid(firebaseUser.getEmail());
                                account.setUsername(strName);
                                account.setRepassword(strRePwd);
                                account.setPassword(strPwd);
                                account.setNickname(null);
                                account.setCoin(200);
                                account.setExp(1);
                                account.setHeart(3);
                                account.setLevel(1);
                                account.setMaxexp(30);

                                // setValue : database에 insert 행위
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);


                                //firestore
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

                                        Map<String, Object> userState = new HashMap<>();
                                        userState.put("coin", "0");
                                        userState.put("exp", "0");
                                        userState.put("heart", "3");

                                        //사용자 경험치, 목숨, 코인 상태 저장
                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").set(userState)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                        Map<String, Object> doc = new HashMap<>();
                                        doc.put("buy", "X");
                                        doc.put("category", "");
                                        doc.put("name", "");
                                        doc.put("price", "");

                                        //옷 세트 1
                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c1_torse").set(doc)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c1_leg").set(doc)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                        //옷 세트 2
                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c2_torse").set(doc)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c2_leg").set(doc)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                        //옷 세트 3
                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c3_torse").set(doc)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c3_leg").set(doc)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                    }
                                });


                                Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                customProgressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                } else {
                    mTextPwdError.setVisibility(View.VISIBLE);
                }
            }

            });


        }
    }