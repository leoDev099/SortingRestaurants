package com.example.leonardo.sortingrestaurants.presenter;

import com.example.leonardo.sortingrestaurants.interfaces.presenters.ISearchResultPresenter;
import com.example.leonardo.sortingrestaurants.interfaces.views.ISearchResultView;
import com.example.leonardo.sortingrestaurants.model.Restaurant;
import com.example.leonardo.sortingrestaurants.model.Restaurants;

import java.util.List;
import java.util.Optional;

/**
 * Created by leonardo on 01/21/18.
 */

public class SearchResultPresenter implements ISearchResultPresenter {

    private ISearchResultView searchResultView;
    private Boolean exists = false;
    Restaurant defaultRestaurant;

    public SearchResultPresenter(ISearchResultView searchResultView){
        this.searchResultView = searchResultView;
    }

    @Override
    public void loadSearchResults(Restaurants restaurants, String query) {

        List<Restaurant> restaurantList = restaurants.getRestaurants();
        defaultRestaurant = new Restaurant();
        defaultRestaurant.setName("");
        defaultRestaurant.setStatus("");
        defaultRestaurant.setIsFavorite(false);

        Optional<Restaurant> matchingObject = restaurantList.stream().
                filter(p -> p.getName().equals(query)).
                findFirst();

        Restaurant restaurant = matchingObject.orElse(defaultRestaurant);

        searchResultView.showSearchResult(restaurant);
    }
}
