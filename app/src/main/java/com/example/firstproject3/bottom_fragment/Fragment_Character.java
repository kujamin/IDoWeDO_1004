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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstproject3.AchieveActivity;
import com.example.firstproject3.CustomTodoAdapter;
import com.example.firstproject3.DecoActivity;

import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.MainActivity;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class Fragment_Character extends Fragment {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    TextView textLevel;
    TextView textExp;
    TextView textCoin;
    Handler handler;
    int maxExp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        progressBar = (ProgressBar) rootView.findViewById(R.id.expProgressBar);
        textLevel = (TextView) rootView.findViewById(R.id.textViewLevel);
        textExp = (TextView) rootView.findViewById(R.id.textViewExp);
        textCoin = (TextView) rootView.findViewById(R.id.textview_coin);


        LinearLayout lstore = rootView.findViewById(R.id.storePage);
        LinearLayout ldeco = rootView.findViewById(R.id.decoPage);
        LinearLayout lachieve = rootView.findViewById(R.id.achievePage);


        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);

                int currentexp = Integer.parseInt(group.getExp());
                int coin = Integer.parseInt(group.getCoin());
                int level = Integer.parseInt(group.getLevel());

                maxExp = 100 + (level);
                textExp.setText(currentexp + " / " + maxExp);

                progressBar.setMax(maxExp);
                progressBar.setProgress(currentexp);
                if (currentexp >= maxExp) {
                    level++;
                    currentexp = currentexp - maxExp;
                }
                textLevel.setText("Lv." + level);
                textCoin.setText(coin + "");

                String expup = Integer.toString(currentexp);
                String levelup = Integer.toString(level);

                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("level").setValue(levelup);
                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("exp").setValue(expup);

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
