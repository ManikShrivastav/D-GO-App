package com.dgo.dgo.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.dgo.dgo.R;
import com.dgo.dgo.activities.SeeAllActivity;
import com.dgo.dgo.modules.CategoryAdapter;
import com.dgo.dgo.modules.CategoryModel;
import com.dgo.dgo.modules.NewProductsAdapter;
import com.dgo.dgo.modules.NewProductsModel;
import com.dgo.dgo.modules.PopularProductsAdapter;
import com.dgo.dgo.modules.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView catSeeAll,newproductSeeAll,popularproductSeeAll;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerview,newProductRecyclerview,popularProductRecyclerview;


    //Category RecyclerView
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Product Recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Product RecyclerView
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //FireStore
    FirebaseFirestore db ;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        //Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getActivity());
        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularProductRecyclerview = root.findViewById(R.id.popular_rec);

        // See All Buttons

        catSeeAll = root.findViewById(R.id.category_see_all);
        popularproductSeeAll = root.findViewById(R.id.popular_see_all);
        newproductSeeAll = root.findViewById(R.id.newProducts_see_all);

        catSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SeeAllActivity.class);
            startActivity(intent);
        });

        popularproductSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SeeAllActivity.class);
            startActivity(intent);
        });

        newproductSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SeeAllActivity.class);
            startActivity(intent);
        });

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);
        //Image Slider
        ImageSlider imageSlider= root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        db.collection("ImageSlider").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                        slideModels.add(new SlideModel(queryDocumentSnapshot.getString("url"),ScaleTypes.FIT));
                        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
                    }
                }

                else{
                    Toast.makeText (getActivity (), "Error in Loading!", Toast.LENGTH_SHORT).show ();
                }
            }
        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText (getActivity (), "Error in Loading!", Toast.LENGTH_SHORT).show ();
                            }
                        });



        progressDialog.setTitle("Welcome to D-GO Works");
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Category
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList =  new ArrayList<>();
        categoryAdapter =  new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            CategoryModel categoryModel = document.toObject(CategoryModel.class);
                            categoryModelList.add(categoryModel);
                            categoryAdapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getActivity(),""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        //New Products
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                            newProductsModelList.add(newProductsModel);
                            newProductsAdapter.notifyDataSetChanged();
                        }
                    } else {

                        Toast.makeText(getActivity(),""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        //Popular Products
        popularProductRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(),popularProductsModelList);
        popularProductRecyclerview.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                            popularProductsModelList.add(popularProductsModel);
                            popularProductsAdapter.notifyDataSetChanged();
                        }
                    } else {

                        Toast.makeText(getActivity(),""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        return root;
    }
}