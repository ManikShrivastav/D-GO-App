package com.dgo.dgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import com.dgo.dgo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalCode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(view -> {

            String userName = name.getText().toString();
            String userCity = city.getText().toString();
            String userAddress = address.getText().toString();
            String userCode = postalCode.getText().toString();
            String userNumber = phoneNumber.getText().toString();

            String final_address = "";

            if (!userName.isEmpty()){
                final_address += userName + ";";
            }

            if (!userCity.isEmpty()){
                final_address += userCity+ ";";
            }

            if (!userAddress.isEmpty()){
                final_address += userAddress+ ";";
            }

            if (!userCode.isEmpty()){
                final_address += userCode+ ";";
            }

            if (!userNumber.isEmpty()){
                final_address += userNumber+ ";";
            }

            if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty()  && !userCode.isEmpty() && !userNumber.isEmpty()){

                Map<String,String> map = new HashMap<>();
                map.put("userAddress",final_address);

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").add(map).addOnCompleteListener(task -> {

                            if (task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this,"Address Added Successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AddAddressActivity.this,AddressActivity.class));
                                finish();
                            }
                        });
            }
            else {
                Toast.makeText(AddAddressActivity.this,"Kindly Fill out all the fields",Toast.LENGTH_LONG).show();
            }

        });

    }
}