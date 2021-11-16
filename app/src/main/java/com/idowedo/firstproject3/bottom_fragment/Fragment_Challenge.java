package com.idowedo.firstproject3.bottom_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idowedo.firstproject3.Challenge_Item;
import com.idowedo.firstproject3.CustomChallengeAdapter;
import com.idowedo.firstproject3.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Challenge extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Challenge_Item> challenge_list;
    private CustomChallengeAdapter customChallengeAdapter;
    private ImageView imageView;
    final String TAG = "MainActivity";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.challenge_rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        challenge_list = new ArrayList<Challenge_Item>();
        customChallengeAdapter = new CustomChallengeAdapter(challenge_list, view.getContext());

        firebaseFirestore.collection("challenge").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                challenge_list.clear();
                for (QueryDocumentSnapshot doc : value) {
                    challenge_list.add(0,new Challenge_Item(doc.getString("challenge_image"), doc.getString("challenge_title"), doc.getString("challenge_content")));
                }
                customChallengeAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(customChallengeAdapter);

//        View.OnClickListener ocl = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(view.getContext(), ClickTransActivity.class);
//                startActivity(i);
//
//            }
//        };
//
//        challenge1.setOnClickListener(ocl);
//        challenge2.setOnClickListener(ocl);
//        challenge3.setOnClickListener(ocl);

        return view;

    }

}
