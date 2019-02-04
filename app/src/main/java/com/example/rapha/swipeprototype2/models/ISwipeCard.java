package com.example.rapha.swipeprototype2.models;

import android.view.View;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public interface ISwipeCard {

    public void onClick(MainActivity mainActivity);
    public void setSwipeCardView(View convertView);
    public int getNewsCategory();
}
