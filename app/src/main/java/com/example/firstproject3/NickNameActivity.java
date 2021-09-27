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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class NickNameActivity extends AppCompatActivity {
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    EditText editNickName;
    TextView textNickError;
    ImageView imageNickArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nick_name);
        
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createGUI() {
    }

}