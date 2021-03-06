package com.idowedo.firstproject3;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.idowedo.firstproject3.Login.LoginActivity;
import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteUser extends Activity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mdatabase;
    private String usercode;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete_user);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser firebaseUser1 = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        Button btn_delete_yes = findViewById(R.id.btn_delete_yes);
        Button btn_delete_no = findViewById(R.id.btn_delete_no);

        btn_delete_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????
                finish();
            }
        });
        // ???????????? ?????? ???????????? ??????
        btn_delete_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();

                            //firestore delete, ?????? ?????? ?????? ??????, ????????? ????????? ?????? ??????
                            mdatabase.child("idowedo").child("UserAccount").child(firebaseUser1.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    UserAccount group = dataSnapshot.getValue(UserAccount.class);
                                    usercode = (group.getEmailid());

                                    firebaseFirestore.collection("user").document(usercode).delete();

                                    firebaseFirestore.collection("challenge").document("????????? ????????????").collection("challenge list").document(usercode)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot document = task.getResult();
                                                if(document.exists()) {
                                                    firebaseFirestore.collection("challenge").document("????????? ????????????").collection("challenge list").document(usercode).delete();
                                                }
                                            }
                                        }
                                    });

                                    firebaseFirestore.collection("challenge").document("?????? 6??? ????????????").collection("challenge list").document(usercode)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot document = task.getResult();
                                                if(document.exists()) {
                                                    firebaseFirestore.collection("challenge").document("?????? 6??? ????????????").collection("challenge list").document(usercode).delete();
                                                }
                                            }
                                        }
                                    });

                                    firebaseFirestore.collection("challenge").document("?????? ?????? ??????").collection("challenge list").document(usercode)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot document = task.getResult();
                                                if(document.exists()) {
                                                    firebaseFirestore.collection("challenge").document("?????? ?????? ??????").collection("challenge list").document(usercode).delete();
                                                }
                                            }
                                        }
                                    });


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            //???????????? DB ??????
                            FirebaseAuth.getInstance().signOut();
                            mFirebaseAuth.signOut();

                            Intent intent = new Intent(DeleteUser.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            onDestroy();

                        } else {
                            //????????? ????????? ?????? ????????? ????????? ??????????????? ?????? ????????? ??????????????? ??? (????????? ????????? ??????????????? ????????? ????????? ??????)
                            Toast.makeText(getApplicationContext(), "?????? ????????? ???????????? ????????? ?????? ???????????? ???????????????!!", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            mFirebaseAuth.signOut();

                            Intent intent = new Intent(DeleteUser.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });


            }
        });
    }


}