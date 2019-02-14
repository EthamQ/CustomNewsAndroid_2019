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
    private LiveData<List<NewsArticleRoomModel>> allUnreadNewsOfTheDayArticles;
    private LiveData<List<NewsArticleRoomModel>> allReadNewsOfTheDayArticles;
    private LiveData<List<NewsArticleRoomModel>> allNewsOfTheDayArticles;
    private LiveData<List<NewsArticleRoomModel>> allDailyArticlesNotArchived;

    public NewsArticleRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.newsArticleDao();

        allSwipeArticles = dao.getAllNewsArticlesByType(NewsArticleRoomModel.SWIPE_CARDS);
        allUnreadSwipeArticles = dao.getAllUnreadNewsArticles(false, false, NewsArticleRoomModel.SWIPE_CARDS);

        allUnreadNewsOfTheDayArticles = dao.getAllUnreadNewsArticles(false, false, NewsArticleRoomModel.NEWS_OF_THE_DAY);
        allReadNewsOfTheDayArticles = dao.getAllUnreadNewsArticles(true, false,NewsArticleRoomModel.NEWS_OF_THE_DAY);
        allDailyArticlesNotArchived = dao.getAllNewsArticles(NewsArticleRoomModel.NEWS_OF_THE_DAY, false);
        allNewsOfTheDayArticles = dao.getAllNewsArticlesByType(NewsArticleRoomModel.NEWS_OF_THE_DAY);
    }

    public void insert(NewsArticleRoomModel newsArticleRoomModel){
        new InsertNewsArticleAsyncTask(dao).execute(newsArticleRoomModel);
    }

    public void deleteAllSwipeArticles(){
        new DeleteAllNewsArticlesAsyncTask (dao).execute(NewsArticleRoomModel.SWIPE_CARDS);
    }

    public void deleteAllDailyArticles(){
        new DeleteAllNewsArticlesAsyncTask (dao).execute(NewsArticleRoomModel.NEWS_OF_THE_DAY);
    }

    public  LiveData<List<NewsArticleRoomModel>> getOneNewsArticle(String title, int articleType){
        return dao.getOneNewsArticle(title, articleType);
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

    public LiveData<List<NewsArticleRoomModel>> getAllNewsOfTheDayArticlesByKeyWord(String keyWord){
        return dao.getAllNewsArticlesByKeyWord(keyWord, NewsArticleRoomModel.NEWS_OF_THE_DAY, false);
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadSwipeArticles(){
        return allUnreadSwipeArticles;
    }

    public LiveData<List<NewsArticleRoomModel>> getAllUnreadNewsOfTheDayArticles(){
        return allUnreadNewsOfTheDayArticles;
    }

    public LiveData<List<NewsArticleRoomModel>> getAllReadNewsOfTheDayArticles(){
        return allReadNewsOfTheDayArticles;
    }

    public LiveData<List<NewsArticleRoomModel>> getAllDailyArticlesNotArchived(){
        return allDailyArticlesNotArchived;
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

    private static class DeleteAllNewsArticlesAsyncTask extends AsyncTask<Integer, Void, Void> {

        private INewsArticleDao dao;

        private DeleteAllNewsArticlesAsyncTask(INewsArticleDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Integer... integers) {
            dao.deleteAllNewsArticles(integers[0]);
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
