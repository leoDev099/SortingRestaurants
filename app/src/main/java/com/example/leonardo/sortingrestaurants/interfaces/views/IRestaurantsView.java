package com.example.leonardo.sortingrestaurants.interfaces.views;

import com.example.leonardo.sortingrestaurants.model.Restaurant;

import java.util.List;

/**
 * Created by leonardo on 16/01/18.
 */

public interface IRestaurantsView {

    void showListRestaurants(List<Restaurant> restaurantList);

    void showLoading();
    void hideLoading();
}
