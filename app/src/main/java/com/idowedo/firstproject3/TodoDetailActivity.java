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
import android.widget.TimePicker;
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

public class TodoDetailActivity extends AppCompatActivity {
    private EditText todoDetail_title, todoDetail_memo;
    private TextView todoDetail_date,  todoDetail_textch, todoDetail_textViewTime;
    private ImageView todoDetail_imageViewChange;
    private TableRow todoDetail_cateTable;
    private Button todoDetail_save, todoDetail_delete;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";
    private String strUrl, userCode;
    int selectHour, selectMinute;
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
        setContentView(R.layout.activity_todo_detail);

        pd = new ProgressDialog(this);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        final TableRow row1 = findViewById(R.id.todoDetail_table1);
        final TableRow row2 = findViewById(R.id.todoDetail_table2);
        final TableRow row3 = findViewById(R.id.todoDetail_table4);
        final TableRow row4 = findViewById(R.id.todoDetail_table6);
        final TableRow time = findViewById(R.id.todoDetail_table5);
        final TableRow row5 = findViewById(R.id.todoDetail_table7);

        time.setVisibility(View.GONE);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.todoDetail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        todoDetail_title = findViewById(R.id.todoDetail_title);
        todoDetail_date = findViewById(R.id.todoDetail_textViewDaily);
        todoDetail_memo = findViewById(R.id.todoDetail_EdittextMemo);
        todoDetail_imageViewChange = findViewById(R.id.todoDetail_imageViewChange);
        todoDetail_textch = findViewById(R.id.todoDetail_textch);
        todoDetail_cateTable = findViewById(R.id.todoDetail_table7);
        todoDetail_save = findViewById(R.id.todoDetail_save);
        todoDetail_delete = findViewById(R.id.todoDetail_delete);
        todoDetail_textViewTime = findViewById(R.id.todoDetail_textViewTime);
        LinearLayout todoDetail_cateLayout = findViewById(R.id.todoDetail_cateLayout);
        TimePicker todoDetail_timePicker = findViewById(R.id.todoDetail_timePicker);

        Intent intent = getIntent();
        String todo_title = intent.getStringExtra("todo_title");
        String todo_memo = intent.getStringExtra("todo_memo");
        String todo_category = intent.getStringExtra("todo_category");
        String todo_date = intent.getStringExtra("todo_date");
        String todo_cateText = intent.getStringExtra("todo_cateText");
        String todo_time = intent.getStringExtra("todo_time");
        String todo_id = intent.getStringExtra("todo_id");

        strUrl = todo_category;

        Glide.with(this).load(todo_category).into(todoDetail_imageViewChange);

        todoDetail_title.setText(todo_title);
        todoDetail_date.setText(todo_date);
        todoDetail_memo.setText(todo_memo);
        todoDetail_textch.setText(todo_cateText);
        todoDetail_textViewTime.setText(todo_time);

        todoDetail_cateLayout.setVisibility(View.VISIBLE);
        //제목 영역 클릭 시
        todoDetail_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                time.setVisibility(View.GONE);

                row1.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymclickline));
                row2.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row5.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
            }
        });

        //달력 팝업
        todoDetail_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }

                imm.hideSoftInputFromWindow(todoDetail_title.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(todoDetail_memo.getWindowToken(), 0);

                time.setVisibility(View.GONE);

                row1.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymclickline));
                row3.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row5.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));

                new DatePickerDialog(TodoDetailActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //메모 영역 클릭 시
        todoDetail_memo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                time.setVisibility(View.GONE);

                row1.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymclickline));
                row5.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
            }
        });

        //카테고리 팝업
        todoDetail_cateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }

                time.setVisibility(View.GONE);

                row1.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row4.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row5.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymclickline));

                Intent intent = new Intent(TodoDetailActivity.this, DailyPopActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //시간 영역 클릭 시
        todoDetail_textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = getCurrentFocus();
                if(view != null) {
                    view.clearFocus();
                }

                imm.hideSoftInputFromWindow(todoDetail_title.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(todoDetail_memo.getWindowToken(), 0);

                time.setVisibility(View.VISIBLE);

                row1.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row2.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row3.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymclickline));
                row4.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
                row5.setBackground(ContextCompat.getDrawable(TodoDetailActivity.this, R.drawable.dailymakeline));
            }
        });

        todoDetail_timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                selectHour = todoDetail_timePicker.getCurrentHour();
                selectMinute = todoDetail_timePicker.getCurrentMinute();

                todoDetail_textViewTime.setText(Integer.toString(todoDetail_timePicker.getCurrentHour()) + "시 " +
                        Integer.toString(todoDetail_timePicker.getCurrentMinute()) + "분");
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

        //저장 버튼 누르면
        todoDetail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_title = todoDetail_title.getText().toString().trim();
                String str_date = todoDetail_date.getText().toString().trim();
                String str_memo = todoDetail_memo.getText().toString().trim();
                String str_cateText = todoDetail_textch.getText().toString().trim();
                String str_time = todoDetail_textViewTime.getText().toString().trim();

                if(str_title.length() == 0){
                    Toast.makeText(getApplicationContext(),"일정 이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else{

                    pd.setTitle("저장 중...");
                    pd.show();

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user todo").document(todo_id);

                    docRef.update("todo_title",str_title);
                    docRef.update("todo_date",str_date);
                    docRef.update("todo_memo",str_memo);
                    docRef.update("todo_category",strUrl);
                    docRef.update("todo_time", str_time);
                    docRef.update("todo_cateText",str_cateText).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        todoDetail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user todo").document(todo_id);

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

        todoDetail_date.setText(sdf.format(myCalendar.getTime()));
    }
    //팝업 가서 선택하고 다시 올 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int i = data.getIntExtra("imgId", 0);
                String str = data.getStringExtra("strStu");
                strUrl = data.getStringExtra("strStuURL");

                todoDetail_textch.setText(str);

                switch (i) {
                    case R.id.imageViewStu:
                        todoDetail_imageViewChange.setImageResource(R.drawable.leaning);
                        break;
                    case R.id.imageViewTimer:
                        todoDetail_imageViewChange.setImageResource(R.drawable.about_time);
                        break;
                    case R.id.imageViewMedi:
                        todoDetail_imageViewChange.setImageResource(R.drawable.medical);
                        break;
                    case R.id.imageViewSpecial:
                        todoDetail_imageViewChange.setImageResource(R.drawable.special_day);
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