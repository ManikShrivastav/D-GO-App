package com.dgo.dgo.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dgo.dgo.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    List<AddressModel> addressModelList;
    SelectedAddress selectedAddress;

    public static boolean addressSelected = false;

    private RadioButton selectedRadioBtn;

    public  AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress){
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(view -> {

            addressSelected = true;

            for (AddressModel address:addressModelList){
                address.setSelected(false);
            }
            addressModelList.get(position).setSelected(true);

            if (selectedRadioBtn != null){
                selectedRadioBtn.setChecked(false);
            }
            selectedRadioBtn = (RadioButton) view;
            selectedRadioBtn.setChecked(true);
            selectedAddress.setAddress(addressModelList.get(position).getUserAddress());
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress{
        void  setAddress(String address);
    }
}
