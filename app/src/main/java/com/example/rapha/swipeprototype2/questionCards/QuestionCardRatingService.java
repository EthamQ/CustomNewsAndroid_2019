package com.example.rapha.swipeprototype2.questionCards;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.QuestionSwipeCard;

import java.util.Date;

public class QuestionCardRatingService {

    public static void likeKeyWord(MainActivity mainActivity, QuestionSwipeCard questionSwipeCard){
        setQuestionCardStatus(mainActivity, questionSwipeCard, KeyWordRoomModel.LIKED);
    }

    public static void dislikeKeyWord(MainActivity mainActivity, QuestionSwipeCard questionSwipeCard){
        setQuestionCardStatus(mainActivity, questionSwipeCard, KeyWordRoomModel.DISLIKED);
    }

    private static void setQuestionCardStatus(MainActivity mainActivity, QuestionSwipeCard questionSwipeCard, int status){
        KeyWordRoomModel keyWordRoomModel = new KeyWordRoomModel(
                questionSwipeCard.questionKeyword,
                questionSwipeCard.getNewsCategory()
        );
        keyWordRoomModel.status = status;
        keyWordRoomModel.shownToUser = new Date();
        KeyWordDbService keyWordDbService = KeyWordDbService.getInstance(mainActivity.getApplication());
        keyWordDbService.update(keyWordRoomModel);
    }
}
