package com.raphael.rapha.myNews.api;

import android.util.Log;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.api.apiKey.ApiKey;
import com.raphael.rapha.myNews.api.apiQuery.IQueryListener;
import com.raphael.rapha.myNews.api.apiQuery.NewsApiQueryBuilder;
import com.raphael.rapha.myNews.http.HttpRequestInfo;
import com.raphael.rapha.myNews.loading.LoadingService;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.http.HttpRequest;
import com.raphael.rapha.myNews.http.HttpUtils;

import org.json.JSONObject;

import java.util.LinkedList;

public class NewsApi {

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
	    int languageId = queryBuilder.getLanguageId();
	    queryBuilder.buildQuery();
	    String urlForApi = URL_ALL_NEWS_API + ApiKey.getApiKey() + queryBuilder.getQuery();
		IQueryListener queryListener = queryBuilder.getQueryListener();
		HttpRequest httpRequest = new HttpRequest(queryBuilder.getHttpRequester(), new HttpRequestInfo());
		JSONObject newsArticleJson = HttpUtils.httpGET(urlForApi, httpRequest);
        LoadingService.answerReceived();
		queryListener.updateLoadingText(LoadingService.getNumberOfAnswersReceivedText());
        return NewsApiHelper.jsonToNewsArticleArray(newsArticleJson, newsCategory, languageId);
	}

	/**
	 * Does the same as queryNewsArticles but doesn't return the articles.
	 * It passes the class calling this function to another function.
	 * This function will then call a callback function in this class with the result from the api as
	 * an input parameter.
	 * @param httpRequest Containing the class calling this function and if necessary additional data
	 * @param queryBuilder
	 * @throws Exception
	 */
	public void queryNewsArticlesAsync(HttpRequest httpRequest, NewsApiQueryBuilder queryBuilder) {
		queryBuilder.buildQuery();
		String urlForApi = URL_ALL_NEWS_API + ApiKey.getApiKey() + queryBuilder.getQuery();
		HttpUtils.httpGETAsync(httpRequest, urlForApi);
	}




}
