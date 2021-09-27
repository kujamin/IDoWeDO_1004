package com.example.firstproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HabbitDetailActivity extends AppCompatActivity {

    private EditText habbitDetail_title, habbitDetail_memo;
    private TextView habbitDetail_date, habbitDetail_cate, habbitDetail_textch;
    private ImageView habbitDetail_imageViewChange;
    private TableRow habbitDetail_cateTable;
    private Button habbitDetail_save, habbitDetail_delete;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";
    private String strUrl;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habbit_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.habbitDetail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        habbitDetail_title = findViewById(R.id.habbitDetail_title);
        habbitDetail_date = findViewById(R.id.habbitDetail_dateResult);
        habbitDetail_memo = findViewById(R.id.habbitDetail_memoResult);
        habbitDetail_imageViewChange = findViewById(R.id.habbitDetail_imageViewChange);
        habbitDetail_textch =findViewById(R.id.habbitDetail_textch);
        habbitDetail_cateTable = findViewById(R.id.habbitDetail_table4);
        habbitDetail_save = findViewById(R.id.habbitDetail_save);
        habbitDetail_delete = findViewById(R.id.habbitDetail_delete);
        LinearLayout habbitDetail_cateLayout = findViewById(R.id.habbitDetail_cateLayout);

        Intent intent = getIntent();
        String habbit_title = intent.getStringExtra("habbit_title");
        String habbit_memo = intent.getStringExtra("habbit_memo");
        String habbit_category = intent.getStringExtra("habbit_category");
        String habbit_date = intent.getStringExtra("habbit_date");
        String habbit_cateText = intent.getStringExtra("habbit_cateText");
        String habbit_id = intent.getStringExtra("habbit_id");

        strUrl = habbit_category;

        Glide.with(this).load(habbit_category).into(habbitDetail_imageViewChange);

        habbitDetail_title.setText(habbit_title);
        habbitDetail_date.setText(habbit_date);
        habbitDetail_memo.setText(habbit_memo);
        habbitDetail_textch.setText(habbit_cateText);
        habbitDetail_cateLayout.setVisibility(View.VISIBLE);

        //달력 팝업
        habbitDetail_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HabbitDetailActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //카테고리 팝업
        habbitDetail_cateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabbitDetailActivity.this, DailyPopActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //저장 버튼 누르면
        habbitDetail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_title = habbitDetail_title.getText().toString().trim();
                String str_date = habbitDetail_date.getText().toString().trim();
                String str_memo = habbitDetail_memo.getText().toString().trim();
                String str_cateText = habbitDetail_textch.getText().toString().trim();

                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user habbit").document(habbit_id);

                docRef.update("habbit_title",str_title);
                docRef.update("habbit_date",str_date);
                docRef.update("habbit_memo",str_memo);
                docRef.update("habbit_category",strUrl);
                docRef.update("habbit_cateText",str_cateText).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });



            }
        });

        //삭제 버튼 누르면
        habbitDetail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user habbit").document(habbit_id);

                docRef.delete();
                finish();
            }
        });



    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        habbitDetail_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int i = data.getIntExtra("imgId", 0);
                String str = data.getStringExtra("strStu");
                strUrl = data.getStringExtra("strStuURL");

                habbitDetail_textch.setText(str);

                switch (i) {
                    case R.id.imageViewStu:
                        habbitDetail_imageViewChange.setImageResource(R.drawable.leaning);
                        break;
                    case R.id.imageViewTimer:
                        habbitDetail_imageViewChange.setImageResource(R.drawable.about_time);
                        break;
                    case R.id.imageViewMedi:
                        habbitDetail_imageViewChange.setImageResource(R.drawable.medical);
                        break;
                    case R.id.imageViewSpecial:
                        habbitDetail_imageViewChange.setImageResource(R.drawable.special_day);
                        break;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}