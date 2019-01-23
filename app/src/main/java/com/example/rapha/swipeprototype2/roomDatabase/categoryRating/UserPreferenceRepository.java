package com.example.rapha.swipeprototype2.roomDatabase.categoryRating;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rapha.swipeprototype2.roomDatabase.AppDatabase;

import java.util.List;

public class UserPreferenceRepository {
    private IUserPreferenceDao dao;
    private LiveData<List<UserPreferenceRoomModel>> allCategories;

    public UserPreferenceRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.dao();
        allCategories = dao.getAllUserPreferences();
    }

    public void insert(UserPreferenceRoomModel userPreferenceRoomModel){
        new InsertPreferenceAsyncTask(dao).execute(userPreferenceRoomModel);
    }

    public void update(UserPreferenceRoomModel userPreferenceRoomModel){
        Log.d("RIGHTEXIT", "update");
        new UpdatePreferencesAsyncTask(dao).execute(userPreferenceRoomModel);
    }

    public void delete(){

    }

    public void deleteAll(){
        new DeleteAllPreferencesAsyncTask(dao).execute();
    }

    public LiveData<List<UserPreferenceRoomModel>> getAllUserPreferences(){
        return allCategories;
    }

    private static class InsertPreferenceAsyncTask extends AsyncTask<UserPreferenceRoomModel, Void, Void>{

        private IUserPreferenceDao dao;

        private InsertPreferenceAsyncTask(IUserPreferenceDao dao){
                this.dao = dao;
        }
        @Override
        protected Void doInBackground(UserPreferenceRoomModel... userPreferenceRoomModels) {
            dao.insertOneNewsCategory(userPreferenceRoomModels[0]);
            return null;
        }
    }

    private static class DeleteAllPreferencesAsyncTask extends AsyncTask<Void, Void, Void>{

        private IUserPreferenceDao dao;

        private DeleteAllPreferencesAsyncTask(IUserPreferenceDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllPreferences();
            return null;
        }
    }

    private static class UpdatePreferencesAsyncTask extends AsyncTask<UserPreferenceRoomModel, Void, Void>{

        private IUserPreferenceDao dao;

        private UpdatePreferencesAsyncTask(IUserPreferenceDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(UserPreferenceRoomModel... preferences) {
            dao.updateUserPreference(preferences[0]);
            return null;
        }
    }
}
