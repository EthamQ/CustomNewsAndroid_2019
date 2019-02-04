package com.example.rapha.swipeprototype2.models;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class IntroductionSwipeCard implements ISwipeCard {

    String introductionText  = "Swipe interesting articles to the right.\n\n " +
                    "Swipe articles that aren't interesting to the left.\n\n" +
                    "Swipe in any direction to start reading articles.";

    public IntroductionSwipeCard() { }

    @Override
    public void onClick(MainActivity mainActivity) {

    }

    @Override
    public void setSwipeCardView(View convertView) {
        TextView textView = convertView.findViewById(R.id.card_main_text);
        textView.setText(this.introductionText);
        convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        imageView.setVisibility(TextView.INVISIBLE);
    }
}
