package com.example.leonardo.sortingrestaurants.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.leonardo.sortingrestaurants.R;
import com.example.leonardo.sortingrestaurants.databinding.ItemRestaurantBinding;
import com.example.leonardo.sortingrestaurants.model.Restaurant;
import com.example.leonardo.sortingrestaurants.model.events.FavoritesEvent;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**
 * Created by leonardo on 16/01/18.
 */

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.RestaurantViewHolder>{

    private List<Restaurant> restaurantsList;
    private Integer sortValueId;
    private String sortingOptionSelected;

    public RestaurantsListAdapter(@NonNull List<Restaurant> restaurantsList, Integer sortValueId,
                                  String sortingOptionSelected) {
        this.restaurantsList = restaurantsList;
        this.sortValueId = sortValueId;
        this.sortingOptionSelected = sortingOptionSelected;
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantBinding itemRestaurantBinding;
        private String sortOptionValue;

        public RestaurantViewHolder(ItemRestaurantBinding binding, String sortingOptionSelected) {
            super(binding.getRoot());

            sortOptionValue = sortingOptionSelected;
           itemRestaurantBinding = binding;
        }

        public void bind(@NonNull Restaurant restaurant){
            itemRestaurantBinding.setRestaurant(restaurant);
            itemRestaurantBinding.ratingBarItemRestaurant.setRating(restaurant.getSortingValues().getRatingAverage().floatValue());
            itemRestaurantBinding.tvSortingValueName.setText(sortOptionValue+": ");
            if(sortOptionValue.equals("Rating avg")) {
                itemRestaurantBinding.tvSortingValueData.setText(String.valueOf(restaurant.getSelectedSortingValue()));
            }else{
                itemRestaurantBinding.tvSortingValueData.setText(String.valueOf((int)restaurant.getSelectedSortingValue()));
            }
            itemRestaurantBinding.btnItemRestaurantFavorites.setOnClickListener(
                    v -> {
                        Boolean isFavorite;
                        String itemRestaurantName = restaurant.getName();
                        isFavorite= itemRestaurantBinding.btnItemRestaurantFavorites.isChecked();
                        restaurant.setIsFavorite(isFavorite);
                        bind(restaurant);
                        EventBus.getDefault().post(new FavoritesEvent(isFavorite, itemRestaurantName));
                    }

            );
            itemRestaurantBinding.executePendingBindings();
        }

    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRestaurantBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(binding, sortingOptionSelected);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {

        Restaurant restaurant = restaurantsList.get(position);
        holder.bind(restaurant);
    }


    public void notifyData(List<Restaurant> restaurantList) {
        this.restaurantsList = restaurantList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

}
