package com.example.leonardo.sortingrestaurants.interfaces.presenters;

import com.example.leonardo.sortingrestaurants.model.Restaurants;

/**
 * Created by User on 1/22/2018.
 */

public interface ISearchResultPresenter {
    void loadSearchResults(Restaurants restaurants, String query);
}
