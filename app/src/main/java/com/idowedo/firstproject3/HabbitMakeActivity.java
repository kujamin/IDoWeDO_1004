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

import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class HabbitMakeActivity extends AppCompatActivity {

    int selectYear, selectMonth, selectDay, selectHour, selectMinute;
    static final int REQ_ADD_CONTACT = 1;

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String userCode;

    LinearLayout cateLayout;
    TextView textCate, textch, textDaily;
    ImageView imageViewA;
    ArrayList<Person> persons;
    Company company;
    ProgressDialog pd;
    FirebaseFirestore db;
    String strUrl;
    View view;
    TextView text10;
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
        setContentView(R.layout.activity_habbit_make);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.habbit_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnReser = findViewById(R.id.buttonReser);
        textDaily = findViewById(R.id.textViewDaily);
        textCate = findViewById(R.id.textViewCate);
        final EditText textMemo = findViewById(R.id.EdittextMemo);
        final EditText textDailyName = findViewById(R.id.editTextTextPersonName);
        final TableRow dailyname = findViewById(R.id.dailyName);
        final TableRow dailycal = findViewById(R.id.dailycal);
        final TableRow dailymemo = findViewById(R.id.dailymemo);
        final TableRow dailycate = findViewById(R.id.dailycate);
        text10 = findViewById(R.id.textView10);
        imageViewA = findViewById(R.id.imageViewChange);
        cateLayout = findViewById(R.id.cateLayout);
        textch = findViewById(R.id.textch);

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        cateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }

                dailycal.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailyname.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailymemo.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailycate.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymclickline));


                Intent intent = new Intent(HabbitMakeActivity.this, DailyPopActivity.class);
                startActivityForResult(intent, REQ_ADD_CONTACT);
            }
        });

        textDailyName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                dailycal.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailyname.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymclickline));
                dailymemo.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailycate.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));

            }
        });

        textDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }

                imm.hideSoftInputFromWindow(textDailyName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(textMemo.getWindowToken(), 0);

                dailycal.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymclickline));
                dailyname.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailymemo.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailycate.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                new DatePickerDialog(HabbitMakeActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        textMemo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                dailycal.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailyname.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailymemo.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymclickline));
                dailycate.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
            }
        });

        textCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = getCurrentFocus();
                if (view != null) {
                    view.clearFocus();
                }

                dailycal.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailyname.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailymemo.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymakeline));
                dailycate.setBackground(ContextCompat.getDrawable(HabbitMakeActivity.this, R.drawable.dailymclickline));


                Intent intent = new Intent(HabbitMakeActivity.this, DailyPopActivity.class);
                startActivityForResult(intent, REQ_ADD_CONTACT);
            }
        });

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

        btnReser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String title =  textDailyName.getText().toString().trim();//슨관 제목
                String date = textDaily.getText().toString().trim(); //습관 날짜
                String memo = textMemo.getText().toString().trim(); //습관 메모
                String category = strUrl; //습관 카테고리 텍스트
                String cateText = textch.getText().toString().trim();
                String id = UUID.randomUUID().toString();
                long now = System.currentTimeMillis();
                Date dateS = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String dateStart = dateFormat.format(dateS);

                if(title.length() == 0){
                    Toast.makeText(getApplicationContext(),"습관 이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else if (date == ""){
                    Toast.makeText(getApplicationContext(),"기한을 설정하세요.",Toast.LENGTH_SHORT).show();
                }
                else if (category == null){
                    Toast.makeText(getApplicationContext(),"분류를 설정하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadData(title, date, memo, category, cateText, id, userCode, dateStart);
                    finish();
                }
                //String usercode = ((usercode)getApplication()).getUsercode();



//                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        UserAccount group = dataSnapshot.getValue(UserAccount.class);
//                        int currentexp = group.getExp();
//                        currentexp += 5;
//                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("exp").setValue(currentexp);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }
        });

    }//onCreate

    private void uploadData(String title, String date, String memo, String category, String cateText, String id, String userCode, String dateStart) {
        pd.setTitle("습관 생성 중...");

        pd.show();


        Map<String, Object> doc = new HashMap<>();
        doc.put("habbit_title", title);
        doc.put("habbit_date", date);
        doc.put("habbit_memo", memo);
        doc.put("habbit_category", category);
        doc.put("habbit_cateText", cateText);
        doc.put("habbit_id", id);
        doc.put("habbit_checkbox",false);
        doc.put("userCode", userCode);
        doc.put("habbit_dateStart",dateStart);


        db.collection("user").document(userCode).collection("user habbit").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ADD_CONTACT) {
            if (resultCode == RESULT_OK) {
                int i = data.getIntExtra("imgId", 0);
                String str = data.getStringExtra("strStu");
                strUrl = data.getStringExtra("strStuURL");

                textch.setText(str);

                switch (i) {
                    case R.id.imageViewStu:
                        imageViewA.setImageResource(R.drawable.leaning);
                        textCate.setVisibility(View.INVISIBLE);
                        cateLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageViewTimer:
                        imageViewA.setImageResource(R.drawable.about_time);
                        textCate.setVisibility(View.INVISIBLE);
                        cateLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageViewMedi:
                        imageViewA.setImageResource(R.drawable.medical);
                        textCate.setVisibility(View.INVISIBLE);
                        cateLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.imageViewSpecial:
                        imageViewA.setImageResource(R.drawable.special_day);
                        textCate.setVisibility(View.INVISIBLE);
                        cateLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        textDaily.setText(sdf.format(myCalendar.getTime()));
        text10.setVisibility(View.VISIBLE);
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