package com.idowedo.firstproject3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.idowedo.firstproject3.Login.ProgressDialog;
import com.idowedo.firstproject3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NickNameActivity extends AppCompatActivity {
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase; //실시간 데이터베이스
    private DocumentReference documentReferenceC;
    private String userCode;
    ProgressDialog customProgressDialog;

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
        mDatabase = FirebaseDatabase.getInstance().getReference("idowedo");

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        createGUI();

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        editNickName = (EditText) findViewById(R.id.editNickName);
        textNickError = (TextView) findViewById(R.id.textNickError);
        imageNickArrow = (ImageView) findViewById(R.id.imageNickArrow);
        imageNickArrow.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);
        //파이어베이스에서 유저의 정보를 계정의 고유 아이디로 식별해서 해당 유저의 정보를 추출 그중에 닉네임의 값이 null 이면 신규 이용자 null이 아니면 기존회원 가입자이므로 그것을 판단하고 액티비티를 전환해줌
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (value != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //닉네임의 길이를 통해 너무 긴 닉네임은 불가하도록 함
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = editNickName.getText().toString();
                if (input.length() >= 9) {
                    textNickError.setVisibility(View.VISIBLE);
                    imageNickArrow.setVisibility(View.INVISIBLE);
                }

                if (input.length() < 9) {
                    textNickError.setVisibility(View.INVISIBLE);
                    textNickError.setText("닉네임은 8글자 이하로 입력해주세요.");

                    imageNickArrow.setVisibility(View.VISIBLE);

                    imageNickArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customProgressDialog.show();
                            customProgressDialog.setCancelable(false);
                            //파이어베이스에서 유저의 정보를 계정의 고유 아이디로 식별해서 해당 유저의 정보를 추출 닉네임 값을 사용자가 지정한 닉네임으로 파이어베이스에 업데이트 이후 메인으로 이동
                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    customProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "일정을 생성하고 좋은 습관을 만들어보세요!", Toast.LENGTH_SHORT).show();
                                    mDatabase.child("UserAccount").child(firebaseUser.getUid()).child("nickname").setValue(editNickName.getText().toString());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });

                        }
                    });
                }

                if (input.length() == 0) {
                    textNickError.setVisibility(View.VISIBLE);
                    imageNickArrow.setVisibility(View.INVISIBLE);
                    textNickError.setText("닉네임은 1글자 이상 입력해주세요.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void createGUI() {
    }

}