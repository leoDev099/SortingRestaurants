package com.example.leonardo.sortingrestaurants.presenter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.leonardo.sortingrestaurants.data.preferences.StringPreference;
import com.example.leonardo.sortingrestaurants.interfaces.presenters.IRestaurantsPresenter;
import com.example.leonardo.sortingrestaurants.interfaces.views.IRestaurantsView;
import com.example.leonardo.sortingrestaurants.model.Favorite;
import com.example.leonardo.sortingrestaurants.model.Restaurant;
import com.example.leonardo.sortingrestaurants.model.Restaurants;
import com.example.leonardo.sortingrestaurants.model.events.FavoritesEvent;
import com.example.leonardo.sortingrestaurants.services.FeedService;
import com.example.leonardo.sortingrestaurants.utils.RestaurantStatusComparator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by leonardo on 16/01/18.
 */

public class RestaurantsPresenter implements IRestaurantsPresenter{

    @Inject
    StringPreference stringPreference;

    private IRestaurantsView restaurantsView;
    private FeedService feedService;
    private CompositeSubscription compositeSubscription;
    private Restaurants restaurantsContent;
    private List<Restaurant> restaurantList;

    public RestaurantsPresenter(IRestaurantsView restaurantsView){
        this.restaurantsView = restaurantsView;
        this.feedService = new FeedService();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadRestaurants(final Integer sortValueId, String sortingOptionSelected,
                                String[] orderPrioritiesList, String isFavoriteListJson) {
        Subscription subscriptionRestaurant;

        subscriptionRestaurant = feedService.getFeedRestaurantsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Restaurants>() {
                    @Override
                    public void onCompleted() {
                        restaurantList = restaurantsContent.getRestaurants();
                        setSelectedSortingValue(sortingOptionSelected, sortValueId, restaurantList);
                        setIsFavoriteValue(isFavoriteListJson);
                        sortRestaurantsListBy(restaurantList,sortingOptionSelected, orderPrioritiesList);
                        restaurantsView.showListRestaurants(restaurantList);
                        restaurantsView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("error", e.getMessage());
                        restaurantsView.hideLoading();
                    }

                    @Override
                    public void onNext(Restaurants restaurantsFeed) {
                        restaurantsContent = restaurantsFeed;

                    }
                });

        compositeSubscription.add(subscriptionRestaurant);

    }

    private void sortRestaurantsListBy(List<Restaurant> restaurantListToSort, String sortingOptionSelected, String[] orderPrioritiesList){

        Map<String, Integer> statusOrder = getStatusOderMap(orderPrioritiesList);

        //condition due to it makes more sense for me to sort rating avg and popularity from max to min
        if(sortingOptionSelected.equals("Rating avg")|sortingOptionSelected.equals("Popularity")) {

            restaurantListToSort.sort(Comparator.comparing((Restaurant::getIsFavorite)).reversed().
                    thenComparing((new RestaurantStatusComparator(statusOrder)).
                    thenComparing(Comparator.comparing(Restaurant::getSelectedSortingValue).reversed())));
        }else{
            restaurantListToSort.sort(Comparator.comparing((Restaurant::getIsFavorite)).reversed().
                    thenComparing((new RestaurantStatusComparator(statusOrder)).
                            thenComparing(Comparator.comparing(Restaurant::getSelectedSortingValue))));
        }
    }

    private Map<String,Integer> getStatusOderMap (String[] orderPrioritiesList){

        List<String> sortingCriteria = Arrays.asList(orderPrioritiesList);
        Map<String, Integer> statusOrder = new HashMap<>();

        for(int i =0; i < sortingCriteria.size(); i++) {
            String status = sortingCriteria.get(i);
            statusOrder.put(status, i);
        }
        return statusOrder;
    }

    private void setIsFavoriteValue(String isFavoriteListJson){
        Gson gson = new Gson();
        List<Favorite> favoriteList = new ArrayList<>();
        if (isFavoriteListJson.equals("")){
            favoriteList = new ArrayList<>();
        }else{
            Type favoritesListType = new TypeToken<ArrayList<Favorite>>(){}.getType();
            favoriteList = gson.fromJson(isFavoriteListJson, favoritesListType);
        }

        Set<String> favoritedRestaurants =
                favoriteList.stream()
                        .map(Favorite::getItemRestaurantName)
                        .collect(Collectors.toSet());

        restaurantList.stream()
                .filter(c -> favoritedRestaurants.contains(c.getName())).forEach(r -> r.setIsFavorite(true));

    }

    private void setSelectedSortingValue(String selectedSortedValue, @NonNull Integer sortValueId, List<Restaurant> restaurantList){
        //refactor this method to lambdaExpression passing method
        switch (sortValueId){
            case 0:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = (double) restaurant.getSortingValues().getBestMatch();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                break;
            case 1:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = (double) restaurant.getSortingValues().getNewest();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                break;
            case 2:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = restaurant.getSortingValues().getRatingAverage();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                break;
            case 3:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    double sortedValue = (double) restaurant.getSortingValues().getDistance();
                    restaurant.setSelectedSortingValue(sortedValue);
                }
                break;
            case 4:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = (double) restaurant.getSortingValues().getPopularity();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                break;
            case 5:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = (double) restaurant.getSortingValues().getAverageProductPrice();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                break;
            case 6:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = (double) restaurant.getSortingValues().getDeliveryCosts();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                break;
            case 7:
                for(int i = 0; i<restaurantList.size() ; i++){
                    Restaurant restaurant = restaurantList.get(i);
                    if(restaurant != null) {
                        double sortedValue = (double) restaurant.getSortingValues().getMinCost();
                        restaurant.setSelectedSortingValue(sortedValue);
                    }
                }
                default:
                    Log.i("no_id", "no id in the dropdown");
                break;
            }
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }
}