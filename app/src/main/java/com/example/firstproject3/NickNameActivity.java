package com.example.firstproject3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject3.Login.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class NickNameActivity extends AppCompatActivity {
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase; //실시간 데이터베이스
    public String strNickname;

    EditText editNickName;
    TextView textNickError;
    ImageView imageNickArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nick_name);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        createGUI();

        editNickName = (EditText) findViewById(R.id.editNickName);
        textNickError = (TextView) findViewById(R.id.textNickError);
        imageNickArrow = (ImageView) findViewById(R.id.imageNickArrow);

        imageNickArrow.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);


        editNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = editNickName.getText().toString();
                if(input.length() >= 8){
                    textNickError.setVisibility(View.VISIBLE);
                }

                if(input.length() < 8){
                    textNickError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageNickArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strNickname = editNickName.getText().toString();

                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserAccount group = dataSnapshot.getValue(UserAccount.class);
                        String nickname = (group.getNickname());
                        nickname = strNickname;
                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("nickname").setValue(nickname);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void createGUI() {
    }

}