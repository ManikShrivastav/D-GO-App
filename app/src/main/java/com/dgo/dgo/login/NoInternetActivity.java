package com.dgo.dgo.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.dgo.dgo.R;
import com.dgo.dgo.onboarding.SplashScreenActivity;


public class NoInternetActivity extends AppCompatActivity {

    Button no_internet_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_no_internet);
        //getSupportActionBar().hide();
        no_internet_retry = findViewById(R.id.check_internet_button);

    }

    public void tryagain(View view) {
        startActivity(new Intent(NoInternetActivity.this, SplashScreenActivity.class));
    }
    @Override
    public void onBackPressed() {
               finishAffinity();
         }
}
