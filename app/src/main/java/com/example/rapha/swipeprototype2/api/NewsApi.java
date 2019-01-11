package com.example.rapha.swipeprototype2.api;

import android.util.Log;

import com.example.rapha.swipeprototype2.NewsArticle;
import com.example.rapha.swipeprototype2.utils.HttpUtils;

import org.json.JSONObject;

import java.util.LinkedList;

public class NewsApi {

    private static final String API_KEY_NEWS_API = "919137955d4a4543aaef87404795554d";
    public static final String URL_TOP_HEADLINES_NEWS_API = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";
    public static final String URL_ALL_NEWS_API = "https://newsapi.org/v2/everything?apiKey=";

	public NewsApi(){ }

    public LinkedList<NewsArticle> queryNewsArticles(NewsApiQueryBuilder queryBuilder) throws Exception {
	    queryBuilder.buildQuery();
	    String urlForApi = URL_ALL_NEWS_API + API_KEY_NEWS_API + queryBuilder.getQuery();
	    Log.d("##", urlForApi);
        JSONObject newsArticleJson = HttpUtils.httpGET(urlForApi);
        return NewsApiUtils.jsonToNewsArticleArray(newsArticleJson);
	}




}
