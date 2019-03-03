package com.raphael.rapha.myNews.api;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.NewsOfTheDayFragment;
import com.raphael.rapha.myNews.http.HttpRequest;
import com.raphael.rapha.myNews.sharedPreferencesAccess.NewsOfTheDayTimeService;
import com.raphael.rapha.myNews.topics.TopicWordsTransformation;

public class NewsOfTheDayApiService {

    static final int DAYS_BEFORE = 7;
    static final int REQUEST_AMOUNT_AFTER_INITIAL_LOAD = 20;

    /**
     * Requests articles from the api using the topics strings for the query.
     * Requests in the language that is passed to the function by languageId.
     * Calls a callback function in the calling class when finished.
     * @param httpRequest Contains the calling class.
     * @param topics An array of all topics to be added to the query for the api.
     * @param languageId Int representation of the language.
     * @throws Exception
     */
    public static void getArticlesNewsApiByTopics(HttpRequest httpRequest, String[] topics, int languageId) throws Exception{
        boolean firstTimeRequestingData = false;
        if(httpRequest.requestInfo.getContext() != null){
            firstTimeRequestingData = NewsOfTheDayTimeService.firstTimeLoadingData(
                    httpRequest.requestInfo.getContext()
            );
        }
        int requestAmount = firstTimeRequestingData ? ApiService.MAX_NUMBER_OF_ARTICLES : REQUEST_AMOUNT_AFTER_INITIAL_LOAD;
        ApiService.getArticlesNewsApiByKeyWords(
                httpRequest,
                topics,
                languageId,
                requestAmount,
                DAYS_BEFORE
        );
    }
}
