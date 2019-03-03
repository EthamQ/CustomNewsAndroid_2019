package com.raphael.rapha.myNews.api;

import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.generalServices.JSONService;

import org.json.JSONObject;

import java.util.LinkedList;

public class NewsApiHelper {

    public static final int NEWS_CATEGORY_NOT_SET = -1;
    public static final int LANGUAGE_ID_NOT_SET = -1;


    /**
     * Receives the entire JSON containing news articles from the NewsApi.
     * It then converts all the news articles in NewsArticle objects and returns them
     * as an NewsArticle[] array.
     * @param newsArticlesJson
     * @return
     * @throws Exception
     */
    public static LinkedList<NewsArticle> jsonToNewsArticleArray(JSONObject newsArticlesJson, int newsCategory, int languageId)throws Exception{
        String jsonArticleKey = "articles";
        int numberOfArticles = newsArticlesJson.getJSONArray(jsonArticleKey).length();
        LinkedList<NewsArticle> newsArticles = new LinkedList<>();
        for(int i = 0; i < numberOfArticles - 1; i++) {
            JSONObject articleJson = JSONService.getArrayEntryFromJson(newsArticlesJson, jsonArticleKey, i);
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

}
