package com.dgo.dgo.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.dgo.dgo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView final_total,product_name,product_price,quantity;
    Button check_out;
    private FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //Tool Bar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());


        double amount;
        amount = DetailedActivity.totalQuantity * getIntent().getDoubleExtra("amount",0.0);

        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        quantity = findViewById(R.id.quantity_payment);
        final_total = findViewById(R.id.total_amt);
        check_out = findViewById(R.id.pay_btn);
        final_total.setText("Rs. " + amount);
        product_price.setText("Rs. "+DetailedActivity.productPrice_cart);
        product_name.setText(DetailedActivity.productName_cart);
        quantity.setText(DetailedActivity.totalQuantity_cart);

        check_out.setOnClickListener(view -> final_order());
    }

    private void final_order() {
        String saveCurrentTime, saveCurrentDate;
        String productName,productPrice,totalQuantity,order_address,user_id;
        String final_amount;

        Calendar calForDate = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        user_id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        productName  = DetailedActivity.productName_cart;
        productPrice = DetailedActivity.productPrice_cart;
        totalQuantity = DetailedActivity.totalQuantity_cart;
        final_amount = final_total.getText().toString();
        order_address = AddressActivity.final_address;

        final HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("CustomerId ", user_id);
        orderMap.put("CustomerAddress ", order_address);
        orderMap.put("productName " , productName);
        orderMap.put("productPrice ", productPrice);
        orderMap.put("currentTime ", saveCurrentTime);
        orderMap.put("currentDate ", saveCurrentDate);
        orderMap.put("totalQuantity ", totalQuantity);
        orderMap.put("totalPrice ", final_amount);


        firestore.collection("Final Order").document(auth.getCurrentUser().getEmail())
                .collection("User").add(orderMap).addOnCompleteListener(task -> {
                    Toast.makeText(PaymentActivity.this,"Order Confirmed",Toast.LENGTH_LONG).show();
                    finish();
                });


    }
}