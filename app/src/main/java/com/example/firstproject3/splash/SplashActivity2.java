package com.example.firstproject3.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.firstproject3.Login.LoginActivity;
import com.example.firstproject3.MainActivity;
import com.example.firstproject3.R;

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
                        Intent intent = new Intent(SplashActivity2.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashActivity2.this.finish();
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