package com.example.firstproject3.bottom_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.firstproject3.AchieveActivity;
import com.example.firstproject3.AchievementPopupActivity;
import com.example.firstproject3.AtCheck.Attendance_CheckActivity;
import com.example.firstproject3.DecoActivity;

import com.example.firstproject3.LevelupActivity;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.NickPopup;
import com.example.firstproject3.R;
import com.example.firstproject3.StoreActivity;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.logging.Level;

public class Fragment_Character extends Fragment {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    TextView textLevel, textExp, textCoin, textNickName;
    Handler handler;
    private DocumentReference documentReference;
    private FirebaseFirestore firebaseFirestore;
    private int coin, level, maxExp, currentExp, heart;
    private RatingBar ratingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        ImageView cloStateHead = rootView.findViewById(R.id.imageViewHeadMini);
        ImageView cloStateTorso = rootView.findViewById(R.id.imageViewTorsoMini);
        ImageView cloStateLeg = rootView.findViewById(R.id.imageViewLegMini);
        ImageView cloStateArm = rootView.findViewById(R.id.imageViewArmMini);

        progressBar = (ProgressBar) rootView.findViewById(R.id.expProgressBar);
        textLevel = (TextView) rootView.findViewById(R.id.textViewLevel);
        textExp = (TextView) rootView.findViewById(R.id.textViewExp);
        textCoin = (TextView) rootView.findViewById(R.id.textview_coin);
        textNickName = (TextView) rootView.findViewById(R.id.tv_nickname);
        ratingBar = rootView.findViewById(R.id.ratingBar2);


        LinearLayout lstore = rootView.findViewById(R.id.storePage);
        LinearLayout ldeco = rootView.findViewById(R.id.decoPage);
        LinearLayout lachieve = rootView.findViewById(R.id.achievePage);

//        String userCode = ((LoginActivity)LoginActivity.context_login).strEmail;

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                String userCode = (group.getEmailid());

//                firebaseFirestore.collection("user").document(userCode).collection("user todo")
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                if (error != null) {
//                                    Log.w("TAG", "Listen failed.", error);
//                                    return;
//                                }
//                                for (QueryDocumentSnapshot doc : value) {
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                                    Date today = new Date ( );
//                                    Date tomorrow = new Date ( today.getTime ( ) + (long) ( 1000 * 60 * 60 * 24 ) );
//                                }
//                            }
//                        });

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
                                    heart = Integer.parseInt(document.getString("heart"));

                                    if(currentExp >= maxExp) {
                                        level++;
                                        currentExp = currentExp - maxExp;
                                        maxExp += 10;
                                        documentReference.update("maxExp",maxExp+"");
                                        documentReference.update("level",level+"");
                                        documentReference.update("coin",String.valueOf(coin+5));
                                        documentReference.update("heart","5");
                                        documentReference.update("exp",currentExp+"");
                                        getActivity().startActivity(new Intent(getActivity(), LevelupActivity.class));
                                    }

                                    if(heart <= 0){
                                        documentReference.update("heart","5");
                                        documentReference.update("coin",String.valueOf(coin - 50));
                                    }

                                    if(coin < 0){
                                        documentReference.update("coin","0");
                                    }

                                    progressBar.setMax(maxExp);
                                    progressBar.setProgress(currentExp);

                                    ratingBar.setRating(heart);

                                    textLevel.setText("Lv." + level);
                                    textCoin.setText(coin + "");
                                    textExp.setText(currentExp + " / " + maxExp);

                                }
                            }
                        });

                        DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user character").document("deco");

                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();

                                            Glide.with(rootView.getContext()).load(document.getString("cloHead")).into(cloStateHead);
                                            Glide.with(rootView.getContext()).load(document.getString("cloTorso")).into(cloStateTorso);
                                            Glide.with(rootView.getContext()).load(document.getString("cloLeg")).into(cloStateLeg);
                                            Glide.with(rootView.getContext()).load(document.getString("cloArm")).into(cloStateArm);

                                            cloStateHead.setVisibility(View.VISIBLE);
                                            cloStateTorso.setVisibility(View.VISIBLE);
                                            cloStateLeg.setVisibility(View.VISIBLE);
                                            cloStateArm.setVisibility(View.VISIBLE);

                                        }

                                    }
                                });
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
                String nickname = (group.getNickname());

                textNickName.setText(nickname + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //닉네임 변경 꾹눌렀을때때
        textNickName.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Intent i = new Intent(rootView.getContext(), NickPopup.class);
                startActivity(i);

                return false;
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
