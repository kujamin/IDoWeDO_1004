package com.idowedo.firstproject3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idowedo.firstproject3.R;

public class PopupActivity extends Activity {
    ImageView Challlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        Challlogo = findViewById(R.id.imageViewPopup);

        Challlogo.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);

        //특정 텍스트에 대한 스타일 적용(체크란 글자가 나오면 굵음과 #F4385E color 적용)
        TextView textSendToTitle = findViewById(R.id.textViewCheck);

        String content = textSendToTitle.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word_check = "체크";
        int start = content.indexOf(word_check);
        int end = start + word_check.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F4385E")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textSendToTitle.setText(spannableString);
    }

    public void onClikcPopupClose(View v){
        Intent intent = new Intent();
        intent.putExtra("result", true);
        setResult(RESULT_OK, intent);

        finish();
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