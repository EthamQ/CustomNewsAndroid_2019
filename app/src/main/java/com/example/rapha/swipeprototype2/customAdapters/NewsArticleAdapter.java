package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.R;

import java.io.InputStream;
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

        // News Headline
        TextView textView = convertView.findViewById(R.id.itemText);
        textView.setText(newsArticle.title);

        // News image
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        imageView.setImageBitmap(newsArticle.imageForTextView);

        // Image background
        if(newsArticle.imageForTextView == null){
            convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
        }

        return convertView;
    }

}
