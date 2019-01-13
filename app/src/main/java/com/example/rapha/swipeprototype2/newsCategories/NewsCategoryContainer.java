package com.example.rapha.swipeprototype2.newsCategories;

import android.util.Log;

import java.util.LinkedList;

public class NewsCategoryContainer {

    // All available news categories.
    public final Finance finance;
    public final Food food;
    public final Movie movie;
    public final Politics politics;
    public final Technology technology;

    public NewsCategoryContainer(){
        finance = new Finance();
        food = new Food();
        movie = new Movie();
        politics = new Politics();
        technology = new Technology();
    }

    /**
     * Another class can fill the rating value of the different categories
     * and the needed total amount for every NewsCategory is calculated with the ratings.
     * @return Returns a LinkedList of NewsCategory objects that contain the information
     * how many of each NewsCategory should be requested.
     */
    public LinkedList<NewsCategory> getCategoryDistribution(){
        LinkedList<NewsCategory> categories = new LinkedList<>();
        int totalRating = this.finance.getRating()
                + this.food.getRating()
                + this.movie.getRating()
                + this.politics.getRating()
                + this.technology.getRating();
        Log.d("RATING", "Total rating: " + totalRating);

        finance.amountToRequestFromApi = NewsCategoryUtils.calculateDistribution(finance, totalRating);
        food.amountToRequestFromApi = NewsCategoryUtils.calculateDistribution(food, totalRating);
        movie.amountToRequestFromApi = NewsCategoryUtils.calculateDistribution(movie, totalRating);
        politics.amountToRequestFromApi = NewsCategoryUtils.calculateDistribution(politics, totalRating);
        technology.amountToRequestFromApi = NewsCategoryUtils.calculateDistribution(technology, totalRating);

        categories.add(finance);
        categories.add(food);
        categories.add(movie);
        categories.add(politics);
        categories.add(technology);

        return categories;
    }

}
