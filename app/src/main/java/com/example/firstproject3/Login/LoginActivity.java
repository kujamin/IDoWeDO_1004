package com.example.firstproject3.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstproject3.MainActivity;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;


public class LoginActivity extends AppCompatActivity
{

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();
        context_login = this;

        usercode = (usercode) getApplicationContext();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirstProject3");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 로그인 요청
                strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();


                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseFirestore.collection("user").whereEqualTo("id",strEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            userCode = String.valueOf(document.getData().get("user code"));
                                            usercode.setUsercode(strEmail);

                                            // 로그인 성공
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("userCode",userCode);
                                            startActivity(intent);
                                            finish(); // 현재 액티비티 파괴
                                        }

                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });




                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }


}