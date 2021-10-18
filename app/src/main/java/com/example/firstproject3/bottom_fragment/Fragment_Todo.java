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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private String strDate;
    private int i = 0;
    private int j = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_todo, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

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

                firebaseFirestore.collection("user").document(userCode).collection("user todo")
                        .whereEqualTo("todo_date", strDate)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        todo_count = value.size();
                        todo_list.clear();

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

                        for (QueryDocumentSnapshot doc : value) {
                                todo_list.add(0, new Todo_Item(doc.getString("todo_category"), doc.getString("todo_title"), doc.getString("todo_id"), doc.getBoolean("todo_checkbox")));
                                String date1 = doc.getString("todo_date");
                        }
                        //어답터 갱신
                        customTodoAdapter.notifyDataSetChanged();
                    }
                });

                firebaseFirestore.collection("user").document(userCode).collection("user todo").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("todo_title") != null) {

                                doc.getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();

                                            String todo_id = document.getString("todo_id");

                                            if (todo_id != null) {
                                                DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user todo").document(todo_id);

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                                                Date todoDate = null;
                                                long now = System.currentTimeMillis();
                                                Date dateC = new Date(now);

                                                String strDate = document.getString("todo_date");

                                                try {
                                                    todoDate = dateFormat.parse(strDate);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                                Date todoTom = new Date(todoDate.getTime() + (long) (1000 * 60 * 60 * 24));
                                                String strTom = dateFormat.format(todoTom);
                                                String strC = dateFormat.format(dateC);

                                                Date date1 = null;
                                                Date date2 = null;
                                                try {
                                                    date1 = dateFormat.parse(strTom);
                                                    date2 = dateFormat.parse(strC);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                                int compare1 = date1.compareTo(date2);


                                                if (compare1 == 0) {
                                                    if(document.getBoolean("todo_pass") == false) {

                                                        if (document.getBoolean("todo_checkbox") == false) {
                                                            j++;
                                                        }
                                                        docRef.update("todo_pass", true);
                                                        docRef.update("todo_checkbox", false);
                                                    }
                                                }

                                            }
                                        }
                                    }
                                });


                            }
                        }

                        if (j != 0) {
                            DocumentReference documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character").document("state");
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        int heartC = Integer.parseInt(document.getString("heart"));
                                        documentReference.update("heart", String.valueOf(heartC - 1));
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });
                            j = 0;
                        }
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


                            doc.getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        String habbit_id = document.getString("habbit_id");

                                        if (habbit_id != null) {
                                            DocumentReference docRef = firebaseFirestore.collection("user").document(userCode).collection("user habbit").document(habbit_id);

                                            long now = System.currentTimeMillis();
                                            Date dateC = new Date(now);
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                                            Date dateE = null;
                                            Date dateRC = null;
                                            Date dateS = null;

                                            String strE = document.getString("habbit_date");
                                            String strS = document.getString("habbit_dateStart");

                                            String strC = dateFormat.format(dateC);

                                            Date date1 = null;
                                            Date date2 = null;

                                            try {
                                                date1 = dateFormat.parse(strS);
                                                date2 = dateFormat.parse(strC);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            int compare1 = date1.compareTo(date2);

                                            if(compare1 < 0){

                                                if(document.getBoolean("habbit_checkbox") == false){
                                                    i++;
                                                }

                                                docRef.update("habbit_dateStart", strC);
                                                docRef.update("habbit_checkbox",false);
                                            }

                                            try {
                                                dateE = dateFormat.parse(strE);
                                                dateRC = dateFormat.parse(strC);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            int compare = dateRC.compareTo(dateE);

                                            if (compare > 0) {
                                                docRef.delete();
                                            }
                                        }
                                    }
                                }
                            });
                                habbit_list.add(0, new Habbit_Item(doc.getString("habbit_category"), doc.getString("habbit_title"), doc.getString("habbit_id"), doc.getBoolean("habbit_checkbox")));

                            }
                            //어답터 갱신
                            customHabbitAdapter.notifyDataSetChanged();
                        }

                        if(i!=0){
                            DocumentReference documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character").document("state");
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        int heartC = Integer.parseInt(document.getString("heart"));
                                        documentReference.update("heart",String.valueOf(heartC-1));
                                    }
                                    else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });
                            i=0;
                        }
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
