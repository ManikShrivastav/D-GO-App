package com.dgo.dgo.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.dgo.dgo.R;
import com.dgo.dgo.activities.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText email,password,name,confirmpassword;
    private FirebaseAuth auth;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        //  getSupportActionBar().hide();

//        Context Context = null;
//        FirebaseApp.initializeApp(Context);
        auth=FirebaseAuth.getInstance();

        if (auth.getCurrentUser()!= null){
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirmpassword);

        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if (isFirstTime){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.apply();

            Intent intent = new Intent(RegistrationActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }



    public void signup(View view) {

        String userName=name.getText().toString();
        String userPassword=password.getText().toString();
        String userEmail=email.getText().toString();
        String confirmPassword= confirmpassword.getText().toString();

        if (TextUtils.isEmpty(userName)){
            name.setError("Required");
            name.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(userEmail)){
            email.setError("Required");
            email.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(userPassword)){
            password.setError("Required");
            password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)){
            confirmpassword.setError("Required");
            confirmpassword.requestFocus();
            return;
        }

        if (!userPassword.equals(confirmPassword)){
            confirmpassword.setError("Required");
            confirmpassword.requestFocus();
            return;
        }

        if (userPassword.length()<6){
            Toast.makeText(this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(RegistrationActivity.this, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this,"Successfully Registered!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }else
                    {
                        Toast.makeText(RegistrationActivity.this,"Registration Failed!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}