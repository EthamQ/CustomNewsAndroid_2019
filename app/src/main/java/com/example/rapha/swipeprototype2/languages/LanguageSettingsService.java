package com.example.rapha.swipeprototype2.languages;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.sharedPreferencesAccess.SharedPreferencesService;

import java.util.LinkedList;
import java.util.List;

public class LanguageSettingsService {

    public static final String[] languageItems = {" English", " German", " Russian", " French"};
    // Never change those final values below!
    public static final int INDEX_ENGLISH = 0;
    public static final int INDEX_GERMAN = 1;
    public static final int INDEX_RUSSIAN = 2;
    public static final int INDEX_FRENCH = 3;
    public final static String GERMAN = "de";
    public final static String ENGLISH = "en";
    public final static String RUSSIAN = "ru";
    public final static String FRENCH = "fr";

    public static String getLanguageIdAsString(int languageId){
        switch (languageId){
            case INDEX_ENGLISH: return ENGLISH;
            case INDEX_GERMAN: return GERMAN;
            case INDEX_RUSSIAN : return RUSSIAN ;
            case INDEX_FRENCH: return FRENCH;
            default: return ENGLISH;
        }
    }

    /**
     * Save the checked languages.
     * @param context
     * @param isChecked
     */
    public static void saveChecked(Context context, final boolean[] isChecked) {
        if(!(isChecked == null) && context != null){
            for(Integer i = 0; i < isChecked.length; i++)
            {
                SharedPreferencesService.storeDataDefault(context, isChecked[i], i.toString());
            }
        }
    }

    /**
     * Return which languages are currently selected by the user.
     * @param context
     * @return
     */
    public static boolean[] loadChecked(Context context) {
        boolean [] languageCheckboxes = new boolean[languageItems.length];
        if(context != null){
            for(Integer i = 0; i < languageItems.length; i++)
            {
                languageCheckboxes[i] = SharedPreferencesService.getBooleanDefault(context, i.toString());
            }
            setDefaultEnglish(languageCheckboxes);
        }
        return languageCheckboxes;
    }

    /**
     * Save the currently selected languages so when we request the news of the day from the
     * database we know which languages were selected when we loaded the new articles of the day
     * even if the user changed the languages again during that time.
     * @param context
     */
    public static void saveCheckedLoadedNewsOfTheDay(Context context) {
        boolean[] currentLanguageSelection = loadChecked(context);
        if(!(currentLanguageSelection == null) && context != null){
            for(Integer i = 0; i < currentLanguageSelection.length; i++)
            {
                SharedPreferencesService.storeDataDefault(
                        context,
                        currentLanguageSelection[i],
                        getLanguageIdAsString(i));
            }
        }
    }

    /**
     * Load which languages were currently selected when we loaded new articles of the day the last time.
     * @param context
     * @return
     */
    public static boolean[] loadCheckedLoadedNewsOfTheDay(Context context) {
        boolean [] languageCheckboxes = new boolean[languageItems.length];
        if(context != null){
            for(Integer i = 0; i < languageItems.length; i++)
            {
                languageCheckboxes[i] = SharedPreferencesService.getBooleanDefault(context, getLanguageIdAsString(i));
            }
            setDefaultEnglish(languageCheckboxes);
        }
        return languageCheckboxes;
    }

    /**
     * Return wether or not the two language selections are equal.
     * @param initialSelection
     * @param newSelection
     * @return
     */
    public static boolean userChangedLanguage(boolean[] initialSelection, boolean[] newSelection){
        for(int i = 0; i < initialSelection.length; i++){
            if(initialSelection[i] != newSelection[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * Generates an int array with the given size.
     * At every position there will be a language id of the given language selection that is true (selected).
     * They alternate, so if index 2 and 3 are true the returned array will look something like that:
     * 23232323
     * @param size
     * @param languageSelection
     * @return
     */
    public static int[] generateLanguageDistributionNewsOfTheDay(int size, boolean[] languageSelection){
        // Store the id for every active language in a list
        List<Integer> allActiveLanguageIds = new LinkedList<>();
        for(int id = 0; id < languageSelection.length; id++){
            if(languageSelection[id]){
                allActiveLanguageIds.add(id);
            }
        }

        // Add the language ids alternately to the int array.
        int[] languageDistribution = new int[size];
        int numberOfLanguages = allActiveLanguageIds.size();
        int j = 0;
        for(int i = 0; i < languageDistribution.length; i++){
            languageDistribution[i] = allActiveLanguageIds.get(j++ % numberOfLanguages);
        }

        return languageDistribution;
    }

    /**
     * If all values of the boolean array are false set the index that
     * represents english to true.
     * @param languages
     */
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
