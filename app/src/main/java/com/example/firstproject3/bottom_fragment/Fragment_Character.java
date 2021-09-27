package com.example.firstproject3.bottom_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstproject3.AchieveActivity;
import com.example.firstproject3.DecoActivity;
import com.example.firstproject3.R;
import com.example.firstproject3.StoreActivity;

public class Fragment_Character extends Fragment {
    private View view;

    /*
    public void storeOnclick(View v) {
        Intent i = new Intent(view.getContext(), StoreActivity.class);
        startActivity(i);
    }

    public void achieveOnclick(View v) {
        Intent i = new Intent(view.getContext(), AchieveActivity.class);
        startActivity(i);
    }

    public void decoOnclick(View v) {
        Intent i = new Intent(view.getContext(), DecoActivity.class);
        startActivity(i);
    }
*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);

        LinearLayout lstore = view.findViewById(R.id.storePage);
        LinearLayout ldeco = view.findViewById(R.id.decoPage);
        LinearLayout lachieve = view.findViewById(R.id.achievePage);

        lstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), StoreActivity.class);
                startActivity(i);
            }
        });

        lachieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), AchieveActivity.class);
                startActivity(i);
            }
        });

        ldeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), DecoActivity.class);
                startActivity(i);
            }
        });



        return view;
    }
}
