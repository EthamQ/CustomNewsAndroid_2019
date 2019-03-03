package com.raphael.rapha.myNews.temporaryDataStorage;

import android.util.Log;

public class LanguageSelectionDataStorage {

    private static boolean languageSelectionBackup[];

    public static void backUpPreviousLanguageSelection(boolean [] languageSelection){
        Log.d("bbuppp", "set backup language");
        languageSelectionBackup = languageSelection;
    }

    public static boolean[] restorePreviousLanguageSelection(){
        Log.d("bbuppp", "get backup language");
        return languageSelectionBackup;
    }
}
