package com.example.rapha.swipeprototype2.languageSettings;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class LanguageSettingsService {

    public static final String[] languageItems = {" English", " German", " Russian", " French"};
    public static final int INDEX_ENGLISH = 0;
    public static final int INDEX_GERMAN = 1;
    public static final int INDEX_RUSSIAN = 2;
    public static final int INDEX_FRENCH = 3;

    public static void saveChecked(MainActivity mainActivity, final boolean[] isChecked) {
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(Integer i = 0; i < isChecked.length; i++)
        {
            editor.putBoolean(i.toString(), isChecked[i]);
        }
        editor.commit();
    }

    public static boolean[] loadChecked(MainActivity mainActivity) {
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        boolean [] reChecked = new boolean[languageItems.length];
        for(Integer i = 0; i < languageItems.length; i++)
        {
            reChecked[i] = sharedPreferences.getBoolean(i.toString(), false);
        }
        return reChecked;
    }

}
