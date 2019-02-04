package com.example.rapha.swipeprototype2.models;

import android.view.View;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class SwipeCard {

    public String mainText;

    public SwipeCard(String mainText){
        this.mainText = mainText;
    }

    // TODO: use behaviour pattern

    public void onClick(MainActivity mainActivity){
        // only overwritten when needed
    }

    public void setSwipeCardView(View convertView){  }
}
