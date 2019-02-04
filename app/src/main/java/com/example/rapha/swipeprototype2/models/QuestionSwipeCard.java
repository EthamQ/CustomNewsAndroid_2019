package com.example.rapha.swipeprototype2.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class QuestionSwipeCard implements ISwipeCard {

    String questionText = "Are you interested in...";
    String questionKeyword;
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
        imageView.setImageResource(R.drawable.newsdefault);
    }

    @Override
    public int getNewsCategory() {
        return newsCategory;
    }
}
