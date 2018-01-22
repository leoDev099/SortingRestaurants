package com.example.leonardo.sortingrestaurants.utils;
import com.example.leonardo.sortingrestaurants.data.preferences.StringPreference;
import com.example.leonardo.sortingrestaurants.model.Favorite;
import com.example.leonardo.sortingrestaurants.model.events.FavoritesEvent;
import android.content.Context;
import android.content.SharedPreferences;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

import static com.example.leonardo.sortingrestaurants.view.activities.RestaurantsActivity.PREFS_NAME;

/**
 * Created by leonardo on 16/01/18.
 */

public class AdapterDuties{

    private StringPreference stringPreference;
    private Context context;

    public AdapterDuties(Context context){
        this.context = context;
        EventBus.getDefault().register(this);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        stringPreference = new StringPreference(sharedPreferences, PREFS_NAME);
    }

    private void setNewValueSharedPreferences(Boolean isFavorite, String itemRestaurantName){

        if(isFavorite){
            addValueToSharedPreferences(isFavorite, itemRestaurantName);
        }else{
            removeValueFromSharedPreferences(itemRestaurantName);
        }
    }

    private void addValueToSharedPreferences(Boolean isFavorite, String itemRestaurantName){

        List<Favorite> favoriteList = new ArrayList<>();
        favoriteList.add(new Favorite(itemRestaurantName,isFavorite));

        stringPreference.set(favoriteList);
    }

    private void removeValueFromSharedPreferences(String itemRestaurantName){

        List<Favorite> favoriteList = new ArrayList<>();
        favoriteList = stringPreference.get();

        stringPreference.delete();
        favoriteList.removeIf(obj -> obj.getItemRestaurantName().equals(itemRestaurantName));
        stringPreference.set(favoriteList);
    }

    @Subscribe
    public void onEvent(FavoritesEvent event) {
        setNewValueSharedPreferences(event.isFavorite, event.itemRestaurantName);
    }
}
