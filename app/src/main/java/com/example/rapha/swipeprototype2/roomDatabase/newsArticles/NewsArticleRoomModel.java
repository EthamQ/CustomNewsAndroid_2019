package com.example.rapha.swipeprototype2.roomDatabase.newsArticles;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

@Entity(tableName = "NewsArticles", primaryKeys = {"title", "articleType"},
        indices = {@Index(value = {"title"}, unique = true),
})
public class NewsArticleRoomModel {

    @Ignore
    public static final int SWIPE_CARDS = 0;
    @Ignore
    public static final int NEWS_OF_THE_DAY = 1;

    @NonNull
    public String title;
    @NonNull
    public int articleType;

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
    public boolean archived = false;
    public String foundWithKeyWord;

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
        this.articleType = SWIPE_CARDS;
    }

    public void belongsToNewsOfTheDay(){
        this.articleType = NEWS_OF_THE_DAY;
    }

}
