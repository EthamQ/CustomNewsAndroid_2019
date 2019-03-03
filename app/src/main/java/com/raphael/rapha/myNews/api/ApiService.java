package com.raphael.rapha.myNews.api;

import com.raphael.rapha.myNews.api.apiQuery.NewsApiQueryBuilder;
import com.raphael.rapha.myNews.http.HttpRequest;
import com.raphael.rapha.myNews.languages.TranslationService;
import com.raphael.rapha.myNews.generalServices.DateService;

public class ApiService {

    // How old should the news articles be? (days)
    public static int AMOUNT_DAYS_BEFORE_TODAY = 7;
    public static final int MAX_NUMBER_OF_ARTICLES = 100;

    /**
     * Request news articles from the api.
     * It is void because it will call a callback function when the articles are retrieved.
     * The class that defines this callback function is defined in the HttpRequest object.
     * @param httpRequest
     * @param queryWords
     * @param language
     * @param amount
     * @param daysBefore
     * @throws Exception
     */
    public static void getArticlesNewsApiByKeyWords(HttpRequest httpRequest, String[] queryWords, int language, int amount, int daysBefore) throws Exception{
        NewsApiQueryBuilder builder = new NewsApiQueryBuilder(language);
        builder.setDateFrom(DateService.getDateBefore(daysBefore));
        queryWords = TranslationService.translateEnglishTo(queryWords, language);
        builder.addQueryWord(queryWords);
        builder.setNumberOfNewsArticles(amount);
        builder.buildQuery();
        NewsApi newsApi = new NewsApi();
        newsApi.queryNewsArticlesAsync(httpRequest, builder);
    }
}
