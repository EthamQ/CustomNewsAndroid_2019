package com.example.rapha.swipeprototype2.newsCategories;

public class NewsCategory {

    private int rating = 5;
    private int categoryID;

    public String[] DEFAULT_QUERY_STRINGS_EN;
    public String[] USER_DETERMINED_QUERY_STRINGS_EN;

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

}
