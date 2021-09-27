package com.example.firstproject3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DailyPopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_daily_pop);

        final TextView textViewStu = findViewById(R.id.textViewStu);
        final TextView textViewTimerr = findViewById(R.id.textViewTimerr);
        final TextView textViewMedi = findViewById(R.id.textViewMedi);
        final TextView textViewSpecial = findViewById(R.id.textViewSpecial);

        textViewStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strStu = textViewStu.getText().toString();
                intent.putExtra("strStu",strStu);
                intent.putExtra("strStuURL","https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/leaning.png?alt=media&token=cebd2c3d-3f17-47ce-8d17-3649f0ef1521");
                ImageView img = findViewById(R.id.imageViewStu);
                int imgId = img.getId();
                intent.putExtra("imgId", imgId);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        textViewTimerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strStu = textViewTimerr.getText().toString();
                intent.putExtra("strStu",strStu);
                intent.putExtra("strStuURL","https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/about_time.png?alt=media&token=e4af4338-c088-42c1-ab6a-10fa9bacd464");
                ImageView img = findViewById(R.id.imageViewTimer);
                int imgId = img.getId();
                intent.putExtra("imgId", imgId);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        textViewMedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strStu = textViewMedi.getText().toString();
                intent.putExtra("strStu",strStu);
                intent.putExtra("strStuURL","https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/medical.png?alt=media&token=91d68976-ce4a-47eb-8b10-7ca15f5933c6");
                ImageView img = findViewById(R.id.imageViewMedi);
                int imgId = img.getId();
                intent.putExtra("imgId", imgId);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        textViewSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strStu = textViewSpecial.getText().toString();
                intent.putExtra("strStu",strStu);
                intent.putExtra("strStuURL","https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/special_day.png?alt=media&token=293c1cd8-ffd3-4d8a-945f-ef0f301ab91c");
                ImageView img = findViewById(R.id.imageViewSpecial);
                int imgId = img.getId();
                intent.putExtra("imgId", imgId);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭시 안 닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }
}