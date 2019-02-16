package com.example.rapha.swipeprototype2.questionCards;

import android.util.Log;

import com.example.rapha.swipeprototype2.api.ApiService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.ISwipeCard;
import com.example.rapha.swipeprototype2.swipeCardContent.QuestionSwipeCard;
import com.example.rapha.swipeprototype2.utils.DateUtils;
import com.example.rapha.swipeprototype2.utils.CollectionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionCardService {

    final static private int AMOUNT_QUESTIONS = ApiService.MAX_NUMBER_OF_ARTICLES / 5;

    /**
     * Add question swipe cards at random indices to the list of swipe cards.
     * @param swipeCardsList
     */
    public static void mixQuestionCardsIntoSwipeCards(ArrayList<ISwipeCard> swipeCardsList, List<KeyWordRoomModel> keyWords){
        Log.d("questioncard", "mixQuestionCardsIntoSwipeCards");
        LinkedList<QuestionSwipeCard> questionSwipeCards = generateQuestionCards(keyWords, (swipeCardsList.size() / 4));
        // Generate random indices.
        int[] randomIndices = new int[questionSwipeCards.size()];
        for(int i = 0; i < randomIndices.length; i++){
            int randomIndex = ThreadLocalRandom.current().nextInt(2, swipeCardsList.size());
            randomIndices[i] = randomIndex;
        }

        // Add the question swipe cards to the original swipeCardsList at every random generated index.
        for(int i = 0; i < randomIndices.length; i++){
            swipeCardsList.add(randomIndices[i], questionSwipeCards.get(i));
        }
    }

    private static LinkedList<QuestionSwipeCard> generateQuestionCards(List<KeyWordRoomModel> keyWords, int amount){
        LinkedList<QuestionSwipeCard> questionCards = new LinkedList();
                for(int i = 0; i < keyWords.size(); i++){
                    if(questionShouldBeAsked(keyWords.get(i))){
                        QuestionSwipeCard questionCard = new QuestionSwipeCard(
                                keyWords.get(i).keyWord,
                                keyWords.get(i).categoryId
                        );
                        questionCards.add(questionCard);
                    }
                }
                questionCards = CollectionService.orderListRandomly(questionCards);
                questionCards = (LinkedList)CollectionService.removeAllEntriesStartingAt(questionCards, amount);
                Log.d("questioncard", "Number of cards: " + questionCards.size());
                return questionCards;
    }

    private static boolean questionShouldBeAsked(KeyWordRoomModel keyWordRoomModel){
        if(keyWordRoomModel.shownToUser == null){
            return true;
        }
        Date today = new Date();
        Date lastShown = keyWordRoomModel.shownToUser;
        int dayDifference = DateUtils.daysBetween(lastShown, today);
        Log.d("daydifference", "Today: " + today.toString() + "Shown: " + lastShown.toString() + "Difference: " + dayDifference);
        return dayDifference >= 7;
    }

}

