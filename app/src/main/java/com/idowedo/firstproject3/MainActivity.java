package com.idowedo.firstproject3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.idowedo.firstproject3.AtCheck.Attendance_CheckActivity;
import com.idowedo.firstproject3.Login.UserAccount;
import com.idowedo.firstproject3.R;
import com.idowedo.firstproject3.bottom_fragment.Fragment_Challenge;
import com.idowedo.firstproject3.bottom_fragment.Fragment_Character;
import com.idowedo.firstproject3.bottom_fragment.Fragment_Timer;
import com.idowedo.firstproject3.bottom_fragment.Fragment_Todo;
import com.idowedo.firstproject3.daily.CalendarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView tv_get_email, tv_get_name;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth; //?????????????????? ????????????
    private DatabaseReference mDatabase;
    //fragment ??????
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft = fm.beginTransaction();
    private Fragment_Todo fragment_todo;
    private Fragment_Timer fragment_timer;
    private Fragment_Challenge fragment_challenge;
    private Fragment_Character fragment_character;
    private Intent intentM;
    private TextView textView;
    private long backpressedTime = 0;
    private String usercode;
    private AppBarConfiguration mAppBarConfiguration;
    boolean isOpen = true;
    FloatingActionButton fab, todoFab, habbitFab;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    private String str1 = null, str2 = null, str3 = null;
    private String startDate, endDate;
    private AlarmManager alarmManager;
    private Calendar calendarEnd1, calendarEnd2, calendarEnd3;
    private int count1 = 0, count2 = 0, count3 = 0, studyState = 0;;
    private String[] time30 = null;
    private DocumentReference doc1, doc2, doc3;



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_question:
                Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.Daily) {
            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.attendence) {
            Intent intent = new Intent(getApplicationContext(), Attendance_CheckActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentM = getIntent();
        String userCode = intentM.getStringExtra("userCode");

        textView = findViewById(R.id.textView5);

        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        //?????????
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeader = navigationView.getHeaderView(0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.test)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        todoFab = (FloatingActionButton) findViewById(R.id.todoFab);
        habbitFab = (FloatingActionButton) findViewById(R.id.habbitFab);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        tv_get_email = (TextView) navHeader.findViewById(R.id.tv_get_email);
        tv_get_name = (TextView) navHeader.findViewById(R.id.tv_get_name);


        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                String name = (group.getUsername());
                String email = (group.getEmailid());
                usercode = (group.getEmailid());

                tv_get_name.setText("??????: "+ name);
                tv_get_email.setText("ID: " + email);


                /////????????? ?????? 30??? ?????? ???//////
                ///////????????? ????????????
                DocumentReference docRef1 = firebaseFirestore.collection("challenge").document("????????? ????????????").collection("challenge list").document(usercode);

                        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if(document.getString("challenge_id") != null) {
                                    calendarEnd1 = Calendar.getInstance();
                                    calendarEnd1.setTimeInMillis(System.currentTimeMillis());
                                    calendarEnd1.set(Calendar.YEAR, 2021);
                                    calendarEnd1.set(Calendar.MONTH, 10);
                                    calendarEnd1.set(Calendar.DATE, 20);

                                    firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("????????? ????????????").collection("OX")
                                            .whereEqualTo("userChallStudy_OX", "O").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    count1 = count1 + 1;//???????????? ????????? ?????? ??????
                                                }//for

                                                doc1 = firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("????????? ????????????");
                                                doc1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            DocumentSnapshot document = task.getResult();
                                                            String tf = document.getString("show");

                                                            if(tf != null) {
                                                                if(tf.equals("false")) {
                                                                    Toast.makeText(getApplicationContext(), tf, Toast.LENGTH_SHORT).show();
                                                                    //????????? ?????? ????????? ?????? ?????? ?????? ??????
                                                                    if(Calendar.getInstance().after(calendarEnd1)) {
                                                                        if(count1 == 30) { //????????? ?????? ???
                                                                            doc1.update("show", "true");

                                                                            Intent intent = new Intent(MainActivity.this, ChallSuccPopActivity.class);
                                                                            intent.putExtra("challSuccTitle", "????????? ????????????");
                                                                            startActivity(intent);
                                                                        } else if(count1 == 24) {   //????????? 24??? ?????? ???
                                                                            doc1.update("show", "true");

                                                                            Intent intent = new Intent(MainActivity.this, RewardCoinActivity.class);
                                                                            intent.putExtra("challcount", "24");
                                                                            startActivity(intent);
                                                                        } else {
                                                                            doc1.update("show", "true");

                                                                            Intent intent = new Intent(MainActivity.this, RewardCoinActivity.class);
                                                                            intent.putExtra("challcount", "1");
                                                                            startActivity(intent);
                                                                        }
                                                                    }
                                                                }//if
                                                            }
                                                        }//if
                                                    }//oncomplete
                                                });

                                            }//if
                                        }//onComplete
                                    });
                                }
                             }
                        }
                    });//doRef1

                /////////?????? 6??? ????????????
                DocumentReference docRef2 = firebaseFirestore.collection("challenge").document("?????? 6??? ????????????").collection("challenge list").document(usercode);

                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if(document.getString("challenge_id") != null) {
                                calendarEnd2 = Calendar.getInstance();
                                calendarEnd2.setTimeInMillis(System.currentTimeMillis());
                                calendarEnd2.set(Calendar.YEAR, 2021);
                                calendarEnd2.set(Calendar.MONTH, 10);
                                calendarEnd2.set(Calendar.DATE, 28);

                                firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("?????? 6??? ????????????").collection("OX")
                                        .whereEqualTo("userChallWakeup_OX", "O").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                count2 = count2 + 1;//???????????? ????????? ?????? ??????
                                            }//for
                                            doc2 = firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("?????? 6??? ????????????");
                                            doc2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    DocumentSnapshot document = task.getResult();
                                                    String tf = document.getString("show");

                                                    if(tf != null) {
                                                        if(tf.equals("false")) {
                                                            //????????? ?????? ????????? ?????? ?????? ?????? ??????
                                                            if(Calendar.getInstance().after(calendarEnd2)) {
                                                                if(count2 == 30) { //????????? ?????? ???
                                                                    doc2.update("show", "true");

                                                                    Intent intent = new Intent(MainActivity.this, ChallSuccPopActivity.class);
                                                                    intent.putExtra("challSuccTitle", "?????? 6??? ????????????");
                                                                    startActivity(intent);
                                                                } else if(count2 == 24){    //????????? 24??? ?????? ???
                                                                    doc2.update("show", "true");

                                                                    Intent intent = new Intent(MainActivity.this, RewardCoinActivity.class);
                                                                    intent.putExtra("challcount", "24");
                                                                    startActivity(intent);
                                                                } else {
                                                                    doc2.update("show", "true");

                                                                    Intent intent = new Intent(MainActivity.this, RewardCoinActivity.class);
                                                                    intent.putExtra("challcount", "1");
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                            });
                                        }//if
                                    }//onComplete
                                });
                            }
                        }
                    }
                });//doRef2


                /////////?????? ?????? ??????
                DocumentReference docRef3 = firebaseFirestore.collection("challenge").document("?????? ?????? ??????").collection("challenge list").document(usercode);

                docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if(document.getString("challenge_id") != null) {
                                calendarEnd3 = Calendar.getInstance();
                                calendarEnd3.setTimeInMillis(System.currentTimeMillis());
                                calendarEnd3.set(Calendar.YEAR, 2021);
                                calendarEnd3.set(Calendar.MONTH, 10);
                                calendarEnd3.set(Calendar.DATE, 23);

                                firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("?????? ?????? ??????").collection("OX")
                                        .whereEqualTo("userChallWalk_OX", "O").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                count3 = count3 + 1;//???????????? ????????? ?????? ??????
                                            }//for
                                            doc3 = firebaseFirestore.collection("user").document(usercode).collection("user challenge").document("?????? ?????? ??????");
                                            doc3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    DocumentSnapshot document = task.getResult();
                                                    String tf = document.getString("show");

                                                    if(tf != null) {
                                                        if(tf.equals("false")) {
                                                            //????????? ?????? ????????? ?????? ?????? ?????? ??????
                                                            if(Calendar.getInstance().after(calendarEnd2)) {
                                                                if(count3 == 30) { //????????? ?????? ???
                                                                    doc3.update("show", "true");

                                                                    Intent intent = new Intent(MainActivity.this, ChallSuccPopActivity.class);
                                                                    intent.putExtra("challSuccTitle", "?????? ?????? ??????");
                                                                    startActivity(intent);
                                                                } else if(count3 == 24){    //????????? 24??? ?????? ???
                                                                    doc3.update("show", "true");

                                                                    Intent intent = new Intent(MainActivity.this, RewardCoinActivity.class);
                                                                    intent.putExtra("challcount", "24");
                                                                    startActivity(intent);
                                                                } else {
                                                                    doc3.update("show", "true");

                                                                    Intent intent = new Intent(MainActivity.this, RewardCoinActivity.class);
                                                                    intent.putExtra("challcount", "1");
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                            });
                                        }//if
                                    }//onComplete
                                });
                            }
                        }
                    }
                });//doRef3

                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Toast.makeText(getApplicationContext(),count1 + "",Toast.LENGTH_SHORT);
        todoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyMakeActivity.class);
                intent.putExtra("userCode", userCode);
                startActivity(intent);

//                fab.startAnimation(rotateBackward);
                todoFab.startAnimation(fabOpen);
                habbitFab.startAnimation(fabOpen);
                todoFab.setClickable(false);
                habbitFab.setClickable(false);
                isOpen = true;
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));

                todoFab.setVisibility(View.GONE);
                habbitFab.setVisibility(View.GONE);
                TextView textView = findViewById(R.id.textView5);
                textView.setVisibility(View.INVISIBLE);
                textView.setClickable(false);

            }
        });

        habbitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HabbitMakeActivity.class);
                intent.putExtra("userCode", userCode);
                startActivity(intent);

//                fab.startAnimation(rotateBackward);
                todoFab.startAnimation(fabOpen);
                habbitFab.startAnimation(fabOpen);
                todoFab.setClickable(false);
                habbitFab.setClickable(false);
                isOpen = true;
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));

                todoFab.setVisibility(View.GONE);
                habbitFab.setVisibility(View.GONE);
                TextView textView = findViewById(R.id.textView5);
                textView.setVisibility(View.INVISIBLE);
                textView.setClickable(false);

            }
        });


        fabOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.from_button_anim);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });




        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fragment_Todo:
                        setFrag(0);
                        toolbar.setTitle("????????? ??? ???");
                        break;
                    case R.id.fragment_Timer:
                        setFrag(1);
                        toolbar.setTitle("????????????");
                        break;
                    case R.id.fragment_Challenge:
                        setFrag(3);
                        toolbar.setTitle("?????????");
                        break;
                    case R.id.fragment_Character:
                        setFrag(4);
                        toolbar.setTitle("?????????");
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

//        setFrag(0);
        fragment_todo = new Fragment_Todo();
        fm.beginTransaction().replace(R.id.frame, fragment_todo).commit();

        getHashKey();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fab.startAnimation(rotateBackward);
                todoFab.startAnimation(fabOpen);
                habbitFab.startAnimation(fabOpen);
                todoFab.setClickable(false);
                habbitFab.setClickable(false);
                isOpen = true;
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));

                todoFab.setVisibility(View.GONE);
                habbitFab.setVisibility(View.GONE);

                textView.setVisibility(View.GONE);
                textView.setClickable(false);
            }
        });

    }//onCreate

    private void getHashKey(){ //????????? ????????????
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    private void setFrag(int n) {
        switch (n) {
            case 0:
                if (fragment_todo == null) {
                    fragment_todo = new Fragment_Todo();
                    fm.beginTransaction().add(R.id.frame, fragment_todo).commit();
                }

                if (fragment_todo != null) fm.beginTransaction().show(fragment_todo).commit();
                if (fragment_timer != null) fm.beginTransaction().hide(fragment_timer).commit();
                if (fragment_challenge != null)
                    fm.beginTransaction().hide(fragment_challenge).commit();
                if (fragment_character != null)
                    fm.beginTransaction().hide(fragment_character).commit();

                break;
            case 1:
                if (fragment_timer == null) {
                    fragment_timer = new Fragment_Timer();
                    fm.beginTransaction().add(R.id.frame, fragment_timer).commit();
                }

                if (fragment_todo != null) fm.beginTransaction().hide(fragment_todo).commit();
                if (fragment_timer != null) fm.beginTransaction().show(fragment_timer).commit();
                if (fragment_challenge != null)
                    fm.beginTransaction().hide(fragment_challenge).commit();
                if (fragment_character != null)
                    fm.beginTransaction().hide(fragment_character).commit();

                break;
            case 3:
                if (fragment_challenge == null) {
                    fragment_challenge = new Fragment_Challenge();
                    fm.beginTransaction().add(R.id.frame, fragment_challenge).commit();
                }

                if (fragment_todo != null) fm.beginTransaction().hide(fragment_todo).commit();
                if (fragment_timer != null) fm.beginTransaction().hide(fragment_timer).commit();
                if (fragment_challenge != null)
                    fm.beginTransaction().show(fragment_challenge).commit();
                if (fragment_character != null)
                    fm.beginTransaction().hide(fragment_character).commit();

                break;
            case 4:
                if (fragment_character == null) {
                    fragment_character = new Fragment_Character();
                    fm.beginTransaction().add(R.id.frame, fragment_character).commit();
                }

                if (fragment_todo != null) fm.beginTransaction().hide(fragment_todo).commit();
                if (fragment_timer != null) fm.beginTransaction().hide(fragment_timer).commit();
                if (fragment_challenge != null)
                    fm.beginTransaction().hide(fragment_challenge).commit();
                if (fragment_character != null)
                    fm.beginTransaction().show(fragment_character).commit();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //BusProvider.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                EventBus.getInstance().post(ActivityResultEvent.create(requestCode, resultCode, data));
                fragment_timer.onActivityResultEvent(ActivityResultEvent.create(requestCode, resultCode, data));
            }
        }

    }

    //fab ???????????????
    private void animateFab() {
        if (isOpen) {
//            fab.startAnimation(rotateForward);
            todoFab.startAnimation(fabClose);
            habbitFab.startAnimation(fabClose);
            todoFab.setClickable(true);
            habbitFab.setClickable(true);
            isOpen = false;
            fab.setImageDrawable(getResources().getDrawable(R.drawable.outline_multiplication_white_24dp));

            todoFab.setVisibility(View.VISIBLE);
            habbitFab.setVisibility(View.VISIBLE);
            TextView textView = findViewById(R.id.textView5);
            textView.setVisibility(View.VISIBLE);
            textView.setClickable(true);
        } else {
//            fab.startAnimation(rotateBackward);
            todoFab.startAnimation(fabOpen);
            habbitFab.startAnimation(fabOpen);
            todoFab.setClickable(false);
            habbitFab.setClickable(false);
            isOpen = true;
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));

            todoFab.setVisibility(View.GONE);
            habbitFab.setVisibility(View.GONE);

            textView.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.textView5);
            textView.setVisibility(View.INVISIBLE);
            textView.setClickable(false);

        }
    }

    //?????? ?????? ??????
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //??? 3??? ????????? ?????? ?????????
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}