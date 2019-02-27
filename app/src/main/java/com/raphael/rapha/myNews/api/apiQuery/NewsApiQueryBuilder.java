package com.raphael.rapha.myNews.api.apiQuery;

import android.util.Log;

import com.raphael.rapha.myNews.http.IHttpRequester;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.raphael.rapha.myNews.topics.TopicService;

import java.util.List;

public class NewsApiQueryBuilder {
    
    QueryCategoryContainer categoryContainer;
    private String finalQuery = "";
    private int newsCategory;

    private IQueryListener queryListener;

    private IHttpRequester httpRequester;

    private int languageId = -1;
    public final static String GERMAN = "de";
    public final static String ENGLISH = "en";
    public final static String RUSSIAN = "ru";
    public final static String FRENCH = "fr";

    public NewsApiQueryBuilder(int languageId){
        categoryContainer = new QueryCategoryContainer();
        this.setLanguage(languageId);
    }

    public void setQueryListener(IQueryListener queryListener) { this.queryListener = queryListener; }

    public IQueryListener getQueryListener() {
        return queryListener;
    }

    public IHttpRequester getHttpRequester() { return httpRequester; }

    public void setHttpRequester(IHttpRequester httpRequester) { this.httpRequester = httpRequester; }

    public int getLanguageId() {
        return languageId;
    }

    /**
     * Adds every query word from the category corresponding to the id
     * to the queryWord String.
     * @param newsCategory
     */
    public void setQueryCategory(int newsCategory, List<KeyWordRoomModel> allTopics){
        Log.d("iii", "query cat:: " + newsCategory);
        this.newsCategory = newsCategory;
        String hashMapKey = QueryCategoryContainer.QueryWord.hashMapKey;
        QueryCategory queryCategory = categoryContainer.allQueryCategories.get(hashMapKey);
        String[] queryWords = TopicService.getTopicsForCategory(allTopics, newsCategory, this.languageId);
        addQueryStrings(queryCategory, queryWords);
    }

    public void addQueryWord(String[] queryWords){
        String hashMapKey = QueryCategoryContainer.QueryWord.hashMapKey;
        QueryCategory queryCategory = categoryContainer.allQueryCategories.get(hashMapKey);
        addQueryStrings(queryCategory, queryWords);
    }

    /**
     * Adds all strings from the query word string array to the
     * queryString property of the queryCategory.
     * They are concatenated with an OR.
     * @param queryCategory
     * @param queryWords
     */
    private void addQueryStrings(QueryCategory queryCategory, String[] queryWords){
        for (int i = 0; i < queryWords.length; i++){
            Log.d("jopp","Received query word: " + queryWords[i]);
            queryCategory.queryString += queryWords[i];
            if(queryWords[i].length() > 0){
                if(!(i == queryWords.length - 1)){
                    queryCategory.queryString += " OR ";
                }
            }
        }
        Log.d("jopp","querystring: " + queryCategory.queryString);
    }

    public int getNewsCategory(){return this.newsCategory;}

    public void setDateFrom(String ISO8601Date){
        String hashMapKey = QueryCategoryContainer.DateFrom.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = ISO8601Date;
    }

    public void setDateTo(String dateIso8601){
        String hashMapKey = QueryCategoryContainer.DateTo.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = dateIso8601;
    }

    private void setLanguage(int languageId){
        this.languageId = languageId;
        String languageQueryString;
        switch(languageId){
            case LanguageSettingsService
                    .INDEX_ENGLISH: languageQueryString = ENGLISH; break;
            case LanguageSettingsService
                    .INDEX_FRENCH: languageQueryString = FRENCH; break;
            case LanguageSettingsService
                    .INDEX_GERMAN: languageQueryString = GERMAN; break;
            case LanguageSettingsService
                    .INDEX_RUSSIAN: languageQueryString = RUSSIAN; break;
            default: languageQueryString = ENGLISH; break;
        }
        String hashMapKey = QueryCategoryContainer.Language.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = languageQueryString;
    }

    public void setNumberOfNewsArticles(int number){
        String hashMapKey = QueryCategoryContainer.PageSize.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = number + "";
    }

    /**
     * Call it after all the set methods have been called.
     * Concatenates every query string that has been set to the final
     * query that can be added to the url (url encoded)
     */
    public void buildQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("&");
        for (String key : categoryContainer.allQueryCategories.keySet()) {
            QueryCategory currentCategory = categoryContainer.allQueryCategories.get(key);
            if(!currentCategory.isEmpty()){
                sb.append(currentCategory.keyUrlEncoded);
                sb.append(currentCategory.queryString);
                sb.append("&");
            }
        }

        String query = sb.toString();
        // remove the last "&"
        Log.d("query", "finalQuery: " + query.substring(0, query.length()-1));
        this.finalQuery = query.substring(0, query.length()-1);
    }

    /**
     * Get the query url encoded that can be added to the url after
     * buildQuery() has been called.
     * @return
     */
    public String getQuery(){
        return this.finalQuery;
    }
}
