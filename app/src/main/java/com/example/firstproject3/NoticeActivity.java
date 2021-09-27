package com.example.firstproject3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class NoticeActivity extends AppCompatActivity {
    int noticeId;
    boolean closeType1 = false, closeType2 = false, closeType3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        LinearLayout notice1 = findViewById(R.id.notice1);
        LinearLayout notice2 = findViewById(R.id.notice2);
        LinearLayout notice3 = findViewById(R.id.notice3);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeId = v.getId();
                LinearLayout content;
                ImageView img;

                switch (noticeId) {
                    case R.id.notice1 :
                        img = findViewById(R.id.imageNotice1);
                        content = findViewById(R.id.noticeContent1);
                        if(closeType1) { //closeType이 1일 때 공지 내용 숨기기
                            content.setVisibility(View.GONE);
                            img.setImageResource(R.drawable.down_arrow);
                            closeType1 = false;
                        } else { //closeType이 0일 때 공지 내용 보이기
                            content.setVisibility(View.VISIBLE);
                            img.setImageResource(R.drawable.up_arrow);
                            closeType1 = true;
                        }
                        break;
                    case R.id.notice2 :
                        img = findViewById(R.id.imageNotice2);
                        content = findViewById(R.id.noticeContent2);
                        if(closeType2) { //closeType이 1일 때 공지 내용 숨기기
                            content.setVisibility(View.GONE);
                            img.setImageResource(R.drawable.down_arrow);
                            closeType2 = false;
                        } else { //closeType이 0일 때 공지 내용 보이기
                            content.setVisibility(View.VISIBLE);
                            img.setImageResource(R.drawable.up_arrow);
                            closeType2 = true;
                        }
                        break;
                    case R.id.notice3 :
                        img = findViewById(R.id.imageNotice3);
                        content = findViewById(R.id.noticeContent3);
                        if(closeType3) { //closeType이 1일 때 공지 내용 숨기기
                            content.setVisibility(View.GONE);
                            img.setImageResource(R.drawable.down_arrow);
                            closeType3 = false;
                        } else { //closeType이 0일 때 공지 내용 보이기
                            content.setVisibility(View.VISIBLE);
                            img.setImageResource(R.drawable.up_arrow);
                            closeType3 = true;
                        }
                        break;
                }//switch
            }//onClick
        };//ocl

        notice1.setOnClickListener(ocl);
        notice2.setOnClickListener(ocl);
        notice3.setOnClickListener(ocl);

    }//onCreeate
}