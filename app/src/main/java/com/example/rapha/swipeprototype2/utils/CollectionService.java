package com.example.rapha.swipeprototype2.utils;


import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CollectionService {

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

    public static List removeAllEntriesStartingAt(List list, int startIndex){
        for(int i = list.size() - 1; i > startIndex; i--){
            if(i >= 0){
                list.remove(i);
            }
        }
        return list;
    }

    public static boolean arrayHasNullValues(Object[] array){
        for(int i = 0; i < array.length; i++){
            if(array[i] == null){
                return true;
            }
        }
        return false;
    }
}
