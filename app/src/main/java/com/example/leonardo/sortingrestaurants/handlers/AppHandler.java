package com.example.leonardo.sortingrestaurants.handlers;

import android.app.Application;
import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leonardo on 16/01/18.
 */

public class AppHandler extends Application {

    public static final String SERVER_URL = "https://api.jsonbin.io/b/";
    public static final String TOP_FREE_URL = "5a44bb3e7835fe482d9cbfeb";

    private static Context context;
    private static AppHandler appHandler;
    public static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        appHandler = this;
        context = getApplicationContext();
    }

    public static AppHandler getInstance() {
        return appHandler;
    }

    public static Context getContext() {
        return context;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(SERVER_URL)
                    .build();
        }
        return retrofit;
    }

}
