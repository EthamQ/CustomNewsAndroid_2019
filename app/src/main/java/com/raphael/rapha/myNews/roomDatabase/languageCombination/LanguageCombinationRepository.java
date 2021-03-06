package com.raphael.rapha.myNews.roomDatabase.languageCombination;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.raphael.rapha.myNews.roomDatabase.AppDatabase;

import java.util.List;
import android.arch.lifecycle.Observer;

public class LanguageCombinationRepository {

    private ILanguageCombinationDao dao;
    private LiveData<List<LanguageCombinationRoomModel>> allCombinations;

    public LanguageCombinationRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.languageCombinationDao();
        allCombinations = dao.getAll();
    }

    public LiveData<List<LanguageCombinationRoomModel>> getAll(){
        return allCombinations;
    }
     public void getAllRemoveObserver(Observer observer){
        allCombinations.removeObserver(observer);
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
