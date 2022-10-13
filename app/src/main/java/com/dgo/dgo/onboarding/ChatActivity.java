package com.dgo.dgo.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.dgo.dgo.R;
import com.dgo.dgo.login.RegistrationActivity;


public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chat);
    }

    public void back(View view) {
        startActivity(new Intent(ChatActivity.this,ConstructionServicesActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(ChatActivity.this, RegistrationActivity.class));
        finish();
    }
}