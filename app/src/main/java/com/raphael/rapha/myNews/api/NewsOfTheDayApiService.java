package com.raphael.rapha.myNews.api;
import com.raphael.rapha.myNews.http.HttpRequest;
import com.raphael.rapha.myNews.sharedPreferencesAccess.NewsOfTheDayTimeService;

public class NewsOfTheDayApiService {

    static final int DAYS_BEFORE = 7;
    static final int REQUEST_AMOUNT_AFTER_INITIAL_LOAD = 20;

    public static void getArticlesNewsApiByKeyWords(HttpRequest httpRequest, String[] queryWords, int language) throws Exception{
        int requestAmount = REQUEST_AMOUNT_AFTER_INITIAL_LOAD;
        ApiService.getArticlesNewsApiByKeyWords(
                httpRequest,
                queryWords,
                language,
                requestAmount,
                DAYS_BEFORE
        );
    }
}
