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

    public final LinkedList<NewsCategory> allCategories = new LinkedList<>();

    public NewsCategoryContainer(){
        finance = new Finance();
        food = new Food();
        movie = new Movie();
        politics = new Politics();
        technology = new Technology();

        allCategories.add(finance);
        allCategories.add(food);
        allCategories.add(movie);
        allCategories.add(politics);
        allCategories.add(technology);
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
        finance.amountToFetchFromApi = NewsCategoryContainerUtils.calculateDistribution(this.finance, totalRating);
        food .amountToFetchFromApi = NewsCategoryContainerUtils.calculateDistribution(this.food , totalRating);
        movie.amountToFetchFromApi = NewsCategoryContainerUtils.calculateDistribution(this.movie, totalRating);
        politics.amountToFetchFromApi = NewsCategoryContainerUtils.calculateDistribution(this.politics, totalRating);
        technology.amountToFetchFromApi = NewsCategoryContainerUtils.calculateDistribution(this.technology, totalRating);

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

        public static final String[] FINANCE_QUERY_STRINGS_EN = new String[]{
                "Economy", "Finance", "stock market", "Wages", "Investment", "Jobs", "Taxes",
                "insurance"
        };

        public static final String[] FINANCE_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "bank", "money", "market", "refund", "fund",
                "bills", "customer", "employer", "employee"
        };

        public Finance(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Finance";
            this.DEFAULT_QUERY_STRINGS_EN = FINANCE_DEFAULT_QUERY_STRINGS_EN;
            this.USER_DETERMINED_QUERY_STRINGS_EN = FINANCE_QUERY_STRINGS_EN;
        }
    }

    public static class Food  extends NewsCategory{

        public static final int CATEGORY_ID = 4;

        public static final String[] FOOD_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "pasta", "cook", "food", "meal", "delicious",
                    "recipe", "vegetables", "tomato", "dinner", "pan"
        };

        public Food(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Food";
            this.DEFAULT_QUERY_STRINGS_EN = FOOD_DEFAULT_QUERY_STRINGS_EN;
            this.USER_DETERMINED_QUERY_STRINGS_EN = new String[0];
        }
    }

    public static class Movie  extends NewsCategory{

        public static final int CATEGORY_ID = 3;

        public static final String[] MOVIE_QUERY_STRINGS_EN = new String[] {
                "Movie", "Cinema", "Television"
        };

        public static final String[] MOVIE_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "blockbuster", "tv", "actor"
        };

        public Movie(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Movie";
            this.USER_DETERMINED_QUERY_STRINGS_EN = MOVIE_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = MOVIE_DEFAULT_QUERY_STRINGS_EN;
        }
    }

    public static class Politics extends NewsCategory{

        public static final int CATEGORY_ID = 0;

        public static final String[] POLITIC_QUERY_STRINGS_EN = new String[] {
                "Trump", "Putin", "Merkel", "Macron", "Russia",
                "USA", "Syria", "ISIS", "War", "Weapons", "Erdogan", "Foreign policy"
        };
        public static final String[] POLITIC_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "politic", "president", "white house"
        };

        public Politics(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Politics";
            this.USER_DETERMINED_QUERY_STRINGS_EN = POLITIC_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = POLITIC_DEFAULT_QUERY_STRINGS_EN;
        }
    }

    public static class Technology extends NewsCategory{

        public static final int CATEGORY_ID = 1;

        public static final String[] TECHNOLOGY_QUERY_STRINGS_EN = new String[] {
                "Apple", "Smartphone", "Apps", "Android", "Programming", "Machine Learning",
                "AI Artificial Intelligence", "Google", "Java", "C++", "Python", "Webdesign"
        };

        public static final String[] TECHNOLOGY_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "technology", "computer", "hacker", "mac"
        };

        public Technology(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Technology";
            this.USER_DETERMINED_QUERY_STRINGS_EN = TECHNOLOGY_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = TECHNOLOGY_DEFAULT_QUERY_STRINGS_EN;
        }

    }
}
