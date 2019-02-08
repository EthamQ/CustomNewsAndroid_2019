package com.example.rapha.swipeprototype2.roomDatabase.newsArticles;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

@Entity(tableName = "NewsArticles")
public class NewsArticleRoomModel {

    @NonNull
    @PrimaryKey()
    public String title;
    public String sourceId;
    public String sourceName;
    public String author;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;
    public int newsCategory;
    public boolean hasBeenRead = false;

    public NewsArticleRoomModel(){
        this.sourceId = "";
        this.sourceName = "";
        this.title = "";
        this.description = "";
        this.url = "";
        this.urlToImage = "";
        this.publishedAt = "";
        this.content = "";
        this.newsCategory = 0;
    }

}
