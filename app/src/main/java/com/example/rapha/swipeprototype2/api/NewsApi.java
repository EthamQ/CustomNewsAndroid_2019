package com.example.rapha.swipeprototype2.api;

import android.util.Log;

import com.example.rapha.swipeprototype2.api.apiQuery.NewsApiQueryBuilder;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.utils.HttpUtils;

import org.json.JSONObject;

import java.util.LinkedList;

public class NewsApi {

    public static final String URL_TOP_HEADLINES_NEWS_API = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";
    public static final String URL_ALL_NEWS_API = "https://newsapi.org/v2/everything?apiKey=";

	public NewsApi(){ }

	/**
	 * Receives a NewsApiQueryBuilder object, receives a query string from it and
	 * sends a http request to the api with the api url containing the query string.
	 * @param queryBuilder
	 * @return Returns the answer from the api as a List of news articles.
	 * @throws Exception
	 */
    public LinkedList<NewsArticle> queryNewsArticles(NewsApiQueryBuilder queryBuilder) throws Exception {
	    int newsCategory = queryBuilder.getNewsCategory();
	    queryBuilder.buildQuery();
	    String urlForApi = URL_ALL_NEWS_API + ApiKey.API_KEY_NEWS_API + queryBuilder.getQuery();
	    Log.d("##", urlForApi);
        JSONObject newsArticleJson = HttpUtils.httpGET(urlForApi);
        return NewsApiUtils.jsonToNewsArticleArray(newsArticleJson, newsCategory);
	}




}
