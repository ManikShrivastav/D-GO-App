package com.dgo.dgo.modules;

import java.io.Serializable;

public class NewProductsModel implements Serializable {

    String description;
    String name;
    String rating;
    int price;
    String img_url;

    public NewProductsModel(){

    }

    public NewProductsModel(String description,String name,String rating,int price,String img_url){
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.img_url = img_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
