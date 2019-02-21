package com.example.rapha.swipeprototype2.newsCategories;

public class NewsCategoryService {

    public static String getDisplayNameForCategory(int categoryId){
        final NewsCategoryContainer categories = new NewsCategoryContainer();
        for(NewsCategory category : categories.allCategories){
            if(categoryId == category.getCategoryID()){
                return category.displayName;
            }
        }
        return "";
    }
}
