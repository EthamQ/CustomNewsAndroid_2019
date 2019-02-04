package com.example.rapha.swipeprototype2.questionCards;

import android.widget.LinearLayout;

import com.example.rapha.swipeprototype2.models.ISwipeCard;
import com.example.rapha.swipeprototype2.models.QuestionSwipeCard;
import com.example.rapha.swipeprototype2.newsCategories.NewsCategoryContainer;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuestionCardService {

    final private int AMOUNT_QUESTIONS = 10;

    public static LinkedList<QuestionSwipeCard> getQuestionCards(){
        LinkedList<QuestionSwipeCard> questionCards = new LinkedList();
        NewsCategoryContainer categoryContainer = new NewsCategoryContainer();

        for(int i = 0; i < categoryContainer.allCategories.size(); i++){
            QuestionSwipeCard questionCard = new QuestionSwipeCard(
                    categoryContainer.allCategories.get(i).displayName
            );
            questionCards.add(questionCard);
        }
        return questionCards;
    }

    public static LinkedList<ISwipeCard> mixQuestionCardsIntoSwipeCards(){
        LinkedList<ISwipeCard> newList = new LinkedList<>();
        return newList;
    }
}
