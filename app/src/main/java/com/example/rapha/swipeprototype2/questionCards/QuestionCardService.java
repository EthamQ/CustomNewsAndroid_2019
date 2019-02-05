package com.example.rapha.swipeprototype2.questionCards;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.ISwipeCard;
import com.example.rapha.swipeprototype2.swipeCardContent.QuestionSwipeCard;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionCardService {

    final private int AMOUNT_QUESTIONS = 10;

    /**
     * Add question swipe cards at random indices to the list of swipe cards.
     * @param swipeCardsList
     */
    public static void mixQuestionCardsIntoSwipeCards(ArrayList<ISwipeCard> swipeCardsList, List<KeyWordRoomModel> keyWords){
        LinkedList<QuestionSwipeCard> questionSwipeCards = generateQuestionCards(keyWords);
        // Generate random indices.
        int[] randomIndices = new int[questionSwipeCards.size()];
        for(int i = 0; i < randomIndices.length; i++){
            int randomIndex = ThreadLocalRandom.current().nextInt(0, swipeCardsList.size());
            randomIndices[i] = randomIndex;
        }

        // Add the question swipe cards to the original swipeCardsList at every random generated index.
        for(int i = 0; i < randomIndices.length; i++){
            swipeCardsList.add(randomIndices[i], questionSwipeCards.get(i));
        }
    }

    private static LinkedList<QuestionSwipeCard> generateQuestionCards(List<KeyWordRoomModel> keyWords){
        LinkedList<QuestionSwipeCard> questionCards = new LinkedList();
                for(int i = 0; i < keyWords.size(); i++){
                    QuestionSwipeCard questionCard = new QuestionSwipeCard(
                            keyWords.get(i).keyWord,
                            keyWords.get(i).categoryId
                    );
                    questionCards.add(questionCard);
                }
        return questionCards;
    }
}

