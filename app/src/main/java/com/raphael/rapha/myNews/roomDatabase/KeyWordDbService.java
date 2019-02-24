package com.raphael.rapha.myNews.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRepository;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.List;

public class KeyWordDbService {

    private static KeyWordDbService instance;
    KeyWordRepository repository;

    private KeyWordDbService(Application application){
        this.repository = new KeyWordRepository(application);
        FillDatabase.fillKeyWords(repository);
    }

    public static synchronized KeyWordDbService getInstance(Application application){
        if(instance == null){
            instance = new KeyWordDbService(application);
        }
        return instance;
    }

    public void insert(KeyWordRoomModel keyWordRoomModel){
        repository.insert(keyWordRoomModel);
    }

    public void deleteAll(){
        repository.deleteAll();
        FillDatabase.fillKeyWords(repository);
    }

    public void update(KeyWordRoomModel keyWordRoomModel){
        repository.update(keyWordRoomModel);
    }

    public void setAsNewsOfTheDayKeyWord(KeyWordRoomModel keyWordRoomModel){
        repository.setUsedInArticleOfTheDay(keyWordRoomModel, true);
    }

    public void removeAsNewsOfTheDayKeyWord(KeyWordRoomModel keyWordRoomModel){
        repository.setUsedInArticleOfTheDay(keyWordRoomModel, false);
    }

    public LiveData<List<KeyWordRoomModel>> getAllKeyWords(){
        return repository.getAllKeyWords();
    }

    public LiveData<List<KeyWordRoomModel>> getAllKeyWordsArticlesOfTheDay(){
        return repository.getAllKeyWords(true);
    }

    public LiveData<List<KeyWordRoomModel>> getAllLikedKeyWords(){
        return repository.getAllLikedKeyWords();
    }

    public void resetDailyKeyWords(){
        getAllKeyWordsArticlesOfTheDay().observeForever(new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> keyWordRoomModels) {
                for(int i = 0; i < keyWordRoomModels.size(); i++){
                    removeAsNewsOfTheDayKeyWord(keyWordRoomModels.get(i));
                }
                getAllKeyWordsArticlesOfTheDay().removeObserver(this);
            }
        });
    }
}
