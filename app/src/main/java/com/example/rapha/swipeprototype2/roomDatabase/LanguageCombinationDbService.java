package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;

import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRepository;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRepository;

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

    public int insertLanguageCombination(boolean[] languageCombination){
        LanguageCombinationRoomModel combo = new LanguageCombinationRoomModel();
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
        repository.insert(combo);
        return combo.id;
    }
}
