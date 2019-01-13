package com.example.rapha.swipeprototype2.newsCategories;

public class Food  extends NewsCategory{

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
