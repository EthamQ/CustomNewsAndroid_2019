package com.example.rapha.swipeprototype2.utils;


import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ListService {

    public HashMap<Integer, UserPreferenceRoomModel> ratingListToHashMap(LinkedList<UserPreferenceRoomModel> ratings){
        HashMap<Integer, UserPreferenceRoomModel> ratingAsHashMap = new HashMap<>();
        for(int i = 0; i < ratings.size(); i++){
            int currentCategory = ratings.get(i).getNewsCategoryId();
            ratingAsHashMap.put(currentCategory, ratings.get(i));
        }
        return ratingAsHashMap;
    }

    public static LinkedList orderListRandomly(LinkedList list){
        LinkedList listCopy = new LinkedList();
        listCopy.addAll(list);
        LinkedList random = new LinkedList();
        int originalSize = listCopy.size();
        for(int i = 0; i < originalSize; i++){
            int randomIndex = RandomService.getRandomNumber(0, listCopy.size());
            random.add(listCopy.get(randomIndex));
            listCopy.remove(randomIndex);
        }
        return random;
    }

    public static LinkedList removeAllEntriesStartingAt(LinkedList list, int startIndex){
        for(int i = list.size() - 1; i > startIndex; i--){
            list.remove(i);
        }
        return list;
    }
}
