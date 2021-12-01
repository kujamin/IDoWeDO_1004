package com.idowedo.firstproject3.bottom_fragment;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.idowedo.firstproject3.AchieveActivity;
import com.idowedo.firstproject3.AchievementPopupActivity;
import com.idowedo.firstproject3.DecoActivity;

import com.idowedo.firstproject3.LevelupActivity;
import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.NickPopup;
import com.idowedo.firstproject3.R;
import com.idowedo.firstproject3.StoreActivity;
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

public class Fragment_Character extends Fragment {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    TextView textLevel, textExp, textCoin, textNickName;
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

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                String userCode = (group.getEmailid());//현재 로그인된 이메일 계정 가져오기

                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character").document("state");

                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        //사용자의 코인, 레벨, 체력 가져오기
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

                                    //30레벨 달성 시 업적 팝업 한번만 뜨게 하기
                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("lvAchieve").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int value = snapshot.getValue(Integer.class);
                                           if(value == 0) {
                                               if(level == 30) {
                                                   value ++;
                                                   Intent i = new Intent(rootView.getContext(), AchievementPopupActivity.class);
                                                   mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("lvAchieve").setValue(value);
                                                   startActivity(i);
                                               }
                                           } else {

                                           }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    //코인 1000 모이면 업적 팝업 띄우기
                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("coinAchieve").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int value = snapshot.getValue(Integer.class);
                                            if(value == 0) {
                                                if(coin >= 1000) {
                                                    value ++;
                                                    Intent i = new Intent(rootView.getContext(), AchievementPopupActivity.class);
                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("coinAchieve").setValue(value);
                                                    startActivity(i);
                                                }
                                            } else {

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                }
                            }
                        });

                        DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user character").document("deco");

                        //사용자가 저장한 옷 불러오기
                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();

                                            Activity activity = (Activity) rootView.getContext();
                                            if (activity.isFinishing())
                                                return;

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
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //사용자 닉네임 띄우기
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

        //닉네임 부분 길게 눌렀을 때 닉네임 변경 팝업 띄우기
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
