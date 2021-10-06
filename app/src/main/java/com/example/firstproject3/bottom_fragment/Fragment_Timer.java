package com.example.firstproject3.bottom_fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject3.ActivityResultEvent;
import com.example.firstproject3.BusProvider;
import com.example.firstproject3.CustomChoiceListViewAdapter;
import com.example.firstproject3.Habbit_Item;
import com.example.firstproject3.ListViewAdapter;
import com.example.firstproject3.Login.LoginActivity;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.R;
import com.example.firstproject3.SaveActivity;
import com.example.firstproject3.TimerActivity;
import com.example.firstproject3.Timer_Item;
import com.example.firstproject3.Todo_Item;
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
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class Fragment_Timer extends Fragment {

    private View view, viewList;
    private Button btnGirok;
    private LinearLayout layoutRecordPaper, addTimerlist;
    private RecyclerView timer_recyclerView;
    public String text, str;
    private TextView textRecord, textTitlt;
    private FirebaseFirestore firebaseFirestore;
    private ListViewAdapter timerAdapter;
    private ArrayList<Timer_Item> timer_list;
    private int timer_count;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String strUrl;
    final String TAG = "MainActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, false);
        viewList = inflater.inflate(R.layout.timer_listview, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        textRecord = view.findViewById(R.id.timerTextGoal);
        btnGirok = view.findViewById(R.id.girokButton);
        layoutRecordPaper = view.findViewById(R.id.layoutRecordPaper);
        addTimerlist = view.findViewById(R.id.addTimerlist);
        textTitlt = viewList.findViewById(R.id.timerTextName);

        firebaseFirestore = FirebaseFirestore.getInstance();
        timer_recyclerView = view.findViewById(R.id.rec_timer);
        timer_recyclerView.setHasFixedSize(true);
        timer_recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        timer_recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));//리사이클러뷰 구분선

        timer_list = new ArrayList<Timer_Item>();
        timerAdapter = new ListViewAdapter(timer_list, view.getContext());

        //String userCode = ((LoginActivity)LoginActivity.context_login).strEmail;

        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                String userCode = (group.getEmailid());

                firebaseFirestore.collection("user").document(userCode).collection("user timer").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        timer_count = value.size();
                        timer_list.clear();

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("timer_title") != null) {
                                timer_list.add(0,new Timer_Item(doc.getString("timer_title"), doc.getString("timer_goal"), doc.getString("timer_record"), doc.getString("timer_recImg"), doc.getString("timer_id")));
                                timer_recyclerView.setVisibility(View.VISIBLE);
                                layoutRecordPaper.setVisibility(View.GONE);
                                addTimerlist.setVisibility(View.VISIBLE);

                            }
                        }
                        //어답터 갱신
                        timerAdapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        timer_recyclerView.setAdapter(timerAdapter);

        btnGirok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(getContext(), SaveActivity.class);
                getActivity().startActivityForResult(new Intent(getContext(), SaveActivity.class), 101);
            }
        });

        layoutRecordPaper.setVisibility(View.VISIBLE);
        timer_recyclerView.setVisibility(View.GONE);
        addTimerlist.setVisibility(View.GONE);

        addTimerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), SaveActivity.class));
            }
        });

        if (getArguments() != null) {
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroyView() {
        BusProvider.getInstance().unregister(this);
        super.onDestroyView();

    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onActivityResultEvent(@NonNull ActivityResultEvent event) {
        onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100: {
                if (data != null) {
                    String strName = data.getStringExtra("strName");
                    String strGoal  = data.getStringExtra("strGoal");
                    strUrl = data.getStringExtra("timerUrl");
                    Toast.makeText(getContext(), strUrl, Toast.LENGTH_LONG).show();
                }
            }
//            case 101: {
//                if (data != null) {
//                    text = data.getStringExtra("strName");
//
//                    ListViewAdapter adapter;
//
//                    // Adapter 생성
//                    adapter = new ListViewAdapter() ;
//
//                    // 리스트뷰 참조 및 Adapter달기
//
//                    recyclerView.setAdapter(adapter);
//
//                    // 첫 번째 아이템 추가.
//                    adapter.addTimer(ContextCompat.getDrawable(getContext(), R.drawable.chara),
//                            text, "기록하기");
//
//                    // 두 번째 아이템 추가.
//                    adapter.addTimer(ContextCompat.getDrawable(getContext(), R.drawable.chara),
//                            "타이머2", "기록하기");
//
//                    // 세 번째 아이템 추가.
//                    adapter.addTimer(ContextCompat.getDrawable(getContext(), R.drawable.chara),
//                            "타이머3", "기록하기");
//
//                    layoutRecordPaper.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                break;
//            }
//        }//switch
        }


    }
}
