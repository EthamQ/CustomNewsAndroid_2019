package com.raphael.rapha.myNews.languages;

import android.util.Log;

public class TranslationService {

    public static String[] translateEnglishTo(String[] english, int languageId){
        switch(languageId){
            case LanguageSettingsService.INDEX_FRENCH: return translateToFrench(english);
            case LanguageSettingsService.INDEX_GERMAN: return translateToGerman(english);
            case LanguageSettingsService.INDEX_RUSSIAN: return translateToRussian(english);
            case LanguageSettingsService.INDEX_ENGLISH: return english;
            default: return english;
        }
    }

    public static String[] translateToGerman(String[] english){
        GermanDictionary german = new GermanDictionary();
        return german.translateArrayOfWords(english);
    }

    public static String[] translateToRussian(String[] english){
        RussianDictionary russian = new RussianDictionary();
        return russian.translateArrayOfWords(english);
    }

    public static String[] translateToFrench(String[] english){
        FrenchDictionary french = new FrenchDictionary();
        return french.translateArrayOfWords(english);
    }


}
