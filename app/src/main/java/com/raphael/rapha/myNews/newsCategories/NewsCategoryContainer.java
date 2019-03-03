package com.raphael.rapha.myNews.newsCategories;

import com.raphael.rapha.myNews.categoryDistribution.Distribution;

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
    public final Sport sport;
    public final Health health;
    public final Crime crime;
    public final Science science;

    public final LinkedList<NewsCategory> allCategories = new LinkedList<>();

    public NewsCategoryContainer(){
        finance = new Finance();
        food = new Food();
        movie = new Movie();
        politics = new Politics();
        technology = new Technology();
        sport = new Sport();
        health = new Health();
        crime = new Crime();
        science = new Science();

        allCategories.add(finance);
        allCategories.add(food);
        allCategories.add(movie);
        allCategories.add(politics);
        allCategories.add(technology);
        allCategories.add(sport);
        allCategories.add(health);
        allCategories.add(crime);
        allCategories.add(science);
    }

    /**
     * Another class can fill the rating value of the different categories
     * and the needed total amount for every NewsCategory is calculated with the ratings.
     * @return Returns a LinkedList of NewsCategory objects that contain the information
     * how many of each NewsCategory should be requested.
     */
    public LinkedList<Distribution> getCategoryDistribution(){
        LinkedList<Distribution> distributionList = new LinkedList<>();

        int totalRating = 0;
        for(NewsCategory category: allCategories){
            totalRating += category.getRating();
        }

        for(NewsCategory category: allCategories){
            Distribution distribution = new Distribution(category.getNewsCategoryID());
            distribution.calculateAmountToFetchFromApi(category, totalRating);
            distributionList.add(distribution);
        }

        return distributionList;
    }

    public static NewsCategory getCategory(int categoryId){
            switch(categoryId){
                case Politics.CATEGORY_ID: return new Politics();
                case Finance.CATEGORY_ID: return new Finance();
                case Movie.CATEGORY_ID: return new Movie();
                case Food.CATEGORY_ID: return new Food();
                case Technology.CATEGORY_ID: return new Technology();
                case Sport.CATEGORY_ID: return new Sport();
                case Health.CATEGORY_ID: return new Health();
                case Crime.CATEGORY_ID: return new Crime();
                case Science.CATEGORY_ID: return new Science();
                default: return new NewsCategory();
            }
    }

    //Below all the different news categories as classes
    public static class Finance extends NewsCategory {

        public static final int CATEGORY_ID = 2;

        public static final String[] FINANCE_QUERY_STRINGS_EN = new String[]{
                "Startup", "Economy", "Finance", "Stock market", "Wages", "Investment", "Jobs", "Taxes",
                "Insurance",
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

        public static final String[] FOOD_QUERY_STRINGS_EN = new String[] {
                "Cooking", "Recipes", "Restaurant",
        };

        public static final String[] FOOD_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "pasta", "food", "meal", "delicious",
                "vegetables", "tomato", "dinner", "pan", "kitchen"
        };

        public Food(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Food";
            this.DEFAULT_QUERY_STRINGS_EN = FOOD_DEFAULT_QUERY_STRINGS_EN;
            this.USER_DETERMINED_QUERY_STRINGS_EN = FOOD_QUERY_STRINGS_EN;
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
            this.displayName = "Movies";
            this.USER_DETERMINED_QUERY_STRINGS_EN = MOVIE_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = MOVIE_DEFAULT_QUERY_STRINGS_EN;
        }
    }

    public static class Politics extends NewsCategory{

        public static final int CATEGORY_ID = 0;

        public static final String[] POLITIC_QUERY_STRINGS_EN = new String[] {
                "Surveillance", "Trump", "Putin", "Macron", "Russia",
                "USA", "Syria", "ISIS", "War", "Weapons", "Erdogan", "Foreign policy", "German politics",
                "Europe", "Sanctions", "Law", "Communism", "Capitalism", "Socialism", "Dictator",
                "Democracy", "Corruption"

        };
        public static final String[] POLITIC_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "politic", "president", "white house", "treaty", "government", "parliament", "regime",
                "negotiation", "diplomat"
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
                "Youtube", "Apple", "Smartphone", "Apps", "Android", "Programming", "Machine Learning",
                "AI Artificial Intelligence", "Google", "Spotify", "Facebook", "Webdesign", "Drones"
        };

        public static final String[] TECHNOLOGY_DEFAULT_QUERY_STRINGS_EN = new String[] {
            "technology", "computer", "hacker", "mac", "hardware", "software", "screen",
                "application"
        };

        public Technology(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Technology";
            this.USER_DETERMINED_QUERY_STRINGS_EN = TECHNOLOGY_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = TECHNOLOGY_DEFAULT_QUERY_STRINGS_EN;
        }

    }

    public static class Sport extends NewsCategory{

        public static final int CATEGORY_ID = 5;

        public static final String[] SPORT_QUERY_STRINGS_EN = new String[] {
                "Soccer", "Football", "Swimming", "Boxing", "MMA", "Tennis", "Hockey", "Bike", "Hiking",
                "Rugby", "Baseball"
        };

        public static final String[] SPORT_DEFAULT_QUERY_STRINGS_EN = new String[] {
                "sport", "score", "tournament", "championship", "race", "athlete", "olympic"
        };

        public Sport(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Sport";
            this.USER_DETERMINED_QUERY_STRINGS_EN = SPORT_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = SPORT_DEFAULT_QUERY_STRINGS_EN;
        }
    }

    public static class Health extends NewsCategory{

        public static final int CATEGORY_ID = 6;

        public static final String[] HEALTH_QUERY_STRINGS_EN = new String[] {
                "Healthcare", "Fitness", "Disease", "Virus",
                "Nutrition", "Diet", "Vaccination", "Mental illness", "Workout"
        };

        public static final String[] HEALTH_DEFAULT_QUERY_STRINGS_EN = new String[] {
                "health", "doctor", "exercise",
                "hospital", "pharmacy", "medicare", "prevention",
                "disorder", "cancer", "heart"
        };

        public Health(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Health";
            this.USER_DETERMINED_QUERY_STRINGS_EN = HEALTH_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = HEALTH_DEFAULT_QUERY_STRINGS_EN;
        }
    }

    public static class Crime extends NewsCategory{

        public static final int CATEGORY_ID = 7;

        public static final String[] CRIME_QUERY_STRINGS_EN = new String[] {
                "Murder", "Police", "Prison", "Terror", "Mafia", "Death"
        };

        public static final String[] CRIME_DEFAULT_QUERY_STRINGS_EN = new String[] {
                "kill", "crime", "choke", "steal", "shoot", "felony",
                "rape", "fraud", "misdemeanour", "robbery", "offence", "victim", "kidnap",
                "plagiarize", "violent", "gang", "stab"
        };

        public Crime(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Crime";
            this.USER_DETERMINED_QUERY_STRINGS_EN = CRIME_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = CRIME_DEFAULT_QUERY_STRINGS_EN;
        }
    }

    public static class Science extends NewsCategory{

        public static final int CATEGORY_ID = 8;

        public static final String[] SCIENCE_QUERY_STRINGS_EN = new String[]{
                "Physics", "Climate change", "Biology", "Chemistry", "Genetics",
                "Mathematics", "Neuroscience", "Engineering", "Cryptography"
        };

        public static final String[] SCIENCE_DEFAULT_QUERY_STRINGS_EN = new String[] {
                "science", "scientist", "study", "atom", "laboratory", "radioactiv", "quantum"
        };

        public Science(){
            this.setCategoryID(CATEGORY_ID);
            this.displayName = "Science";
            this.USER_DETERMINED_QUERY_STRINGS_EN = SCIENCE_QUERY_STRINGS_EN;
            this.DEFAULT_QUERY_STRINGS_EN = SCIENCE_DEFAULT_QUERY_STRINGS_EN;
        }
    }

}
