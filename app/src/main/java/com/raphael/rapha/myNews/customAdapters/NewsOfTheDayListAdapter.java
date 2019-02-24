package com.raphael.rapha.myNews.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

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
