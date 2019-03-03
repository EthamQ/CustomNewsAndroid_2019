package com.raphael.rapha.myNews.roomDatabase.categoryRating;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.raphael.rapha.myNews.roomDatabase.AppDatabase;

import java.util.List;

public class NewsCategoryRatingRepository {
    private INewsCategoryRatingDao dao;
    private LiveData<List<NewsCategoryRatingRoomModel>> allCategories;

    public NewsCategoryRatingRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.dao();
        allCategories = dao.getAllUserPreferences();
    }

    public void insert(NewsCategoryRatingRoomModel userPreferenceRoomModel){
        new InsertPreferenceAsyncTask(dao).execute(userPreferenceRoomModel);
    }

    public void update(NewsCategoryRatingRoomModel userPreferenceRoomModel){
        new UpdatePreferencesAsyncTask(dao).execute(userPreferenceRoomModel);
    }

    public void delete(){

    }

    public void deleteAll(){
        new DeleteAllPreferencesAsyncTask(dao).execute();
    }

    public LiveData<List<NewsCategoryRatingRoomModel>> getAllUserPreferences(){
        return allCategories;
    }

    private static class InsertPreferenceAsyncTask extends AsyncTask<NewsCategoryRatingRoomModel, Void, Void>{

        private INewsCategoryRatingDao dao;

        private InsertPreferenceAsyncTask(INewsCategoryRatingDao dao){
                this.dao = dao;
        }
        @Override
        protected Void doInBackground(NewsCategoryRatingRoomModel... userPreferenceRoomModels) {
            dao.insertOneNewsCategory(userPreferenceRoomModels[0]);
            return null;
        }
    }

    private static class DeleteAllPreferencesAsyncTask extends AsyncTask<Void, Void, Void>{

        private INewsCategoryRatingDao dao;

        private DeleteAllPreferencesAsyncTask(INewsCategoryRatingDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllPreferences();
            return null;
        }
    }

    private static class UpdatePreferencesAsyncTask extends AsyncTask<NewsCategoryRatingRoomModel, Void, Void>{

        private INewsCategoryRatingDao dao;

        private UpdatePreferencesAsyncTask(INewsCategoryRatingDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(NewsCategoryRatingRoomModel... preferences) {
            dao.updateUserPreference(preferences[0]);
            return null;
        }
    }
}
