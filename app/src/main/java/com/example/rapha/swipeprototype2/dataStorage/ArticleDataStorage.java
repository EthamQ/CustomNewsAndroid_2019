package com.example.rapha.swipeprototype2.dataStorage;

import com.example.rapha.swipeprototype2.models.NewsArticle;

import java.util.ArrayList;

public class ArticleDataStorage {

    // If you switch between fragments or activities store the previously loaded
    // articles here to immediately load and display when returning to the SwipeFragment.
    private static ArrayList<NewsArticle> temporaryStoredArticles = new ArrayList<>();

    public static void storeArticlesTemporarily(ArrayList<NewsArticle> articles){
        temporaryStoredArticles = articles;
    }

    public static ArrayList<NewsArticle> getTemporaryStoredArticles(){
        return temporaryStoredArticles;
    }

    public static boolean temporaryArticlesExist(){
        return temporaryStoredArticles.size() > 0;
    }
}
