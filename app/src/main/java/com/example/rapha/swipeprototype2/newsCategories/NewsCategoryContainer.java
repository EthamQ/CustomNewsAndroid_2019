package com.example.rapha.swipeprototype2.newsCategories;

import android.util.Log;

import com.example.rapha.swipeprototype2.categoryDistribution.Distribution;

import java.util.LinkedList;

/**
 * Contains an instance of every news category we have.
 * Another class can set rating values for each available category and receive the
 * correct amount for every category that it should request from the api.
 */
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
        LinkedList<Distribution> distributionList = new LinkedList<>();
        int totalRating = this.finance.getRating()
                + this.food.getRating()
                + this.movie.getRating()
                + this.politics.getRating()
                + this.technology.getRating();
        Log.d("RATING", "Total rating: " + totalRating);

        // Instantiate a Distribution object for every category.
        Distribution finance = new Distribution(this.finance.getCategoryID());
        Distribution food = new Distribution(this.food.getCategoryID());
        Distribution movie = new Distribution(this.movie.getCategoryID());
        Distribution politics = new Distribution(this.politics.getCategoryID());
        Distribution technology = new Distribution(this.technology.getCategoryID());

        // Set the correct amount to be requested for every Distribution object.
        finance.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.finance, totalRating);
        food .amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.food , totalRating);
        movie.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.movie, totalRating);
        politics.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.politics, totalRating);
        technology.amountToFetchFromApi = NewsCategoryUtils.calculateDistribution(this.technology, totalRating);

        // Add every distribution object to the LinkedList.
        distributionList.add(finance);
        distributionList.add(food);
        distributionList.add(movie);
        distributionList.add(politics);
        distributionList.add(technology);

        return distributionList;
    }

    public static NewsCategory getCategory(int categoryId){
            switch(categoryId){
                case Politics.CATEGORY_ID: return new Politics();
                case Finance.CATEGORY_ID: return new Finance();
                case Movie.CATEGORY_ID: return new Movie();
                case Food.CATEGORY_ID: return new Food();
                case Technology.CATEGORY_ID: return new Technology();
                default: return new NewsCategory();
            }

    }


    //Below all the different news categories as classes
    public static class Finance extends NewsCategory {

        public static final int CATEGORY_ID = 2;


        public Finance(){
            this.setCategoryID(CATEGORY_ID);
            this.QUERY_STRINGS_EN = new String[]{
                    "bank", "money", "economy", "finance", "market",
                    "refund", "fund", "wage", "investment", "jobs", "tax",
                    "bills", "customer", "employer", "employee", "insurance"
            };
            this.QUERY_STRINGS_GER = new String[]{
                    "bank", "money", "economy", "finance", "market",
                    "refund", "fund", "wage", "investment", "jobs", "tax",
                    "bills", "customer", "employer", "employee", "insurance"
            };
            this.QUERY_STRINGS_FR = new String[]{
                    "bank", "money", "economy", "finance", "market",
                    "refund", "fund", "wage", "investment", "jobs", "tax",
                    "bills", "customer", "employer", "employee", "insurance"
            };
            this.QUERY_STRINGS_RU = new String[]{
                    "bank", "money", "economy", "finance", "market",
                    "refund", "fund", "wage", "investment", "jobs", "tax",
                    "bills", "customer", "employer", "employee", "insurance"
            };
        }
    }

    public static class Food  extends NewsCategory{

        public static final int CATEGORY_ID = 4;

        public Food(){
            this.setCategoryID(CATEGORY_ID);
            this.QUERY_STRINGS_EN = new String[] {
                    "pasta", "cook", "food", "meal", "delicious",
                    "recipe", "vegetables", "tomato", "pasta", "dinner"
            };
            this.QUERY_STRINGS_GER = new String[] {
                    "pasta", "cook", "food", "meal", "delicious",
                    "recipe", "vegetables", "tomato", "pasta", "dinner"
            };
            this.QUERY_STRINGS_FR = new String[] {
                    "pasta", "cook", "food", "meal", "delicious",
                    "recipe", "vegetables", "tomato", "pasta", "dinner"
            };
            this.QUERY_STRINGS_RU = new String[] {
                    "pasta", "cook", "food", "meal", "delicious",
                    "recipe", "vegetables", "tomato", "pasta", "dinner"
            };
        }
    }

    public static class Movie  extends NewsCategory{

        public static final int CATEGORY_ID = 3;

        public Movie(){
            this.setCategoryID(CATEGORY_ID);
            this.QUERY_STRINGS_EN = new String[] {
                    "movie", "cinema", "blockbuster", "television", "tv",
            };
            this.QUERY_STRINGS_GER = new String[] {
                    "movie", "cinema", "blockbuster", "television", "tv",
            };
            this.QUERY_STRINGS_FR = new String[] {
                    "movie", "cinema", "blockbuster", "television", "tv",
            };
            this.QUERY_STRINGS_RU = new String[] {
                    "movie", "cinema", "blockbuster", "television", "tv",
            };
        }
    }

    public static class Politics extends NewsCategory{

        public static final int CATEGORY_ID = 0;

        public Politics(){
            this.setCategoryID(CATEGORY_ID);
            this.QUERY_STRINGS_EN = new String[] {
                    "Trump", "Putin", "Politic", "War", "Troops",
                    "president", "syria", "white house"
            };
            this.QUERY_STRINGS_GER = new String[] {
                    "Trump", "Putin", "Politic", "War", "Troops",
                    "president", "syria", "white house"
            };
            this.QUERY_STRINGS_FR = new String[] {
                    "Trump", "Putin", "Politic", "War", "Troops",
                    "president", "syria", "white house"
            };
            this.QUERY_STRINGS_RU = new String[] {
                    "Trump", "Putin", "Politic", "War", "Troops",
                    "president", "syria", "white house"
            };
        }
    }

    public static class Technology extends NewsCategory{

        public static final int CATEGORY_ID = 1;

        public Technology(){
            this.setCategoryID(CATEGORY_ID);
            this.QUERY_STRINGS_EN = new String[] {
                    "Apple", "Phone", "technology", "computer", "hacker",
                    "streaming", "app", "mac"
            };
            this.QUERY_STRINGS_GER = new String[] {
                    "Apple", "Phone", "technology", "computer", "hacker",
                    "streaming", "app", "mac"
            };
            this.QUERY_STRINGS_RU = new String[] {
                    "Apple", "Phone", "technology", "computer", "hacker",
                    "streaming", "app", "mac"
            };
            this.QUERY_STRINGS_FR = new String[] {
                    "Apple", "Phone", "technology", "computer", "hacker",
                    "streaming", "app", "mac"
            };
        }
    }
}
