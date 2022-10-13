package com.dgo.dgo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.dgo.dgo.R;
import com.dgo.dgo.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth auth;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //  getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        checkBox=findViewById(R.id.checkBox);
        // Show Password
        boolean showpass = checkBox.isChecked();

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> showPassword(compoundButton.isChecked()));

    }



    public void onBackPressed() {
        finish();
    }

    public void showPassword(boolean showpass){
        if (showpass){
            password.setInputType(144);
        }   else{
            password.setInputType(129);
        }
        password.setSelection(password.getText().length());
    }


    public void signin(View view) {

        String userPassword=password.getText().toString();
        String userEmail=email.getText().toString();


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

        if (userPassword.length()<6){
            Toast.makeText(this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }


        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Successfully Logged In!",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }else
                    {
                        Toast.makeText(LoginActivity.this,"Login Failed!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void signup(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        finish();
    }

    public void forget(View view) {
        startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
    }
}