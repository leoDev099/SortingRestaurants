package com.example.leonardo.sortingrestaurants.interfaces.presenters;

/**
 * Created by leonardo on 16/01/18.
 */

public interface IRestaurantsPresenter {
    void loadRestaurants(Integer sortValueId, String sortingOptionSelected,
                         String[] orderPrioritiesList, String isFavoriteListJson);
    void onDestroy();
}
