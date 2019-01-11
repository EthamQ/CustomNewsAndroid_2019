package com.example.rapha.swipeprototype2.categories;

import android.util.Log;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.UserPreferences.FilterNewsService;
import com.example.rapha.swipeprototype2.UserPreferences.UserPreference;

import java.util.LinkedList;

public class Categories {

    private static final int TOTAL_CATEGORIES = 5;
    public final Finance finance;
    public final Food food;
    public final Movie movie;
    public final Politics politics;
    public final Technology technology;

    public Categories(){
        finance = new Finance();
        food = new Food();
        movie = new Movie();
        politics = new Politics();
        technology = new Technology();
    }

    public LinkedList<NewsCategory> getCategoryDistribution(){
        LinkedList<NewsCategory> categories = new LinkedList<>();
        int totalRating = this.finance.getRating()
                + this.food.getRating()
                + this.movie.getRating()
                + this.politics.getRating()
                + this.technology.getRating();
        Log.d("**", "Total rating: " + totalRating);

        finance.amountInCurrentQuery = CategoryUtils.calculateDistribution(finance, totalRating);
        food.amountInCurrentQuery = CategoryUtils.calculateDistribution(food, totalRating);
        movie.amountInCurrentQuery = CategoryUtils.calculateDistribution(movie, totalRating);
        politics.amountInCurrentQuery = CategoryUtils.calculateDistribution(politics, totalRating);
        technology.amountInCurrentQuery = CategoryUtils.calculateDistribution(technology, totalRating);

        categories.add(finance);
        categories.add(food);
        categories.add(movie);
        categories.add(politics);
        categories.add(technology);

        return categories;
    }

}
