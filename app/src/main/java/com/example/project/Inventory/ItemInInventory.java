package com.example.project.Inventory;

import android.content.SharedPreferences;

import com.example.project.R;

import java.util.HashMap;
import java.util.Map;

public class ItemInInventory {

    private String product;
    private String description;
    private int image;

    public  ItemInInventory(String product, String description, int image){
        this.product = product;
        this.description = description;
        this.image = image;
    }

    public String getProduct(){
        return product;

    }

    public String getDescription(){
        return description;

    }

    public int getImages(){
        return image;

    }

}





