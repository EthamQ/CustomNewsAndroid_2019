package com.raphael.rapha.myNews.swipeCardContent;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.categoryDistribution.CategoryRatingService;
import com.raphael.rapha.myNews.questionCards.QuestionCardRatingService;
import com.raphael.rapha.myNews.questionCards.QuestionCardService;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.raphael.rapha.myNews.sharedPreferencesAccess.SwipeTimeService;

import java.util.List;

public class QuestionSwipeCard implements ISwipeCard {

    private String questionText = "Are you interested in...";
    public String questionKeyword;
    private int newsCategory;

    public QuestionSwipeCard(String questionKeyword, int newsCategory){
        this.questionKeyword = questionKeyword;
        this.newsCategory = newsCategory;
    }

    @Override
    public void onClick(Activity activity) {

    }

    @Override
    public void setSwipeCardView(View convertView) {
        final TextView question = convertView.findViewById(R.id.card_main_text);
        final TextView questionKeyword = convertView.findViewById(R.id.question_keyword);
        question.setText(this.questionText);
        questionKeyword.setText(this.questionKeyword + " ?");
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        imageView.setImageResource(R.drawable.frage);
    }

    @Override
    public int getNewsCategory() {
        return newsCategory;
    }

    @Override
    public void like(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsInteresting(swipeFragment, this);

        // Check if it is the first time the user likes a topic.
        LiveData<List<KeyWordRoomModel>> likedKeyWordsLiveData
                = swipeFragment.keyWordDbService.getAllLikedKeyWords();
        likedKeyWordsLiveData.observe(swipeFragment, new Observer<List<KeyWordRoomModel>>() {
            @Override
            public void onChanged(@Nullable List<KeyWordRoomModel> likedTopics) {
                QuestionCardRatingService.likeKeyWord(swipeFragment, QuestionSwipeCard.this);
                swipeFragment.showDailyNewsDialogue(likedTopics.size());
                likedKeyWordsLiveData.removeObserver(this);
            }
        });
    }
    @Override
    public void dislike(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsNotInteresting(swipeFragment, this);
        QuestionCardRatingService.dislikeKeyWord(swipeFragment, this);
    }

    @Override
    public void onSwipe(SwipeFragment swipeFragment, float scrollProgressPercent) {
        TextView leftIndicator = swipeFragment.leftIndicator;
        TextView rightIndicator = swipeFragment.rightIndicator;
        leftIndicator.setText("No");
        rightIndicator.setText("Yes");
        leftIndicator.setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
        rightIndicator.setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);

        Button skip = swipeFragment.skip;
        skip.setAlpha(scrollProgressPercent == 0 ? 1 : 0);
    }

    @Override
    public void initAlphaSkipButton(Button skipButton) {
        skipButton.setAlpha(1);
    }

}
