package com.dgo.dgo.modules;

import java.io.Serializable;

public class ShowAllModel implements Serializable {
    String description;
    String name;
    String rating;
    int price;
    String img_url;
    String type;

    public ShowAllModel(){}

    public ShowAllModel(String description,String name,String rating,String img_url, String type,int price){
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.img_url =img_url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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

    public void setType(String type) {
        this.type = type;
    }

}
