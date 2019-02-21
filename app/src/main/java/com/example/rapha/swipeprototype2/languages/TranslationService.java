package com.example.rapha.swipeprototype2.languages;

import android.util.Log;

public class TranslationService {

    public static String[] translateToGerman(String[] english){
        GermanDictionary german = new GermanDictionary();
        for(int i = 0; i < german.translateArrayOfWords(english).length; i++){
            Log.d("topiccc", "Translated words german: " + german.translateArrayOfWords(english)[i]);
        }
        return german.translateArrayOfWords(english);
    }

    public static String[] translateToRussian(String[] english){
        RussianDictionary russian = new RussianDictionary();
        for(int i = 0; i < russian.translateArrayOfWords(english).length; i++){
            Log.d("topiccc", "Translated words russian: " + russian.translateArrayOfWords(english)[i]);
        }
        return russian.translateArrayOfWords(english);
    }

    public static String[] translateToFrench(String[] english){
        FrenchDictionary french = new FrenchDictionary();
        return french.translateArrayOfWords(english);
    }


}
