package com.raphael.rapha.myNews.roomDatabase.articleLanguageLink;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.raphael.rapha.myNews.roomDatabase.AppDatabase;

import java.util.List;

public class ArticleLanguageLinkRepository {

    private IArticleLanguageLinkDao dao;

    public ArticleLanguageLinkRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.articleLanguageLinkDao();
    }

    public LiveData<List<ArticleLanguageLinkRoomModel>> getAll(){
        return this.dao.getAll();
    }

    public void insert(ArticleLanguageLinkRoomModel articleLanguageLinkRoomModel){
        new InsertAsyncTask(dao).execute(articleLanguageLinkRoomModel);
    }

    public void update(ArticleLanguageLinkRoomModel articleLanguageLinkRoomModel){
        new UpdateAsyncTask(dao).execute(articleLanguageLinkRoomModel);
    }

    private static class InsertAsyncTask extends AsyncTask<ArticleLanguageLinkRoomModel, Void, Void> {
        private IArticleLanguageLinkDao dao;
        private InsertAsyncTask(IArticleLanguageLinkDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(ArticleLanguageLinkRoomModel... articleLanguageLinkRoomModels) {
            dao.insertOne(articleLanguageLinkRoomModels[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<ArticleLanguageLinkRoomModel, Void, Void> {
        private IArticleLanguageLinkDao dao;
        private UpdateAsyncTask(IArticleLanguageLinkDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(ArticleLanguageLinkRoomModel... articleLanguageLinkRoomModels) {
            dao.update(articleLanguageLinkRoomModels[0]);
            return null;
        }
    }
}
