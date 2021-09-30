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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.ContentHandler;
import java.util.ArrayList;


public class CustomChallengeAdapter extends RecyclerView.Adapter<CustomChallengeAdapter.CustomViewHoler> {

    private ArrayList<Challenge_Item> arrayList;
    private android.content.Context context;
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
