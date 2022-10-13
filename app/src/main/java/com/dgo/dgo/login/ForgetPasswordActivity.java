package com.dgo.dgo.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import com.dgo.dgo.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText resetemail;
    private FirebaseAuth auth;
    private  String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //  getSupportActionBar().hide();
        resetemail=findViewById(R.id.resetemail);
        auth=FirebaseAuth.getInstance();
    }

    public void forget_password(View view) {
        validateData();
    }

    private void validateData() {
        email = resetemail.getText().toString();
        if (email.isEmpty()){
            resetemail.setError("Required");
        }else{
            forgetpassword();
        }
    }

    private void forgetpassword() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgetPasswordActivity.this,"Check your Email! If not received kindly check Spam Folder",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(ForgetPasswordActivity.this,""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}