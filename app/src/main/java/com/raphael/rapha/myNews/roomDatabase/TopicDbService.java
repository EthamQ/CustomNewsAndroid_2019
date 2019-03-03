package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.raphael.rapha.myNews.roomDatabase.topics.TopicRepository;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;

import java.util.List;

public class TopicDbService {

    private static TopicDbService instance;
    TopicRepository repository;

    private TopicDbService(Application application){
        this.repository = new TopicRepository(application);
        FillDatabase.fillKeyWords(repository);
    }

    public static synchronized TopicDbService getInstance(Application application){
        if(instance == null){
            instance = new TopicDbService(application);
        }
        return instance;
    }

    public void insert(TopicRoomModel keyWordRoomModel){
        repository.insert(keyWordRoomModel);
    }

    public void deleteAll(){
        repository.deleteAll();
        FillDatabase.fillKeyWords(repository);
    }

    public void update(TopicRoomModel keyWordRoomModel){
        repository.update(keyWordRoomModel);
    }

    public void setAsNewsOfTheDayKeyWord(TopicRoomModel keyWordRoomModel){
        repository.setUsedInArticleOfTheDay(keyWordRoomModel, true);
    }

    public void removeAsNewsOfTheDayKeyWord(TopicRoomModel keyWordRoomModel){
        repository.setUsedInArticleOfTheDay(keyWordRoomModel, false);
    }

    public LiveData<List<TopicRoomModel>> getAllKeyWords(){
        return repository.getAllKeyWords();
    }

    public LiveData<List<TopicRoomModel>> getAllKeyWordsArticlesOfTheDay(){
        return repository.getAllKeyWords(true);
    }

    public LiveData<List<TopicRoomModel>> getAllLikedKeyWords(){
        return repository.getAllLikedKeyWords();
    }

    public void resetDailyKeyWords(){
        getAllKeyWordsArticlesOfTheDay().observeForever(new Observer<List<TopicRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<TopicRoomModel> keyWordRoomModels) {
                for(int i = 0; i < keyWordRoomModels.size(); i++){
                    removeAsNewsOfTheDayKeyWord(keyWordRoomModels.get(i));
                }
                getAllKeyWordsArticlesOfTheDay().removeObserver(this);
            }
        });
    }
}
