package com.idowedo.firstproject3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idowedo.firstproject3.R;

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

        //학업에 해당하는 이미지를 선택했을 경우 DB storage에 저장된 이미지의 주소값을 넘김
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

        //타이머에 해당하는 이미지를 선택했을 경우 DB storage에 저장된 이미지의 주소값을 넘김
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

        //의료에 해당하는 이미지를 선택했을 경우 DB storage에 저장된 이미지의 주소값을 넘김
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

        //특별한 날에 해당하는 이미지를 선택했을 경우 DB storage에 저장된 이미지의 주소값을 넘김
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
}