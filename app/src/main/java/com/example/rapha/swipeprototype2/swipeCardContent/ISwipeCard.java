package com.example.rapha.swipeprototype2.swipeCardContent;

import android.app.Activity;
import android.view.View;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public interface ISwipeCard {

    void onClick(Activity activity);
    void setSwipeCardView(View convertView);
    void like(SwipeFragment swipeFragment);
    void dislike(SwipeFragment swipeFragment);
    void onSwipe(SwipeFragment swipeFragment, float scrollProgressPercent);
    int getNewsCategory();
    String toString();
}
