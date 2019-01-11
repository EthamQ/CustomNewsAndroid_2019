package com.example.rapha.swipeprototype2.UserPreferences;

import com.example.rapha.swipeprototype2.categories.Categories;

public class UserPreference {

    private Categories categoryPreference;

    public UserPreference(){
        this.categoryPreference = new Categories();
    }

    public Categories getCategories() {
        return this.categoryPreference;
    }

}
