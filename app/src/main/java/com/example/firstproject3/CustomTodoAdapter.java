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
import com.example.firstproject3.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomTodoAdapter extends RecyclerView.Adapter<CustomTodoAdapter.CustomViewHolder> {

    private ArrayList<Todo_Item> arrayList;
    static Context context;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";
    private String strEmail;

    public CustomTodoAdapter(ArrayList<Todo_Item> arrayList, Context context) {
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
                .load(arrayList.get(position).getTodo_category())
                .into(holder.todo_category);
        holder.todo_title.setText(arrayList.get(position).getTodo_title());

        holder.todo_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.todo_checkBox.isChecked()){
                    firebaseFirestore = FirebaseFirestore.getInstance();

                    strEmail = ((LoginActivity)LoginActivity.context_login).strEmail;

                    Toast.makeText(context,strEmail,Toast.LENGTH_LONG).show();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(strEmail);

                    docRef.update("todo_checkbox",true);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();


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
                    DocumentReference docRef = firebaseFirestore.collection("user").document(strEmail);

                    docRef.update("todo_checkbox",false);
                }
            }
        });
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user").document(strEmail);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            Intent intent = new Intent(context, TodoDetailActivity.class);
                            intent.putExtra("todo_title",document.getString("todo_title"));
                            intent.putExtra("todo_memo",document.getString("todo_memo"));
                            intent.putExtra("todo_category",document.getString("todo_category"));
                            intent.putExtra("todo_date",document.getString("todo_date"));
                            intent.putExtra("todo_cateText",document.getString("todo_cateText"));
                            intent.putExtra("todo_time", document.getString("todo_time"));
                            intent.putExtra("todo_id",document.getString("todo_id"));

                            context.startActivity(intent);

                            Toast.makeText(v.getContext(),"날짜 : " + document.getString("todo_date")
                                    + "\n메모 : " + document.getString("todo_memo")
                                    + "\n시간 : " + document.getString("todo_time"),Toast.LENGTH_SHORT).show();
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
        ImageView todo_category;
        TextView todo_title;
        CheckBox todo_checkBox;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.todo_category = itemView.findViewById(R.id.todo_category);
            this.todo_title = itemView.findViewById(R.id.todo_title);
            this.todo_checkBox = itemView.findViewById(R.id.todo_CheckBox);


        }
    }
}
