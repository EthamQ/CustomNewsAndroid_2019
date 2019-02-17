package com.example.rapha.swipeprototype2.api.apiQuery;

import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.IKeyWordProvider;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryUtils;
import com.example.rapha.swipeprototype2.utils.DateUtils;

public class NewsApiQueryBuilder {
    
    QueryCategoryContainer categoryContainer;
    private String finalQuery = "";
    private int newsCategory;
    private int languageId = -1;
    public final static String GERMAN = "de";
    public final static String ENGLISH = "en";
    public final static String RUSSIAN = "ru";
    public final static String FRENCH = "fr";

    public NewsApiQueryBuilder(int languageId){
        categoryContainer = new QueryCategoryContainer();
        this.setLanguage(languageId);
    }

    /**
     * Adds every query word from the category corresponding to the id
     * to the queryWord String.
     * @param newsCategory
     */
    public void setQueryCategory(int newsCategory, IKeyWordProvider keyWordProvider){
        this.newsCategory = newsCategory;
        String hashMapKey = QueryCategoryContainer.QueryWord.hashMapKey;
        QueryCategory queryCategory = categoryContainer.allQueryCategories.get(hashMapKey);
        String[] queryWords = NewsCategoryUtils.getQueryStrings(keyWordProvider, newsCategory, this.languageId);
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
            queryCategory.queryString += queryWords[i];
            if(!(i == queryWords.length - 1)){
                queryCategory.queryString += " OR ";
            }
        }
    }

    public int getNewsCategory(){return this.newsCategory;}

    /**
     * Filters news articles by the date they were published.
     * Expects the following format: setDateFrom(2018, 05, 11)
     * -> add leading zeros!
     * @param year
     * @param month
     * @param day
     */
    public void setDateFrom(String year, String month, String day){
        String hashMapKey = QueryCategoryContainer.DateFrom.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = DateUtils.dateToISO8601(year, month, day);
    }

    public void setDateFrom(String ISO8601Date){
        String hashMapKey = QueryCategoryContainer.DateFrom.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = ISO8601Date;
    }

    /**
     * Filters news articles by the date they were published.
     * Expects the following format: setDateFrom(2018, 05, 11)
     * -> add leading zeros!
     * @param year
     * @param month
     * @param day
     */
    public void setDateTo(String year, String month, String day){
        String hashMapKey = QueryCategoryContainer.DateTo.hashMapKey;
        this.categoryContainer.allQueryCategories.get(hashMapKey).queryString = DateUtils.dateToISO8601(year, month, day);
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
