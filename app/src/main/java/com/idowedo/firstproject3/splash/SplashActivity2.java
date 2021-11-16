package com.idowedo.firstproject3.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.idowedo.firstproject3.Login.LoginActivity;
import com.idowedo.firstproject3.MainActivity;
import com.idowedo.firstproject3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity2 extends Activity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
            super.onAttachedToWindow();
            Window window = getWindow();
            window.setFormat(PixelFormat.RGBA_8888);
    }
        Thread splashTread;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash2);
            StartAnimation();
        }
        private void StartAnimation() {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
            anim.reset();
            LinearLayout l = (LinearLayout)findViewById(R.id.lin_lay);
            l.clearAnimation();
            l.startAnimation(anim);

            anim = AnimationUtils.loadAnimation(this, R.anim.translate);
            anim.reset();
            ImageView iv = (ImageView) findViewById(R.id.splash2);

            iv.setColorFilter(Color.parseColor("#F4385E"), PorterDuff.Mode.SRC_IN);
            iv.clearAnimation();
            iv.startAnimation(anim);

            splashTread = new Thread() {
                @Override
                public void run() {
                    try {
                        int waited = 0;
                        //Splash screen pause time
                        while (waited < 3500) {
                            sleep(100);
                            waited += 100;
                        }
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            Intent intent = new Intent(SplashActivity2.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashActivity2.this.finish();
                        } else{
                            Intent intent = new Intent(SplashActivity2.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashActivity2.this.finish();
                        }
                    } catch (InterruptedException e) {
                        //do hothing
                    } finally {
                        SplashActivity2.this.finish();
                    }
                }
            };
            splashTread.start();
        }
    }