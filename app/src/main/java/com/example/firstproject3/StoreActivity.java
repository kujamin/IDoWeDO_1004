package com.example.firstproject3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.firstproject3.Login.LoginActivity;
import com.example.firstproject3.Login.UserAccount;
import com.example.firstproject3.daily.CalListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class StoreActivity extends AppCompatActivity {
    View darkView;
    LinearLayout popupStore;
    LinearLayout soldoutView1, soldoutView2, soldoutView3, soldoutView4, soldoutView5,
            soldoutView6, soldoutView7, soldoutView8, soldoutView9, soldoutView10,
            soldoutView11, soldoutView12, soldoutView13, soldoutView14, soldoutView15,
            soldoutView16, soldoutView17, soldoutView18, soldoutView19, soldoutView20,
            soldoutView21, soldoutView22, soldoutView23, soldoutView24, soldoutView25,
            soldoutView26, soldoutView27, soldoutView28, soldoutView29;
    int itemId;
    private FirebaseFirestore firebaseFirestore;
    private int itemCoin, myCoin,
            strCoin1, strCoin2, strCoin3, strCoin4, strCoin5, strCoin6,
            strCoin7, strCoin8, strCoin9, strCoin10, strCoin11, strCoin12,
            strCoin13, strCoin14, strCoin15, strCoin16, strCoin17, strCoin18,
            strCoin19, strCoin20, strCoin21, strCoin22, strCoin23, strCoin24,
            strCoin25, strCoin26, strCoin27, strCoin28, strCoin29, coin;
    private DocumentReference documentReference, documentReferenceC;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;
    private String userCode;

    TextView tv_popup, tv_currentMycoin, tv_Item1, tv_Item2, tv_Item3, tv_Item4, tv_Item5, tv_Item6, tv_Item7, tv_Item8, tv_Item9, tv_Item10, tv_Item11, tv_Item12, tv_Item13, tv_Item14, tv_Item15, tv_Item16, tv_Item17, tv_Item18, tv_Item19, tv_Item20, tv_Item21, tv_Item22, tv_Item23, tv_Item24, tv_Item25, tv_Item26, tv_Item27, tv_Item28, tv_Item29;
    String tvItem1, tvItem2, tvItem3, tvItem4, tvItem5, tvItem6, tvItem7, tvItem8, tvItem9, tvItem10, tvItem11, tvItem12, tvItem13, tvItem14, tvItem15, tvItem16, tvItem17, tvItem18, tvItem19, tvItem20, tvItem21, tvItem22, tvItem23, tvItem24, tvItem25, tvItem26, tvItem27, tvItem28, tvItem29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.stre_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //String usercode = ((LoginActivity)LoginActivity.context_login).strEmail;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        darkView = findViewById(R.id.DarkView);
        popupStore = findViewById(R.id.popupStore);
        TextView textYes = findViewById(R.id.textYes);
        TextView textNo = findViewById(R.id.textNo);
        tv_currentMycoin = findViewById(R.id.currentMycoin);
        soldoutView1 = findViewById(R.id.SoldoutView1);
        soldoutView2 = findViewById(R.id.SoldoutView2);
        soldoutView3 = findViewById(R.id.SoldoutView3);
        soldoutView4 = findViewById(R.id.SoldoutView4);
        soldoutView5 = findViewById(R.id.SoldoutView5);
        soldoutView6 = findViewById(R.id.SoldoutView6);
        soldoutView7 = findViewById(R.id.SoldoutView7);
        soldoutView8 = findViewById(R.id.SoldoutView8);
        soldoutView9 = findViewById(R.id.SoldoutView9);
        soldoutView10 = findViewById(R.id.SoldoutView10);
        soldoutView11 = findViewById(R.id.SoldoutView11);
        soldoutView12 = findViewById(R.id.SoldoutView12);
        soldoutView13 = findViewById(R.id.SoldoutView13);
        soldoutView14 = findViewById(R.id.SoldoutView14);
        soldoutView15 = findViewById(R.id.SoldoutView15);
        soldoutView16 = findViewById(R.id.SoldoutView16);
        soldoutView17 = findViewById(R.id.SoldoutView17);
        soldoutView18 = findViewById(R.id.SoldoutView18);
        soldoutView19 = findViewById(R.id.SoldoutView19);
        soldoutView20 = findViewById(R.id.SoldoutView20);
        soldoutView21 = findViewById(R.id.SoldoutView21);
        soldoutView22 = findViewById(R.id.SoldoutView22);
        soldoutView23 = findViewById(R.id.SoldoutView23);
        soldoutView24 = findViewById(R.id.SoldoutView24);
        soldoutView25 = findViewById(R.id.SoldoutView25);
        soldoutView26 = findViewById(R.id.SoldoutView26);
        soldoutView27 = findViewById(R.id.SoldoutView27);
        soldoutView28 = findViewById(R.id.SoldoutView28);
        soldoutView29 = findViewById(R.id.SoldoutView29);

        LinearLayout itemLayout1 = findViewById(R.id.item1);
        LinearLayout itemLayout2 = findViewById(R.id.item2);
        LinearLayout itemLayout3 = findViewById(R.id.item3);
        LinearLayout itemLayout4 = findViewById(R.id.item4);
        LinearLayout itemLayout5 = findViewById(R.id.item5);
        LinearLayout itemLayout6 = findViewById(R.id.item6);
        LinearLayout itemLayout7 = findViewById(R.id.item7);
        LinearLayout itemLayout8 = findViewById(R.id.item8);
        LinearLayout itemLayout9 = findViewById(R.id.item9);
        LinearLayout itemLayout10 = findViewById(R.id.item10);
        LinearLayout itemLayout11 = findViewById(R.id.item11);
        LinearLayout itemLayout12 = findViewById(R.id.item12);
        LinearLayout itemLayout13 = findViewById(R.id.item13);
        LinearLayout itemLayout14 = findViewById(R.id.item14);
        LinearLayout itemLayout15 = findViewById(R.id.item15);
        LinearLayout itemLayout16 = findViewById(R.id.item16);
        LinearLayout itemLayout17 = findViewById(R.id.item17);
        LinearLayout itemLayout18 = findViewById(R.id.item18);
        LinearLayout itemLayout19 = findViewById(R.id.item19);
        LinearLayout itemLayout20 = findViewById(R.id.item20);
        LinearLayout itemLayout21 = findViewById(R.id.item21);
        LinearLayout itemLayout22 = findViewById(R.id.item22);
        LinearLayout itemLayout23 = findViewById(R.id.item23);
        LinearLayout itemLayout24 = findViewById(R.id.item24);
        LinearLayout itemLayout25 = findViewById(R.id.item25);
        LinearLayout itemLayout26 = findViewById(R.id.item26);
        LinearLayout itemLayout27 = findViewById(R.id.item27);
        LinearLayout itemLayout28 = findViewById(R.id.item28);
        LinearLayout itemLayout29 = findViewById(R.id.item29);

        tv_popup = findViewById(R.id.textview_popup);
        tv_Item1 = findViewById(R.id.tv_item1);
        tv_Item2 = findViewById(R.id.tv_item2);
        tv_Item3 = findViewById(R.id.tv_item3);
        tv_Item4 = findViewById(R.id.tv_item4);
        tv_Item5 = findViewById(R.id.tv_item5);
        tv_Item6 = findViewById(R.id.tv_item6);
        tv_Item7 = findViewById(R.id.tv_item7);
        tv_Item8 = findViewById(R.id.tv_item8);
        tv_Item9 = findViewById(R.id.tv_item9);
        tv_Item10 = findViewById(R.id.tv_item10);
        tv_Item11 = findViewById(R.id.tv_item11);
        tv_Item12 = findViewById(R.id.tv_item12);
        tv_Item13 = findViewById(R.id.tv_item13);
        tv_Item14 = findViewById(R.id.tv_item14);
        tv_Item15 = findViewById(R.id.tv_item15);
        tv_Item16 = findViewById(R.id.tv_item16);
        tv_Item17 = findViewById(R.id.tv_item17);
        tv_Item18 = findViewById(R.id.tv_item18);
        tv_Item19 = findViewById(R.id.tv_item19);
        tv_Item20 = findViewById(R.id.tv_item20);
        tv_Item21 = findViewById(R.id.tv_item21);
        tv_Item22 = findViewById(R.id.tv_item22);
        tv_Item23 = findViewById(R.id.tv_item23);
        tv_Item24 = findViewById(R.id.tv_item24);
        tv_Item25 = findViewById(R.id.tv_item25);
        tv_Item26 = findViewById(R.id.tv_item26);
        tv_Item27 = findViewById(R.id.tv_item27);
        tv_Item28 = findViewById(R.id.tv_item28);
        tv_Item29 = findViewById(R.id.tv_item29);


        mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount group = dataSnapshot.getValue(UserAccount.class);
                userCode = (group.getEmailid());

                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                        .document("state");

                documentReferenceC.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();

                                    myCoin = Integer.parseInt(document.getString("coin"));


                                    tv_currentMycoin.setText(myCoin + "");

                                }
                            }
                        });
                    }
                });


                firebaseFirestore.collection("user").document(userCode).collection("user character")
                        .document("state").collection("store")
                        .whereEqualTo("buy", "O")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String str = (String) document.getData().get("name");
                                switch (str) {
                                    //1 basic
                                    case "basic_torso_01":
                                        soldoutView1.setVisibility(View.VISIBLE);
                                        itemLayout1.setEnabled(false);
                                        break;
                                    case "basic_leg_01":
                                        soldoutView2.setVisibility(View.VISIBLE);
                                        itemLayout2.setEnabled(false);
                                        break;
                                    //2 business
                                    case "business_head_01":
                                        soldoutView3.setVisibility(View.VISIBLE);
                                        itemLayout3.setEnabled(false);
                                        break;
                                    case "business_torso_01":
                                        soldoutView4.setVisibility(View.VISIBLE);
                                        itemLayout4.setEnabled(false);
                                        break;
                                    case "business_leg_01":
                                        soldoutView5.setVisibility(View.VISIBLE);
                                        itemLayout5.setEnabled(false);
                                        break;
                                    //3 formal
                                    case "formal_head_01":
                                        soldoutView6.setVisibility(View.VISIBLE);
                                        itemLayout6.setEnabled(false);
                                        break;
                                    case "formal_torso_01":
                                        soldoutView7.setVisibility(View.VISIBLE);
                                        itemLayout7.setEnabled(false);
                                        break;
                                    case "formal_leg_01":
                                        soldoutView8.setVisibility(View.VISIBLE);
                                        itemLayout8.setEnabled(false);
                                        break;
                                    //4 hood
                                    case "hood_torso_01":
                                        soldoutView9.setVisibility(View.VISIBLE);
                                        itemLayout9.setEnabled(false);
                                        break;
                                    case "hood_leg_01":
                                        soldoutView10.setVisibility(View.VISIBLE);
                                        itemLayout10.setEnabled(false);
                                        break;
                                    //5 knight
                                    case "knight_head_01":
                                        soldoutView11.setVisibility(View.VISIBLE);
                                        itemLayout11.setEnabled(false);
                                        break;
                                    case "knight_torso_01":
                                        soldoutView12.setVisibility(View.VISIBLE);
                                        itemLayout12.setEnabled(false);
                                        break;
                                    case "knight_leg_01":
                                        soldoutView13.setVisibility(View.VISIBLE);
                                        itemLayout13.setEnabled(false);
                                        break;
                                    //6 magician
                                    case "magician_head_01":
                                        soldoutView14.setVisibility(View.VISIBLE);
                                        itemLayout14.setEnabled(false);
                                        break;
                                    case "magician_torso_01":
                                        soldoutView15.setVisibility(View.VISIBLE);
                                        itemLayout15.setEnabled(false);
                                        break;
                                    case "magician_leg_01":
                                        soldoutView16.setVisibility(View.VISIBLE);
                                        itemLayout16.setEnabled(false);
                                        break;
                                    //7 rogue
                                    case "rogue_head_01":
                                        soldoutView17.setVisibility(View.VISIBLE);
                                        itemLayout17.setEnabled(false);
                                        break;
                                    case "rogue_torso_01":
                                        soldoutView18.setVisibility(View.VISIBLE);
                                        itemLayout18.setEnabled(false);
                                        break;
                                    case "rogue_leg_01":
                                        soldoutView19.setVisibility(View.VISIBLE);
                                        itemLayout19.setEnabled(false);
                                        break;
                                    //7 alien1
                                    case "alien_head_01":
                                        soldoutView20.setVisibility(View.VISIBLE);
                                        itemLayout20.setEnabled(false);
                                        break;
                                    case "alien_torso_01":
                                        soldoutView21.setVisibility(View.VISIBLE);
                                        itemLayout21.setEnabled(false);
                                        break;
                                    case "alien_leg_01":
                                        soldoutView22.setVisibility(View.VISIBLE);
                                        itemLayout22.setEnabled(false);
                                        break;
                                    //8 alien2
                                    case "alien_head_02":
                                        soldoutView23.setVisibility(View.VISIBLE);
                                        itemLayout23.setEnabled(false);
                                        break;
                                    case "alien_torso_02":
                                        soldoutView24.setVisibility(View.VISIBLE);
                                        itemLayout24.setEnabled(false);
                                        break;
                                    case "alien_leg_02":
                                        soldoutView25.setVisibility(View.VISIBLE);
                                        itemLayout25.setEnabled(false);
                                        break;
                                    //9 hand 4개
                                    case "book_hand_01":
                                        soldoutView26.setVisibility(View.VISIBLE);
                                        itemLayout26.setEnabled(false);
                                        break;
                                    case "claw_hand_01":
                                        soldoutView27.setVisibility(View.VISIBLE);
                                        itemLayout27.setEnabled(false);
                                        break;
                                    case "knife_hand_01":
                                        soldoutView28.setVisibility(View.VISIBLE);
                                        itemLayout28.setEnabled(false);
                                        break;
                                    case "staff_hand_01":
                                        soldoutView29.setVisibility(View.VISIBLE);
                                        itemLayout29.setEnabled(false);
                                        break;

                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //팝업창 띄우는 이벤트 리스너
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemId = v.getId();
                darkView.setVisibility(View.VISIBLE);
                popupStore.setVisibility(View.VISIBLE);
                tv_currentMycoin.setText(myCoin + "");
                switch (itemId) {
                    case R.id.item1:
                        tvItem1 = tv_Item1.getText().toString();
                        tv_popup.setText("'" + tvItem1 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item2:
                        tvItem2 = tv_Item2.getText().toString();
                        tv_popup.setText("'" + tvItem2 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item3:
                        tvItem3 = tv_Item3.getText().toString();
                        tv_popup.setText("'" + tvItem3 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item4:
                        tvItem4 = tv_Item4.getText().toString();
                        tv_popup.setText("'" + tvItem4 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item5:
                        tvItem5 = tv_Item5.getText().toString();
                        tv_popup.setText("'" + tvItem5 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item6:
                        tvItem6 = tv_Item6.getText().toString();
                        tv_popup.setText("'" + tvItem6 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item7:
                        tvItem7 = tv_Item7.getText().toString();
                        tv_popup.setText("'" + tvItem7 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item8:
                        tvItem8 = tv_Item8.getText().toString();
                        tv_popup.setText("'" + tvItem8 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item9:
                        tvItem9 = tv_Item9.getText().toString();
                        tv_popup.setText("'" + tvItem9 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item10:
                        tvItem10 = tv_Item10.getText().toString();
                        tv_popup.setText("'" + tvItem10 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item11:
                        tvItem11 = tv_Item11.getText().toString();
                        tv_popup.setText("'" + tvItem11 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item12:
                        tvItem12 = tv_Item12.getText().toString();
                        tv_popup.setText("'" + tvItem12 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item13:
                        tvItem13 = tv_Item13.getText().toString();
                        tv_popup.setText("'" + tvItem13 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item14:
                        tvItem14 = tv_Item14.getText().toString();
                        tv_popup.setText("'" + tvItem14 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item15:
                        tvItem15 = tv_Item15.getText().toString();
                        tv_popup.setText("'" + tvItem15 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item16:
                        tvItem16 = tv_Item16.getText().toString();
                        tv_popup.setText("'" + tvItem16 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item17:
                        tvItem17 = tv_Item17.getText().toString();
                        tv_popup.setText("'" + tvItem17 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item18:
                        tvItem18 = tv_Item18.getText().toString();
                        tv_popup.setText("'" + tvItem18 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item19:
                        tvItem19 = tv_Item19.getText().toString();
                        tv_popup.setText("'" + tvItem19 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item20:
                        tvItem20 = tv_Item20.getText().toString();
                        tv_popup.setText("'" + tvItem20 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item21:
                        tvItem21 = tv_Item21.getText().toString();
                        tv_popup.setText("'" + tvItem21 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item22:
                        tvItem22 = tv_Item22.getText().toString();
                        tv_popup.setText("'" + tvItem22 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item23:
                        tvItem23 = tv_Item23.getText().toString();
                        tv_popup.setText("'" + tvItem23 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item24:
                        tvItem24 = tv_Item24.getText().toString();
                        tv_popup.setText("'" + tvItem24 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item25:
                        tvItem25 = tv_Item25.getText().toString();
                        tv_popup.setText("'" + tvItem25 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item26:
                        tvItem26 = tv_Item26.getText().toString();
                        tv_popup.setText("'" + tvItem26 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item27:
                        tvItem27 = tv_Item27.getText().toString();
                        tv_popup.setText("'" + tvItem27 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item28:
                        tvItem28 = tv_Item28.getText().toString();
                        tv_popup.setText("'" + tvItem28 + "'" + " 을 구매하시겠습니까??");

                        break;
                    case R.id.item29:
                        tvItem29 = tv_Item29.getText().toString();
                        tv_popup.setText("'" + tvItem29 + "'" + " 을 구매하시겠습니까??");

                        break;

                }

            }
        };

        itemLayout1.setOnClickListener(ocl);
        itemLayout2.setOnClickListener(ocl);
        itemLayout3.setOnClickListener(ocl);
        itemLayout4.setOnClickListener(ocl);
        itemLayout5.setOnClickListener(ocl);
        itemLayout6.setOnClickListener(ocl);
        itemLayout7.setOnClickListener(ocl);
        itemLayout8.setOnClickListener(ocl);
        itemLayout9.setOnClickListener(ocl);
        itemLayout10.setOnClickListener(ocl);
        itemLayout11.setOnClickListener(ocl);
        itemLayout12.setOnClickListener(ocl);
        itemLayout13.setOnClickListener(ocl);
        itemLayout14.setOnClickListener(ocl);
        itemLayout15.setOnClickListener(ocl);
        itemLayout16.setOnClickListener(ocl);
        itemLayout17.setOnClickListener(ocl);
        itemLayout18.setOnClickListener(ocl);
        itemLayout19.setOnClickListener(ocl);
        itemLayout20.setOnClickListener(ocl);
        itemLayout21.setOnClickListener(ocl);
        itemLayout22.setOnClickListener(ocl);
        itemLayout23.setOnClickListener(ocl);
        itemLayout24.setOnClickListener(ocl);
        itemLayout25.setOnClickListener(ocl);
        itemLayout26.setOnClickListener(ocl);
        itemLayout27.setOnClickListener(ocl);
        itemLayout28.setOnClickListener(ocl);
        itemLayout29.setOnClickListener(ocl);

        TextView textCoin1 = findViewById(R.id.textCoin1);
        TextView textCoin2 = findViewById(R.id.textCoin2);
        TextView textCoin3 = findViewById(R.id.textCoin3);
        TextView textCoin4 = findViewById(R.id.textCoin4);
        TextView textCoin5 = findViewById(R.id.textCoin5);
        TextView textCoin6 = findViewById(R.id.textCoin6);
        TextView textCoin7 = findViewById(R.id.textCoin7);
        TextView textCoin8 = findViewById(R.id.textCoin8);
        TextView textCoin9 = findViewById(R.id.textCoin9);
        TextView textCoin10 = findViewById(R.id.textCoin10);
        TextView textCoin11 = findViewById(R.id.textCoin11);
        TextView textCoin12 = findViewById(R.id.textCoin12);
        TextView textCoin13 = findViewById(R.id.textCoin13);
        TextView textCoin14 = findViewById(R.id.textCoin14);
        TextView textCoin15 = findViewById(R.id.textCoin15);
        TextView textCoin16 = findViewById(R.id.textCoin16);
        TextView textCoin17 = findViewById(R.id.textCoin17);
        TextView textCoin18 = findViewById(R.id.textCoin18);
        TextView textCoin19 = findViewById(R.id.textCoin19);
        TextView textCoin20 = findViewById(R.id.textCoin20);
        TextView textCoin21 = findViewById(R.id.textCoin21);
        TextView textCoin22 = findViewById(R.id.textCoin22);
        TextView textCoin23 = findViewById(R.id.textCoin23);
        TextView textCoin24 = findViewById(R.id.textCoin24);
        TextView textCoin25 = findViewById(R.id.textCoin25);
        TextView textCoin26 = findViewById(R.id.textCoin26);
        TextView textCoin27 = findViewById(R.id.textCoin27);
        TextView textCoin28 = findViewById(R.id.textCoin28);
        TextView textCoin29 = findViewById(R.id.textCoin29);

        strCoin1 = Integer.parseInt(textCoin1.getText().toString());
        strCoin2 = Integer.parseInt(textCoin2.getText().toString());
        strCoin3 = Integer.parseInt(textCoin3.getText().toString());
        strCoin4 = Integer.parseInt(textCoin4.getText().toString());
        strCoin5 = Integer.parseInt(textCoin5.getText().toString());
        strCoin6 = Integer.parseInt(textCoin6.getText().toString());
        strCoin7 = Integer.parseInt(textCoin7.getText().toString());
        strCoin8 = Integer.parseInt(textCoin8.getText().toString());
        strCoin9 = Integer.parseInt(textCoin9.getText().toString());
        strCoin10 = Integer.parseInt(textCoin10.getText().toString());
        strCoin11 = Integer.parseInt(textCoin11.getText().toString());
        strCoin12 = Integer.parseInt(textCoin12.getText().toString());
        strCoin13 = Integer.parseInt(textCoin13.getText().toString());
        strCoin14 = Integer.parseInt(textCoin14.getText().toString());
        strCoin15 = Integer.parseInt(textCoin15.getText().toString());
        strCoin16 = Integer.parseInt(textCoin16.getText().toString());
        strCoin17 = Integer.parseInt(textCoin17.getText().toString());
        strCoin18 = Integer.parseInt(textCoin18.getText().toString());
        strCoin19 = Integer.parseInt(textCoin19.getText().toString());
        strCoin20 = Integer.parseInt(textCoin20.getText().toString());
        strCoin21 = Integer.parseInt(textCoin21.getText().toString());
        strCoin22 = Integer.parseInt(textCoin22.getText().toString());
        strCoin23 = Integer.parseInt(textCoin23.getText().toString());
        strCoin24 = Integer.parseInt(textCoin24.getText().toString());
        strCoin25 = Integer.parseInt(textCoin25.getText().toString());
        strCoin26 = Integer.parseInt(textCoin26.getText().toString());
        strCoin27 = Integer.parseInt(textCoin27.getText().toString());
        strCoin28 = Integer.parseInt(textCoin28.getText().toString());
        strCoin29 = Integer.parseInt(textCoin29.getText().toString());


        //팝업창에서 '예' 클릭 시 화면 닫힘 & SOLD OUT
        textYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkView.setVisibility(View.INVISIBLE);
                popupStore.setVisibility(View.INVISIBLE);


                mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                .document("state");

                        documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    myCoin = Integer.parseInt(doc.getString("coin"));
                                    switch (itemId) {
                                        //c1
                                        case R.id.item1:
                                            if (myCoin >= 100) {
                                                soldoutView1.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c1_torse");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });

                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin1));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item2:
                                            if (myCoin >= 100) {
                                                soldoutView2.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c1_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin2));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c2
                                        case R.id.item3:
                                            if (myCoin >= 100) {
                                                soldoutView3.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c2_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin3));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item4:
                                            if (myCoin >= 150) {
                                                soldoutView4.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c2_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin4));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item5:
                                            if (myCoin >= 150) {
                                                soldoutView5.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c2_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin5));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c3
                                        case R.id.item6:
                                            if (myCoin >= 200) {
                                                soldoutView6.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c3_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin6));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item7:
                                            if (myCoin >= 200) {
                                                soldoutView7.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c3_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin7));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item8:
                                            if (myCoin >= 200) {
                                                soldoutView8.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c3_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin8));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c4
                                        case R.id.item9:
                                            if (myCoin >= 250) {
                                                soldoutView9.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c4_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin9));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item10:
                                            if (myCoin >= 250) {
                                                soldoutView10.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c4_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin10));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c5
                                        case R.id.item11:
                                            if (myCoin >= 300) {
                                                soldoutView11.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c5_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin11));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item12:
                                            if (myCoin >= 450) {
                                                soldoutView12.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c5_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin12));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item13:
                                            if (myCoin >= 400) {
                                                soldoutView13.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c5_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin13));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c6
                                        case R.id.item14:
                                            if (myCoin >= 300) {
                                                soldoutView14.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c6_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin14));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item15:
                                            if (myCoin >= 550) {
                                                soldoutView15.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c6_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin15));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item16:
                                            if (myCoin >= 400) {
                                                soldoutView16.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c6_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin16));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c7
                                        case R.id.item17:
                                            if (myCoin >= 400) {
                                                soldoutView17.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c7_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin17));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item18:
                                            if (myCoin >= 450) {
                                                soldoutView18.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c7_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin18));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item19:
                                            if (myCoin >= 350) {
                                                soldoutView19.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c7_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin19));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        //c8
                                        case R.id.item20:
                                            if (myCoin >= 400) {
                                                soldoutView20.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c8_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin20));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.item21:
                                            if (myCoin >= 450) {
                                                soldoutView21.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c8_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin21));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        case R.id.item22:
                                            if (myCoin >= 400) {
                                                soldoutView22.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c8_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin22));
                                                tv_currentMycoin.setText(myCoin + "");
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        //c9
                                        case R.id.item23:
                                            if (myCoin >= 400) {
                                                soldoutView23.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c9_head");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin23));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                                tv_currentMycoin.setText(myCoin + "");
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        case R.id.item24:
                                            if (myCoin >= 450) {
                                                soldoutView24.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c9_torso");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin24));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                                tv_currentMycoin.setText(myCoin + "");
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        case R.id.item25:
                                            if (myCoin >= 400) {
                                                soldoutView25.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c9_leg");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin25));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        //c10~c13
                                        case R.id.item26:
                                            if (myCoin >= 500) {
                                                soldoutView26.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c10_hand");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin26));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        case R.id.item27:
                                            if (myCoin >= 500) {
                                                soldoutView27.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c11_hand");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin27));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        case R.id.item28:
                                            if (myCoin >= 500) {
                                                soldoutView28.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c12_hand");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin28));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                        case R.id.item29:
                                            if (myCoin >= 500) {
                                                soldoutView29.setVisibility(View.VISIBLE);

                                                firebaseFirestore = FirebaseFirestore.getInstance();
                                                documentReference = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state").collection("store").document("c13_hand");

                                                documentReferenceC = firebaseFirestore.collection("user").document(userCode).collection("user character")
                                                        .document("state");

                                                documentReferenceC.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            myCoin = Integer.parseInt(doc.getString("coin"));

                                                            mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.getValue(Integer.class);
                                                                    value += 1;
                                                                    mDatabase.child("idowedo").child("UserAccount").child(firebaseUser.getUid()).child("storepoint").setValue(value);
                                                                    if (value == 5 || value == 29) {
                                                                        startActivity(new Intent(StoreActivity.this, AchievementPopupActivity.class));
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                                documentReference.update("buy", "O");
                                                documentReferenceC.update("coin", String.valueOf(myCoin - strCoin29));
                                                Toast.makeText(StoreActivity.this, "구매 완료!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(StoreActivity.this, "코인이 부족합니다!!", Toast.LENGTH_SHORT).show();
                                            }
                                            tv_currentMycoin.setText(myCoin + "");
                                            break;
                                    }


                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //팝업창에서 '아니오' 클릭 시 화면 닫힘
        textNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkView.setVisibility(View.INVISIBLE);
                popupStore.setVisibility(View.INVISIBLE);
                tv_currentMycoin.setText(myCoin + "");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return onOptionsItemSelected(item);
    }
}