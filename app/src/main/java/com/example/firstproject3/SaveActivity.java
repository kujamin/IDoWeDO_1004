package com.example.firstproject3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.firstproject3.Login.UserAccount;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SaveActivity extends AppCompatActivity {

    private TextView saveText;
    private EditText nameEdit;
    private EditText goalEditHour, goalEditMin;
    ProgressDialog pd;
    FirebaseFirestore db;
    private String goalHour, goalMin, userCode;
    private int hour,min;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        nameEdit = findViewById(R.id.editTimerName);
        goalEditHour = findViewById(R.id.editGoalHour);
        goalEditMin = findViewById(R.id.editGoalMin);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        //String usercode = ((usercode)getApplication()).getUsercode();

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

//        goalHour = goalEditHour.getText().toString().trim();
//        goalMin = goalEditMin.getText().toString().trim();

        //이름 목표치 null일 때때


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

        saveText = findViewById(R.id.saveText);
        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalHour = String.valueOf(goalEditHour.getText());
                goalMin = String.valueOf(goalEditMin.getText());
                Intent intent = new Intent();
                String id = UUID.randomUUID().toString();
                String strName = nameEdit.getText().toString().trim();
                String strGoal = goalHour + " : " + goalMin;
                String strUrl = "https://firebasestorage.googleapis.com/v0/b/graduationproject-6a8ed.appspot.com/o/record_button3.png?alt=media&token=c679fa0b-f416-44ed-867f-c8969daeab7f";

                uploadData(id, strName, strGoal, userCode, strUrl);

                intent.putExtra("timer_id", id);
                intent.putExtra("strName",strName);
                intent.putExtra("strGoal",strGoal);
                intent.putExtra("timerUrl", strUrl);

                setResult(RESULT_OK,intent);

                finish();
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void uploadData(String id, String title, String goal, String userCode, String strUrl) {
        pd.setTitle("스톱워치 생성 중...");

        pd.show();

        Map<String, Object> doc = new HashMap<>();
        doc.put("timer_id", id);
        doc.put("timer_title", title);
        doc.put("timer_goal", goal);
        doc.put("timer_record", "00 : 00 : 00");
        doc.put("timer_recImg", strUrl);
        doc.put("userCode", userCode);
        doc.put("timer_time","0");

        db.collection("user").document(userCode).collection("user timer").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(SaveActivity.this, title + " 일정이 설정되었습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(SaveActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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