package com.raphael.rapha.myNews.generalServices;

import android.util.Log;

import com.raphael.rapha.myNews.activities.mainActivity.MainActivity;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.ISwipeCard;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.LinkedList;
import java.util.List;

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
//        Log.d("AMOUNT", "Articles left: " + swipeFragment.swipeCardsList.size());
    }

    public static void logAllArticles(LinkedList<NewsArticle> newsArticles, String info){
        for (int i = 0; i < newsArticles.size(); i++){
            Log.d("ARTICLES", info + newsArticles.get(i).toString());
        }
    }

    public static void logKeyWordsFromDb(List<TopicRoomModel> keyWords){
        for(int i = 0; i < keyWords.size(); i++){
            Log.d("keywords", keyWords.get(i).toString());
        }
    }

    public static void logArticleModels(List<NewsArticleRoomModel> list, String key){
        for (int i = 0; i < list.size(); i++){
            Log.d(key, list.get(i).title);
        }
    }

    public static void logSwipeCards(List<ISwipeCard> swipeCardsList, String key){
        for(int i = 0; i < swipeCardsList.size(); i++){
            Log.d(key, swipeCardsList.get(i).toString());
        }
    }




}
