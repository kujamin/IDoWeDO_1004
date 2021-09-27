package com.example.firstproject3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject3.daily.CalListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClickTransActivity extends AppCompatActivity {
    ImageView imgarrow;
    TextView textChallAttend;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity", id, pw, code;

    public void onClickBack(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_trans);

        Intent intent = getIntent();
        String textChall1 = intent.getStringExtra("textChall1");
        String usercode = ((usercode)getApplication()).getUsercode();
        String randomid = UUID.randomUUID().toString();

        firebaseFirestore = FirebaseFirestore.getInstance();

        imgarrow = findViewById(R.id.imageViewarrow);
        textChallAttend = findViewById(R.id.textViewAttend);

        imgarrow.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);

        //참가하기 버튼 누르면
        textChallAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClickTransActivity.this, PopupActivity.class));

                firebaseFirestore.collection("user")
                        .whereEqualTo("user code", usercode)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("id"));
                                        id = (String) document.getData().get("id");
                                        pw = (String) document.getData().get("password");
                                        code = (String) document.getData().get("user code");
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                Map<String, Object> doc = new HashMap<>();
                doc.put("challenge_randomid", randomid);
                doc.put("challenge_id", id);
                doc.put("challenge_pw", pw);
                doc.put("challenge_code", code);

                firebaseFirestore.collection("user challenge").document(textChall1).collection("challenge list").document(randomid).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });



                textChallAttend.setText("참가중...");
            }
        });

    }
}