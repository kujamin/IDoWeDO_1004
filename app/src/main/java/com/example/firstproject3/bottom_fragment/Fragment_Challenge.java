package com.example.firstproject3.bottom_fragment;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firstproject3.Challenge_Item;
import com.example.firstproject3.ClickTransActivity;
import com.example.firstproject3.CustomChallengeAdapter;
import com.example.firstproject3.HabbitMakeActivity;
import com.example.firstproject3.R;
import com.example.firstproject3.Todo_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
