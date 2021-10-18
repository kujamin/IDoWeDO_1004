package com.example.firstproject3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firstproject3.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DeleteUser extends Activity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete_user);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseUser firebaseUser = mFirebaseAuth.getInstance().getCurrentUser();
        dataRef = mDatabase.getReference(firebaseUser.getUid());

        Button btn_delete_yes = findViewById(R.id.btn_delete_yes);
        Button btn_delete_no = findViewById(R.id.btn_delete_no);

        btn_delete_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //취소하기
                finish();
            }
        });

        btn_delete_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "회원 탈퇴가 성공했습니다.", Toast.LENGTH_SHORT).show();

                            dataRef.removeValue();
                            Intent intent = new Intent(DeleteUser.this, LoginActivity.class);
                            startActivity(intent);
                            System.exit(0);
                        } else {
                            Toast.makeText(getApplicationContext(), "회원 탈퇴를 진행하기 위해선 다시 로그인이 필요합니다!!", Toast.LENGTH_SHORT).show();
                            mFirebaseAuth.signOut();
                            Intent intent = new Intent(DeleteUser.this, LoginActivity.class);
                            startActivity(intent);
                            System.exit(0);
                        }
                    }
                });


            }
        });
    }


}