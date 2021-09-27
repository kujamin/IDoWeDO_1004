package com.example.firstproject3.bottom_fragment;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.firstproject3.ClickTransActivity;
import com.example.firstproject3.HabbitMakeActivity;
import com.example.firstproject3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fragment_Challenge extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge, container, false);

        LinearLayout challenge1 = view.findViewById(R.id.challenge1);
        LinearLayout challenge2 = view.findViewById(R.id.challenge2);
        LinearLayout challenge3 = view.findViewById(R.id.challenge3);

        TextView textChall1 = view.findViewById(R.id.textChall1);
        String strChall1 = textChall1.getText().toString().trim();

        TextView textChall2 = view.findViewById(R.id.textChall2);
        TextView textChall3 = view.findViewById(R.id.textChall3);
        firebaseFirestore = FirebaseFirestore.getInstance();


        challenge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ClickTransActivity.class);
                intent.putExtra("textChall1", strChall1);
                startActivity(intent);
            }
        });

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
