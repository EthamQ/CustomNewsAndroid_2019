package com.raphael.rapha.myNews.temporaryDataStorage;

import android.util.Log;

import com.raphael.rapha.myNews.swipeCardContent.ISwipeCard;

import java.util.ArrayList;

public class ArticleDataStorage {

    // If you switch between fragments or activities store the previously loaded
    // articles here to immediately load and display when returning to the SwipeFragment.
    private static ArrayList<ISwipeCard> temporaryStoredArticles = new ArrayList<>();
    private static ArrayList<ISwipeCard> backUpArticlesIfError = new ArrayList<>();

    public static ArrayList<ISwipeCard> getBackUpArticlesIfError() {
        return backUpArticlesIfError;
    }

    public static void setBackUpArticlesIfError(ArrayList<ISwipeCard> backUpArticlesIfError) {
        ArticleDataStorage.backUpArticlesIfError.clear();
        ArticleDataStorage.backUpArticlesIfError.addAll(backUpArticlesIfError);
    }

    public static void storeArticlesTemporarily(ArrayList<ISwipeCard> articles){
        temporaryStoredArticles.clear();
        temporaryStoredArticles.addAll(articles);
    }

    public static void clearData(){
        temporaryStoredArticles.clear();
    }

    public static ArrayList<ISwipeCard> getTemporaryStoredArticles(){
        return temporaryStoredArticles;
    }

    public static boolean temporaryArticlesExist(){
        return temporaryStoredArticles.size() > 0;
    }

}
