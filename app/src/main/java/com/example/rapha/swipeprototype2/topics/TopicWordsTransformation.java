package com.example.rapha.swipeprototype2.topics;

import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TopicWordsTransformation {

    HashMap<String, LinkedList<String>> dictionary;

    public TopicWordsTransformation(){
        dictionary = new HashMap<>();
        initTransformDictionary();
    }

    private void initTransformDictionary(){
        LinkedList<String> germanPolitics = new LinkedList<>();
        germanPolitics.add("Merkel");
        germanPolitics.add("Seehofer");
        germanPolitics.add("SPD");
        germanPolitics.add("CDU");
        germanPolitics.add("FDP");
        germanPolitics.add("Grüne");
        germanPolitics.add("Sigmar Gabriel");
        germanPolitics.add("Bundeskanzler");
        dictionary.put("German Politics".toUpperCase(), germanPolitics);

        LinkedList<String> programming = new LinkedList<>();
        programming.add("C++");
        programming.add("Java");
        programming.add("Python");
        programming.add("Javascript");
        programming.add("Typescript");
        programming.add("Algorithm");
        dictionary.put("Programming".toUpperCase(), programming);

        LinkedList<String> drones = new LinkedList<>();
        programming.add("drone");
        dictionary.put("drones".toUpperCase(), drones);

        LinkedList<String> cooking = new LinkedList<>();
        programming.add("cook");
        dictionary.put("Cooking".toUpperCase(), cooking);

        LinkedList<String> recipes = new LinkedList<>();
        programming.add("recipe");
        dictionary.put("Recipes".toUpperCase(), recipes);
    }


    public LinkedList<KeyWordRoomModel> transformQueryStrings(KeyWordRoomModel keyWordRoomModel){
        LinkedList<KeyWordRoomModel> toReturn = new LinkedList<>();
        LinkedList<String> transformedEntries = dictionary.get(keyWordRoomModel.keyWord.toUpperCase());
        if(transformedEntries == null){
            toReturn.add(keyWordRoomModel);
            return toReturn;
        } else{
            for(int i = 0; i < transformedEntries.size(); i++){
                KeyWordRoomModel toAdd = new KeyWordRoomModel(transformedEntries.get(i), keyWordRoomModel.categoryId);
                toReturn.add(toAdd);
            }
            return toReturn;
        }
    }

    public String[] getKeyWordsFromTopics(KeyWordRoomModel topicToLookFor){
        List<KeyWordRoomModel> transformedKeyWords = transformQueryStrings(topicToLookFor);
        String[] keyWords = new String[transformedKeyWords.size()];
        for(int k = 0; k < keyWords.length; k++){
            keyWords[k] = transformedKeyWords.get(k).keyWord;
        }
        return keyWords;
    }
}
