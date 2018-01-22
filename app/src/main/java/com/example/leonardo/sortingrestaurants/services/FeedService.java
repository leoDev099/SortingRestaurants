package com.example.leonardo.sortingrestaurants.services;

import com.example.leonardo.sortingrestaurants.handlers.AppHandler;
import com.example.leonardo.sortingrestaurants.model.Restaurants;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by leonardo on 16/01/18.
 */

public class FeedService {

    public Observable<Restaurants> getFeedRestaurantsObservable(){
        return AppHandler.getRetrofit().
                create(FeedApi.class).
                getFeedRestaurants();
    }

    private interface FeedApi{
        @GET(AppHandler.TOP_FREE_URL)
        Observable<Restaurants> getFeedRestaurants();

    }
}
