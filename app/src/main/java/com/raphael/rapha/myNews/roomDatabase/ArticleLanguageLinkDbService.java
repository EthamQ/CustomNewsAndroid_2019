package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;

import com.raphael.rapha.myNews.roomDatabase.articleLanguageLink.ArticleLanguageLinkRepository;
import com.raphael.rapha.myNews.roomDatabase.articleLanguageLink.ArticleLanguageLinkRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.LinkedList;

public class ArticleLanguageLinkDbService {

    private static ArticleLanguageLinkDbService instance;
    ArticleLanguageLinkRepository repository;

    public ArticleLanguageLinkDbService(Application application) {
        this.repository = new ArticleLanguageLinkRepository(application);
    }

    public static synchronized ArticleLanguageLinkDbService getInstance(Application application) {
        if (instance == null) {
            instance = new ArticleLanguageLinkDbService(application);
        }
        return instance;
    }

    public void linkArticleWithLanguage(LinkedList<NewsArticle> newsArticles, int languageCombinationId) {
        for (int i = 0; i < newsArticles.size(); i++) {
            NewsArticle currentArticle = newsArticles.get(i);
            if (currentArticle.articleType == NewsArticleRoomModel.SWIPE_CARDS) {
                ArticleLanguageLinkRoomModel linkModel = new ArticleLanguageLinkRoomModel();
                linkModel.articleTitle = currentArticle.title;
                linkModel.languageCombination = languageCombinationId;
                repository.insert(linkModel);
            }
        }
    }
}
