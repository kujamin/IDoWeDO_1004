package com.idowedo.firstproject3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomHabbitAdapter extends RecyclerView.Adapter<CustomHabbitAdapter.CustomViewHolder> {
    private ArrayList<Habbit_Item> arrayList;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String usercode;

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

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getHabbit_category())
                .into(holder.habbit_category);
        holder.habbit_title.setText(arrayList.get(position).getHabbit_title());
        holder.habbit_checkBox.setChecked(arrayList.get(position).getHabbit_checkbox());

        //앱 종료 후에도 글씨 상태 유지
        if(holder.habbit_checkBox.isChecked()){
            holder.habbit_title.setPaintFlags(holder.habbit_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.habbit_title.setTextColor(Color.GRAY);
        }
        else {
            holder.habbit_title.setPaintFlags(0);
            holder.habbit_title.setTextColor(Color.BLACK);
        }

        //습관 목록 클릭 시 글씨 상태 변화
        holder.habbit_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.habbit_checkBox.isChecked()){
                    holder.habbit_title.setPaintFlags(holder.habbit_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.habbit_title.setTextColor(Color.GRAY);
                }
                else {
                    holder.habbit_title.setPaintFlags(0);
                    holder.habbit_title.setTextColor(Color.BLACK);
        }
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());//현재 로그인된 이메일 계정 가져오기

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //습관 체크박스 클릭 시
        holder.habbit_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.habbit_checkBox.isChecked()){ //습관 체크되었을 때

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(usercode).collection("user habbit").document(arrayList.get(position).getHabbit_id());

                    DocumentReference docRefC = firebaseFirestore.collection("user").document(usercode).collection("user character").document("state");

                    //습관 체크 시 경험치 상승
                    docRefC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                int myExp = Integer.parseInt(document.getString("exp"));
                                docRefC.update("exp",String.valueOf(myExp+10));

                            }
                        }
                    });
                    docRef.update("habbit_checkbox", true);

                    //30레벨 달성 시 업적 획득
                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("dotodo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int value = snapshot.getValue(Integer.class);
                            value += 1;
                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("dotodo").setValue(value);
                            if(value == 30)
                            {
                                Toast.makeText(context.getApplicationContext(), "획득한 배지가 있어요! 확인하러 가세요",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{   //체크박스 체크 해제 시
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(usercode).collection("user habbit").document(arrayList.get(position).getHabbit_id());

                    docRef.update("habbit_checkbox",false);
                }
            }
        });

        //습관 목록 클릭 시 화면 이동
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user").document(usercode).collection("user habbit").document(arrayList.get(position).getHabbit_id());
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
