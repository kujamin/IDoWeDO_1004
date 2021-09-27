package com.example.firstproject3;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoreActivity extends AppCompatActivity {
    View darkView;
    LinearLayout popupStore;
    LinearLayout soldoutView1, soldoutView2, soldoutView3, soldoutView4, soldoutView5, soldoutView6, soldoutView7, soldoutView8,
            soldoutView9, soldoutView10, soldoutView11, soldoutView12;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        darkView = findViewById(R.id.DarkView);
        popupStore = findViewById(R.id.popupStore);
        TextView textYes = findViewById(R.id.textYes);
        TextView textNo = findViewById(R.id.textNo);
        soldoutView1 = findViewById(R.id.SoldoutView1);
        soldoutView2 = findViewById(R.id.SoldoutView2);
        soldoutView3 = findViewById(R.id.SoldoutView3);
        soldoutView4 = findViewById(R.id.SoldoutView4);
        soldoutView5 = findViewById(R.id.SoldoutView5);
        soldoutView6 = findViewById(R.id.SoldoutView6);
        soldoutView7 = findViewById(R.id.SoldoutView7);
        soldoutView8 = findViewById(R.id.SoldoutView8);
        soldoutView9 = findViewById(R.id.SoldoutView9);
        soldoutView10 = findViewById(R.id.SoldoutView10);
        soldoutView11 = findViewById(R.id.SoldoutView11);
        soldoutView12 = findViewById(R.id.SoldoutView12);


        //팝업창 띄우는 이벤트 리스너
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemId = v.getId();
                darkView.setVisibility(View.VISIBLE);
                popupStore.setVisibility(View.VISIBLE);
            }
        };

        LinearLayout itemLayout1 = findViewById(R.id.item1);
        LinearLayout itemLayout2 = findViewById(R.id.item2);
        LinearLayout itemLayout3 = findViewById(R.id.item3);
        LinearLayout itemLayout4 = findViewById(R.id.item4);
        LinearLayout itemLayout5 = findViewById(R.id.item5);
        LinearLayout itemLayout6 = findViewById(R.id.item6);
        LinearLayout itemLayout7 = findViewById(R.id.item7);
        LinearLayout itemLayout8 = findViewById(R.id.item8);
        LinearLayout itemLayout9 = findViewById(R.id.item9);
        LinearLayout itemLayout10 = findViewById(R.id.item10);
        LinearLayout itemLayout11 = findViewById(R.id.item11);
        LinearLayout itemLayout12 = findViewById(R.id.item12);

        itemLayout1.setOnClickListener(ocl);
        itemLayout2.setOnClickListener(ocl);
        itemLayout3.setOnClickListener(ocl);
        itemLayout4.setOnClickListener(ocl);
        itemLayout5.setOnClickListener(ocl);
        itemLayout6.setOnClickListener(ocl);
        itemLayout7.setOnClickListener(ocl);
        itemLayout8.setOnClickListener(ocl);
        itemLayout9.setOnClickListener(ocl);
        itemLayout10.setOnClickListener(ocl);
        itemLayout11.setOnClickListener(ocl);
        itemLayout12.setOnClickListener(ocl);

        //팝업창에서 '예' 클릭 시 화면 닫힘 & SOLD OUT
        textYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkView.setVisibility(View.INVISIBLE);
                popupStore.setVisibility(View.INVISIBLE);

                switch (itemId) {
                    case R.id.item1 :
                        soldoutView1.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item2 :
                        soldoutView2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item3 :
                        soldoutView3.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item4 :
                        soldoutView4.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item5 :
                        soldoutView5.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item6 :
                        soldoutView6.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item7 :
                        soldoutView7.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item8 :
                        soldoutView8.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item9 :
                        soldoutView9.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item10 :
                        soldoutView10.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item11 :
                        soldoutView11.setVisibility(View.VISIBLE);
                        break;
                    case R.id.item12 :
                        soldoutView12.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //팝업창에서 '아니오' 클릭 시 화면 닫힘
        textNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkView.setVisibility(View.INVISIBLE);
                popupStore.setVisibility(View.INVISIBLE);
            }
        });
    }
}