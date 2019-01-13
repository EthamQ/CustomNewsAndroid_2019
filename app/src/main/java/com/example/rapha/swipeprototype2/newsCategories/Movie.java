package com.example.rapha.swipeprototype2.newsCategories;

public class Movie  extends NewsCategory{

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
