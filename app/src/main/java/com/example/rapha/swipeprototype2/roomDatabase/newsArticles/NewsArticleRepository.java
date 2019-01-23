package com.example.rapha.swipeprototype2.roomDatabase.newsArticles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.roomDatabase.AppDatabase;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.IUserPreferenceDao;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRepository;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

public class NewsArticleRepository {

    private LiveData<List<NewsArticleRoomModel>> allArticles;
    private LiveData<List<NewsArticleRoomModel>> allUnreadArticles;
    private INewsArticleDao dao;

    public NewsArticleRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.newsArticleDao();
        allArticles = dao.getAllNewsArticles();
        allUnreadArticles = dao.getAllUnreadNewsArticles(false);
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

    public LiveData<List<NewsArticleRoomModel>> getAllArticles(){
        return allArticles;
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadArticles(){
        return allUnreadArticles;
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
