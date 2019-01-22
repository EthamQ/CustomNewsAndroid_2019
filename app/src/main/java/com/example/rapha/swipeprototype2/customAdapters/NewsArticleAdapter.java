package com.example.rapha.swipeprototype2.customAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.models.NewsArticle;
import com.example.rapha.swipeprototype2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsArticleAdapter extends ArrayAdapter{

    public NewsArticleAdapter(@NonNull Context context, int resourceID, ArrayList<NewsArticle> newsArticles) {
        super(context, resourceID, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        NewsArticle newsArticle = (NewsArticle) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipe_card, parent, false);
        }

        // News Headline
        TextView textView = convertView.findViewById(R.id.itemText);
        textView.setText(newsArticle.title);

        // News image
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        try{
            Picasso.get()
                    .load(newsArticle.urlToImage)
                    .error(R.drawable.newsdefault)
                    .into(imageView);
        } catch(Exception e){
            convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
        }

        // The first default information card doesn't need an image
        if(newsArticle.isDefault || (newsArticle.urlToImage == null)){
            convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
            imageView.setVisibility(TextView.INVISIBLE);
        }

        return convertView;
    }

}
