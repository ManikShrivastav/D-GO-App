package com.dgo.dgo.modules;

public class AddressModel {

    String userAddress;
    boolean isSelected;

    public AddressModel(){

    }

    public AddressModel(String userAddress,boolean isSelected){
        this.userAddress = userAddress;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}