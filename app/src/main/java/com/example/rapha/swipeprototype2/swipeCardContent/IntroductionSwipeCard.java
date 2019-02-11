package com.example.rapha.swipeprototype2.swipeCardContent;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class IntroductionSwipeCard implements ISwipeCard {

    String introductionText  = "Swipe interesting articles to the right.\n\n " +
                    "Swipe articles that aren't interesting to the left.\n\n" +
                    "Swipe in any direction to start reading articles.\n\n" +
                    "More information under the info menu";

    public IntroductionSwipeCard() { }

    @Override
    public void onClick(Activity activity) {

    }

    @Override
    public void setSwipeCardView(View convertView) {
//        TextView textView = convertView.findViewById(R.id.card_main_text);
//        textView.setText(this.introductionText);
//        convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
//        ImageView imageView = convertView.findViewById(R.id.news_card_image);
//        imageView.setVisibility(TextView.INVISIBLE);
        LinearLayout layout = convertView.findViewById(R.id.introcard_content);
        layout.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public int getNewsCategory() {
        return -1;
    }

    @Override
    public void like(SwipeFragment swipeFragment) { }
    @Override
    public void dislike(SwipeFragment swipeFragment) { }


}
