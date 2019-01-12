package com.example.rapha.swipeprototype2.categories;

public class NewsCategory {

    private int rating = 1;
    private int categoryID;
    public int amountInCurrentQuery = 0; //how many results for this category should the query return?

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
