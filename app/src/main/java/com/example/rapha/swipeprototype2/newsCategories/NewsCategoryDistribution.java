package com.example.rapha.swipeprototype2.newsCategories;

import java.util.LinkedList;

/**
 * A wrapper class to store the total amount of each news category that should be requested
 * calculated by NewsCategoryContainer.
 */
public class NewsCategoryDistribution {

    private LinkedList<NewsCategory> distribution;

    public NewsCategoryDistribution(){}

    public void setDistribution(LinkedList<NewsCategory> distribution) {
        this.distribution = distribution;
    }

    public LinkedList<NewsCategory> getDistribution() {
        return distribution;
    }
}
