package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.IInsertsLanguageCombination;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationData;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRepository;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRepository;

import java.util.List;

public class LanguageCombinationDbService {

    private static LanguageCombinationDbService instance;
    LanguageCombinationRepository repository;

    public LanguageCombinationDbService(Application application){
        this.repository = new LanguageCombinationRepository(application);
    }

    public static synchronized LanguageCombinationDbService getInstance(Application application){
        if(instance == null){
            instance = new LanguageCombinationDbService(application);
        }
        return instance;
    }

    public int insertLanguageCombination(LanguageCombinationData data, boolean[] languageCombination){
        LanguageCombinationRoomModel combo = new LanguageCombinationRoomModel();
        Log.d("eee", "insertLanguageCombination" + combo.id);
        final int indexEN = LanguageSettingsService.INDEX_ENGLISH;
        final int indexGER = LanguageSettingsService.INDEX_GERMAN;
        final int indexFR = LanguageSettingsService.INDEX_FRENCH;
        final int indexRU = LanguageSettingsService.INDEX_RUSSIAN;
        for(int i = 0; i < languageCombination.length; i++){
            if(languageCombination[indexEN]){
                combo.english = true;
            }
            if(languageCombination[indexGER]){
                combo.german = true;
            }
            if(languageCombination[indexFR]){
                combo.french = true;
            }
            if(languageCombination[indexRU]){
                combo.russian = true;
            }
        }
        repository.insert(combo, data);
        return combo.id;
    }

    public LiveData<List<LanguageCombinationRoomModel>> getAll(){
        return repository.getAll();
    }

    public void getAllRemoveObserver(Observer observer){ repository.getAllRemoveObserver(observer); }

    public static boolean languageSelectionIsEqual(boolean[] currentLanguages, LanguageCombinationRoomModel currentCombination){
        return currentCombination.german == currentLanguages[LanguageSettingsService.INDEX_GERMAN]
                && currentCombination.french == currentLanguages[LanguageSettingsService.INDEX_FRENCH]
                && currentCombination.russian == currentLanguages[LanguageSettingsService.INDEX_RUSSIAN]
                && currentCombination.english == currentLanguages[LanguageSettingsService.INDEX_ENGLISH];
    }
}
