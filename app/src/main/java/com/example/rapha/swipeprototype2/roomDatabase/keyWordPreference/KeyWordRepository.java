package com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rapha.swipeprototype2.roomDatabase.AppDatabase;

import java.util.List;

public class KeyWordRepository {

    private LiveData<List<KeyWordRoomModel>> allKeyWords;
    private LiveData<List<KeyWordRoomModel>> allLikedKeyWords;
    private IKeyWordDao dao;

    public KeyWordRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.keyWordDaoDao();
        allKeyWords = dao.getAllKeyWords();
        allLikedKeyWords = dao.getKeyWordsByStatus(KeyWordRoomModel.LIKED);
    }

    public void insert(KeyWordRoomModel keyWordRoomModel){
        new InsertKeyWordAsyncTask(dao).execute(keyWordRoomModel);
    }

    public void deleteAll(){
        new DeleteAllKeyWordsAsyncTask (dao).execute();
    }

    public void update(KeyWordRoomModel keyWordRoomModel){
        new UpdateOneKeyWordAsyncTask (dao).execute(keyWordRoomModel);
    }

    public LiveData<List<KeyWordRoomModel>> getAllKeyWords(){
        return allKeyWords;
    }

    public LiveData<List<KeyWordRoomModel>> getAllLikedKeyWords(){
        return allLikedKeyWords;
    }

    private static class InsertKeyWordAsyncTask extends AsyncTask<KeyWordRoomModel, Void, Void> {
        private IKeyWordDao dao;
        private InsertKeyWordAsyncTask(IKeyWordDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(KeyWordRoomModel... keyWordRoomModels) {
            dao.insertOneKeyWord(keyWordRoomModels[0]);
            return null;
        }
    }

    private static class DeleteAllKeyWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private IKeyWordDao dao;
        private DeleteAllKeyWordsAsyncTask(IKeyWordDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllKeyWords();
            return null;
        }
    }

    private static class UpdateOneKeyWordAsyncTask extends AsyncTask<KeyWordRoomModel, Void, Void> {
        private IKeyWordDao dao;
        private UpdateOneKeyWordAsyncTask (IKeyWordDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(KeyWordRoomModel... keyWordRoomModels) {
            dao.updateKeyWord(keyWordRoomModels[0]);
            return null;
        }
    }
}
