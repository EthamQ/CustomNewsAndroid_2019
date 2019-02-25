package com.raphael.rapha.myNews.api;

import android.util.Log;

import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class NewsApiHelper {

    /**
     * Receives the entire JSON containing news articles from the NewsApi.
     * It then converts all the news articles in NewsArticle objects and returns them
     * as an NewsArticle[] array.
     * @param newsArticlesJson
     * @return
     * @throws Exception
     */
    public static LinkedList<NewsArticle> jsonToNewsArticleArray(JSONObject newsArticlesJson, int newsCategory, int languageId)throws Exception{
        Log.d("displayy", "jsonToNewsArticleArray: " + languageId);
        String jsonArticleKey = "articles";
        int numberOfArticles = newsArticlesJson.getJSONArray(jsonArticleKey).length();
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        for(int i = 0; i < numberOfArticles - 1; i++) {
            JSONObject articleJson = JSONUtils.getArrayEntryFromJson(newsArticlesJson, jsonArticleKey, i);
            NewsArticle newsArticle = new NewsArticle();
            newsArticle.setArticleProperties(
                    articleJson,
                    newsCategory,
                    LanguageSettingsService.getLanguageIdAsString(languageId)
            );
            if(newsArticle != null){
                newsArticles.add(newsArticle);
            }
        }
        return newsArticles;
    }

    /**
     * Helper function.
     * Returns the "totalResults" property from the original JSON sent by the NewsApi.
     * If it doesn't exist it returns 0.
     * @param newsArticlesJson
     * @return
     */
    private static int getTotalResults(JSONObject newsArticlesJson){
        int totalNumber = 0;
        try {
            totalNumber = newsArticlesJson.getInt("totalResults");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return totalNumber;
    }
}