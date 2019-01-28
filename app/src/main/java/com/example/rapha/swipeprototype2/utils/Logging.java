package com.example.rapha.swipeprototype2.utils;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.models.NewsArticle;

import java.util.LinkedList;

public class Logging {

    public static void logAmountOfArticles(MainActivity mainActivity){
        // ugly quick testing
//        int amount0 = 0;
//        int amount1 = 0;
//        int amount2 = 0;
//        int amount3 = 0;
//        int amount4 = 0;
//        for(int i = 0; i< swipeFragment.apiArticlesToAdd.size(); i++){
//            Log.d("§§§", swipeFragment.apiArticlesToAdd.get(i).toString());
//            if(swipeFragment.apiArticlesToAdd.get(i).newsCategory == 0){
//                amount0++;
//            }
//            if(swipeFragment.apiArticlesToAdd.get(i).newsCategory == 1){
//                amount1++;
//            }
//            if(swipeFragment.apiArticlesToAdd.get(i).newsCategory == 2){
//                amount2++;
//            }
//            if(swipeFragment.apiArticlesToAdd.get(i).newsCategory == 3){
//                amount3++;
//            }
//            if(swipeFragment.apiArticlesToAdd.get(i).newsCategory == 4){
//                amount4++;
//            }
//        }
//        Log.d("&&&", "0: " + amount0 + "\n"
//                + "1: " + amount1 + "\n"
//                + "2: " + amount2 + "\n"
//                + "3: " + amount3 + "\n"
//                + "4: " + amount4 + "\n");
    }

    public static void logArticlesLeft(MainActivity mainActivity){
//        Log.d("AMOUNT", "Articles left: " + swipeFragment.articlesArrayList.size());
    }

    public static void logAllArticles(LinkedList<NewsArticle> newsArticles, String info){
        for (int i = 0; i < newsArticles.size(); i++){
            Log.d("ARTICLES", info + newsArticles.get(i).toString());
        }
    }


}
