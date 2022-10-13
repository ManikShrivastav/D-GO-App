package com.dgo.dgo.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


import com.dgo.dgo.R;


public class ConstructionServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_construction_services);
    }
    public void forward(View view) {
        startActivity(new Intent(ConstructionServicesActivity.this,ChatActivity.class));
        finish();
    }
}
