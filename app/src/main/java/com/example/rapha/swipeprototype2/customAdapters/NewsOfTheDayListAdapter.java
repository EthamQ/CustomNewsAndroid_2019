package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.swipeCardContent.ISwipeCard;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsOfTheDayListAdapter extends ArrayAdapter {
    public NewsOfTheDayListAdapter(@NonNull Context context, int resource, ArrayList<NewsArticle> newsArticles) {
        super(context, resource, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        NewsArticle newsArticle = (NewsArticle) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_of_the_day_list_item, parent, false);
        }
        newsArticle.setNewsOfTheDayView(convertView);
        return convertView;
    }
}
