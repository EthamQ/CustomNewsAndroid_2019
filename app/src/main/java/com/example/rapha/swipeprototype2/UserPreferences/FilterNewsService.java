package com.example.rapha.swipeprototype2.UserPreferences;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rapha.swipeprototype2.MainActivity;
import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.categories.Categories;
import com.example.rapha.swipeprototype2.categories.CategoryDistribution;
import com.example.rapha.swipeprototype2.categories.NewsCategory;
import com.example.rapha.swipeprototype2.roomDatabase.IUserPreferenceDao;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.LinkedList;

public class FilterNewsService{

    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    public static CategoryDistribution getCategoryDistribution(MainActivity mainActivity) throws Exception{
        Log.d("ää", "inside getCategoryDistribution");
        UserPreference userPreference = new UserPreference();
        FilterNewsUtils.retrieveAndSetCategoryRating(userPreference, mainActivity);
        //Log.d("===", "Preference after setting: " + userPreference);
        LinkedList<NewsCategory> distribution = userPreference.getCategories().getCategoryDistribution();
        CategoryDistribution categoryDistribution = new CategoryDistribution();
        categoryDistribution.setDistribution(distribution);
        for(int i = 0; i < distribution.size(); i++){
            Log.d("**", "amount in query: " + distribution.get(i).amountInCurrentQuery);
            Log.d("**", "category id: " + distribution.get(i).getCategoryID());
        }
        return categoryDistribution;
    }


    public static void callbackFunction(UserPreference userPreference, MainActivity mainActivity)throws Exception {
        Log.d("ää", "inside callbackFunction");
        Log.d("===", "in callbackFunction()");
        Log.d("===", "Preference after setting: " + userPreference);
        LinkedList<NewsCategory> distribution = userPreference.getCategories().getCategoryDistribution();
        CategoryDistribution categoryDistribution = new CategoryDistribution();
        categoryDistribution.setDistribution(distribution);
        for(int i = 0; i < distribution.size(); i++){
            Log.d("**", "amount in query: " + distribution.get(i).amountInCurrentQuery);
            Log.d("**", "category id: " + distribution.get(i).getCategoryID());
        }
        Wrapper w = new Wrapper(categoryDistribution, mainActivity);
        new FilterAsyncTask().execute(w);
        //ApiService.getAllArticlesNewsApiCallback(categoryDistribution, mainActivity);
    }

    private static class Wrapper{

        CategoryDistribution categoryDistribution;
        MainActivity mainActivity;

        public Wrapper(CategoryDistribution categoryDistribution, MainActivity mainActivity) {
            this.categoryDistribution = categoryDistribution;
            this.mainActivity = mainActivity;
        }
    }

    private static class FilterAsyncTask extends AsyncTask<Wrapper, MainActivity, Void> {


        private FilterAsyncTask(){

        }
        @Override
        protected Void doInBackground(Wrapper... wrappers) {
            try {
                ApiService.getAllArticlesNewsApiCallback(wrappers[0].categoryDistribution, wrappers[0].mainActivity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
