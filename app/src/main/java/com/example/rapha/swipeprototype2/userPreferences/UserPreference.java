package com.example.rapha.swipeprototype2.userPreferences;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

public class UserPreference {

    private NewsCategoryContainer categoryPreference;

    public UserPreference(){
        this.categoryPreference = new NewsCategoryContainer();
    }

    public NewsCategoryContainer getCategories() {
        return this.categoryPreference;
    }

}
