package com.example.rapha.swipeprototype2.swipeCardContent;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class ErrorSwipeCard implements ISwipeCard {

    String errorText = "It was not possible to load new articles from the server.\n\n" +
            "Check your internet connection or wait a little bit and try it again.";

    @Override
    public void setSwipeCardView(View convertView) {
        ((TextView) convertView.findViewById(R.id.card_main_text)).setText(errorText);
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        imageView.setImageResource(R.drawable.oops);
    }

    @Override
    public int getNewsCategory() {
        return -1;
    }

    @Override
    public void like(SwipeFragment swipeFragment) { }
    @Override
    public void dislike(SwipeFragment swipeFragment) { }
    @Override
    public void onClick(MainActivity mainActivity) { }

}
