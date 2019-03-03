package com.raphael.rapha.myNews.questionCards;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.QuestionSwipeCard;

import java.util.Date;

public class QuestionCardRatingService {

    public static void likeKeyWord(SwipeFragment swipeFragment, QuestionSwipeCard questionSwipeCard){
        setQuestionCardStatus(swipeFragment, questionSwipeCard, TopicRoomModel.LIKED);
    }

    public static void dislikeKeyWord(SwipeFragment swipeFragment, QuestionSwipeCard questionSwipeCard){
        setQuestionCardStatus(swipeFragment, questionSwipeCard, TopicRoomModel.DISLIKED);
    }

    private static void setQuestionCardStatus(SwipeFragment swipeFragment, QuestionSwipeCard questionSwipeCard, int status){
        TopicRoomModel keyWordRoomModel = new TopicRoomModel(
                questionSwipeCard.questionKeyword,
                questionSwipeCard.getNewsCategory()
        );
        keyWordRoomModel.usedInArticleOfTheDay = false;
        keyWordRoomModel.status = status;
        keyWordRoomModel.shownToUser = new Date();
        swipeFragment.keyWordDbService.update(keyWordRoomModel);
    }
}
