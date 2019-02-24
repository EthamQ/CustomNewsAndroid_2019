package com.raphael.rapha.myNews.languages;

import android.util.Log;

import java.util.HashMap;

public abstract class LanguageDictionary {

    HashMap<String, String> dictionary = new HashMap<>();

    protected abstract void createDictionary();

    public String[] translateArrayOfWords(String[] english){
        String[] translationArray = new String[english.length];
        for(int i = 0; i < english.length; i++){
            String translation = translateWord(english[i].toUpperCase());
            translationArray[i] = translation.length() > 0 ? translation : english[i];
        }
        return translationArray;
    }

    protected String translateWord(String english){
        Log.d("transll", "English word: " + english);
        Log.d("transll", "Translated word: " + dictionary.get(english));
        String translation = dictionary.get(english);
        return translation != null ? translation : "";
    }


}
