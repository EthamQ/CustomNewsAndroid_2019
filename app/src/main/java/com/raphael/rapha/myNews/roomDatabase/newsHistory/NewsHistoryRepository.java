package com.raphael.rapha.myNews.roomDatabase.newsHistory;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.raphael.rapha.myNews.roomDatabase.AppDatabase;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.DeleteData;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.INewsArticleDao;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;

import java.util.List;

public class NewsHistoryRepository {

    private INewsHistoryDao dao;
    private LiveData<List<NewsHistoryRoomModel>> allArticles;

    public NewsHistoryRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.newsHistoryDao();
        allArticles = dao.getAll();
    }

    public LiveData<List<NewsHistoryRoomModel>> getAll(){
        return this.allArticles;
    }

    public void insert(NewsHistoryRoomModel newsHistoryRoomModel){
        new InsertAsyncTask(dao).execute(newsHistoryRoomModel);
    }

    public void update(NewsHistoryRoomModel newsHistoryRoomModel){
        new UpdateAsyncTask(dao).execute(newsHistoryRoomModel);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(dao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<NewsHistoryRoomModel, Void, Void> {
        private INewsHistoryDao dao;
        private InsertAsyncTask(INewsHistoryDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NewsHistoryRoomModel... newsArticleRoomModels) {
            dao.insertOne(newsArticleRoomModels[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<NewsHistoryRoomModel, Void, Void> {
        private INewsHistoryDao dao;
        private UpdateAsyncTask(INewsHistoryDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NewsHistoryRoomModel... newsArticleRoomModels) {
            dao.update(newsArticleRoomModels[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private INewsHistoryDao dao;

        private DeleteAllAsyncTask(INewsHistoryDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
}
