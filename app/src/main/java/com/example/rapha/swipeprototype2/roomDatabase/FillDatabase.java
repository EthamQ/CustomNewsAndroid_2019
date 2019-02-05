package com.example.rapha.swipeprototype2.roomDatabase;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRepository;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRepository;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;


public class FillDatabase {

    /**
     * Fill the table that contains the categories with its corresponding ratings
     * if it is empty.
     * @param repository
     */
    public static void fillCategories(UserPreferenceRepository repository){
        NewsCategoryContainer newsNewsCategoryContainer = new NewsCategoryContainer();
        repository.insert(new UserPreferenceRoomModel(
                newsNewsCategoryContainer.finance.getCategoryID(),
                newsNewsCategoryContainer.finance.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsNewsCategoryContainer.food.getCategoryID(),
                newsNewsCategoryContainer.food.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsNewsCategoryContainer.movie.getCategoryID(),
                newsNewsCategoryContainer.movie.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsNewsCategoryContainer.politics.getCategoryID(),
                newsNewsCategoryContainer.politics.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsNewsCategoryContainer.technology.getCategoryID(),
                newsNewsCategoryContainer.technology.getRating()));
    }


    static String[] politicQueryWords = new String[] {
            "Trump", "Putin", "Merkel", "Macron", "Russia",
            "USA", "Syria", "ISIS", "war", "weapons", "Erdogan"
    };
    public static void fillKeyWords(KeyWordRepository repository){
        // repository.deleteAll();
        for(int i = 0; i < politicQueryWords.length; i++){
            repository.insert(new KeyWordRoomModel(
                    politicQueryWords[i],
                    NewsCategoryContainer.Politics.CATEGORY_ID)
            );
        }
    }
}
