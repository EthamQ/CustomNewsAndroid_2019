package com.example.rapha.swipeprototype2.models;

import android.view.View;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class ErrorSwipeCard implements ISwipeCard {

    String errorText = "It was not possible to load new articles.\n" +
            "Wait a little bit and try it again.";
    @Override
    public void onClick(MainActivity mainActivity) {

    }

    @Override
    public void setSwipeCardView(View convertView) {
        ((TextView) convertView.findViewById(R.id.card_main_text)).setText(errorText);
    }

    @Override
    public int getNewsCategory() {
        return -1;
    }
}
