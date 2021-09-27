package com.example.firstproject3;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AchieveActivity extends AppCompatActivity {
    LinearLayout slideLayout;
    View darkView;
    ImageView showImg;
    TextView showName, showExplain;
    int badgeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);

        slideLayout = findViewById(R.id.slideLayout);
        darkView = findViewById(R.id.DarkView);
        showImg = findViewById(R.id.showImgBadge);
        showName = findViewById(R.id.showNameBadge);
        showExplain = findViewById(R.id.showExplainBadge);

        final Animation translateup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_top);
        final Animation translatedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_bottom);

        LinearLayout layoutBadge1 = findViewById(R.id.badge1);
        LinearLayout layoutBadge2 = findViewById(R.id.badge2);
        LinearLayout layoutBadge3 = findViewById(R.id.badge3);
        LinearLayout layoutBadge4 = findViewById(R.id.badge4);
        LinearLayout layoutBadge5 = findViewById(R.id.badge5);
        LinearLayout layoutBadge6 = findViewById(R.id.badge6);
        LinearLayout layoutBadge7 = findViewById(R.id.badge7);
        LinearLayout layoutBadge8 = findViewById(R.id.badge8);
        LinearLayout layoutBadge9 = findViewById(R.id.badge9);
        LinearLayout layoutBadge10 = findViewById(R.id.badge10);
        LinearLayout layoutBadge11 = findViewById(R.id.badge11);
        LinearLayout layoutBadge12 = findViewById(R.id.badge12);
        LinearLayout layoutBadge13 = findViewById(R.id.badge13);
        LinearLayout layoutBadge14 = findViewById(R.id.badge14);
        LinearLayout layoutBadge15 = findViewById(R.id.badge15);

        //팝업창 닫는 이벤트
        darkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLayout.startAnimation(translatedown);
                slideLayout.setVisibility(View.INVISIBLE);
                darkView.setVisibility(View.INVISIBLE);
            }
        });

        //뱃지 클릭 시 팝업창에 띄움
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badgeId = v.getId();
                ImageView img;
                TextView text;
                Drawable clickimg;
                String str;

                switch (badgeId) {
                    case R.id.badge1 :
                        img = findViewById(R.id.imagebadge1);
                        text = findViewById(R.id.textBadge1);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge2 :
                        img = findViewById(R.id.imagebadge2);
                        text = findViewById(R.id.textbadge2);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge3 :
                        img = findViewById(R.id.imagebadge3);
                        text = findViewById(R.id.textbadge3);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge4 :
                        img = findViewById(R.id.imagebadge4);
                        text = findViewById(R.id.textbadge4);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge5 :
                        img = findViewById(R.id.imagebadge5);
                        text = findViewById(R.id.textbadge5);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge6 :
                        img = findViewById(R.id.imagebadge6);
                        text = findViewById(R.id.textbadge6);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge7 :
                        img = findViewById(R.id.imagebadge7);
                        text = findViewById(R.id.textbadge7);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge8 :
                        img = findViewById(R.id.imagebadge8);
                        text = findViewById(R.id.textbadge8);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge9 :
                        img = findViewById(R.id.imagebadge9);
                        text = findViewById(R.id.textbadge9);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge10 :
                        img = findViewById(R.id.imagebadge10);
                        text = findViewById(R.id.textbadge10);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge11 :
                        img = findViewById(R.id.imagebadge11);
                        text = findViewById(R.id.textbadge11);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge12 :
                        img = findViewById(R.id.imagebadge12);
                        text = findViewById(R.id.textbadge12);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge13 :
                        img = findViewById(R.id.imagebadge13);
                        text = findViewById(R.id.textbadge13);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge14 :
                        img = findViewById(R.id.imagebadge14);
                        text = findViewById(R.id.textbadge14);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                    case R.id.badge15 :
                        img = findViewById(R.id.imagebadge15);
                        text = findViewById(R.id.textbadge15);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        break;
                }//switch
            }//onClick
        };//ocl

        layoutBadge1.setOnClickListener(ocl);
        layoutBadge2.setOnClickListener(ocl);
        layoutBadge3.setOnClickListener(ocl);
        layoutBadge4.setOnClickListener(ocl);
        layoutBadge5.setOnClickListener(ocl);
        layoutBadge6.setOnClickListener(ocl);
        layoutBadge7.setOnClickListener(ocl);
        layoutBadge8.setOnClickListener(ocl);
        layoutBadge9.setOnClickListener(ocl);
        layoutBadge10.setOnClickListener(ocl);
        layoutBadge11.setOnClickListener(ocl);
        layoutBadge12.setOnClickListener(ocl);
        layoutBadge13.setOnClickListener(ocl);
        layoutBadge14.setOnClickListener(ocl);
        layoutBadge15.setOnClickListener(ocl);

    }//onCreate
}