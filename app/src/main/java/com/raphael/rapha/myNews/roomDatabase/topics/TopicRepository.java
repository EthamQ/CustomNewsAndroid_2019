package com.raphael.rapha.myNews.roomDatabase.topics;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.raphael.rapha.myNews.roomDatabase.AppDatabase;

import java.util.List;

public class TopicRepository {

    private LiveData<List<TopicRoomModel>> allKeyWords;
    private LiveData<List<TopicRoomModel>> allLikedKeyWords;
    private ITopicDao dao;

    public TopicRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.keyWordDao();
        allKeyWords = dao.getAllKeyWords();
        allLikedKeyWords = dao.getKeyWordsByStatus(TopicRoomModel.LIKED);
    }

    public void insert(TopicRoomModel keyWordRoomModel){
        new InsertKeyWordAsyncTask(dao).execute(keyWordRoomModel);
    }

    public void deleteAll(){
        new DeleteAllKeyWordsAsyncTask (dao).execute();
    }

    public void update(TopicRoomModel keyWordRoomModel){
        new UpdateOneKeyWordAsyncTask (dao).execute(keyWordRoomModel);
    }

    public void setUsedInArticleOfTheDay(TopicRoomModel keyWordRoomModel, boolean usedInArticleOfTheDay){
        keyWordRoomModel.usedInArticleOfTheDay = usedInArticleOfTheDay;
        update(keyWordRoomModel);
    }

    public LiveData<List<TopicRoomModel>> getAllKeyWords(){
        return allKeyWords;
    }

    public LiveData<List<TopicRoomModel>> getAllKeyWords(boolean usedInArticleOfTheDay){
        return dao.getAllKeyWords(usedInArticleOfTheDay);
    }

    public LiveData<List<TopicRoomModel>> getAllLikedKeyWords(){
        return allLikedKeyWords;
    }

    private static class InsertKeyWordAsyncTask extends AsyncTask<TopicRoomModel, Void, Void> {
        private ITopicDao dao;
        private InsertKeyWordAsyncTask(ITopicDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(TopicRoomModel... keyWordRoomModels) {
            dao.insertOneKeyWord(keyWordRoomModels[0]);
            return null;
        }
    }

    private static class DeleteAllKeyWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ITopicDao dao;
        private DeleteAllKeyWordsAsyncTask(ITopicDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllKeyWords();
            return null;
        }
    }

    private static class UpdateOneKeyWordAsyncTask extends AsyncTask<TopicRoomModel, Void, Void> {
        private ITopicDao dao;
        private UpdateOneKeyWordAsyncTask (ITopicDao dao){
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(TopicRoomModel... keyWordRoomModels) {
            dao.updateKeyWord(keyWordRoomModels[0]);
            return null;
        }
    }
}
