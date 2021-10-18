package com.example.firstproject3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.ContentHandler;
import java.util.ArrayList;


public class CustomChallengeAdapter extends RecyclerView.Adapter<CustomChallengeAdapter.CustomViewHoler> {

    private ArrayList<Challenge_Item> arrayList;
    private android.content.Context context;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";

    public CustomChallengeAdapter(ArrayList<Challenge_Item> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_item,parent,false);
        CustomViewHoler holder = new CustomViewHoler(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHoler holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getChallenge_image()).into(holder.challenge_image);
        holder.challenge_title.setText(arrayList.get(position).getChallenge_title());
        holder.challenge_content.setText(arrayList.get(position).getChallenge_content());
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = firebaseFirestore.getInstance();
                DocumentReference doRef = firebaseFirestore.collection("challenge").document(arrayList.get(position).getChallenge_title());

                doRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            Intent intent = new Intent(context, ClickTransActivity.class);
                            intent.putExtra("chall_title", document.getString("challenge_title"));
                            intent.putExtra("chall_img", document.getString("challenge_image"));

                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("challengepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int value = snapshot.getValue(Integer.class);
                                    value += 1;
                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("challengepoint").setValue(value);
                                    if(value == 1)
                                    {
                                        Toast.makeText(context.getApplicationContext(), "획득한 배지가 있어요! 확인하러 가세요",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            context.startActivity(intent);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHoler extends RecyclerView.ViewHolder {
        ImageView challenge_image;
        TextView challenge_title;
        TextView challenge_content;

        public CustomViewHoler(@NonNull View itemView) {
            super(itemView);
            this.challenge_image = itemView.findViewById(R.id.challenge_image);
            this.challenge_title = itemView.findViewById(R.id.challenge_title);
            this.challenge_content = itemView.findViewById(R.id.challenge_content);
        }
    }
}
