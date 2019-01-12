package com.example.rapha.swipeprototype2.UserPreferences;

import com.example.rapha.swipeprototype2.categories.Categories;
import com.example.rapha.swipeprototype2.categories.NewsCategory;

public class UserPreference {

    private Categories categoryPreference;

    public UserPreference(){
        this.categoryPreference = new Categories();
    }

    public Categories getCategories() {
        return this.categoryPreference;
    }

    @Override
    public String toString(){
        String ret = "";
        NewsCategory technology = categoryPreference.technology;
        final NewsCategory finance = categoryPreference.finance;
        final NewsCategory politics = categoryPreference.politics;
        final NewsCategory food = categoryPreference.food;
        final NewsCategory movie = categoryPreference.movie;
        ret += "CategoryId: " + technology.getCategoryID() + "Rating: " + technology.getRating();
        ret += "\n";
        ret += "CategoryId: " + finance.getCategoryID() + "Rating: " + finance.getRating();
        ret += "\n";
        ret += "CategoryId: " + politics.getCategoryID() + "Rating: " + politics.getRating();
        ret += "\n";
        ret += "CategoryId: " + food.getCategoryID() + "Rating: " + food.getRating();
        ret += "\n";
        ret += "CategoryId: " + movie.getCategoryID() + "Rating: " + movie.getRating();
        ret += "\n";
        return ret;

    }

}
