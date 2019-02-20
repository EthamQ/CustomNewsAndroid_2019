package com.example.rapha.swipeprototype2.queryWords;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;

public class QueryWordService {

    public static String getCurrentLanguageString(MainActivity mainActivity){
        boolean[] activeLanguages = LanguageSettingsService.loadChecked(mainActivity);
        return buildLanguageString(activeLanguages);
    }

    public static String buildLanguageString(boolean[] languages){
        StringBuilder builder = new StringBuilder();
        final int indexEN = LanguageSettingsService.INDEX_ENGLISH;
        final int indexGER = LanguageSettingsService.INDEX_GERMAN;
        final int indexFR = LanguageSettingsService.INDEX_FRENCH;
        final int indexRU = LanguageSettingsService.INDEX_RUSSIAN;
        String[] allLanguages = LanguageSettingsService.languageItems;
        for(int i = 0; i < allLanguages.length; i++){
            if(languages[indexEN]){
                builder.append(LanguageSettingsService.languageItems[indexEN]);
            }
            if(languages[indexGER]){
                builder.append(LanguageSettingsService.languageItems[indexGER]);
            }
            if(languages[indexFR]){
                builder.append(LanguageSettingsService.languageItems[indexFR]);
            }
            if(languages[indexRU]){
                builder.append(LanguageSettingsService.languageItems[indexRU]);
            }
        }

        // don't change order!
        if(languages[indexEN]){
            builder.append(LanguageSettingsService.languageItems[indexEN]);
        }
        if(languages[indexGER]){
            builder.append(LanguageSettingsService.languageItems[indexGER]);
        }
        if(languages[indexFR]){
            builder.append(LanguageSettingsService.languageItems[indexFR]);
        }
        if(languages[indexRU]){
            builder.append(LanguageSettingsService.languageItems[indexRU]);
        }
        return builder.toString();
    }
}
