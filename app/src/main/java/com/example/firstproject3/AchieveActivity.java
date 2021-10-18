package com.example.firstproject3;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.firstproject3.CustomTodoAdapter.achieve_point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.firstproject3.Login.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AchieveActivity extends AppCompatActivity {
    LinearLayout slideLayout;
    View darkView;
    ImageView showImg;
    TextView showName, showExplain;
    int badgeId;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference, documentReferenceC;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String userCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.achieve_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        slideLayout = findViewById(R.id.slideLayout);
        darkView = findViewById(R.id.DarkView);
        showImg = findViewById(R.id.showImgBadge);
        showName = findViewById(R.id.showNameBadge);
        showExplain = findViewById(R.id.showExplainBadge);

        final Animation translateup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_top);
        final Animation translatedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_bottom);

        LinearLayout layoutBadge1 = findViewById(R.id.badge1);
        LinearLayout layoutBadge2 = findViewById(R.id.badge2);
        LinearLayout layoutBadge3 = findViewById(R.id.badge3);
        LinearLayout layoutBadge4 = findViewById(R.id.badge4);
        LinearLayout layoutBadge5 = findViewById(R.id.badge5);
        LinearLayout layoutBadge6 = findViewById(R.id.badge6);
        LinearLayout layoutBadge7 = findViewById(R.id.badge7);
        LinearLayout layoutBadge8 = findViewById(R.id.badge8);
        LinearLayout layoutBadge9 = findViewById(R.id.badge9);
        LinearLayout layoutBadge10 = findViewById(R.id.badge10);
        LinearLayout layoutBadge11 = findViewById(R.id.badge11);
        LinearLayout layoutBadge12 = findViewById(R.id.badge12);
        LinearLayout layoutBadge13 = findViewById(R.id.badge13);
        LinearLayout layoutBadge14 = findViewById(R.id.badge14);
        LinearLayout layoutBadge15 = findViewById(R.id.badge15);

        //팝업창 닫는 이벤트
        darkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLayout.startAnimation(translatedown);
                slideLayout.setVisibility(View.INVISIBLE);
                darkView.setVisibility(View.INVISIBLE);
            }
        });

        /*private void getChecked(todo_do, todo_done)
        {

        };*/

        //뱃지 클릭 시 팝업창에 띄움
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badgeId = v.getId();
                ImageView img;
                TextView text, showExplain;
                Drawable clickimg;
                String str;
                showExplain = findViewById(R.id.showExplainBadge);


                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserAccount group = dataSnapshot.getValue(UserAccount.class);
                        userCode = (group.getEmailid());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                switch (badgeId) {
                    case R.id.badge1:
                        img = findViewById(R.id.imagebadge1);
                        text = findViewById(R.id.textBadge1);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();
                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("datecnt").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class);
                                if (value < 30) {
                                    Toast.makeText(getApplicationContext(), "현재까지 " + value + "일 밖에 출석하지 못했습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    /*documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                            .document("achieve");
                                    documentReference.update("30일 달성", "1");*/
                                    slideLayout.setVisibility(View.VISIBLE);
                                    darkView.setVisibility(View.VISIBLE);
                                    slideLayout.startAnimation(translateup);
                                    showImg.setImageDrawable(clickimg);
                                    showName.setText(str);
                                    showExplain.setText("출석체크 30일 달성");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;

                    case R.id.badge2:
                        img = findViewById(R.id.imagebadge2);
                        text = findViewById(R.id.textbadge2);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();
                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("datecnt").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class);
                                if (value < 100) {
                                    Toast.makeText(getApplicationContext(), "현재까지 " + value + " 일 밖에 출석하지 못했습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    slideLayout.setVisibility(View.VISIBLE);
                                    darkView.setVisibility(View.VISIBLE);
                                    slideLayout.startAnimation(translateup);
                                    showImg.setImageDrawable(clickimg);
                                    showName.setText(str);
                                    showExplain.setText("출석체크 100일 달성");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;

                    case R.id.badge3:
                        img = findViewById(R.id.imagebadge3);
                        text = findViewById(R.id.textbadge3);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class);
                                if (value < 5) {
                                    Toast.makeText(getApplicationContext(), "상점에서 더 많은 아이템을 구매해보세요!", Toast.LENGTH_SHORT).show();
                                } else {
                                    slideLayout.setVisibility(View.VISIBLE);
                                    darkView.setVisibility(View.VISIBLE);
                                    slideLayout.startAnimation(translateup);
                                    showImg.setImageDrawable(clickimg);
                                    showName.setText(str);
                                    showExplain.setText("상점에서 부위별 의상 2개이상 구매하기");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        break;
                    case R.id.badge4:
                        img = findViewById(R.id.imagebadge4);
                        text = findViewById(R.id.textbadge4);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("dotodo").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class);
                                if (value < 30) {
                                    Toast.makeText(getApplicationContext(), "더 많은 계획을 달성해보세요", Toast.LENGTH_SHORT).show();
                                } else {
                                    slideLayout.setVisibility(View.VISIBLE);
                                    darkView.setVisibility(View.VISIBLE);
                                    slideLayout.startAnimation(translateup);
                                    showImg.setImageDrawable(clickimg);
                                    showName.setText(str);
                                    showExplain.setText("30가지의 업무 수행 완료!");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;

                    case R.id.badge5:
                        img = findViewById(R.id.imagebadge5);
                        text = findViewById(R.id.textbadge5);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class);
                                if (value < 29) {
                                    Toast.makeText(getApplicationContext(), "코인을 더 모아 아이템을 구매해보세요!", Toast.LENGTH_SHORT).show();
                                } else {
                                    slideLayout.setVisibility(View.VISIBLE);
                                    darkView.setVisibility(View.VISIBLE);
                                    slideLayout.startAnimation(translateup);
                                    showImg.setImageDrawable(clickimg);
                                    showName.setText(str);
                                    showExplain.setText("상점에 있는 모든 패션아이템 구매하기");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    case R.id.badge6:
                        img = findViewById(R.id.imagebadge6);
                        text = findViewById(R.id.textbadge6);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();
                        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("challengepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int value = snapshot.getValue(Integer.class);
                                if (value == 0) {
                                    Toast.makeText(getApplicationContext(), "아직 챌린지에 참여하지 않았어요!", Toast.LENGTH_SHORT).show();
                                } else {
                                    slideLayout.setVisibility(View.VISIBLE);
                                    darkView.setVisibility(View.VISIBLE);
                                    slideLayout.startAnimation(translateup);
                                    showImg.setImageDrawable(clickimg);
                                    showName.setText(str);
                                    showExplain.setText("최초의 챌린지 참가 신청하기");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    case R.id.badge7:
                        img = findViewById(R.id.imagebadge7);
                        text = findViewById(R.id.textbadge7);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");

                        break;
                    case R.id.badge8:
                        img = findViewById(R.id.imagebadge8);
                        text = findViewById(R.id.textbadge8);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge9:
                        img = findViewById(R.id.imagebadge9);
                        text = findViewById(R.id.textbadge9);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge10:
                        img = findViewById(R.id.imagebadge10);
                        text = findViewById(R.id.textbadge10);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge11:
                        img = findViewById(R.id.imagebadge11);
                        text = findViewById(R.id.textbadge11);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge12:
                        img = findViewById(R.id.imagebadge12);
                        text = findViewById(R.id.textbadge12);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge13:
                        img = findViewById(R.id.imagebadge13);
                        text = findViewById(R.id.textbadge13);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge14:
                        img = findViewById(R.id.imagebadge14);
                        text = findViewById(R.id.textbadge14);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                    case R.id.badge15:
                        img = findViewById(R.id.imagebadge15);
                        text = findViewById(R.id.textbadge15);
                        clickimg = img.getDrawable();
                        str = (String) text.getText();

                        slideLayout.setVisibility(View.VISIBLE);
                        darkView.setVisibility(View.VISIBLE);
                        slideLayout.startAnimation(translateup);
                        showImg.setImageDrawable(clickimg);
                        showName.setText(str);
                        showExplain.setText("새로운 뱃지가 곧 공개될거에요!");
                        break;
                }//switch
            }//onClick
        };//ocl

        layoutBadge1.setOnClickListener(ocl);
        layoutBadge2.setOnClickListener(ocl);
        layoutBadge3.setOnClickListener(ocl);
        layoutBadge4.setOnClickListener(ocl);
        layoutBadge5.setOnClickListener(ocl);
        layoutBadge6.setOnClickListener(ocl);
        layoutBadge7.setOnClickListener(ocl);
        layoutBadge8.setOnClickListener(ocl);
        layoutBadge9.setOnClickListener(ocl);
        layoutBadge10.setOnClickListener(ocl);
        layoutBadge11.setOnClickListener(ocl);
        layoutBadge12.setOnClickListener(ocl);
        layoutBadge13.setOnClickListener(ocl);
        layoutBadge14.setOnClickListener(ocl);
        layoutBadge15.setOnClickListener(ocl);


//        //특정 텍스트에 대한 스타일 적용
//        TextView text1 = findViewById(R.id.textBadge1);
//        TextView text2 = findViewById(R.id.textbadge2);
//        TextView text3 = findViewById(R.id.textbadge3);
//        TextView text4 = findViewById(R.id.textbadge4);
//        TextView text5 = findViewById(R.id.textbadge5);
//        TextView text6 = findViewById(R.id.textbadge6);
//
//        String content1 = text1.getText().toString();
//        SpannableString spannableString = new SpannableString(content1);
//
//        String word = "30일";
//        int start = content1.indexOf(word);
//        int end = start + word.length();
//
//        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F4385E")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        text1.setText(spannableString);
    }//onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}