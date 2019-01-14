package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.R;

import java.util.ArrayList;

public class NewsArticleAdapter extends ArrayAdapter{

    public NewsArticleAdapter(@NonNull Context context, int resourceID, ArrayList<NewsArticle> newsArticles) {
        super(context, resourceID, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        NewsArticle newsArticle = (NewsArticle) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.itemText);
        textView.setText(newsArticle.title);
        return convertView;
    }
}
