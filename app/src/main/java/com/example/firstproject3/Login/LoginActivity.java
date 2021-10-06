package com.example.firstproject3.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.firstproject3.usercode;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private FirebaseUser currentUser;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; // 로그인 입력필드
    private FirebaseFirestore firebaseFirestore;
    String id = UUID.randomUUID().toString();
    private String TAG = "MainActivity";
    public String userCode;
    public String strEmail;
    private Bundle bundle;
    public usercode usercode;
    public Context context;
    public static Context context_login;
    ProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();
        context_login = this;

        usercode = (usercode) getApplicationContext();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirstProject3");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);


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

                if(strEmail.length() > 0 && strPwd.length() > 0 ) {
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            customProgressDialog.dismiss();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(LoginActivity.this, "존재하지 않는 아이디 에요!", Toast.LENGTH_SHORT).show();
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

                                            // 로그인 성공
                                            Intent intent = new Intent(LoginActivity.this, NickNameActivity.class);
                                            intent.putExtra("userCode", userCode);
                                            startActivity(intent);
                                            finish(); // 현재 액티비티 파괴
                                        }
                                    }
                                }
                            });
                        }
                    }
                }); } else {
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
            }
        });




    }



}