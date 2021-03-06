package com.idowedo.firstproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private String strUrl, userCode;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private View view;
    private ProgressDialog pd;
    InputMethodManager imm;

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
        super.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habbit_detail);

        pd = new ProgressDialog(this);

        final TableRow row1 = findViewById(R.id.habbitDetail_table1);
        final TableRow row2 = findViewById(R.id.habbitDetail_table2);
        final TableRow row3 = findViewById(R.id.habbitDetail_table3);
        final TableRow row4 = findViewById(R.id.habbitDetail_table4);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.habbitDetail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

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

        //인텐트로 얻어온 값으로 채워주기
        strUrl = habbit_category;

        Glide.with(this).load(habbit_category).into(habbitDetail_imageViewChange);

        habbitDetail_title.setText(habbit_title);
        habbitDetail_date.setText(habbit_date);
        habbitDetail_memo.setText(habbit_memo);
        habbitDetail_textch.setText(habbit_cateText);
        habbitDetail_cateLayout.setVisibility(View.VISIBLE);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        //제목 영역 클릭 시
        habbitDetail_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                row1.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymclickline));
                row2.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));

            }
        });

        //달력 팝업
        habbitDetail_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }

                imm.hideSoftInputFromWindow(habbitDetail_title.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(habbitDetail_memo.getWindowToken(), 0);


                row1.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymclickline));
                row3.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                new DatePickerDialog(HabbitDetailActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //메모 영역 클릭 시
        habbitDetail_memo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                row1.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymclickline));
                row4.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
            }
        });

        //카테고리 팝업
        habbitDetail_cateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }


                row1.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(HabbitDetailActivity.this, R.drawable.dailymclickline));

                Intent intent = new Intent(HabbitDetailActivity.this, DailyPopActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //String usercode = ((usercode)getApplication()).getUsercode();
        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                userCode = (group.getEmailid());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

                if(str_title.length() == 0){
                    Toast.makeText(getApplicationContext(),"습관 이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else{

                    pd.setTitle("저장 중...");
                    pd.show();

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user habbit").document(habbit_id);

                    docRef.update("habbit_title",str_title);
                    docRef.update("habbit_date",str_date);
                    docRef.update("habbit_memo",str_memo);
                    docRef.update("habbit_category",strUrl);
                    docRef.update("habbit_cateText",str_cateText).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"변경되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });

        //삭제 버튼 누르면
        habbitDetail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user habbit").document(habbit_id);

                pd.setTitle("삭제 중...");
                pd.show();

                docRef.delete();
                finish();
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }

    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        habbitDetail_date.setText(sdf.format(myCalendar.getTime()) + "까지 반복");
    }
    //카테고리 팝업에서 선택하고 돌아올 때
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