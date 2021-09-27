package com.example.firstproject3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomHabbitAdapter extends RecyclerView.Adapter<CustomHabbitAdapter.CustomViewHolder> {

    private ArrayList<Habbit_Item> arrayList;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";


    public CustomHabbitAdapter(ArrayList<Habbit_Item> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override //여기에 온클릭 가능하다네
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getHabbit_category())
                .into(holder.habbit_category);
        holder.habbit_title.setText(arrayList.get(position).getHabbit_title());

        holder.habbit_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.habbit_checkBox.isChecked()){
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user habbit").document(arrayList.get(position).getHabbit_id());


                    docRef.update("habbit_checkbox",true);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                Toast.makeText(v.getContext(),"타이틀 : " + document.getString("habbit_title"),Toast.LENGTH_SHORT).show();

                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
                else{
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user habbit").document(arrayList.get(position).getHabbit_id());

                    docRef.update("habbit_checkbox",false);
                }
            }
        });

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user habbit").document(arrayList.get(position).getHabbit_id());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Intent intent = new Intent(context,HabbitDetailActivity.class);
                            intent.putExtra("habbit_title",document.getString("habbit_title"));
                            intent.putExtra("habbit_memo",document.getString("habbit_memo"));
                            intent.putExtra("habbit_category",document.getString("habbit_category"));
                            intent.putExtra("habbit_date",document.getString("habbit_date"));
                            intent.putExtra("habbit_cateText",document.getString("habbit_cateText"));
                            intent.putExtra("habbit_id",document.getString("habbit_id"));

                            context.startActivity(intent);

                            Toast.makeText(v.getContext(),"날짜 : " + document.getString("habbit_date")
                                    + arrayList.get(position).getHabbit_title()
                                    + "/n타이틀 : " + document.getString("habbit_title")
                                    + "\n메모 : " + document.getString("habbit_memo"),Toast.LENGTH_LONG).show();

                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
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

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView habbit_category;
        TextView habbit_title;
        CheckBox habbit_checkBox;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.habbit_category = itemView.findViewById(R.id.todo_category);
            this.habbit_title = itemView.findViewById(R.id.todo_title);
            this.habbit_checkBox = itemView.findViewById(R.id.todo_CheckBox);
        }
    }
}
