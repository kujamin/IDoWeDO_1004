package com.example.firstproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChallSuccPopActivity extends Activity {
    private Button rewardBtn;
    private TextView textSuccTitle;
    private String strchallTitle;

    public void challPopupClose(View v){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chall_succ_pop);

        rewardBtn = findViewById(R.id.rewardBtn);
        textSuccTitle = findViewById(R.id.textSuccTitle);

        strchallTitle = getIntent().getStringExtra("challSuccTitle");

        textSuccTitle.setText(strchallTitle + textSuccTitle.getText());

        rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallSuccPopActivity.this, RewardChallActivity.class);
                intent.putExtra("rewardTitle", strchallTitle);
                startActivity(intent);
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