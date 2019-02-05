package com.example.rapha.swipeprototype2.languageSettings;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

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

    public static boolean[] loadChecked(SwipeFragment swipeFragment) {
        SharedPreferences sharedPreferences = swipeFragment.mainActivity.getPreferences(Context.MODE_PRIVATE);
        boolean [] languageCheckboxes = new boolean[languageItems.length];
        for(Integer i = 0; i < languageItems.length; i++)
        {
            languageCheckboxes[i] = sharedPreferences.getBoolean(i.toString(), false);
        }
        setDefaultEnglish(languageCheckboxes);
        return languageCheckboxes;
    }

    private static void setDefaultEnglish(boolean [] languages){
        boolean languageSet = false;
        for(int i = 0; i < languages.length; i++){
            if(languages[i]){
                languageSet = true;
                break;
            }
        }
        if(!languageSet){
            languages[LanguageSettingsService.INDEX_ENGLISH] = true;
        }
    }

}
