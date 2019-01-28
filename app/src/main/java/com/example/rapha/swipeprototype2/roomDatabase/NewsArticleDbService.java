package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRepository;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRepository;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;

import java.util.LinkedList;
import java.util.List;

public class NewsArticleDbService {

    private static NewsArticleDbService instance;
    NewsArticleRepository repository;

    private NewsArticleDbService(Application application){
        this.repository = new NewsArticleRepository(application);
    }

    public static synchronized NewsArticleDbService getInstance(Application application){
        if(instance == null){
            instance = new NewsArticleDbService(application);
        }
        return instance;
    }

    public void insert(NewsArticleRoomModel newsArticleRoomModel){
        repository.insert(newsArticleRoomModel);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public void update(NewsArticleRoomModel newsArticleRoomModel){
        repository.update(newsArticleRoomModel);
    }

    public LiveData<List<NewsArticleRoomModel>> getAllArticles(){
        return repository.getAllArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadArticles(){
        return repository.getAllUnreadArticles();
    }

    public NewsArticleRoomModel createNewsArticleRoomModel(NewsArticle newsArticle){
        NewsArticleRoomModel dbModel = new NewsArticleRoomModel();
        dbModel.sourceId = newsArticle.sourceId;
        dbModel.sourceName = newsArticle.sourceName;
        dbModel.title = newsArticle.title;
        dbModel.description = newsArticle.description;
        dbModel.url = newsArticle.url;
        dbModel.urlToImage = newsArticle.urlToImage;
        dbModel.publishedAt = newsArticle.publishedAt;
        dbModel.content = newsArticle.content;
        dbModel.newsCategory = newsArticle.newsCategory;
        return dbModel;
    }

    public NewsArticle createNewsArticle(NewsArticleRoomModel dbModel){
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
        return newsArticle;
    }

    public LinkedList<NewsArticle> createNewsArticleList(List<NewsArticleRoomModel> dbModels, int limit){
        if(limit > dbModels.size()){
            limit = dbModels.size();
        }
        LinkedList<NewsArticle> articleList = new LinkedList<>();
        for (int i = 0; i < limit; i++){
            articleList.add(createNewsArticle(dbModels.get(i)));
        }
        return articleList;
    }

    public void insertNewsArticles(LinkedList<NewsArticle> newsArticles){
        for(int i = 0; i < newsArticles.size(); i++){
            insert(createNewsArticleRoomModel(newsArticles.get(i)));
        }
    }

}