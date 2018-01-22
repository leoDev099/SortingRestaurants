package com.example.leonardo.sortingrestaurants.data.preferences;


import android.content.SharedPreferences;

/**
 * Created by leonardo on 18/01/18.
 */

class BasePreference {

    protected final SharedPreferences mPreferences;
    protected final String mKey;

    public BasePreference(final SharedPreferences preferences, final String key) {
        mPreferences = preferences;
        mKey = key;
    }

    public boolean isSet() {
        return mPreferences.contains(mKey);
    }


    public void delete() {
        mPreferences.edit().remove(mKey).apply();
    }

}
