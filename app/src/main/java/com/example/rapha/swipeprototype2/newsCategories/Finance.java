package com.example.rapha.swipeprototype2.newsCategories;

public class Finance extends NewsCategory {

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
