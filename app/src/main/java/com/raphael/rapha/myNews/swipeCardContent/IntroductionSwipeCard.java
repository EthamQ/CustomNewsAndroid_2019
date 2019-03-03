package com.raphael.rapha.myNews.swipeCardContent;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class IntroductionSwipeCard implements ISwipeCard {

    public IntroductionSwipeCard() { }

    @Override
    public void onClick(Activity activity) {

    }

    @Override
    public void setSwipeCardView(View convertView) {
        LinearLayout layout = convertView.findViewById(R.id.introcard_content);
        layout.setVisibility(LinearLayout.VISIBLE);
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        imageView.setImageResource(R.mipmap.ic_launcher);
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


}
