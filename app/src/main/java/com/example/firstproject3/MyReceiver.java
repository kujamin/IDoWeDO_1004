package com.example.firstproject3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "MainActivity", usercode, title, str1 = null, str2 = null, str3 = null;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리
    private DatabaseReference mDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {
        str1 = intent.getStringExtra("chall1");
        str2 = intent.getStringExtra("chall2");
        str3 = intent.getStringExtra("chall3");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(str1 != null) {
            builder = null;

            Intent i1 = new Intent(context, ConfirmActivity.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, i1, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                        new NotificationChannel("CATODO", "CATODO", NotificationManager.IMPORTANCE_DEFAULT)
                );
                builder = new NotificationCompat.Builder(context, "CATODO");
            } else {
                builder = new NotificationCompat.Builder(context);
            }
            builder.setContentTitle("CATODO");
            builder.setContentText("오늘 " + str1 +" 챌린지를 달성하셨나요?");
            builder.setSmallIcon(R.drawable.logo3);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent1);

            Notification notification1 = builder.build();
            notificationManager.notify(10, notification1);
        }

        if(str2 != null) {
            builder = null;

            Intent i2 = new Intent(context, Confirm2Activity.class);
            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, i2, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                        new NotificationChannel("CATODO", "CATODO", NotificationManager.IMPORTANCE_DEFAULT)
                );
                builder = new NotificationCompat.Builder(context, "CATODO");
            } else {
                builder = new NotificationCompat.Builder(context);
            }
            builder.setContentTitle("CATODO");
            builder.setContentText("오늘 " + str2 +" 챌린지를 달성하셨나요?");
            builder.setSmallIcon(R.drawable.logo3);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent2);

            Notification notification2 = builder.build();
            notificationManager.notify(20, notification2);
        }

        if(str3 != null) {
            builder = null;

            Intent i3 = new Intent(context, Confirm3Activity.class);
            i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0, i3, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                        new NotificationChannel("CATODO", "CATODO", NotificationManager.IMPORTANCE_DEFAULT)
                );
                builder = new NotificationCompat.Builder(context, "CATODO");
            } else {
                builder = new NotificationCompat.Builder(context);
            }
            builder.setContentTitle("CATODO");
            builder.setContentText("오늘 " + str2 +" 챌린지를 달성하셨나요?");
            builder.setSmallIcon(R.drawable.logo3);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent3);

            Notification notification3 = builder.build();
            notificationManager.notify(30, notification3);
        }

    }//onReceive
}