package com.raphael.rapha.myNews.temporaryDataStorage;

import android.util.Log;

import com.raphael.rapha.myNews.newsCategories.NewsCategoryContainer;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;

import java.util.List;

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
        Log.d("newswipe", "SET OFFSET IN STORAGE: cat: " + categoryId + ", offset: " +requestOffset);
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

    public static void setDateOffsets(List<RequestOffsetRoomModel> offsets){
        for(int i = 0; i < offsets.size(); i++){
            // Offset data is retrieved when the http requests are build.
            setOffsetForCategory(
                    offsets.get(i).categoryId,
                    offsets.get(i).requestOffset
            );
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
