package com.prashant.mvvm.view.activity;
/**
 * @author : Prashant P
 * @Name: SplashScreenActivity
 * Created in 2018 as an unpublished copyright work.
 * All rights reserved.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prashant.mvvm.R;


public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        displaySplashScreen();
    }

    /*
     * Showing splash screen with a timer. This will be useful when you
     * want to show case your app logo / company
     */
    private void displaySplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
