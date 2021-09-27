package com.example.firstproject3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

public class SaveActivity extends AppCompatActivity {

    private TextView saveText;
    private EditText nameEdit;
    private EditText goalEdit;
    ProgressDialog pd;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        nameEdit = findViewById(R.id.editName);
        goalEdit = findViewById(R.id.editGoal);

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        saveText = findViewById(R.id.saveText);
        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strName = nameEdit.getText().toString().trim();
                String strGoal = goalEdit.getText().toString().trim();

                uploadData(strName, strGoal);

                intent.putExtra("strName",strName);
                intent.putExtra("strGoal",strGoal);

                setResult(RESULT_OK,intent);

                finish();
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void uploadData(String title, String goal) {
        pd.setTitle("습관 생성 중...");

        pd.show();

        Map<String, Object> doc = new HashMap<>();
        doc.put("goal", goal);

        db.collection("user timer").document(title).set(doc)
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