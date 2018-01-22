package com.example.leonardo.sortingrestaurants.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leonardo on 18/01/18.
 */
@Module
public final class DataBaseModule {

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
