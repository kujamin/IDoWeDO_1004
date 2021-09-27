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
import com.example.firstproject3.Todo_Item;
import com.example.firstproject3.R;
import com.example.firstproject3.usercode;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Todo extends Fragment {

    private ViewGroup viewGroup;
    private FirebaseFirestore firebaseFirestore;
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
        View view = (View) inflater.inflate(R.layout.activity_login, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        todo_recyclerView = viewGroup.findViewById(R.id.rec_Todo);
        todo_recyclerView.setHasFixedSize(true); // 리사이클 뷰 성능 강화
        todo_recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        habbit_recyclerView = viewGroup.findViewById(R.id.rec_Habbit);
        habbit_recyclerView.setHasFixedSize(true); // 리사이클 뷰 성능 강화
        habbit_recyclerView.setLayoutManager(new LinearLayoutManager(viewGroup.getContext()));

        todo_list = new ArrayList<Todo_Item>();
        customTodoAdapter = new CustomTodoAdapter(todo_list, view.getContext());
//        String userCode = ((usercode)getContext()).getUsercode();
//        Toast.makeText(getContext(), userCode, Toast.LENGTH_SHORT).show();

        firebaseFirestore.collection("user todo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                todo_count = value.size();
                todo_list.clear();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("todo_title") != null) {
                        todo_list.add(0,new Todo_Item(doc.getString("todo_category"), doc.getString("todo_title"), doc.getString("todo_id")));
                    }
                }
                //어답터 갱신
                customTodoAdapter.notifyDataSetChanged();
            }
        });

        todo_recyclerView.setAdapter(customTodoAdapter);

        //habbit
        habbit_list = new ArrayList<Habbit_Item>();
        customHabbitAdapter = new CustomHabbitAdapter(habbit_list,viewGroup.getContext());

        customTodoAdapter.notifyDataSetChanged();

        firebaseFirestore.collection("user habbit").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                habbit_count = value.size();
                habbit_list.clear();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("habbit_title") != null) {
                        habbit_list.add(0,new Habbit_Item(doc.getString("habbit_category"), doc.getString("habbit_title"), doc.getString("habbit_id")));
                    }
                }
                //어답터 갱신
                    customHabbitAdapter.notifyDataSetChanged();
            }
        });

            habbit_recyclerView.setAdapter(customHabbitAdapter);

        return viewGroup;

    }//onCreateView

}
