package com.example.leonardo.sortingrestaurants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leonardo on 16/01/18.
 */

public class SortingValues implements Serializable {

    @SerializedName("minCost")
    @Expose
    private Integer minCost;
    @SerializedName("deliveryCosts")
    @Expose
    private Integer deliveryCosts;
    @SerializedName("averageProductPrice")
    @Expose
    private Integer averageProductPrice;
    @SerializedName("popularity")
    @Expose
    private Integer popularity;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("ratingAverage")
    @Expose
    private Double ratingAverage;
    @SerializedName("newest")
    @Expose
    private Integer newest;
    @SerializedName("bestMatch")
    @Expose
    private Integer bestMatch;

    public Integer getMinCost() {
        return minCost;
    }

    public void setMinCost(Integer minCost) {
        this.minCost = minCost;
    }

    public Integer getDeliveryCosts() {
        return deliveryCosts;
    }

    public void setDeliveryCosts(Integer deliveryCosts) {
        this.deliveryCosts = deliveryCosts;
    }

    public Integer getAverageProductPrice() {
        return averageProductPrice;
    }

    public void setAverageProductPrice(Integer averageProductPrice) {
        this.averageProductPrice = averageProductPrice;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public Integer getNewest() {
        return newest;
    }

    public void setNewest(Integer newest) {
        this.newest = newest;
    }

    public Integer getBestMatch() {
        return bestMatch;
    }

    public void setBestMatch(Integer bestMatch) {
        this.bestMatch = bestMatch;
    }
}
