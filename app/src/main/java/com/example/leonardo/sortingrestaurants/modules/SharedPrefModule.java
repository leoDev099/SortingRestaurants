package com.example.leonardo.sortingrestaurants.modules;


import android.content.SharedPreferences;

import com.example.leonardo.sortingrestaurants.data.DataBaseModule;
import com.example.leonardo.sortingrestaurants.data.preferences.StringPreference;


import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by leonardo on 18/01/18.
 */
@Module(includes = DataBaseModule.class)
public class SharedPrefModule {

    @Provides @Singleton
    StringPreference provideMyValue(SharedPreferences prefs) {
        return new StringPreference(prefs, "myKey", "defaultValue");
    }
}