package com.raphael.rapha.myNews.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.util.ArrayList;

public class NewsOfTheDayListAdapter extends ArrayAdapter {
    boolean showTopicText = false;

    public NewsOfTheDayListAdapter(@NonNull Context context, int resource, ArrayList<NewsArticle> newsArticles, boolean showTopicText) {
        super(context, resource, newsArticles);
        this.showTopicText = showTopicText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        NewsArticle newsArticle = (NewsArticle) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_of_the_day_list_item, parent, false);
        }
        if(!showTopicText){
            convertView.findViewById(R.id.news_of_the_day_topic).setVisibility(TextView.GONE);
        }
        newsArticle.setNewsOfTheDayView(convertView);
        return convertView;
    }
}
