package com.example.rapha.swipeprototype2.categories;

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
        this.categoryID = CATEGORY_ID;
    }
}
