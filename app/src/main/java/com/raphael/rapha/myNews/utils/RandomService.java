package com.raphael.rapha.myNews.utils;

import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomService {

    public static int getRandomNumber(int start, int end){
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    /**
     * Return the LinkedList of news articles that the function receives in random order.
     * @param newsArticles
     * @return
     */
    public static LinkedList<NewsArticle> orderRandomly(LinkedList<NewsArticle> newsArticles){
        LinkedList<NewsArticle> randomOrderedList = new LinkedList<>();
        while(newsArticles.size() > 0){
            int randomIndex = getRandomNumber(0, newsArticles.size());
            randomOrderedList.add(newsArticles.get(randomIndex));
            newsArticles.remove(randomIndex);
        }
        return randomOrderedList;
    }
}
