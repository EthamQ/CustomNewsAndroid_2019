package com.raphael.rapha.myNews.api.apiQuery;

import java.util.HashMap;

public class QueryCategoryContainer {

    public HashMap<String, QueryCategory> allQueryCategories;

    public QueryCategoryContainer() {
        allQueryCategories = new HashMap<>();
        this.allQueryCategories.put(QueryWord.hashMapKey, new QueryCategory( QueryWord.keyUrlEncoded));
        this.allQueryCategories.put(Language.hashMapKey, new QueryCategory( Language.keyUrlEncoded));
        this.allQueryCategories.put(DateFrom.hashMapKey, new QueryCategory( DateFrom.keyUrlEncoded));
        this.allQueryCategories.put(DateTo.hashMapKey, new QueryCategory( DateTo.keyUrlEncoded));
        this.allQueryCategories.put(PageSize.hashMapKey, new QueryCategory( PageSize.keyUrlEncoded));
    }

    public static class QueryWord{
        public static final String hashMapKey = "queryWord";
        public static final String keyUrlEncoded = "q=";
    }

    public static class Language{
        public static final String hashMapKey = "language";
        public static final String keyUrlEncoded = "language=";
    }

    public static class DateFrom{
        public static final String hashMapKey = "dateFrom";
        public static final String keyUrlEncoded = "from=";
    }

    public static class DateTo{
        public static final String hashMapKey = "DateTo";
        public static final String keyUrlEncoded = "to=";
    }

    public static class PageSize{
        public static final String hashMapKey = "pageSize";
        public static final String keyUrlEncoded = "pageSize=";
    }
}
