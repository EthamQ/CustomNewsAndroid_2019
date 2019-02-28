package com.raphael.rapha.myNews.roomDatabase.newsHistory;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;

import java.util.Date;

@Entity(tableName = "news_history_article")
public class NewsHistoryRoomModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    public Date timeLiked;
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
    public String languageId;

    public NewsHistoryRoomModel(){
        this.title = "";
        this.sourceId = "";
        this.sourceName = "";
        this.author = "";
        this.description = "";
        this.url = "";
        this.urlToImage = "";
        this.publishedAt = "";
        this.content = "";
        this.newsCategory = -1;
        this.languageId = LanguageSettingsService.ENGLISH;
        this.timeLiked = new Date();
    }

    public void fillModel(NewsArticleRoomModel newsArticleRoomModel){
        this.title = newsArticleRoomModel.title;
        this.sourceId = newsArticleRoomModel.sourceId;
        this.sourceName = newsArticleRoomModel.sourceName;
        this.author = newsArticleRoomModel.author;
        this.description = newsArticleRoomModel.description;
        this.url = newsArticleRoomModel.url;
        this.urlToImage = newsArticleRoomModel.urlToImage;
        this.publishedAt = newsArticleRoomModel.publishedAt;
        this.content = newsArticleRoomModel.content;
        this.newsCategory = newsArticleRoomModel.newsCategory;
        this.languageId = newsArticleRoomModel.languageId;
    }
}
