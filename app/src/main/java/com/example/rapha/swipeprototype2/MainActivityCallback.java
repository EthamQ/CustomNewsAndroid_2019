package com.example.rapha.swipeprototype2;

import java.util.LinkedList;

public interface MainActivityCallback {

    void loadArticlesCallback(final LinkedList<NewsArticle> newsArticlesNewsApi);
}
