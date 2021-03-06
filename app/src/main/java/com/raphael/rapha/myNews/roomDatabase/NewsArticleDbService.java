package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.raphael.rapha.myNews.roomDatabase.newsArticles.DeleteData;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRepository;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;

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

    public void insert(NewsArticle newsArticle){
        NewsArticleRoomModel insert = createNewsArticleRoomModelToInsert(
                newsArticle
        );
        insert.articleType = NewsArticleRoomModel.NEWS_OF_THE_DAY;
        insert(insert);
    }

    public void deleteAllSwipedArticles(DeleteData deleteData){
        repository.deleteAllSwipeArticles(deleteData);
    }

    public void deleteAllDailyArticles(){
        repository.deleteAllDailyArticles();
    }

    public void update(NewsArticleRoomModel newsArticleRoomModel){
        repository.update(newsArticleRoomModel);
    }

    public void setAsRead(NewsArticleRoomModel newsArticleRoomModel){
        newsArticleRoomModel.hasBeenRead = true;
        repository.update(newsArticleRoomModel);
    }

    public void setAsArchived(NewsArticleRoomModel newsArticleRoomModel){
        newsArticleRoomModel.archived = true;
        repository.update(newsArticleRoomModel);
    }

    public LiveData<List<NewsArticleRoomModel>> getAllArticles(){
        return repository.getAllSwipeArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllSwipeArticles(){
        return repository.getAllSwipeArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllNewsOfTheDayArticlesByKeyWord(String keyWord){
        return repository.getAllNewsOfTheDayArticlesByKeyWord(keyWord);
    }

    public  LiveData<List<NewsArticleRoomModel>> getOneNewsArticle(String title, int articleType){
        return repository.getOneNewsArticle(title, articleType);
    }

    public LiveData<List<NewsArticleRoomModel>> getAllNewsOfTheDayArticles(){
        return repository.getAllNewsOfTheDayArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadSwipeArticles(){
        return repository.getAllUnreadSwipeArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadDailyArticles(){
        return repository.getAllUnreadNewsOfTheDayArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllReadDailyArticles(){
        return repository.getAllReadNewsOfTheDayArticles();
    }

    public LiveData<List<NewsArticleRoomModel>> getAllDailyArticlesNotArchived(){
        return repository.getAllDailyArticlesNotArchived();
    }

    public NewsArticleRoomModel createNewsArticleRoomModelToInsert(NewsArticle newsArticle){
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
        dbModel.articleType = newsArticle.articleType;
        dbModel.archived = newsArticle.archived;
        dbModel.hasBeenRead = newsArticle.hasBeenRead;
        dbModel.foundWithKeyWord = newsArticle.foundWithKeyWord;
        dbModel.languageId = newsArticle.languageId;
        return dbModel;
    }

    public NewsArticleRoomModel createNewsArticleRoomModelToUpdate(NewsArticle newsArticle){
        NewsArticleRoomModel dbModel;
        dbModel = createNewsArticleRoomModelToInsert(newsArticle);
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
        newsArticle.articleType = dbModel.articleType;
        newsArticle.archived = dbModel.archived;
        newsArticle.hasBeenRead = dbModel.hasBeenRead;
        newsArticle.foundWithKeyWord = dbModel.foundWithKeyWord;
        newsArticle.languageId = dbModel.languageId;
        return newsArticle;
    }

    public LinkedList<NewsArticle> createNewsArticleList(List<NewsArticleRoomModel> dbModels){
        LinkedList<NewsArticle> articleList = new LinkedList<>();

        for (int i = 0; i < dbModels.size(); i++){
            articleList.add(createNewsArticle(dbModels.get(i)));
        }
        return articleList;
    }

    public void insertNewsArticles(LinkedList<NewsArticle> newsArticles){
        for(int i = 0; i < newsArticles.size(); i++){
            NewsArticle currentArticle = newsArticles.get(i);
            insert(createNewsArticleRoomModelToInsert(currentArticle));
        }
    }



}
