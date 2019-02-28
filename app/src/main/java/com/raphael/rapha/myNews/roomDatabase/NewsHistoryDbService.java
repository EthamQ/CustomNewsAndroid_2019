package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRepository;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsHistory.NewsHistoryRepository;
import com.raphael.rapha.myNews.roomDatabase.newsHistory.NewsHistoryRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class NewsHistoryDbService {

    private static NewsHistoryDbService instance;
    NewsHistoryRepository repository;
    Application application;

    private NewsHistoryDbService(Application application){
        this.application = application;
        this.repository = new NewsHistoryRepository(application);
    }

    public static synchronized NewsHistoryDbService getInstance(Application application){
        if(instance == null){
            instance = new NewsHistoryDbService(application);
        }
        return instance;
    }

    public void insert(NewsArticleRoomModel newsArticleRoomModel){
        NewsHistoryRoomModel newsHistoryRoomModel = new NewsHistoryRoomModel();
        newsHistoryRoomModel.fillModel(newsArticleRoomModel);
        repository.insert(newsHistoryRoomModel);
    }

    public void insert(NewsArticle newsArticle){
        NewsArticleRoomModel newsArticleRoomModel =
                NewsArticleDbService.getInstance(application)
                        .createNewsArticleRoomModelToInsert(newsArticle);
        NewsHistoryRoomModel newsHistoryRoomModel = new NewsHistoryRoomModel();
        newsHistoryRoomModel.fillModel(newsArticleRoomModel);
        repository.insert(newsHistoryRoomModel);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<NewsHistoryRoomModel>> getAll(){
        return repository.getAll();
    }

    public static List<NewsArticle> toNewsArticles(List<NewsHistoryRoomModel> dbModels){
        List<NewsArticle> newsArticles = new ArrayList<>();
        for(NewsHistoryRoomModel model : dbModels){
            newsArticles.add(toNewsArticle(model));
        }
        return newsArticles;
    }

    public static NewsArticle toNewsArticle(NewsHistoryRoomModel dbModel){
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.sourceId = dbModel.sourceId;
        newsArticle.sourceName = dbModel.sourceName;
        newsArticle.title = dbModel.title;
        newsArticle.description = dbModel.description;
        newsArticle.url = dbModel.url;
        newsArticle.urlToImage = dbModel.urlToImage;
        newsArticle.publishedAt = dbModel.publishedAt;
        newsArticle.content = dbModel.content;
        newsArticle.newsCategory = dbModel.newsCategory;
        newsArticle.languageId = dbModel.languageId;
        return newsArticle;
    }
}
