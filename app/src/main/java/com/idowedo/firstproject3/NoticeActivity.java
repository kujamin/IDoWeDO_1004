package com.idowedo.firstproject3;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.idowedo.firstproject3.R;

public class NoticeActivity extends AppCompatActivity {
    int noticeId;
    boolean closeType1 = false, closeType2 = false, closeType3 = false, closeType4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout notice1 = findViewById(R.id.notice1);
        LinearLayout notice2 = findViewById(R.id.notice2);
        LinearLayout notice3 = findViewById(R.id.notice3);
        LinearLayout notice4 = findViewById(R.id.notice4);

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
                    case R.id.notice4 :
                        img = findViewById(R.id.imageNotice4);
                        content = findViewById(R.id.noticeContent4);
                        if(closeType4) { //closeType이 1일 때 공지 내용 숨기기
                            content.setVisibility(View.GONE);
                            img.setImageResource(R.drawable.down_arrow);
                            closeType4 = false;
                        } else { //closeType이 0일 때 공지 내용 보이기
                            content.setVisibility(View.VISIBLE);
                            img.setImageResource(R.drawable.up_arrow);
                            closeType4 = true;
                        }
                        break;
                }//switch
            }//onClick
        };//ocl

        notice1.setOnClickListener(ocl);
        notice2.setOnClickListener(ocl);
        notice3.setOnClickListener(ocl);
        notice4.setOnClickListener(ocl);

    }//onCreeate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return onOptionsItemSelected(item);
    }
}