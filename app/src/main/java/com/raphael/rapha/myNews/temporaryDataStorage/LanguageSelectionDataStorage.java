package com.raphael.rapha.myNews.temporaryDataStorage;

import android.util.Log;

public class LanguageSelectionDataStorage {

    private static boolean languageSelectionBackup[];

    public static void backUpPreviousLanguageSelection(boolean [] languageSelection){
        languageSelectionBackup = languageSelection;
    }

    public static boolean[] restorePreviousLanguageSelection(){
        return languageSelectionBackup;
    }
}
