package com.idowedo.firstproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.idowedo.firstproject3.Login.LoginActivity;
import com.idowedo.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Findpassword extends AppCompatActivity {
    ImageView imgarrow;
    EditText findemail;
    Button but_findpassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        findemail = (EditText) findViewById(R.id.findemail);
        but_findpassword = (Button) findViewById(R.id.but_findpassword);
        firebaseAuth = FirebaseAuth.getInstance();

        imgarrow = findViewById(R.id.imageViewarrow);
        imgarrow.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);

        String eamilAddress=findemail.getText().toString();

        //버튼 클릭시 회원가입시 등록하 이메일로 메일을 보내서 재인증함.
        but_findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eamilAddress = findemail.getText().toString().trim();

                firebaseAuth.sendPasswordResetEmail(eamilAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Findpassword.this, "해당 메일로 이메일을 보냈습니다 확인해주세요!", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Findpassword.this, "메일이 올바른지 다시 확인해주세요!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        imgarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Findpassword.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}