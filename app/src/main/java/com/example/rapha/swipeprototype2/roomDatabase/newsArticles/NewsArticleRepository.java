package com.example.rapha.swipeprototype2.roomDatabase.newsArticles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rapha.swipeprototype2.roomDatabase.AppDatabase;

import java.util.List;

public class NewsArticleRepository {

    private INewsArticleDao dao;
    private LiveData<List<NewsArticleRoomModel>> allSwipeArticles;
    private LiveData<List<NewsArticleRoomModel>> allUnreadSwipeArticles;
    private LiveData<List<NewsArticleRoomModel>> allNewsOfTheDayArticles;

    public NewsArticleRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.newsArticleDao();
        allSwipeArticles = dao.getAllNewsArticlesByType(NewsArticleRoomModel.SWIPE_CARDS);
        allNewsOfTheDayArticles = dao.getAllNewsArticlesByType(NewsArticleRoomModel.NEWS_OF_THE_DAY);
        allUnreadSwipeArticles = dao.getAllUnreadSwipeNewsArticles(false, NewsArticleRoomModel.SWIPE_CARDS);
    }

    public void insert(NewsArticleRoomModel newsArticleRoomModel){
        new InsertNewsArticleAsyncTask(dao).execute(newsArticleRoomModel);
    }

    public void deleteAll(){
        new DeleteAllNewsArticlesAsyncTask (dao).execute();
    }

    public void update(NewsArticleRoomModel newsArticleRoomModel){
        new UpdateOneNewsArticlesAsyncTask(dao).execute(newsArticleRoomModel);
    }

    public LiveData<List<NewsArticleRoomModel>> getAllSwipeArticles(){
        return allSwipeArticles;
    }

    public LiveData<List<NewsArticleRoomModel>> getAllNewsOfTheDayArticles(){
        return allNewsOfTheDayArticles;
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadSwipeArticles(){
        return allUnreadSwipeArticles;
    }

    private static class InsertNewsArticleAsyncTask extends AsyncTask<NewsArticleRoomModel, Void, Void> {

        private INewsArticleDao dao;

        private InsertNewsArticleAsyncTask(INewsArticleDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NewsArticleRoomModel... newsArticleRoomModels) {
            dao.insertOneNewsArticle(newsArticleRoomModels[0]);
            return null;
        }
    }

    private static class DeleteAllNewsArticlesAsyncTask extends AsyncTask<Void, Void, Void> {

        private INewsArticleDao dao;

        private DeleteAllNewsArticlesAsyncTask(INewsArticleDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllNewsArticles();
            return null;
        }
    }

    private static class UpdateOneNewsArticlesAsyncTask extends AsyncTask<NewsArticleRoomModel, Void, Void> {

        private INewsArticleDao dao;

        private UpdateOneNewsArticlesAsyncTask(INewsArticleDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NewsArticleRoomModel... newsArticleRoomModels) {
            dao.updateNewsArticle(newsArticleRoomModels[0]);
            return null;
        }
    }
}
