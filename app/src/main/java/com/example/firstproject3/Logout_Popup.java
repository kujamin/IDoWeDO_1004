package com.example.firstproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstproject3.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logout_Popup extends Activity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logout_popup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

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
                Toast.makeText(getApplicationContext(), "로그아웃에 성공했습니다!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                mFirebaseAuth.signOut();
                Intent intent = new Intent(Logout_Popup.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        //특정 텍스트에 대한 스타일 적용
        TextView textLogoutQ = findViewById(R.id.textLogoutQ);

        String content = textLogoutQ.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "로그아웃";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#595959")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textLogoutQ.setText(spannableString);
    }
}