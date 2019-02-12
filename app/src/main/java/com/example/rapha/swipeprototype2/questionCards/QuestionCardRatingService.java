package com.example.rapha.swipeprototype2.questionCards;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.QuestionSwipeCard;

import java.util.Date;

public class QuestionCardRatingService {

    public static void likeKeyWord(SwipeFragment swipeFragment, QuestionSwipeCard questionSwipeCard){
        setQuestionCardStatus(swipeFragment, questionSwipeCard, KeyWordRoomModel.LIKED);
    }

    public static void dislikeKeyWord(SwipeFragment swipeFragment, QuestionSwipeCard questionSwipeCard){
        setQuestionCardStatus(swipeFragment, questionSwipeCard, KeyWordRoomModel.DISLIKED);
    }

    private static void setQuestionCardStatus(SwipeFragment swipeFragment, QuestionSwipeCard questionSwipeCard, int status){
        KeyWordRoomModel keyWordRoomModel = new KeyWordRoomModel(
                questionSwipeCard.questionKeyword,
                questionSwipeCard.getNewsCategory()
        );
        keyWordRoomModel.usedInArticleOfTheDay = false;
        keyWordRoomModel.status = status;
        keyWordRoomModel.shownToUser = new Date();
        swipeFragment.keyWordDbService.update(keyWordRoomModel);
    }
}
