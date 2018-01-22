package com.example.leonardo.sortingrestaurants.view.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.leonardo.sortingrestaurants.R;
import com.example.leonardo.sortingrestaurants.databinding.FragmentSearchResultBinding;
import com.example.leonardo.sortingrestaurants.interfaces.presenters.ISearchResultPresenter;
import com.example.leonardo.sortingrestaurants.interfaces.views.ISearchResultView;
import com.example.leonardo.sortingrestaurants.model.Restaurant;
import com.example.leonardo.sortingrestaurants.model.Restaurants;
import com.example.leonardo.sortingrestaurants.model.events.ToolbarEvent;
import com.example.leonardo.sortingrestaurants.presenter.SearchResultPresenter;

import org.greenrobot.eventbus.EventBus;

public class SearchResultFragment extends Fragment implements ISearchResultView{

    private static final String ARG_RESTAURANTS = "restaurants";
    private static final String ARG_QUERY_RESULT = "queryResult";

    private Restaurants restaurants;
    private String queryResult;
    private Restaurant queryOutput;

    public SearchResultFragment() {
    }

    public static SearchResultFragment newInstance(Restaurants restaurants, String queryResult) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAURANTS, restaurants);
        args.putString(ARG_QUERY_RESULT, queryResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new ToolbarEvent(null,true));
        if (getArguments() != null) {
            restaurants = (Restaurants) getArguments().getSerializable(ARG_RESTAURANTS);
            queryResult = getArguments().getString(ARG_QUERY_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSearchResultBinding binding= DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_result, container, false);

        ISearchResultPresenter resultPresenter = new SearchResultPresenter(this);
        resultPresenter.loadSearchResults(restaurants,queryResult);

        View view = binding.getRoot();
        binding.setRestaurant(queryOutput);

        if(queryOutput.getName().equals("")){
            binding.cvItemRestaurant.setVisibility(View.GONE);
            Toast.makeText(getActivity(),R.string.toast_message_not_found_seargh,Toast.LENGTH_SHORT).show();

        }else{
            binding.ratingBarItemRestaurantSearch.setRating(queryOutput.getSortingValues().getRatingAverage().floatValue());
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showSearchResult(Restaurant restaurant) {
        this.queryOutput = restaurant;
    }
}
