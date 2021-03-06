package com.raphael.rapha.myNews.questionCards;

import android.util.Log;

import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.ISwipeCard;
import com.raphael.rapha.myNews.swipeCardContent.QuestionSwipeCard;
import com.raphael.rapha.myNews.generalServices.DateService;
import com.raphael.rapha.myNews.generalServices.CollectionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionCardService {

    /**
     * Add question swipe cards at random indices to the list of swipe cards.
     * @param swipeCardsList
     */
    public static void mixQuestionCardsIntoSwipeCards(ArrayList<ISwipeCard> swipeCardsList, List<TopicRoomModel> keyWords){
        Log.d("questioncard", "mixQuestionCardsIntoSwipeCards");
        LinkedList<QuestionSwipeCard> questionSwipeCards = generateQuestionCards(keyWords, (swipeCardsList.size() / 3));
        // Generate random indices.
        int[] randomIndices = new int[questionSwipeCards.size()];
        for(int i = 0; i < randomIndices.length; i++){
            if(swipeCardsList.size() > 2){
                int randomIndex = ThreadLocalRandom.current().nextInt(2, swipeCardsList.size());
                randomIndices[i] = randomIndex;
            }
        }

        // Add the question swipe cards to the original swipeCardsList at every random generated index.
        for(int i = 0; i < randomIndices.length; i++){
            swipeCardsList.add(randomIndices[i], questionSwipeCards.get(i));
        }
    }

    private static LinkedList<QuestionSwipeCard> generateQuestionCards(List<TopicRoomModel> keyWords, int amount){
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

    private static boolean questionShouldBeAsked(TopicRoomModel keyWordRoomModel){
        if(keyWordRoomModel.shownToUser == null){
            return true;
        }
        Date today = new Date();
        Date lastShown = keyWordRoomModel.shownToUser;
        int dayDifference = DateService.daysBetween(lastShown, today);
        Log.d("daydifference", "Today: " + today.toString() + "Shown: " + lastShown.toString() + "Difference: " + dayDifference);
        return dayDifference >= 7;
    }

}

