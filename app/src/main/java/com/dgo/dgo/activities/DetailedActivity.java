package com.dgo.dgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.dgo.dgo.R;
import com.dgo.dgo.modules.NewProductsModel;
import com.dgo.dgo.modules.PopularProductsModel;
import com.dgo.dgo.modules.ShowAllModel;
import com.google.firebase.auth.FirebaseAuth;

public class DetailedActivity extends AppCompatActivity {


    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button buyNow;
    ImageView addItems,removeItems;

    Toolbar toolbar;
    static int totalQuantity = 1;
    int totalPrice = 0;

    public static  String productName_cart,productPrice_cart,totalQuantity_cart;



    //New Products
    NewProductsModel newProductsModel = null;

    //Popular Products
    PopularProductsModel popularProductsModel = null;

    //Show All
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());



        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;

        } else if (obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }
        else if (obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel)  obj;
        }



        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.rating);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        buyNow = findViewById(R.id.buy_now);


        // New Products

        if (newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            description.setText(newProductsModel.getDescription());
            rating.setText(newProductsModel.getRating());
            name.setText(newProductsModel.getName());

            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }
        // Popular Products
        if (popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            description.setText(popularProductsModel.getDescription());
            rating.setText(popularProductsModel.getRating());
            name.setText(popularProductsModel.getName());

            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }
        // Show Products
        if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            price.setText(String.valueOf(showAllModel.getPrice()));
            description.setText(showAllModel.getDescription());
            rating.setText(showAllModel.getRating());
            name.setText(showAllModel.getName());

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }

        //Buy Now
        buyNow.setOnClickListener(view -> {
            Intent intent =new Intent(DetailedActivity.this,AddressActivity.class);


            totalQuantity_cart = quantity.getText().toString();

            productName_cart = name.getText().toString();

            productPrice_cart = price.getText().toString();

            if (newProductsModel != null){
                intent.putExtra("item",newProductsModel);
            }
            if (popularProductsModel != null){
                intent.putExtra("item",popularProductsModel);
            }
            if (showAllModel != null){
                intent.putExtra("item",showAllModel);
            }


            startActivity(intent);
            finish();
        });

        addItems.setOnClickListener(view -> {

            if (totalQuantity < 10){
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));

                if (newProductsModel != null){
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                }
                if (popularProductsModel != null){
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                }

                if (showAllModel != null){
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                }
            }
        });

        removeItems.setOnClickListener(view -> {
            if(totalQuantity > 1){
                totalQuantity--;
                quantity.setText(String.valueOf(totalQuantity));

                if (newProductsModel != null){
                    totalPrice = newProductsModel.getPrice() * totalQuantity;
                }
                if (popularProductsModel != null){
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                }

                if (showAllModel != null){
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                }
            }
            else {
                Toast.makeText(DetailedActivity.this,"For more than 10,Please contact through WhatsApp",Toast.LENGTH_LONG).show();
            }

        });


        removeItems.setOnClickListener(view -> {
            if (totalQuantity <= 1 ){
                totalQuantity = 1;
            }
            else {
                totalQuantity--;
            }
            quantity.setText(String.valueOf(totalQuantity));
        });



    }


}