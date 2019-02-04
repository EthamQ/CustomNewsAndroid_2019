package com.example.rapha.swipeprototype2.questionCards;

import com.example.rapha.swipeprototype2.models.ISwipeCard;
import com.example.rapha.swipeprototype2.models.QuestionSwipeCard;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategory;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionCardService {

    final private int AMOUNT_QUESTIONS = 10;

    /**
     * Add question swipe cards at random indices to the list of swipe cards.
     * @param swipeCardsList
     */
    public static void mixQuestionCardsIntoSwipeCards(ArrayList<ISwipeCard> swipeCardsList){
        LinkedList<QuestionSwipeCard> questionSwipeCards = generateQuestionCards();
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

    private static LinkedList<QuestionSwipeCard> generateQuestionCards(){
        LinkedList<QuestionSwipeCard> questionCards = new LinkedList();
        NewsCategoryContainer categoryContainer = new NewsCategoryContainer();
        for(int i = 0; i < categoryContainer.allCategories.size(); i++){
            NewsCategory currentCategory = categoryContainer.allCategories.get(i);
            QuestionSwipeCard questionCard = new QuestionSwipeCard(
                    currentCategory.displayName,
                    currentCategory.getCategoryID()
            );
            questionCards.add(questionCard);
        }
        return questionCards;
    }
}

