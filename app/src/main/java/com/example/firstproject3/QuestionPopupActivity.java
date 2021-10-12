package com.example.firstproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionPopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_question_popup);

        Button btn_yes = findViewById(R.id.Btn_question_yes);
        Button btn_no = findViewById(R.id.Btn_question_no);

        //아니요 버튼 눌렀을 때
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //예 버튼 눌렀을 때 -> 지정된 개발자 이메일 주소와 메일 제목으로 메일을 보낼 수 있음
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"idowedo101@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "Catodo앱에 대해 문의드립니다.");
                email.putExtra(Intent.EXTRA_TEXT, "");

                startActivity(email);
            }
        });

        //특정 텍스트에 대한 스타일 적용
        TextView textSendToTitle = findViewById(R.id.TextSendToManager);

        String content = textSendToTitle.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "메일";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F87791")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textSendToTitle.setText(spannableString);
    }
}