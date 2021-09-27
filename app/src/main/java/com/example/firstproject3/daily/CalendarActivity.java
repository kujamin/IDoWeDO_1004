package com.example.firstproject3.daily;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject3.R;
import com.example.firstproject3.Todo_Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView listcal;
    private ArrayList<CalListActivity> cal_list;
    private CustomCalendar calAdapter;
    private MaterialCalendarView calendarView;
    private String strDate;
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);

        String sDY = String.valueOf(CalendarDay.today().getYear());
        String sDM = String.valueOf(CalendarDay.today().getMonth()+1);
        String sDD = String.valueOf(CalendarDay.today().getDay());

        if (sDM.length() != 2) {
            sDM = 0 + sDM;
        }
        if (sDD.length() != 2){
            sDD = 0 + sDD;
        }


        strDate = sDY + "/" + sDM + "/" + sDD;
        Toast.makeText(getApplicationContext(),strDate,Toast.LENGTH_SHORT).show();

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.setSelectedDate(CalendarDay.today());

        listcal = findViewById(R.id.listcal);
        listcal.setHasFixedSize(true);
        listcal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        cal_list = new ArrayList<CalListActivity>();
        calAdapter = new CustomCalendar(cal_list, getApplicationContext());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("user todo")
                .whereEqualTo("todo_date", strDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cal_list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, document.getId() + " => " + document.getData().get("todo_title"));
                                cal_list.add(0, new CalListActivity((String)document.getData().get("todo_title"),(String)document.getData().get("todo_time")));
                            }
                            calAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        listcal.setAdapter(calAdapter);

//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseFirestore.collection("user todo").whereEqualTo("todo_date", strDate).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                cal_list.clear();
//
//                for (QueryDocumentSnapshot doc : value) {
//                    cal_list.add(0, new CalListActivity(doc.getString("todo_title"), doc.getString("todo_time")));
//                }
//                calAdapter.notifyDataSetChanged();
//            }
//        });
//        CollectionReference collectionReference = firebaseFirestore.collection("user todo");
//        collectionReference.whereEqualTo("todo_date", strDate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    //컬렉션 아래에 있는 모든 정보를 가져온다.
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        cal_list.add(0, new CalListActivity(document.getString("todo_title"), document.getString("todo_time")));
//                        //document.getData() or document.getId() 등등 여러 방법으로
//                        //데이터를 가져올 수 있다.
//                    }
//                    //그렇지 않을때
//                } else {
//
//                }
//            }
//        });
//        collectionReference.whereEqualTo("todo_date", strDate).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null){
//                    Log.w(TAG, "Listen failed.", error);
//                    return;
//                }
//                cal_list.clear();
//                for (QueryDocumentSnapshot doc : value){
//                    cal_list.add(0,new CalListActivity(doc.getString("todo_title"), doc.getString("todo_time")));
//                }
//                calAdapter.notifyDataSetChanged();
//            }
//        });

//        listcal.setAdapter(calAdapter);

        //캘린더뷰 클릭 시 이벤트
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String month = String.valueOf(date.getMonth() + 1);
                String day = String.valueOf(date.getDay());

                if (month.length() != 2) {
                    month = 0 + month;
                }
                if (day.length() != 2){
                    day = 0 + day;
                }

                strDate = date.getYear() + "/" + month + "/" + day;

                firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("user todo")
                        .whereEqualTo("todo_date", strDate)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    cal_list.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("todo_title"));
                                        cal_list.add(0, new CalListActivity((String)document.getData().get("todo_title"),(String)document.getData().get("todo_time")));
                                    }
                                    calAdapter.notifyDataSetChanged();
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                listcal.setAdapter(calAdapter);

                Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
            }
        });
    }
}