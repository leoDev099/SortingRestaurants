package com.example.leonardo.sortingrestaurants.model.events;

import com.example.leonardo.sortingrestaurants.model.Restaurants;

/**
 * Created by leonardo on 16/01/18.
 */

public class RestaurantEvent {

    public final Restaurants restaurants;

    public RestaurantEvent(Restaurants restaurants) {
        this.restaurants = restaurants;
    }
}
