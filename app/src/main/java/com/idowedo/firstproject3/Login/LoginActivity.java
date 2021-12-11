package com.idowedo.firstproject3.Login;

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

import com.idowedo.firstproject3.Findpassword;
import com.idowedo.firstproject3.MainActivity;
import com.idowedo.firstproject3.NickNameActivity;
import com.idowedo.firstproject3.R;
import com.idowedo.firstproject3.usercode;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton btn_google;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private GoogleSignInClient googleSignInClient;    //구글 api 클라이언트 객체
    private static final int RED_SIGN_GOOGLE = 9001; // 구글로그인 결과 코드

    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; // 로그인 입력필드
    private FirebaseFirestore firebaseFirestore;
    String id = UUID.randomUUID().toString();
    private String TAG = "MainActivity";
    public String userCode;
    public String strEmail, nick;
    private Bundle bundle;
    public com.idowedo.firstproject3.usercode usercode;
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

        //구글 로그인을 위한 인증 서비스
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
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

                //이메일과 비밀번호를 반드시 입력해야 넘어갈수 있도록함
                if (strEmail.length() > 0 && strPwd.length() > 0) {
                    mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (customProgressDialog != null) {
                                    customProgressDialog.dismiss();
                                    customProgressDialog = null;
                                }
                                // 그에 따른 로직
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

                                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                                                //로그인을 할때 닉네임의 존재 여부를 통해서 닉네임이 = null 이면 신규유저 null이 아니면 기존유저이므로 신규유저만 닉네임 액티비티로 이동할수있게함
                                                mDatabaseRef.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        UserAccount group = dataSnapshot.getValue(UserAccount.class);
                                                        String nickname = (group.getNickname());
                                                        nick = nickname;

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
                    if (customProgressDialog != null) {
                        customProgressDialog.dismiss();
                        customProgressDialog = null;
                    }
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

        TextView tv_findPW = (TextView) findViewById(R.id.tv_findPW);
        tv_findPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 비밀번호 찾는 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, Findpassword.class);
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
                                account.setLvAchieve(0);
                                account.setCoinAchieve(0);

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

                                        //할 일
                                        firebaseFirestore.collection("user").document(strEmail).collection("user todo").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        ////출석체크 생성////
                                        firebaseFirestore.collection("user").document(strEmail).collection("user attendance").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        //습관
                                        firebaseFirestore.collection("user").document(strEmail).collection("user habbit").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        //타이머
                                        firebaseFirestore.collection("user").document(strEmail).collection("user timer").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        //챌린지
                                        firebaseFirestore.collection("user").document(strEmail).collection("user challenge").document("blank").set(data2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        //캐릭터
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

                                        /////////챌린지 보상 의상

                                        /////핑크 잠옷/////
                                        Map<String, Object> r1 = new HashMap<>();
                                        r1.put("buy", "X");
                                        r1.put("category", "head");
                                        r1.put("name", "sleep_head_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r1_head").set(r1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r2 = new HashMap<>();
                                        r2.put("buy", "X");
                                        r2.put("category", "torse");
                                        r2.put("name", "sleep_torse_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r1_torso").set(r2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r3 = new HashMap<>();
                                        r3.put("buy", "X");
                                        r3.put("category", "leg");
                                        r3.put("name", "sleep_leg_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r1_leg").set(r3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        //////보라 잠옷////
                                        Map<String, Object> r4 = new HashMap<>();
                                        r4.put("buy", "X");
                                        r4.put("category", "head");
                                        r4.put("name", "sleep_head_02");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r2_head").set(r4).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r5 = new HashMap<>();
                                        r5.put("buy", "X");
                                        r5.put("category", "torso");
                                        r5.put("name", "sleep_torso_02");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r2_torso").set(r5).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r6 = new HashMap<>();
                                        r6.put("buy", "X");
                                        r6.put("category", "leg");
                                        r6.put("name", "sleep_leg_02");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r2_leg").set(r6).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        ////골프복///
                                        Map<String, Object> r7 = new HashMap<>();
                                        r7.put("buy", "X");
                                        r7.put("category", "head");
                                        r7.put("name", "golf_head_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r3_head").set(r7).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r8 = new HashMap<>();
                                        r8.put("buy", "X");
                                        r8.put("category", "torso");
                                        r8.put("name", "golf_torso_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r3_torso").set(r8).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r9 = new HashMap<>();
                                        r9.put("buy", "X");
                                        r9.put("category", "leg");
                                        r9.put("name", "golf_leg_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r3_leg_01").set(r9).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r10 = new HashMap<>();
                                        r10.put("buy", "X");
                                        r10.put("category", "leg");
                                        r10.put("name", "golf_leg_02");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r3_leg_02").set(r10).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        /////교복///
                                        Map<String, Object> r11 = new HashMap<>();
                                        r11.put("buy", "X");
                                        r11.put("category", "hand");
                                        r11.put("name", "student_hand_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r4_hand").set(r11).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r12 = new HashMap<>();
                                        r12.put("buy", "X");
                                        r12.put("category", "torso");
                                        r12.put("name", "student_torso_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r4_torso").set(r12).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r13 = new HashMap<>();
                                        r13.put("buy", "X");
                                        r13.put("category", "leg");
                                        r13.put("name", "student_leg_01");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r4_leg_01").set(r13).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        Map<String, Object> r14 = new HashMap<>();
                                        r14.put("buy", "X");
                                        r14.put("category", "leg");
                                        r14.put("name", "student_leg_02");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character")
                                                .document("state").collection("reward").document("r4_leg_02").set(r12).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        //옷 세트 1 basic
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

                                        //옷 세트 2 business
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


                                        //////옷 세트 3 formal/////
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
                                        doc7.put("buy", "X");
                                        doc7.put("category", "torso");
                                        doc7.put("name", "formal_torso_01");
                                        doc7.put("price", "100");

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
                                        doc8.put("buy", "X");
                                        doc8.put("category", "leg");
                                        doc8.put("name", "formal_leg_01");
                                        doc8.put("price", "100");

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

                                        //////옷 세트 4 hood/////
                                        Map<String, Object> doc9 = new HashMap<>();
                                        doc9.put("buy", "X");
                                        doc9.put("category", "torso");
                                        doc9.put("name", "hood_torso_01");
                                        doc9.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c4_torso").set(doc9)
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

                                        Map<String, Object> doc10 = new HashMap<>();
                                        doc10.put("buy", "X");
                                        doc10.put("category", "leg");
                                        doc10.put("name", "hood_leg_01");
                                        doc10.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c4_leg").set(doc10)
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

                                        //////옷 세트 5 kinght/////
                                        Map<String, Object> doc11 = new HashMap<>();
                                        doc11.put("buy", "X");
                                        doc11.put("category", "head");
                                        doc11.put("name", "knight_head_01");
                                        doc11.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c5_head").set(doc11)
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

                                        Map<String, Object> doc12 = new HashMap<>();
                                        doc12.put("buy", "X");
                                        doc12.put("category", "torso");
                                        doc12.put("name", "knight_torso_01");
                                        doc12.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c5_torso").set(doc12)
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

                                        Map<String, Object> doc13 = new HashMap<>();
                                        doc13.put("buy", "X");
                                        doc13.put("category", "leg");
                                        doc13.put("name", "knight_leg_01");
                                        doc13.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c5_leg").set(doc13)
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

                                        ////옷 세트 6 magician////
                                        Map<String, Object> doc14 = new HashMap<>();
                                        doc14.put("buy", "X");
                                        doc14.put("category", "head");
                                        doc14.put("name", "magician_head_01");
                                        doc14.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c6_head").set(doc14)
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

                                        Map<String, Object> doc15 = new HashMap<>();
                                        doc15.put("buy", "X");
                                        doc15.put("category", "torso");
                                        doc15.put("name", "magician_torso_01");
                                        doc15.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c6_torso").set(doc15)
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

                                        Map<String, Object> doc16 = new HashMap<>();
                                        doc16.put("buy", "X");
                                        doc16.put("category", "leg");
                                        doc16.put("name", "magician_leg_01");
                                        doc16.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c6_leg").set(doc16)
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

                                        ////옷 세트 7 rogue////
                                        Map<String, Object> doc17 = new HashMap<>();
                                        doc17.put("buy", "X");
                                        doc17.put("category", "head");
                                        doc17.put("name", "rogue_head_01");
                                        doc17.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c7_head").set(doc17)
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

                                        Map<String, Object> doc18 = new HashMap<>();
                                        doc18.put("buy", "X");
                                        doc18.put("category", "torso");
                                        doc18.put("name", "rogue_torso_01");
                                        doc18.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c7_torso").set(doc18)
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

                                        Map<String, Object> doc19 = new HashMap<>();
                                        doc19.put("buy", "X");
                                        doc19.put("category", "leg");
                                        doc19.put("name", "rogue_leg_01");
                                        doc19.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c7_leg").set(doc19)
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

                                        ////////옷 세트 8 alien1////
                                        Map<String, Object> doc20 = new HashMap<>();
                                        doc20.put("buy", "X");
                                        doc20.put("category", "head");
                                        doc20.put("name", "alien_head_01");
                                        doc20.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c8_head").set(doc20)
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

                                        Map<String, Object> doc21 = new HashMap<>();
                                        doc21.put("buy", "X");
                                        doc21.put("category", "torso");
                                        doc21.put("name", "alien_torso_01");
                                        doc21.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c8_torso").set(doc21)
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

                                        Map<String, Object> doc22 = new HashMap<>();
                                        doc22.put("buy", "X");
                                        doc22.put("category", "leg");
                                        doc22.put("name", "alien_leg_01");
                                        doc22.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c8_leg").set(doc22)
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

                                        ////////옷 세트 9 alien2////
                                        Map<String, Object> doc23 = new HashMap<>();
                                        doc23.put("buy", "X");
                                        doc23.put("category", "head");
                                        doc23.put("name", "alien_head_02");
                                        doc23.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c9_head").set(doc23)
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

                                        Map<String, Object> doc24 = new HashMap<>();
                                        doc24.put("buy", "X");
                                        doc24.put("category", "torso");
                                        doc24.put("name", "alien_torso_02");
                                        doc24.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c9_torso").set(doc24)
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

                                        Map<String, Object> doc25 = new HashMap<>();
                                        doc25.put("buy", "X");
                                        doc25.put("category", "leg");
                                        doc25.put("name", "alien_leg_02");
                                        doc25.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c9_leg").set(doc25)
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

                                        //////10 무기 세트  hand 4개
                                        Map<String, Object> doc26 = new HashMap<>();
                                        doc26.put("buy", "X");
                                        doc26.put("category", "hand");
                                        doc26.put("name", "book_hand_01");
                                        doc26.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c10_hand").set(doc26)
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

                                        Map<String, Object> doc27 = new HashMap<>();
                                        doc27.put("buy", "X");
                                        doc27.put("category", "hand");
                                        doc27.put("name", "claw_hand_01");
                                        doc27.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c11_hand").set(doc27)
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

                                        Map<String, Object> doc28 = new HashMap<>();
                                        doc28.put("buy", "X");
                                        doc28.put("category", "hand");
                                        doc28.put("name", "knife_hand_01");
                                        doc28.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c12_hand").set(doc28)
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

                                        Map<String, Object> doc29 = new HashMap<>();
                                        doc29.put("buy", "X");
                                        doc29.put("category", "hand");
                                        doc29.put("name", "staff_hand_01");
                                        doc29.put("price", "100");

                                        firebaseFirestore.collection("user").document(strEmail).collection("user character").document("state").collection("store").document("c13_hand").set(doc29)
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

                            updateUI(firebaseUser);
                        } else {
                            Toast.makeText(LoginActivity.this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}