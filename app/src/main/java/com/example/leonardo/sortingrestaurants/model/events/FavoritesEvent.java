package com.example.leonardo.sortingrestaurants.model.events;

/**
 * Created by leonardo on 18/01/18.
 */

public class FavoritesEvent {
    public final Boolean isFavorite;
    public final String itemRestaurantName;

    public FavoritesEvent(Boolean isFavorite, String itemRestaurantName) {
        this.isFavorite = isFavorite;
        this.itemRestaurantName = itemRestaurantName;
    }
}
