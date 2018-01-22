package com.example.leonardo.sortingrestaurants.data.preferences;

import android.content.SharedPreferences;

import com.example.leonardo.sortingrestaurants.model.Favorite;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardo on 18/01/18.
 */

public class StringPreference extends BasePreference {
    protected final String mDefaultValue;
    public static final String DEFAULT_VALUE_VALUE = "";

    private Gson gson = new Gson();

    public StringPreference(final SharedPreferences preferences, final String key) {
        this(preferences, key, DEFAULT_VALUE_VALUE);
    }

    public StringPreference(final SharedPreferences preferences, final String key, final String defaultValue) {
        super(preferences, key);
        mDefaultValue = defaultValue;
    }

    public List<Favorite> get() {
        List<Favorite> favoritesList;
        String jsonFromPreferences = mPreferences.getString(mKey, mDefaultValue);

        if (jsonFromPreferences.equals("")){
            favoritesList = new ArrayList<>();
        }else{
               Type favoritesListType = new TypeToken<ArrayList<Favorite>>(){}.getType();
            favoritesList = gson.fromJson(jsonFromPreferences, favoritesListType);
        }
        return favoritesList;
    }

    public void set(List<Favorite> totalFavouritesList) {
        String valueFinal;
        List<Favorite> favoritesListFromJson = new ArrayList<>();
        favoritesListFromJson = get();

        if (favoritesListFromJson.size() == 0) {
            valueFinal = gson.toJson(totalFavouritesList);

        }else{
            totalFavouritesList.addAll(favoritesListFromJson);
            valueFinal = gson.toJson(totalFavouritesList);
        }
        mPreferences.edit().putString(mKey, valueFinal).apply();
    }
}