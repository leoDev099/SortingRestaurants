package com.example.leonardo.sortingrestaurants.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.leonardo.sortingrestaurants.R;
import com.example.leonardo.sortingrestaurants.adapters.RestaurantsListAdapter;
import com.example.leonardo.sortingrestaurants.interfaces.presenters.IRestaurantsPresenter;
import com.example.leonardo.sortingrestaurants.interfaces.views.IRestaurantsView;
import com.example.leonardo.sortingrestaurants.model.Restaurant;
import com.example.leonardo.sortingrestaurants.model.Restaurants;
import com.example.leonardo.sortingrestaurants.model.events.RestaurantEvent;
import com.example.leonardo.sortingrestaurants.model.events.ToolbarEvent;
import com.example.leonardo.sortingrestaurants.presenter.RestaurantsPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.example.leonardo.sortingrestaurants.view.activities.RestaurantsActivity.PREFS_NAME;

public class RestaurantListFragment extends Fragment implements IRestaurantsView{

    private static final String ARG_SORT_VALUE_ID = "sortValueId";
    private static final String ARG_SORT_OPTION_SELECTED = "sortingOptionSelected";

    private Integer sortValueId;
    private String sortingOptionSelected;
    private ProgressBar pbList;
    private String isFavoriteListJson;

    protected RecyclerView rvRestaurantsList;
    protected RestaurantsListAdapter restaurantsListAdapter;
    protected RecyclerView.LayoutManager layoutManager;

    private IRestaurantsPresenter restaurantsPresenter;
    private OnFragmentInteractionListener mListener;

    public RestaurantListFragment() {
    }

    public static RestaurantListFragment newInstance(Integer sortValueId, String sortingOptionSelected) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SORT_VALUE_ID, sortValueId);
        args.putString(ARG_SORT_OPTION_SELECTED, sortingOptionSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isFavoriteListJson = sharedPreferences.getString(PREFS_NAME,"");
        if (getArguments() != null) {
            sortValueId = getArguments().getInt(ARG_SORT_VALUE_ID);
            sortingOptionSelected = getArguments().getString(ARG_SORT_OPTION_SELECTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] orderPriorityList;

        String toolBarTitle = getResources().getString(R.string.toolbar_title_restaurants_list);
        EventBus.getDefault().post(new ToolbarEvent(toolBarTitle,false));
        setRetainInstance(true);

        View rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        rvRestaurantsList = rootView.findViewById(R.id.rvRestaurantList);
        pbList = rootView.findViewById(R.id.pbList);

        layoutManager = new LinearLayoutManager(getActivity());
        rvRestaurantsList.setLayoutManager(layoutManager);

        orderPriorityList = getResources().getStringArray(R.array.sort_values_priority_order);
        restaurantsListAdapter = new RestaurantsListAdapter(
                new ArrayList<Restaurant>(), sortValueId, sortingOptionSelected);
        rvRestaurantsList.setAdapter(restaurantsListAdapter);

        restaurantsPresenter = new RestaurantsPresenter(this);
        restaurantsPresenter.loadRestaurants(sortValueId, sortingOptionSelected, orderPriorityList, isFavoriteListJson);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showListRestaurants(List<Restaurant> restaurantList) {
        Restaurants restaurantsToPass = new Restaurants();
        restaurantsToPass.setRestaurants(restaurantList);
        EventBus.getDefault().post(new RestaurantEvent(restaurantsToPass));
        restaurantsListAdapter.notifyData(restaurantList);
    }

    @Override
    public void showLoading() {
        pbList.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbList.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        restaurantsPresenter.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
