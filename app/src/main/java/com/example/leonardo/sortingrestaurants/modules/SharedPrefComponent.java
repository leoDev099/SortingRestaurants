package com.example.leonardo.sortingrestaurants.modules;

import com.example.leonardo.sortingrestaurants.view.activities.RestaurantsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by leonardo on 18/01/18.
 */

@Singleton
@Component(modules= {SharedPrefModule.class})
public interface SharedPrefComponent {

    void inject(RestaurantsActivity restaurantsActivity);
}
