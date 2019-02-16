package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.rapha.swipeprototype2.swipeCardContent.ISwipeCard;
import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.swipeCardContent.IntroductionSwipeCard;

import java.util.ArrayList;

public class NewsArticleAdapter extends ArrayAdapter{

    public NewsArticleAdapter(@NonNull Context context, int resourceID, ArrayList<ISwipeCard> newsArticles) {
        super(context, resourceID, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ISwipeCard newsArticle = (ISwipeCard) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipe_card, parent, false);
        }
            newsArticle.setSwipeCardView(convertView);
        return convertView;
    }

}
