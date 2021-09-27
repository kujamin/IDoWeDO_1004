package com.example.firstproject3;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DecoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deco);

        final ImageButton imgbtnHead = findViewById(R.id.imageBtnHead);
        final ImageButton imgbtnTorso = findViewById(R.id.imageBtnTorso);
        final ImageButton imgbtnLeg = findViewById(R.id.imageBtnLeg);
        final ImageButton imgbtnArm = findViewById(R.id.imageBtnArm);

        final ImageButton imgbtnHead1 = findViewById(R.id.imageButtonHead1);
        final ImageButton imgbtnHead2 = findViewById(R.id.imageButtonHead2);
        final ImageButton imgbtnHead3 = findViewById(R.id.imageButtonHead3);
        final ImageButton imgbtnHead4 = findViewById(R.id.imageButtonHead4);
        final ImageButton imgbtnHead5 = findViewById(R.id.imageButtonHead5);
        final ImageButton imgbtnHead6 = findViewById(R.id.imageButtonHead6);

        final ImageButton imgbtnTorse1 = findViewById(R.id.imageButtonTorso1);
        final ImageButton imgbtnTorse2 = findViewById(R.id.imageButtonTorso2);
        final ImageButton imgbtnTorse3 = findViewById(R.id.imageButtonTorso3);
        final ImageButton imgbtnTorse4 = findViewById(R.id.imageButtonTorso4);
        final ImageButton imgbtnTorse5 = findViewById(R.id.imageButtonTorso5);
        final ImageButton imgbtnTorse6 = findViewById(R.id.imageButtonTorso6);

        final ImageButton imgbtnLeg1 = findViewById(R.id.imageButtonLeg1);
        final ImageButton imgbtnLeg2 = findViewById(R.id.imageButtonLeg2);
        final ImageButton imgbtnLeg3 = findViewById(R.id.imageButtonLeg3);
        final ImageButton imgbtnLeg4 = findViewById(R.id.imageButtonLeg4);
        final ImageButton imgbtnLeg5 = findViewById(R.id.imageButtonLeg5);
        final ImageButton imgbtnLeg6 = findViewById(R.id.imageButtonLeg6);

        final ImageButton imgbtnArm1 = findViewById(R.id.imageButtonArm1);
        final ImageButton imgbtnArm2 = findViewById(R.id.imageButtonArm2);
        final ImageButton imgbtnArm3 = findViewById(R.id.imageButtonArm3);
        final ImageButton imgbtnArm4 = findViewById(R.id.imageButtonArm4);
        final ImageButton imgbtnArm5 = findViewById(R.id.imageButtonArm5);
        final ImageButton imgbtnArm6 = findViewById(R.id.imageButtonArm6);

        //각각의 버튼 클릭 시 색상 변경 및 이미지 변경
        imgbtnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#ffffff"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#CACACA"));

                imgbtnHead1.setVisibility(View.VISIBLE);
                imgbtnHead2.setVisibility(View.VISIBLE);
                imgbtnHead3.setVisibility(View.VISIBLE);
                imgbtnHead4.setVisibility(View.VISIBLE);
                imgbtnHead5.setVisibility(View.VISIBLE);
                imgbtnHead6.setVisibility(View.VISIBLE);

                imgbtnTorse1.setVisibility(View.INVISIBLE);
                imgbtnTorse2.setVisibility(View.INVISIBLE);
                imgbtnTorse3.setVisibility(View.INVISIBLE);
                imgbtnTorse4.setVisibility(View.INVISIBLE);
                imgbtnTorse5.setVisibility(View.INVISIBLE);
                imgbtnTorse6.setVisibility(View.INVISIBLE);

                imgbtnLeg1.setVisibility(View.INVISIBLE);
                imgbtnLeg2.setVisibility(View.INVISIBLE);
                imgbtnLeg3.setVisibility(View.INVISIBLE);
                imgbtnLeg4.setVisibility(View.INVISIBLE);
                imgbtnLeg5.setVisibility(View.INVISIBLE);
                imgbtnLeg6.setVisibility(View.INVISIBLE);

                imgbtnArm1.setVisibility(View.INVISIBLE);
                imgbtnArm2.setVisibility(View.INVISIBLE);
                imgbtnArm3.setVisibility(View.INVISIBLE);
                imgbtnArm4.setVisibility(View.INVISIBLE);
                imgbtnArm5.setVisibility(View.INVISIBLE);
                imgbtnArm6.setVisibility(View.INVISIBLE);
            }
        });

        imgbtnTorso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#ffffff"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#CACACA"));

                imgbtnTorse1.setVisibility(View.VISIBLE);
                imgbtnTorse2.setVisibility(View.VISIBLE);
                imgbtnTorse3.setVisibility(View.VISIBLE);
                imgbtnTorse4.setVisibility(View.VISIBLE);
                imgbtnTorse5.setVisibility(View.VISIBLE);
                imgbtnTorse6.setVisibility(View.VISIBLE);

                imgbtnHead1.setVisibility(View.INVISIBLE);
                imgbtnHead2.setVisibility(View.INVISIBLE);
                imgbtnHead3.setVisibility(View.INVISIBLE);
                imgbtnHead4.setVisibility(View.INVISIBLE);
                imgbtnHead5.setVisibility(View.INVISIBLE);
                imgbtnHead6.setVisibility(View.INVISIBLE);

                imgbtnLeg1.setVisibility(View.INVISIBLE);
                imgbtnLeg2.setVisibility(View.INVISIBLE);
                imgbtnLeg3.setVisibility(View.INVISIBLE);
                imgbtnLeg4.setVisibility(View.INVISIBLE);
                imgbtnLeg5.setVisibility(View.INVISIBLE);
                imgbtnLeg6.setVisibility(View.INVISIBLE);

                imgbtnArm1.setVisibility(View.INVISIBLE);
                imgbtnArm2.setVisibility(View.INVISIBLE);
                imgbtnArm3.setVisibility(View.INVISIBLE);
                imgbtnArm4.setVisibility(View.INVISIBLE);
                imgbtnArm5.setVisibility(View.INVISIBLE);
                imgbtnArm6.setVisibility(View.INVISIBLE);
            }
        });

        imgbtnLeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#ffffff"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#CACACA"));

                imgbtnLeg1.setVisibility(View.VISIBLE);
                imgbtnLeg2.setVisibility(View.VISIBLE);
                imgbtnLeg3.setVisibility(View.VISIBLE);
                imgbtnLeg4.setVisibility(View.VISIBLE);
                imgbtnLeg5.setVisibility(View.VISIBLE);
                imgbtnLeg6.setVisibility(View.VISIBLE);

                imgbtnHead1.setVisibility(View.INVISIBLE);
                imgbtnHead2.setVisibility(View.INVISIBLE);
                imgbtnHead3.setVisibility(View.INVISIBLE);
                imgbtnHead4.setVisibility(View.INVISIBLE);
                imgbtnHead5.setVisibility(View.INVISIBLE);
                imgbtnHead6.setVisibility(View.INVISIBLE);

                imgbtnTorse1.setVisibility(View.INVISIBLE);
                imgbtnTorse2.setVisibility(View.INVISIBLE);
                imgbtnTorse3.setVisibility(View.INVISIBLE);
                imgbtnTorse4.setVisibility(View.INVISIBLE);
                imgbtnTorse5.setVisibility(View.INVISIBLE);
                imgbtnTorse6.setVisibility(View.INVISIBLE);

                imgbtnArm1.setVisibility(View.INVISIBLE);
                imgbtnArm2.setVisibility(View.INVISIBLE);
                imgbtnArm3.setVisibility(View.INVISIBLE);
                imgbtnArm4.setVisibility(View.INVISIBLE);
                imgbtnArm5.setVisibility(View.INVISIBLE);
                imgbtnArm6.setVisibility(View.INVISIBLE);
            }
        });

        imgbtnArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbtnHead.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnTorso.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnLeg.setBackgroundColor(Color.parseColor("#CACACA"));
                imgbtnArm.setBackgroundColor(Color.parseColor("#ffffff"));

                imgbtnArm1.setVisibility(View.VISIBLE);
                imgbtnArm2.setVisibility(View.VISIBLE);
                imgbtnArm3.setVisibility(View.VISIBLE);
                imgbtnArm4.setVisibility(View.VISIBLE);
                imgbtnArm5.setVisibility(View.VISIBLE);
                imgbtnArm6.setVisibility(View.VISIBLE);

                imgbtnHead1.setVisibility(View.INVISIBLE);
                imgbtnHead2.setVisibility(View.INVISIBLE);
                imgbtnHead3.setVisibility(View.INVISIBLE);
                imgbtnHead4.setVisibility(View.INVISIBLE);
                imgbtnHead5.setVisibility(View.INVISIBLE);
                imgbtnHead6.setVisibility(View.INVISIBLE);

                imgbtnTorse1.setVisibility(View.INVISIBLE);
                imgbtnTorse2.setVisibility(View.INVISIBLE);
                imgbtnTorse3.setVisibility(View.INVISIBLE);
                imgbtnTorse4.setVisibility(View.INVISIBLE);
                imgbtnTorse5.setVisibility(View.INVISIBLE);
                imgbtnTorse6.setVisibility(View.INVISIBLE);

                imgbtnLeg1.setVisibility(View.INVISIBLE);
                imgbtnLeg2.setVisibility(View.INVISIBLE);
                imgbtnLeg3.setVisibility(View.INVISIBLE);
                imgbtnLeg4.setVisibility(View.INVISIBLE);
                imgbtnLeg5.setVisibility(View.INVISIBLE);
                imgbtnLeg6.setVisibility(View.INVISIBLE);
            }
        });

        final ImageView imgHead = findViewById(R.id.imageHead);
        final ImageView imgTorso = findViewById(R.id.imageTorso);
        final ImageView imgLeg = findViewById(R.id.imageLeg);
        ImageButton btnReset = findViewById(R.id.imageReset);

        //새로고침 메소드
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHead.setVisibility(View.INVISIBLE);
                imgTorso.setVisibility(View.INVISIBLE);
                imgLeg.setVisibility(View.INVISIBLE);
            }
        });

        //옷 갈아입히기 이벤트 리스너
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imgId = v.getId();
                Drawable drawH, drawT, drawL, drawA;
                switch (imgId) {
                    case R.id.imageButtonHead1 :
                        drawH = imgbtnHead1.getDrawable();
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso1 :
                        drawT = imgbtnTorse1.getDrawable();
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg1 :
                        drawL = imgbtnLeg1.getDrawable();
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonHead2 :
                        drawH = imgbtnHead2.getDrawable();
                        imgHead.setImageDrawable(drawH);
                        imgHead.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso2 :
                        drawT = imgbtnTorse2.getDrawable();
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg2 :
                        drawL = imgbtnLeg2.getDrawable();
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonTorso4 :
                        drawT = imgbtnTorse4.getDrawable();
                        imgTorso.setImageDrawable(drawT);
                        imgTorso.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageButtonLeg4 :
                        drawL = imgbtnLeg4.getDrawable();
                        imgLeg.setImageDrawable(drawL);
                        imgLeg.setVisibility(View.VISIBLE);
                        break;
                }//switch
            }//onClick
        };//ocl

        imgbtnHead1.setOnClickListener(ocl);
        imgbtnTorse1.setOnClickListener(ocl);
        imgbtnLeg1.setOnClickListener(ocl);
        imgbtnHead2.setOnClickListener(ocl);
        imgbtnTorse2.setOnClickListener(ocl);
        imgbtnLeg2.setOnClickListener(ocl);
        imgbtnTorse4.setOnClickListener(ocl);
        imgbtnLeg4.setOnClickListener(ocl);
    }
}