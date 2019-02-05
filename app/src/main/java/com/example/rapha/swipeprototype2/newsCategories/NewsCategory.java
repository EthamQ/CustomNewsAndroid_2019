package com.example.rapha.swipeprototype2.newsCategories;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class NewsCategory {

    private int rating = 3;
    private int categoryID;
    public String[] QUERY_STRINGS_EN;
    public String[] QUERY_STRINGS_GER;
    public String[] QUERY_STRINGS_FR;
    public String[] QUERY_STRINGS_RU;
    public String[] DEFAULT_QUERY_STRINGS_EN;
    public String displayName;


    public NewsCategory(){}

    public int getCategoryID() { return categoryID; }

    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }

    public String[] getQueryStrings_EN(SwipeFragment swipeFragment){ return QUERY_STRINGS_EN;}




}
