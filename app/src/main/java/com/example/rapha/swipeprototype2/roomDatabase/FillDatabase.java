package com.example.rapha.swipeprototype2.roomDatabase;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;


public class FillDatabase {

    public static void fillDatabase(UserPreferenceRepository repository){
        fillCategories(repository);
    }

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
}
