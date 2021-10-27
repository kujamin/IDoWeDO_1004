package com.example.firstproject3.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject3.Login.ProgressDialog;
import com.example.firstproject3.Login.RegisterActivity;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.MainActivity;
import com.example.firstproject3.NickNameActivity;
import com.example.firstproject3.R;
import com.example.firstproject3.usercode;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton btn_google;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private GoogleSignInClient googleSignInClient;    //구글 api 클라이언트 객체
    private static final int RED_SIGN_GOOGLE = 100; // 구글로그인 결과 코드

    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; // 로그인 입력필드
    private FirebaseFirestore firebaseFirestore;
    String id = UUID.randomUUID().toString();
    private String TAG = "MainActivity";
    public String userCode;
    public String strEmail, nick;
    private Bundle bundle;
    public com.example.firstproject3.usercode usercode;
    public Context context;
    public static Context context_login;
    ProgressDialog customProgressDialog;

    ImageView imagelogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imagelogo = (ImageView) findViewById(R.id.imageLogo);
        imagelogo.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("915511622927-5bh9gk7go846qadakl0bo5agru5i4bv0.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        context = this.getApplicationContext();
        context_login = this;

        usercode = (usercode) getApplicationContext();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() { //구글 로그인버튼 클릭시 실행
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RED_SIGN_GOOGLE);
            }
        });

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                customProgressDialog.show();
                customProgressDialog.setCancelable(false);

                if (strEmail.length() > 0 && strPwd.length() > 0) {
                    mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                customProgressDialog.dismiss();
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    Toast.makeText(LoginActivity.this, "존재하지 않는 아이디에요!", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(LoginActivity.this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseNetworkException e) {
                                    Toast.makeText(LoginActivity.this, "Firebase NetworkException", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                firebaseFirestore.collection("user").whereEqualTo("id", strEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                userCode = String.valueOf(document.getData().get("user code"));
                                                usercode.setUsercode(strEmail);

                                                mDatabaseRef.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String value = snapshot.getValue(String.class);
                                                        nick = value;
                                                        if (nick != null) {
                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                            intent.putExtra("userCode", userCode);
                                                            startActivity(intent);
                                                            finish(); // 현재 액티비티 파괴
                                                        } else {
                                                            Intent intent = new Intent(LoginActivity.this, NickNameActivity.class);
                                                            intent.putExtra("userCode", userCode);
                                                            startActivity(intent);
                                                            finish(); // 현재 액티비티 파괴
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    customProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        TextView app_register = (TextView) findViewById(R.id.app_register);
        app_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //구글 로그인 인증을 요청했을 때 결과 값을 되돌려 받는 곳.
        super.onActivityResult(requestCode, resultCode, data);

        // 구글로그인 버튼 응답
        if (requestCode == RED_SIGN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                resultLogin(account);
            } catch (ApiException e) {

            }
        }


    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //구글 로그인에 실제로 성공을 했는지
                        if (task.isSuccessful()) { //로그인이 성공하였으면.
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();

                                account.setIdtoken(firebaseUser.getUid());
                                account.setEmailid(firebaseUser.getEmail());
                                account.setUsername(firebaseUser.getDisplayName());
                                account.setNickname(firebaseUser.getDisplayName());
                                account.setdatecnt(0);
                                account.setchallengepoint(0);
                                account.setDotodo(0);
                                account.setStorepoint(0);

                                // setValue : database에 insert 행위
                                mDatabaseRef.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                strEmail = firebaseUser.getEmail();

                                //firestore
                                Map<String, Object> data = new HashMap<>();
                                data.put("id", firebaseUser.getEmail());


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
                                        userState.put("heart", "5");
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

                            Toast.makeText(LoginActivity.this, "구글 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "구글 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}