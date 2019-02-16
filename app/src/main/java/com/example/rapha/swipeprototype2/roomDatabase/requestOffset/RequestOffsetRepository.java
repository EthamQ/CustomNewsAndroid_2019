package com.example.rapha.swipeprototype2.roomDatabase.requestOffset;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rapha.swipeprototype2.roomDatabase.AppDatabase;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.IKeyWordDao;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRepository;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.List;

public class RequestOffsetRepository {

    private IRequestOffsetDao dao;

    public RequestOffsetRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.requestOffsetDao();
    }

    public LiveData<List<RequestOffsetRoomModel>> getAll(){
        return this.dao.getAll();
    }

    public void insert(RequestOffsetRoomModel requestOffsetRoomModel){
        new InsertAsyncTask(dao).execute(requestOffsetRoomModel);
    }

    public void update(RequestOffsetRoomModel requestOffsetRoomModel){
        new UpdateAsyncTask(dao).execute(requestOffsetRoomModel);
    }

    private static class InsertAsyncTask extends AsyncTask<RequestOffsetRoomModel, Void, Void> {
        private IRequestOffsetDao dao;
        private InsertAsyncTask(IRequestOffsetDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(RequestOffsetRoomModel... requestOffsetRoomModels) {
            dao.insertOne(requestOffsetRoomModels[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<RequestOffsetRoomModel, Void, Void> {
        private IRequestOffsetDao dao;
        private UpdateAsyncTask(IRequestOffsetDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(RequestOffsetRoomModel... requestOffsetRoomModels) {
            dao.update(requestOffsetRoomModels[0]);
            return null;
        }
    }
}
