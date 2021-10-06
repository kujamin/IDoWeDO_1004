package com.example.firstproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstproject3.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Logout_Popup extends Activity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logout_popup);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Button btn_logout_yes = findViewById(R.id.btn_logout_yes);
        Button btn_logout_no = findViewById(R.id.btn_logout_no);

        btn_logout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //취소하기
                finish();
            }
        });

        btn_logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그아웃 하기
                mFirebaseAuth.signOut();

                Intent intent = new Intent(Logout_Popup.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}