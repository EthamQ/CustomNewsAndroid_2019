package com.example.rapha.swipeprototype2.categories;

public abstract class NewsCategory {

    private int rating = 0;
    public int amountInCurrentQuery = 0;
    public int categoryID;

    public NewsCategory(){}

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }
}
