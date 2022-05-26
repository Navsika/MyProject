package com.example.project.Model;

public class ModelOfItemInShop {
    String product;
    int price;
    String descriptionOfProduct;
    String whatPoint;
    int howMuchAdd;
    int image;

    public ModelOfItemInShop(String product, int price, String descriptionOfProduct, String whatPoint, int howMuchAdd, int image){
        this.product = product;
        this.price = price;
        this.descriptionOfProduct = descriptionOfProduct;
        this.whatPoint = whatPoint;
        this.howMuchAdd = howMuchAdd;
        this.image = image;
    }

}
