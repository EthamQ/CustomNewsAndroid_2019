package com.example.rapha.swipeprototype2.languages;

public class TranslationService {

    public static String[] translateToGerman(String[] english){
        German german = new German();
        return german.translateToGerman(english);
    }

    public static String[] translateToFrench(String[] english){
        return new String[0];
    }

    public static String[] translateToRussian(String[] english){
        return new String[0];
    }
}
