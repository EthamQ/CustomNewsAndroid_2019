package com.example.rapha.swipeprototype2.dataStorage;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

public class DateOffsetDataStorage {

    public static String requestOffsetFinance;
    public static String requestOffsetFood;
    public static String requestOffsetPolitics;
    public static String requestOffsetMovie;
    public static String requestOffsetTechnology;

    public static void resetData(){
        requestOffsetFood = "";
        requestOffsetMovie = "";
        requestOffsetFinance = "";
        requestOffsetPolitics = "";
        requestOffsetTechnology = "";
    }

    public static void setOffsetForCategory(int categoryId, String requestOffset){
        if(categoryId == NewsCategoryContainer.Movie.CATEGORY_ID){
            requestOffsetMovie = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Finance.CATEGORY_ID){
            requestOffsetFinance = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Technology.CATEGORY_ID){
            requestOffsetTechnology = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Politics.CATEGORY_ID){
            requestOffsetPolitics = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Food.CATEGORY_ID){
            requestOffsetFood = requestOffset;
        }
    }

    public static String getOffsetForCategory(int categoryId){
        if(categoryId == NewsCategoryContainer.Movie.CATEGORY_ID){
            return requestOffsetMovie;
        }
        if(categoryId == NewsCategoryContainer.Finance.CATEGORY_ID){
            return requestOffsetFinance;
        }
        if(categoryId == NewsCategoryContainer.Technology.CATEGORY_ID){
            return requestOffsetTechnology;
        }
        if(categoryId == NewsCategoryContainer.Politics.CATEGORY_ID){
            return requestOffsetPolitics;
        }
        if(categoryId == NewsCategoryContainer.Food.CATEGORY_ID){
            return requestOffsetFood;
        }
        return "";
    }
}
