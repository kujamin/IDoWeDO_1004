package com.example.firstproject3.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstproject3.MainActivity;
import com.example.firstproject3.NickNameActivity;
import com.example.firstproject3.R;
import com.example.firstproject3.daily.CalListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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

        mEtEmail = findViewById(R.id.et_email); //이메일
        mEtName = findViewById(R.id.et_name);   //이름
        mEtPwd = findViewById(R.id.et_pwd);     //비밀번호
        mEtRePwd = findViewById(R.id.et_repwd); //비밀번호 확인
        mBtnRegister = findViewById(R.id.btn_register); // 등록버튼
        mTextPwdError = findViewById(R.id.textPwdError);  //비밀번호 재입력 오류메세지

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 진행
                String strName = mEtName.getText().toString();
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strRePwd = mEtRePwd.getText().toString();

                if(strName.length() > 0 && strEmail.length() > 0 && strPwd.length() > 0 && strRePwd.length() > 0) {
                joinstart(strName, strEmail, strPwd, strRePwd);
                } else if (strName.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (strEmail.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (strPwd.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(RegisterActivity.this, "빠진 항목이 없는지 다시 확인해주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void joinstart(String strName, String strEmail, String strPwd, String strRePwd) {
        if (strPwd.equals(strRePwd)) {
            customProgressDialog.show();
            customProgressDialog.setCancelable(false);
            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    { customProgressDialog.dismiss();

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(RegisterActivity.this, "e-mail 형식에 맞지 않아요!", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthUserCollisionException e) {
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 email 이에요!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "다시 확인해주세요!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
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
                                Log.d(TAG, "DocumentSnapshot data");
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

                                //사용자 경험치, 목숨, 코인 상태 저장
                                Map<String, Object> userState = new HashMap<>();
                                userState.put("coin", "200");
                                userState.put("exp", "0");
                                userState.put("heart" , "3");
                                userState.put("level", "1");
                                userState.put("maxExp", "30");

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

                                //캐릭터 옷 상태 저장
                                Map<String, Object> cloState = new HashMap<>();
                                cloState.put("cloHead", "");
                                cloState.put("cloTorso", "");
                                cloState.put("cloLeg", "");
                                cloState.put("cloArm", "");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("deco").set(cloState)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                //옷 세트 1
                                Map<String, Object> doc1 = new HashMap<>();
                                doc1.put("buy", "X");
                                doc1.put("category", "torse");
                                doc1.put("name", "basic_torso_01");
                                doc1.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c1_torse").set(doc1)
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

                                Map<String, Object> doc2 = new HashMap<>();
                                doc2.put("buy", "X");
                                doc2.put("category", "leg");
                                doc2.put("name", "basic_leg_01");
                                doc2.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c1_leg").set(doc2)
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
                                Map<String, Object> doc3 = new HashMap<>();
                                doc3.put("buy", "X");
                                doc3.put("category", "head");
                                doc3.put("name", "business_head_01");
                                doc3.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c2_head").set(doc3)
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

                                Map<String, Object> doc4 = new HashMap<>();
                                doc4.put("buy", "X");
                                doc4.put("category", "torso");
                                doc4.put("name", "business_torso_01");
                                doc4.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c2_torso").set(doc4)
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

                                Map<String, Object> doc5 = new HashMap<>();
                                doc5.put("buy", "X");
                                doc5.put("category", "leg");
                                doc5.put("name", "business_leg_01");
                                doc5.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c2_leg").set(doc5)
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
                                Map<String, Object> doc6 = new HashMap<>();
                                doc6.put("buy", "X");
                                doc6.put("category", "head");
                                doc6.put("name", "formal_head_01");
                                doc6.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c3_head").set(doc6)
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

                                Map<String, Object> doc7 = new HashMap<>();
                                doc6.put("buy", "X");
                                doc6.put("category", "torso");
                                doc6.put("name", "formal_torso_01");
                                doc6.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c3_torso").set(doc7)
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

                                Map<String, Object> doc8 = new HashMap<>();
                                doc6.put("buy", "X");
                                doc6.put("category", "leg");
                                doc6.put("name", "formal_leg_01");
                                doc6.put("price", "100");

                                firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c3_leg").set(doc8)
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
                        Toast.makeText(RegisterActivity.this, "회원가입에 성공했어요!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                };
            });

        }else{
            customProgressDialog.dismiss();
            mTextPwdError.setVisibility(View.VISIBLE);
        }
    }}
