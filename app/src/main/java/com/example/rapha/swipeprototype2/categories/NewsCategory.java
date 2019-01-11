package com.example.rapha.swipeprototype2.categories;

public class NewsCategory {

    private int rating = 0;
    private int categoryID;
    public int amountInCurrentQuery = 0;

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
