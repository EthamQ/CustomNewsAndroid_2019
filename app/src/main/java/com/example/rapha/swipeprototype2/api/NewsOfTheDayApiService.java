package com.example.rapha.swipeprototype2.api;
import com.example.rapha.swipeprototype2.http.HttpRequest;
import com.example.rapha.swipeprototype2.time.NewsOfTheDayTimeService;

public class NewsOfTheDayApiService {

    static final int DAYS_BEFORE = 7;
    static final int REQUEST_AMOUNT_AFTER_INITIAL_LOAD = 20;

    public static void getArticlesNewsApiByKeyWords(HttpRequest httpRequest, String[] queryWords, int language) throws Exception{
        boolean firstTimeLoading =
                NewsOfTheDayTimeService.firstTimeLoadingData(httpRequest.requestInfo.getContext());
        int requestAmount = firstTimeLoading ?
                ApiService.MAX_NUMBER_OF_ARTICLES : REQUEST_AMOUNT_AFTER_INITIAL_LOAD;
        ApiService.getArticlesNewsApiByKeyWords(
                httpRequest,
                queryWords,
                language,
                requestAmount,
                DAYS_BEFORE
        );
    }
}
