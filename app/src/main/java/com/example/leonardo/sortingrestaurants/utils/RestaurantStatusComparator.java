package com.example.leonardo.sortingrestaurants.utils;

import com.example.leonardo.sortingrestaurants.model.Restaurant;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by leonardo on 18/01/18.
 */

public class RestaurantStatusComparator implements Comparator<Restaurant> {

    private Map<String, Integer> sortOrder;

    public RestaurantStatusComparator(Map<String, Integer> sortOrder){
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Restaurant o1, Restaurant o2) {

        Integer statusPos1 = sortOrder.get(o1.getStatus());
        if(statusPos1 == null){
            throw new IllegalArgumentException("Wrong status found: "+ o1.getStatus());
        }
        Integer statusPos2 = sortOrder.get(o2.getStatus());
        if(statusPos2 == null){
            throw new IllegalArgumentException("Wrong status found: "+ o1.getStatus());
        }
        return statusPos1.compareTo(statusPos2);
    }
}
