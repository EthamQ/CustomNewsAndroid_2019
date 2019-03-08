package com.raphael.rapha.myNews.topics;

import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;

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
        germanPolitics.add("\"Merkel\" OR ");
        germanPolitics.add("\"Seehofer\" OR ");
        germanPolitics.add("\"SPD\" OR ");
        germanPolitics.add("\"CDU\" OR ");
        germanPolitics.add("\"FDP\" OR ");
        germanPolitics.add("\"Grüne\" OR ");
        germanPolitics.add("Bundestag OR ");
        germanPolitics.add("\"Bundeskanzler\"");
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


    /**
     * Receives a single topic display text and transforms it to a list of one or several
     * related words that deliver better search results when you send it to the api to look for them.
     * But the user shouldn't see this list of words, only the api.
     * @param keyWordRoomModel
     * @return
     */
    public LinkedList<TopicRoomModel> transformQueryStrings(TopicRoomModel keyWordRoomModel){
        LinkedList<TopicRoomModel> toReturn = new LinkedList<>();
        LinkedList<String> transformedEntries = dictionary.get(keyWordRoomModel.keyWord.toUpperCase());
        if(transformedEntries == null){
            toReturn.add(keyWordRoomModel);
            return toReturn;
        } else{
            for(int i = 0; i < transformedEntries.size(); i++){
                TopicRoomModel toAdd = new TopicRoomModel(transformedEntries.get(i), keyWordRoomModel.categoryId);
                toReturn.add(toAdd);
            }
            return toReturn;
        }
    }

    public String[] getKeyWordsFromTopics(TopicRoomModel topicToLookFor){
        List<TopicRoomModel> transformedKeyWords = transformQueryStrings(topicToLookFor);
        String[] keyWords = new String[transformedKeyWords.size()];
        for(int k = 0; k < keyWords.length; k++){
            keyWords[k] = transformedKeyWords.get(k).keyWord;
        }
        return keyWords;
    }
}
