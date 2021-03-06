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

public class CustomTodoAdapter extends RecyclerView.Adapter<CustomTodoAdapter.CustomViewHolder> {

    private ArrayList<Todo_Item> arrayList;
    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity";
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String usercode;
    public static int achieve_point = 0;

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

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getTodo_category())
                .into(holder.todo_category);
        holder.todo_title.setText(arrayList.get(position).getTodo_title());
        holder.todo_checkBox.setChecked(arrayList.get(position).getTodo_checkbox());

        //앱 종료 후에도 글씨 상태 유지
        if(holder.todo_checkBox.isChecked()){
            holder.todo_title.setPaintFlags(holder.todo_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.todo_title.setTextColor(Color.GRAY);
        }
        else {
            holder.todo_title.setPaintFlags(0);
            holder.todo_title.setTextColor(Color.BLACK);
        }

        //할 일 목록 클릭 시 글씨 상태 변화
        holder.todo_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.todo_checkBox.isChecked()){
                    holder.todo_title.setPaintFlags(holder.todo_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.todo_title.setTextColor(Color.GRAY);
                }
                else {
                    holder.todo_title.setPaintFlags(0);
                    holder.todo_title.setTextColor(Color.BLACK);
                }
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        //파이어베이스에서 유저의 정보를 계정의 고유 아이디로 식별해서 해당 유저의 정보를 추출
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

        //할 일 체크박스 클릭 시
        holder.todo_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.todo_checkBox.isChecked()){   //할 일 체크되었을 때

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(usercode).collection("user todo").document(arrayList.get(position).getTodo_id());

                    //할 일 체크 시 경험치 상승
                    DocumentReference docRefC = firebaseFirestore.collection("user").document(usercode).collection("user character").document("state");
                    docRefC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                int myExp = Integer.parseInt(document.getString("exp"));
                                docRefC.update("exp",String.valueOf(myExp+10));

                            }
                        }
                    });

                    docRef.update("todo_checkbox",true);

                    //30레벨 달성 시 업적 획득
                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("dotodo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int value = snapshot.getValue(Integer.class);
                            value += 1;
                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("dotodo").setValue(value);
                            if(value == 30)
                            {
                                context.startActivity(new Intent(context, AchievementPopupActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{   //할 일 체크 해제 시
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = firebaseFirestore.collection("user").document(usercode).collection("user todo").document(arrayList.get(position).getTodo_id());

                    docRef.update("todo_checkbox",false);

                }
            }
        });

        //할 일 목록 클릭 시 화면 이동
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference docRef = firebaseFirestore.collection("user").document(usercode).collection("user todo").document(arrayList.get(position).getTodo_id());

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
