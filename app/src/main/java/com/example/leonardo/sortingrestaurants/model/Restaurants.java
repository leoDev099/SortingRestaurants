package com.example.leonardo.sortingrestaurants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leonardo on 16/01/18.
 */

public class Restaurants implements Serializable {

    @SerializedName("restaurants")
    @Expose
    public List<Restaurant> restaurants = null;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;


    }
}
