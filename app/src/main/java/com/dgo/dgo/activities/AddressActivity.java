package com.dgo.dgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dgo.dgo.R;
import com.dgo.dgo.modules.AddressAdapter;
import com.dgo.dgo.modules.AddressModel;
import com.dgo.dgo.modules.NewProductsModel;
import com.dgo.dgo.modules.PopularProductsModel;
import com.dgo.dgo.modules.ShowAllModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    Button addAddress;
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button paymentBtn;
    Toolbar toolbar;
    String mAddress = "";
    public static String final_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addressAdapter.addressSelected = false;

        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());

        //get Data from Detailed Activity
        Object obj = getIntent().getSerializableExtra("item");

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        addAddress = findViewById(R.id.add_address_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(),addressModelList,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            AddressModel addressModel = doc.toObject(AddressModel.class);
                            addressModelList.add(addressModel);
                            addressAdapter.notifyDataSetChanged();
                        }
                    }
                });

        paymentBtn.setOnClickListener(view -> {
            double amount = 0.0;

            final_address = mAddress;

            if (addressAdapter.addressSelected) {

                Intent intent1 = new Intent(AddressActivity.this, PaymentActivity.class);

                if (obj instanceof NewProductsModel) {
                    NewProductsModel newProductsModel = (NewProductsModel) obj;

                    amount = newProductsModel.getPrice();
                }
                if (obj instanceof PopularProductsModel) {
                    PopularProductsModel popularProductsModel = (PopularProductsModel) obj;
                    amount = popularProductsModel.getPrice();
                }

                if (obj instanceof ShowAllModel) {
                    ShowAllModel showAllModel = (ShowAllModel) obj;
                    amount = showAllModel.getPrice();
                }

                intent1.putExtra("amount", amount);
                startActivity(intent1);
            }
            else {
                Toast.makeText(AddressActivity.this, "Kindly Add or Select an address.", Toast.LENGTH_LONG).show();
            }
        });

        addAddress.setOnClickListener(view -> startActivity(new Intent(AddressActivity.this,AddAddressActivity.class)));

    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}