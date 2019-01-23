package com.example.rapha.swipeprototype2.utils;

import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;
import com.example.rapha.swipeprototype2.roomDatabase.UserPreferenceRoomModel;

import java.util.HashMap;
import java.util.LinkedList;

public class ListService {

    public HashMap<Integer, UserPreferenceRoomModel> ratingListToHashMap(LinkedList<UserPreferenceRoomModel> ratings){
        HashMap<Integer, UserPreferenceRoomModel> ratingAsHashMap = new HashMap<>();
        for(int i = 0; i < ratings.size(); i++){
            int currentCategory = ratings.get(i).getNewsCategoryId();
            ratingAsHashMap.put(currentCategory, ratings.get(i));
        }
        return ratingAsHashMap;
    }
}
