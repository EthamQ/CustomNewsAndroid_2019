package com.example.rapha.swipeprototype2.api.apiQuery;

public class QueryCategory {

    String queryString;
    String keyUrlEncoded;

    public QueryCategory(String keyUrlEncoded) {
        this.queryString = "";
        this.keyUrlEncoded = keyUrlEncoded;
    }

    public boolean isEmpty(){
        return this.queryString.isEmpty();
    }
}
