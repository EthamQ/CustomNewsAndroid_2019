package com.example.rapha.swipeprototype2;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class Logging {

    public static void logAmountOfArticles(MainActivity mainActivity){
        // ugly quick testing
        int amount0 = 0;
        int amount1 = 0;
        int amount2 = 0;
        int amount3 = 0;
        int amount4 = 0;
        for(int i = 0; i< mainActivity.newsArticlesToSwipe.size(); i++){
            Log.d("§§§", mainActivity.newsArticlesToSwipe.get(i).toString());
            if(mainActivity.newsArticlesToSwipe.get(i).newsCategory == 0){
                amount0++;
            }
            if(mainActivity.newsArticlesToSwipe.get(i).newsCategory == 1){
                amount1++;
            }
            if(mainActivity.newsArticlesToSwipe.get(i).newsCategory == 2){
                amount2++;
            }
            if(mainActivity.newsArticlesToSwipe.get(i).newsCategory == 3){
                amount3++;
            }
            if(mainActivity.newsArticlesToSwipe.get(i).newsCategory == 4){
                amount4++;
            }
        }
        Log.d("&&&", "0: " + amount0 + "\n"
                + "1: " + amount1 + "\n"
                + "2: " + amount2 + "\n"
                + "3: " + amount3 + "\n"
                + "4: " + amount4 + "\n");
    }

    public static void logArticlesLeft(MainActivity mainActivity){
        Log.d("AMOUNT", "Articles left: " + mainActivity.articlesArrayList.size());
    }
}
