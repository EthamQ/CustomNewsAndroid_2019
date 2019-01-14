package com.example.rapha.swipeprototype2.newsCategories;

import android.util.Log;

import com.example.rapha.swipeprototype2.categoryDistribution.Distribution;

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
    public LinkedList<Distribution> getCategoryDistribution(){
        LinkedList<Distribution> distribution = new LinkedList<>();
        int totalRating = this.finance.getRating()
                + this.food.getRating()
                + this.movie.getRating()
                + this.politics.getRating()
                + this.technology.getRating();
        Log.d("RATING", "Total rating: " + totalRating);

        Distribution finance = new Distribution(this.finance.getCategoryID());
        finance.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.finance, totalRating);
        Distribution food = new Distribution(this.food.getCategoryID());
        food .amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.food , totalRating);
        Distribution movie = new Distribution(this.movie.getCategoryID());
        movie.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.movie, totalRating);
        Distribution politics = new Distribution(this.politics.getCategoryID());
        politics.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.politics, totalRating);
        Distribution technology = new Distribution(this.technology.getCategoryID());
        technology.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.technology, totalRating);

        distribution.add(finance);
        distribution.add(food);
        distribution.add(movie);
        distribution.add(politics);
        distribution.add(technology);

        return distribution;
    }

    //Below all the different news categories as classes

    public static class Finance extends NewsCategory {

        public static final int CATEGORY_ID = 2;

        public static final String[] FINANCE_QUERY_STRINGS_EN = {
                "bank",
                "money",
                "economy",
                "finance",
                "market"
        };

        public Finance(){
            this.setCategoryID(CATEGORY_ID);
        }
    }

    public static class Food  extends NewsCategory{

        public static final int CATEGORY_ID = 4;
        public static final String[] FOOD_QUERY_STRINGS_EN = {
                "pasta",
                "cook",
                "food",
                "meal",
                "delicious"
        };

        public Food(){
            this.setCategoryID(CATEGORY_ID);
        }
    }

    public static class Movie  extends NewsCategory{

        public static final int CATEGORY_ID = 3;
        public static final String[] MOVIE_QUERY_STRINGS_EN = {
                "movie",
                "cinema",
                "blockbuster",
                "television",
                "market"
        };

        public Movie(){
            this.setCategoryID(CATEGORY_ID);
        }
    }

    public static class Politics extends NewsCategory{

        public static final int CATEGORY_ID = 0;
        public static final String[] POLITICS_QUERY_STRINGS_EN = {
                "Trump",
                "Putin",
                "Politic",
                "War",
                "Troops"
        };

        public Politics(){
            this.setCategoryID(CATEGORY_ID);
        }
    }

    public static class Technology extends NewsCategory{

        public static final int CATEGORY_ID = 1;
        public static final String[] TECHNOLOGY_QUERY_STRINGS_EN = {
                "Apple",
                "Phone",
                "technology",
                "computer",
                "hacker"
        };

        public Technology(){
            this.setCategoryID(CATEGORY_ID);
        }
    }
}
