package com.idowedo.firstproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.idowedo.firstproject3.AtCheck.SaturdayDecorator;
import com.idowedo.firstproject3.AtCheck.SundayDecorator;
import com.idowedo.firstproject3.Login.ProgressDialog;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class Challenge_CheckActivity extends Activity {
    private FirebaseAuth mFirebaseAuth; //?????????????????? ????????????
    private DatabaseReference mDatabase;
    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    private TextView monthYearText;
    String time, kcal, menu;
    private CalendarDay date;
    Cursor cursor;
    Button chall_checkBtn;
    MaterialCalendarView calendarView;

    private String usercode, dateR, datee;
    private String strDate;
    int sumCount = 0;
    String[] dateqd;
    int i = 0;
    private int btnstate = 0;
    final String TAG = "MainActivity";
    ProgressDialog customProgressDialog;
    ImageView imageViewX;
    private long pressedTime;
    Bitmap bitmap, capturebmp;
    AlertDialog waitingDialog;
    boolean CheckSuccess;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_challenge_check);

        //?????? ?????? ????????????
        String sDY = String.valueOf(CalendarDay.today().getYear());
        String sDM = String.valueOf(CalendarDay.today().getMonth()+1);
        String sDD = String.valueOf(CalendarDay.today().getDay());

        if (sDM.length() != 2) {
            sDM = 0 + sDM;
        }
        if (sDD.length() != 2){
            sDD = 0 + sDD;
        }

        strDate = sDY + "-" + sDM + "-" + sDD;

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("challenge").child(firebaseUser.getUid());

        waitingDialog = new SpotsDialog.Builder().
                setContext(this)
                .setMessage("Please waiting...")
                .setCancelable(false).build();

        chall_checkBtn = findViewById(R.id.chall_checkbutton);

        imageViewX = (ImageView) findViewById(R.id.imageView3);
        imageViewX.setColorFilter(Color.parseColor("#132F7E"), PorterDuff.Mode.SRC_IN);


        //????????? ?????? ??????
        customProgressDialog = new ProgressDialog(this);
        //???????????? ????????????
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2021, 8, 1))   //????????? ??????
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) //????????? ???
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setPadding(0, -20, 0, 30);
        calendarView.setArrowColor(Color.rgb(19, 47, 126));

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());


        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                usercode = (group.getEmailid());

                firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("?????? ?????? ??????").collection("OX")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("today_date") != null) {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String strd = document.getString("today_date");
                                            Date dated = null;

                                            try {
                                                dated = dateFormat.parse(strd);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            CalendarDay day = CalendarDay.from(dated);
                                            calendarView.addDecorator(new Challenge_EventDecorator(Color.BLACK, Collections.singleton(day),Challenge_CheckActivity.this));
                                        }
                                    }
                                }
                                else{
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("?????? ?????? ??????").collection("OX")
                        .whereEqualTo("today_date",strDate)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("today_date") != null){
                                            btnstate = 1;
                                            chall_checkBtn.setBackground(getDrawable(R.drawable.attencheckeddrawble));
                                            chall_checkBtn.setText("????????????");
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //????????? ?????? ??????
        chall_checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnstate == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            capturebmp = (Bitmap) extras.get("data");
            CheckSuccess = false;

            imageDownload();

        }
    }

    public void imageDownload(){
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                waitingDialog.show();

                // check ????????? ????????? ????????? ??????
                if(btnstate == 0){
                    chall_checkBtn.setBackground(getDrawable(R.drawable.attencheckeddrawble));
                    chall_checkBtn.setText("????????????");
                    calendarView.addDecorator(new Challenge_EventDecorator(Color.BLACK, Collections.singleton(CalendarDay.today()),Challenge_CheckActivity.this));

                    String year = String.valueOf(CalendarDay.today().getYear());
                    String month = String.valueOf(CalendarDay.today().getMonth() + 1);
                    String day = String.valueOf(CalendarDay.today().getDay());

                    if (month.length() != 2) {
                        month = 0 + month;
                    }
                    if (day.length() != 2){
                        day = 0 + day;
                    }

                    dateR = year + "-" + month + "-" + day;

                    Map<String, Object> doc = new HashMap<>();
                    doc.put("userChallWalk_OX", "O");
                    doc.put("userCode", usercode);
                    doc.put("today_date", dateR);

                    firebaseFirestore.collection("user").document(usercode)
                            .collection("user challenge").document("?????? ?????? ??????").collection("OX").document(dateR).set(doc)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });

                    btnstate=1;
                    waitingDialog.dismiss();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void onClikcPopupClose(View v) {
        Intent intent = new Intent();
        intent.putExtra("result", true);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //??????????????? ????????? ??? ?????????
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        return;
    }
}