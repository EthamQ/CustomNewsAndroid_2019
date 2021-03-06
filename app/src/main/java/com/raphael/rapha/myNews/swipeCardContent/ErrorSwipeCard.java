package com.raphael.rapha.myNews.swipeCardContent;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class ErrorSwipeCard implements ISwipeCard {

    private final String errorText = "It was not possible to load new articles from the server.\n\n" +
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
    public void onSwipe(SwipeFragment swipeFragment, float scrollProgressPercent) {
        Button skip = swipeFragment.skip;
        skip.setAlpha(0);
    }

    @Override
    public void initAlphaSkipButton(Button skipButton) {
        skipButton.setAlpha(0);
    }

    @Override
    public void onClick(Activity activity) { }

}
