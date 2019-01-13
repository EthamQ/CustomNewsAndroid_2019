package com.example.rapha.swipeprototype2.newsCategories;

public class Politics extends NewsCategory{

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
