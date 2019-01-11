package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserPreferenceRepository {
    private IUserPreferenceDao dao;
    private LiveData<List<UserPreferenceTable>> allCategories;

    public UserPreferenceRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.dao();
        allCategories = dao.getAllUserPreferences();
    }

    public void insert(UserPreferenceTable userPreferenceTable){
        new InsertPreferenceAsyncTask(dao).execute(userPreferenceTable);
    }

    public void update(){

    }

    public void delete(){

    }

    public LiveData<List<UserPreferenceTable>> getAllUserPreferences(){
        return allCategories;
    }

    private static class InsertPreferenceAsyncTask extends AsyncTask<UserPreferenceTable, Void, Void>{

        private IUserPreferenceDao dao;

        private InsertPreferenceAsyncTask(IUserPreferenceDao dao){
                this.dao = dao;
        }
        @Override
        protected Void doInBackground(UserPreferenceTable... userPreferenceTables) {
            dao.insertOneNewsCategory(userPreferenceTables[0]);
            return null;
        }
    }
}
