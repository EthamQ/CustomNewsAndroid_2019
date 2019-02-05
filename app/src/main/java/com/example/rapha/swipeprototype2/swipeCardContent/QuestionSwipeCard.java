package com.example.rapha.swipeprototype2.swipeCardContent;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
import com.example.rapha.swipeprototype2.questionCards.QuestionCardRatingService;

public class QuestionSwipeCard implements ISwipeCard {

    private String questionText = "Are you interested in...";
    public String questionKeyword;
    private int newsCategory;

    public QuestionSwipeCard(String questionKeyword, int newsCategory){
        this.questionKeyword = questionKeyword;
        this.newsCategory = newsCategory;
    }

    @Override
    public void onClick(MainActivity mainActivity) {

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
        QuestionCardRatingService.likeKeyWord(swipeFragment, this);
    }
    @Override
    public void dislike(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsNotInteresting(swipeFragment, this);
        QuestionCardRatingService.dislikeKeyWord(swipeFragment, this);
    }
}
