package com.raphael.rapha.myNews.temporaryDataStorage;

import android.util.Log;

import com.raphael.rapha.myNews.newsCategories.NewsCategoryContainer;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;

import java.util.List;

//TODO: really bad code needs refactoring!
public class DateOffsetDataStorage {

    public static String requestOffsetFinance;
    public static String requestOffsetFood;
    public static String requestOffsetPolitics;
    public static String requestOffsetMovie;
    public static String requestOffsetTechnology;
    public static String requestOffsetSport;
    public static String requestOffsetHealth;
    public static String requestOffsetCrime;
    public static String requestOffsetScience;


    public static void resetData(){
        requestOffsetFood = "";
        requestOffsetMovie = "";
        requestOffsetFinance = "";
        requestOffsetPolitics = "";
        requestOffsetTechnology = "";
        requestOffsetSport = "";
        requestOffsetHealth = "";
        requestOffsetCrime = "";
        requestOffsetScience = "";
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
        if(categoryId == NewsCategoryContainer.Science.CATEGORY_ID){
            requestOffsetScience = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Sport.CATEGORY_ID){
            requestOffsetSport = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Health.CATEGORY_ID){
            requestOffsetHealth = requestOffset;
        }
        if(categoryId == NewsCategoryContainer.Crime.CATEGORY_ID){
            requestOffsetCrime = requestOffset;
        }
    }

    public static void setDateOffsets(List<RequestOffsetRoomModel> offsets){
        for(int i = 0; i < offsets.size(); i++){
            // Offset data is retrieved when the http requests are build.
            setOffsetForCategory(
                    offsets.get(i).categoryId,
                    offsets.get(i).requestOffset
            );
        }
    }

    public static String getDateOffsetForCategory(int categoryId){
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
        if(categoryId == NewsCategoryContainer.Science.CATEGORY_ID){
            return requestOffsetScience;
        }
        if(categoryId == NewsCategoryContainer.Sport.CATEGORY_ID){
            return requestOffsetSport;
        }
        if(categoryId == NewsCategoryContainer.Health.CATEGORY_ID){
            return requestOffsetHealth;
        }
        if(categoryId == NewsCategoryContainer.Crime.CATEGORY_ID){
            return requestOffsetCrime;
        }
        return "";
    }
}
