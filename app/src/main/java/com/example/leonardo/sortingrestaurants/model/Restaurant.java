package com.example.leonardo.sortingrestaurants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo on 16/01/18.
 */

public class Restaurant implements Serializable {

    @SerializedName("sortingValues")
    @Expose
    private SortingValues sortingValues;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isFavorite")
    @Expose
    private boolean isFavorite;
    @SerializedName("selectedSortingValue")
    @Expose
    private double selectedSortingValue;

    public SortingValues getSortingValues() {
        return sortingValues;
    }

    public void setSortingValues(SortingValues sortingValues) {
        this.sortingValues = sortingValues;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsFavorite(){
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }
    public double getSelectedSortingValue() {
        return selectedSortingValue;
    }
    public void setSelectedSortingValue(double selectedSortingValue) {
        this.selectedSortingValue = selectedSortingValue;
    }
}