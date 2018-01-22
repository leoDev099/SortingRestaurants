package com.example.leonardo.sortingrestaurants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo on 18/01/18.
 */

public class Favorite implements Serializable {

    @SerializedName("item_restaurant_name")
    @Expose
    private String itemRestaurantName;
    @SerializedName("item_is_favorite")
    @Expose
    private boolean itemIsFavorite;

    public Favorite(String itemRestaurantName, boolean itemIsFavorite){
        this.itemRestaurantName = itemRestaurantName;
        this.itemIsFavorite = itemIsFavorite;
    }

    public String getItemRestaurantName() {
        return itemRestaurantName;
    }

    public void setItemRestaurantName(String itemRestaurantName) {
        this.itemRestaurantName = itemRestaurantName;
    }

    public boolean getItemIsFavorite() {
        return itemIsFavorite;
    }

    public void setItemIsFavorite(boolean itemIsFavorite) {
        this.itemIsFavorite = itemIsFavorite;
    }

}
