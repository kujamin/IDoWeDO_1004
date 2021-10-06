package com.example.firstproject3.bottom_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstproject3.AchieveActivity;
import com.example.firstproject3.CustomTodoAdapter;
import com.example.firstproject3.DecoActivity;

import com.example.firstproject3.Login.LoginActivity;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.MainActivity;
import com.example.firstproject3.R;
import com.example.firstproject3.StoreActivity;
import com.example.firstproject3.Timer_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class Fragment_Character extends Fragment {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    TextView textLevel, textExp, textCoin, textNickName;
    Handler handler;
    private DocumentReference documentReference;
    private FirebaseFirestore firebaseFirestore;
    private int coin, level, maxExp, currentExp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressBar = (ProgressBar) rootView.findViewById(R.id.expProgressBar);
        textLevel = (TextView) rootView.findViewById(R.id.textViewLevel);
        textExp = (TextView) rootView.findViewById(R.id.textViewExp);
        textCoin = (TextView) rootView.findViewById(R.id.textview_coin);
        textNickName = (TextView) rootView.findViewById(R.id.tv_nickname);


        LinearLayout lstore = rootView.findViewById(R.id.storePage);
        LinearLayout ldeco = rootView.findViewById(R.id.decoPage);
        LinearLayout lachieve = rootView.findViewById(R.id.achievePage);

//        String userCode = ((LoginActivity)LoginActivity.context_login).strEmail;

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                String userCode = (group.getEmailid());

                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character").document("state");

                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();

                                    coin = Integer.parseInt(document.getString("coin"));
                                    currentExp = Integer.parseInt(document.getString("exp"));
                                    level = Integer.parseInt(document.getString("level"));
                                    maxExp = Integer.parseInt(document.getString("maxExp"));

                                    if(currentExp >= maxExp) {
                                        level++;
                                        currentExp = currentExp - maxExp;
                                        maxExp += 10;
                                        documentReference.update("maxExp",maxExp+"");
                                        documentReference.update("level",level+"");
                                        documentReference.update("exp",currentExp+"");
                                    }
                                    progressBar.setMax(maxExp);
                                    progressBar.setProgress(currentExp);

                                    textLevel.setText("Lv." + level);
                                    textCoin.setText(coin + "");
                                    textExp.setText(currentExp + " / " + maxExp);

                                }
                            }
                        });



//                        maxExp = 100+level;
//                        progressBar.setMax(maxExp);
//                        progressBar.setProgress(currentExp);
//                        if(currentExp >= maxExp) {
//                            level++;
//                            currentExp = currentExp - maxExp;
//                        }
//                        textLevel.setText("Lv." + level);
//                        textCoin.setText(coin + "");
//
//                        documentReference.update("coin", String.valueOf(coin));
//                        documentReference.update("exp", String.valueOf(currentExp)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                textExp.setText(currentExp + " / " + maxExp);
//                                Log.d("TAG","success");
//                            }
//                        });
//                        documentReference.update("level", String.valueOf(level));
//                        documentReference.update("maxExp", String.valueOf(maxExp));
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);

               /* int coin = group.getCoin();
                int currentexp = group.getExp();
                int level = group.getLevel();
                int mexp = group.getMaxexp();*/
                String nickname = (group.getNickname());

                /*mexp = 100 + (level);
                textExp.setText(currentexp + " / " + mexp);*/


                /*progressBar.setMax(mexp);
                progressBar.setProgress(currentexp);
                if (currentexp >= mexp) {
                    level++;
                    currentexp = currentexp - mexp;
                }
                textLevel.setText("Lv." + level);
                textCoin.setText(coin + "");*/
                textNickName.setText(nickname + "");

                /*mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("level").setValue(level);
                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("exp").setValue(currentexp);
                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("maxexp").setValue(mexp);*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootView.getContext(), StoreActivity.class);
                startActivity(i);
            }
        });


        lachieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootView.getContext(), AchieveActivity.class);
                startActivity(i);
            }
        });

        ldeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootView.getContext(), DecoActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}
