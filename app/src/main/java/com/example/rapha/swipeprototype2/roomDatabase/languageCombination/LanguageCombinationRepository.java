package com.example.rapha.swipeprototype2.roomDatabase.languageCombination;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rapha.swipeprototype2.roomDatabase.AppDatabase;

import java.util.List;

public class LanguageCombinationRepository {

    private ILanguageCombinationDao dao;

    public LanguageCombinationRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.languageCombinationDao();
    }

    public LiveData<List<LanguageCombinationRoomModel>> getAll(){
        return this.dao.getAll();
    }

    public void insert(LanguageCombinationRoomModel languageCombinationRoomModel, LanguageCombinationData data){
        new InsertAsyncTask(dao, data).execute(languageCombinationRoomModel);
    }

    public void update(LanguageCombinationRoomModel languageCombinationRoomModel){
        new UpdateAsyncTask(dao).execute(languageCombinationRoomModel);
    }

    private static class InsertAsyncTask extends AsyncTask<LanguageCombinationRoomModel, Void, LanguageCombinationData> {
        ILanguageCombinationDao dao;
        LanguageCombinationData data;
        private InsertAsyncTask(ILanguageCombinationDao dao, LanguageCombinationData data){
            this.dao = dao;
            this.data = data;
        }
        @Override
        protected LanguageCombinationData doInBackground(LanguageCombinationRoomModel... languageCombinationRoomModels) {
            data.insertedId = dao.insertOne(languageCombinationRoomModels[0]);
            return data;
        }

        @Override
        protected void onPostExecute(LanguageCombinationData data) {
            data.inserter.onLanguageCombinationInsertFinished(data);
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<LanguageCombinationRoomModel, Void, Void> {
        private ILanguageCombinationDao dao;
        private UpdateAsyncTask(ILanguageCombinationDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(LanguageCombinationRoomModel... languageCombinationRoomModels) {
            dao.update(languageCombinationRoomModels[0]);
            return null;
        }
    }
}
