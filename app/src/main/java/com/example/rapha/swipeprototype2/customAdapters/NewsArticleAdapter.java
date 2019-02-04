package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.models.ISwipeCard;
import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.R;
import com.squareup.picasso.Picasso;

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

        // News Headline
        newsArticle.setSwipeCardView(convertView);
//        TextView textView = convertView.findViewById(R.id.card_main_text);
//        if(newsArticle.isDefault){
//            textView.setTextColor(Color.BLACK);
//        }
        // News image


        // The first default information card doesn't need an image


        return convertView;
    }

}
