package com.example.rapha.swipeprototype2.roomDatabase;

import com.example.rapha.swipeprototype2.categories.Categories;
import com.example.rapha.swipeprototype2.categories.Finance;
import com.example.rapha.swipeprototype2.categories.NewsCategory;

import java.util.Locale;

public class FillDatabase {

    public static void fillDatabase(UserPreferenceRepository repository){
        fillCategories(repository);
    }

    public static void fillCategories(UserPreferenceRepository repository){
        Categories newsCategories = new Categories();
        repository.insert(new UserPreferenceRoomModel(
                newsCategories.finance.getCategoryID(),
                newsCategories.finance.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsCategories.food.getCategoryID(),
                newsCategories.food.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsCategories.movie.getCategoryID(),
                newsCategories.movie.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsCategories.politics.getCategoryID(),
                newsCategories.politics.getRating()));
        repository.insert(new UserPreferenceRoomModel(
                newsCategories.technology.getCategoryID(),
                newsCategories.technology.getRating()));
    }
}
