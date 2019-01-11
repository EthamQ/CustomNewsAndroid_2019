package com.example.rapha.swipeprototype2.categories;

import java.util.LinkedList;

public class CategoryDistribution {

    private LinkedList<NewsCategory> distribution;

    public CategoryDistribution(){}

    public void setDistribution(LinkedList<NewsCategory> distribution) {
        this.distribution = distribution;
    }

    public LinkedList<NewsCategory> getDistribution() {
        return distribution;
    }
}
