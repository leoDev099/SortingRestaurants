package com.example.leonardo.sortingrestaurants.view.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.leonardo.sortingrestaurants.R;
import com.example.leonardo.sortingrestaurants.data.preferences.StringPreference;
import com.example.leonardo.sortingrestaurants.model.Restaurants;
import com.example.leonardo.sortingrestaurants.model.events.RestaurantEvent;
import com.example.leonardo.sortingrestaurants.model.events.ToolbarEvent;
import com.example.leonardo.sortingrestaurants.utils.AdapterDuties;
import com.example.leonardo.sortingrestaurants.view.fragments.RestaurantListFragment;
import com.example.leonardo.sortingrestaurants.view.fragments.SearchResultFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RestaurantsActivity extends AppCompatActivity implements RestaurantListFragment.OnFragmentInteractionListener{
    public static final String PREFS_NAME = "FavoritesPreferences";
    private android.support.v7.widget.Toolbar toolbarRestaurants;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Context context;
    private Spinner spinner;
    private Restaurants restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        toolbarRestaurants = findViewById(R.id.toolbarRestaurants);
        context = getApplicationContext();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_main, menu);
        MenuItem item = menu.findItem(R.id.menu_sort_options_spinner);
        spinner =(Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sort_values_array,R.layout.layout_bar_spinner_title);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String label = parent.getItemAtPosition(position).toString();
                addRestaurantListFragment(position, label);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_option_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                spinner.setVisibility(View.GONE);
                addSearchResultsFragment(restaurants,query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sort_options_spinner:
                return true;
            case R.id.menu_search_option_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init(){
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        AdapterDuties adapterDuties = new AdapterDuties(context);
        setUpToolBar();
        handleIntent(getIntent());
    }

    public void setUpToolBar(){
        setSupportActionBar(toolbarRestaurants);
    }

    private void addRestaurantListFragment(Integer sortValueId, String sortingOptionSelected){
        RestaurantListFragment restaurantListFragment = RestaurantListFragment.newInstance(sortValueId, sortingOptionSelected);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flRestaurantsContainer, restaurantListFragment, "RestaurantListFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addSearchResultsFragment(Restaurants restaurants, String queryResoult){
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(restaurants, queryResoult);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flRestaurantsContainer, searchResultFragment, "SearchResultFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String queryResult;queryResult = intent.getStringExtra(SearchManager.QUERY);
            addSearchResultsFragment(restaurants, queryResult);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(RestaurantEvent event) {
        restaurants = event.restaurants;
    }

    @Subscribe
    public void onEvent(ToolbarEvent event) {
        if(event.name != null) {
            getSupportActionBar().setTitle(event.name);
        }if(event.isBackButtonPresent != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(event.isBackButtonPresent);
            toolbarRestaurants.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
