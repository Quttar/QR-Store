package com.example.appqrstore;

import kotlinx.coroutines.channels.ActorKt;

public class CartElem {

    public String name;
    public String description;
    public String imageUrl;
    public int count;
    public int price;

    public CartElem(String name, String description, String imageUrl, int count, int price){
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.count = count;
        this.price = price;
    }
}
