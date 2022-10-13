package com.dgo.dgo.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


import com.dgo.dgo.R;
import com.dgo.dgo.activities.MainActivity;
import com.dgo.dgo.login.NoInternetActivity;
import com.dgo.dgo.login.RegistrationActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    Timer splash_timer;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        // getSupportActionBar().hide();

        SharedPreferences getIntroSignInState = getSharedPreferences("app_state", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);
        boolean introTest = getIntroSignInState.getBoolean("intro_state", false);
        boolean signInTest = getIntroSignInState.getBoolean("sign_in_state", false);

        splash_timer = new Timer();
        splash_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(internetStatus()) {
                    if (introTest) {
                        if (signInTest) {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

                        } else {
                            startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
                        }
                    } else {
                        if (isFirstTime){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("firstTime",false);
                            editor.apply();
                            Intent intent = new Intent(SplashScreenActivity.this, ConstructionServicesActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
                        }

                    }
                }
                else{
                    startActivity(new Intent(SplashScreenActivity.this, NoInternetActivity.class));

                }
                finish();
            }
        }, 800);
    }


    public boolean internetStatus() {
        ConnectivityManager checkConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo getNetworkInfo = checkConnectivity.getActiveNetworkInfo();
        return getNetworkInfo != null && getNetworkInfo.isConnectedOrConnecting();
    }
}