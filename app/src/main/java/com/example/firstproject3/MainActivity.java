package com.example.firstproject3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstproject3.AtCheck.Attendance_CheckActivity;
import com.example.firstproject3.bottom_fragment.Fragment_Challenge;
import com.example.firstproject3.bottom_fragment.Fragment_Character;
import com.example.firstproject3.bottom_fragment.Fragment_Timer;
import com.example.firstproject3.bottom_fragment.Fragment_Todo;
import com.example.firstproject3.daily.CalendarActivity;
import com.example.firstproject3.setting.SettingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.otto.Subscribe;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //private MenuItem fragment_todo, fragment_timer;
    //private Menu fragment_todo, fragment_timer;

    private DrawerLayout drawerLayout;
    private FloatingActionButton fabTodo, fabHabbit;
    Intent intentD;
    //fragment 선언
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft = fm.beginTransaction();
    private Fragment_Todo fragment_todo;
    private Fragment_Timer fragment_timer;
    private Fragment_Challenge fragment_challenge;
    private Fragment_Character fragment_character;
    private Intent intentM;

    private AppBarConfiguration mAppBarConfiguration;
    Company company;
    ArrayList<Person> persons;
    private ArrayList<HashMap<String, String>> arraydata;
    private int mISelectedItem = -1;
    Button buttonReser;
    boolean isOpen = true;
    TextView textName;
    FloatingActionButton fab, todoFab, habbitFab;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.Daily){
            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        }

        else if(id == R.id.attendence){
            Intent intent = new Intent(getApplicationContext(), Attendance_CheckActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_setting:
                Toast.makeText(getApplicationContext(),"감사",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentM = getIntent();
        String userCode = intentM.getStringExtra("userCode");


//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.activity_daily_make, null);
//
//        textName = view.findViewById(R.id.editTextTextPersonName);
//        buttonReser = view.findViewById(R.id.buttonReser);



        //서랍장
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.test)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        todoFab = (FloatingActionButton)findViewById(R.id.todoFab);
        habbitFab = (FloatingActionButton)findViewById(R.id.habbitFab);

        todoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyMakeActivity.class);
                intent.putExtra("userCode", userCode);
                startActivity(intent);

                fab.startAnimation(rotateBackward);
                todoFab.startAnimation(fabOpen);
                habbitFab.startAnimation(fabOpen);
                todoFab.setClickable(false);
                habbitFab.setClickable(false);
                isOpen = true;

                TextView textView = findViewById(R.id.textView5);
                textView.setVisibility(View.INVISIBLE);
                textView.setClickable(false);

            }
        });

        habbitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HabbitMakeActivity.class);
                intent.putExtra("userCode", userCode);
                startActivity(intent);

                fab.startAnimation(rotateBackward);
                todoFab.startAnimation(fabOpen);
                habbitFab.startAnimation(fabOpen);
                todoFab.setClickable(false);
                habbitFab.setClickable(false);
                isOpen = true;

                TextView textView = findViewById(R.id.textView5);
                textView.setVisibility(View.INVISIBLE);
                textView.setClickable(false);

            }
        });


        fabOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);

        rotateForward = AnimationUtils.loadAnimation(this,R.anim.from_button_anim);
        rotateBackward = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });



        /*AppBarConfiguration appBarConfiguration2 = new AppBarConfiguration.Builder(R.id.fragment_Todo, R.id.fragment_Timer,
                R.id.fragment_Challenge, R.id.fragment_Character).build();

        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController2, appBarConfiguration2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController2);*/

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.fragment_Todo:
                        setFrag(0);
                        toolbar.setTitle("오늘의 할 일");
                        break;
                    case R.id.fragment_Timer:
                        setFrag(1);
                        toolbar.setTitle("타이머");
                        break;
                    case R.id.fragment_Challenge:
                        setFrag(3);
                        toolbar.setTitle("챌린지");
                        break;
                    case R.id.fragment_Character:
                        setFrag(4);
                        toolbar.setTitle("캐릭터");
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
//        intentD = getIntent();
//        if(intentD != null) {
//            getData();
//        }


    }//onCreate




    private void setFrag(int n){
        switch (n){
            case 0:
                if(fragment_todo == null) {
                    fragment_todo = new Fragment_Todo();
                    fm.beginTransaction().add(R.id.frame, fragment_todo).commit();
                }

                if(fragment_todo != null) fm.beginTransaction().show(fragment_todo).commit();
                if(fragment_timer != null) fm.beginTransaction().hide(fragment_timer).commit();
                if(fragment_challenge != null) fm.beginTransaction().hide(fragment_challenge).commit();
                if(fragment_character != null) fm.beginTransaction().hide(fragment_character).commit();

                break;
            case 1:
                if(fragment_timer == null) {
                    fragment_timer = new Fragment_Timer();
                    fm.beginTransaction().add(R.id.frame, fragment_timer).commit();
                }

                if(fragment_todo != null) fm.beginTransaction().hide(fragment_todo).commit();
                if(fragment_timer != null) fm.beginTransaction().show(fragment_timer).commit();
                if(fragment_challenge != null) fm.beginTransaction().hide(fragment_challenge).commit();
                if(fragment_character != null) fm.beginTransaction().hide(fragment_character).commit();

                break;
            case 3:
                if(fragment_challenge == null) {
                    fragment_challenge = new Fragment_Challenge();
                    fm.beginTransaction().add(R.id.frame, fragment_challenge).commit();
                }

                if(fragment_todo != null) fm.beginTransaction().hide(fragment_todo).commit();
                if(fragment_timer != null) fm.beginTransaction().hide(fragment_timer).commit();
                if(fragment_challenge != null) fm.beginTransaction().show(fragment_challenge).commit();
                if(fragment_character != null) fm.beginTransaction().hide(fragment_character).commit();

                break;
            case 4:
                if(fragment_character == null) {
                    fragment_character = new Fragment_Character();
                    fm.beginTransaction().add(R.id.frame, fragment_character).commit();
                }

                if(fragment_todo != null) fm.beginTransaction().hide(fragment_todo).commit();
                if(fragment_timer != null) fm.beginTransaction().hide(fragment_timer).commit();
                if(fragment_challenge != null) fm.beginTransaction().hide(fragment_challenge).commit();
                if(fragment_character != null) fm.beginTransaction().show(fragment_character).commit();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //BusProvider.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));

        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                EventBus.getInstance().post(ActivityResultEvent.create(requestCode, resultCode, data));
                fragment_timer.onActivityResultEvent(ActivityResultEvent.create(requestCode, resultCode, data));
            }
        }

    }

    //fab 애니메이션
    private void animateFab(){
        if(isOpen){
            fab.startAnimation(rotateForward);
            todoFab.startAnimation(fabClose);
            habbitFab.startAnimation(fabClose);
            todoFab.setClickable(true);
            habbitFab.setClickable(true);
            isOpen = false;

            TextView textView = findViewById(R.id.textView5);
            textView.setVisibility(View.VISIBLE);
            textView.setClickable(true);
        }
        else {
            fab.startAnimation(rotateBackward);
            todoFab.startAnimation(fabOpen);
            habbitFab.startAnimation(fabOpen);
            todoFab.setClickable(false);
            habbitFab.setClickable(false);
            isOpen = true;

            TextView textView = findViewById(R.id.textView5);
            textView.setVisibility(View.INVISIBLE);
            textView.setClickable(false);

        }
    }

    //옵션 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //줄 3개 삼지창 쨋든 서랍장
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}