package com.example.rapha.swipeprototype2.api;

import android.util.Log;

import com.example.rapha.swipeprototype2.utils.CategoryUtils;
import com.example.rapha.swipeprototype2.utils.DateUtils;

public class NewsApiQueryBuilder {
    private String finalQuery = "";
    private String queryWord = "";
    private String language = "";
    private String dateFrom = "";
    private String dateTo = "";

    public final static String GERMAN = "de";
    public final static String ENGLISH = "en";
    public final static String RUSSIAN = "ru";
    public final static String FRENCH = "fr";

    public NewsApiQueryBuilder(){}

    /**
     * Adds every query word from the category corresponding to the id
     * to the queryWord String. They are concatenated with an OR.
     * @param newsCategory
     */
    public void setQueryCategory(int newsCategory){
        String[] queryWords = CategoryUtils.getQueryWords(newsCategory, this.language);
        for (int i = 0; i < queryWords.length; i++){
            this.queryWord += queryWords[i];
            if(!(i == queryWords.length - 1)){
                this.queryWord += " OR ";
            }
        }
    }

    /**
     * Filters news articles by the date they were published.
     * Expects the following format: setDateFrom(2018, 05, 11)
     * -> add leading zeros!
     * @param year
     * @param month
     * @param day
     */
    public void setDateFrom(String year, String month, String day){
        this.dateFrom = DateUtils.dateToISO8601(year, month, day);
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
        this.dateTo = DateUtils.dateToISO8601(year, month, day);
    }

    public void setLanguage(String language){
        this.language = language;
    }

    /**
     * Call it after all the set methods have been called.
     * Concatenates every query string that has been set to the final
     * query that can be added to the url (url encoded)
     */
    public void buildQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("&");
        if(!this.queryWord.isEmpty()){
            sb.append("q=");
            sb.append(this.queryWord);
            sb.append("&");
        }
        if(!this.language.isEmpty()){
            sb.append("language=");
            sb.append(this.language);
            sb.append("&");
        }
        if(!this.dateFrom.isEmpty()){
            sb.append("from=");
            sb.append(this.dateFrom);
            sb.append("&");
        }
        if(!this.dateTo.isEmpty()){
            sb.append("to=");
            sb.append(this.dateTo);
            sb.append("&");
        }
        String query = sb.toString();
        // remove the last "&"
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
