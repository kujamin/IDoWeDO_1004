package com.example.firstproject3.bottom_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject3.CustomHabbitAdapter;
import com.example.firstproject3.CustomTodoAdapter;
import com.example.firstproject3.Habbit_Item;
import com.example.firstproject3.Login.LoginActivity;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.MainActivity;
import com.example.firstproject3.Todo_Item;
import com.example.firstproject3.R;
import com.example.firstproject3.usercode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Todo extends Fragment {

    private ViewGroup viewGroup;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private RecyclerView todo_recyclerView, habbit_recyclerView;
    private CustomTodoAdapter customTodoAdapter;
    private CustomHabbitAdapter customHabbitAdapter;
    private ArrayList<Todo_Item> todo_list;
    private ArrayList<Habbit_Item> habbit_list;
    int todo_count, habbit_count;
    final String TAG = "MainActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_todo, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


        todo_recyclerView = viewGroup.findViewById(R.id.rec_Todo);
        todo_recyclerView.setHasFixedSize(true); // 리사이클 뷰 성능 강화
        todo_recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        habbit_recyclerView = viewGroup.findViewById(R.id.rec_Habbit);
        habbit_recyclerView.setHasFixedSize(true); // 리사이클 뷰 성능 강화
        habbit_recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        todo_list = new ArrayList<Todo_Item>();
        customTodoAdapter = new CustomTodoAdapter(todo_list, viewGroup.getContext());
        //habbit
        habbit_list = new ArrayList<Habbit_Item>();
        customHabbitAdapter = new CustomHabbitAdapter(habbit_list, viewGroup.getContext());

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                 String userCode = (group.getEmailid());

                firebaseFirestore.collection("user").document(userCode).collection("user todo").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        todo_count = value.size();
                        todo_list.clear();

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("todo_title") != null) {
                                todo_list.add(0, new Todo_Item(doc.getString("todo_category"), doc.getString("todo_title"), doc.getString("todo_id")));
                            }
                        }
                        //어답터 갱신
                        customTodoAdapter.notifyDataSetChanged();
                    }
                });

                firebaseFirestore.collection("user").document(userCode).collection("user habbit").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        habbit_count = value.size();
                        habbit_list.clear();

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("habbit_title") != null) {
                                habbit_list.add(0, new Habbit_Item(doc.getString("habbit_category"), doc.getString("habbit_title"), doc.getString("habbit_id")));
                            }
                        }
                        //어답터 갱신
                        customHabbitAdapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        todo_recyclerView.setAdapter(customTodoAdapter);
        habbit_recyclerView.setAdapter(customHabbitAdapter);

        return viewGroup;

    }

}
