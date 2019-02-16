package com.example.rapha.swipeprototype2.roomDatabase.articleLanguageLink;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;

@Entity(tableName = "article_language_link",
        foreignKeys = {
                @ForeignKey(
                        entity = NewsArticleRoomModel.class,
                        parentColumns = "title",
                        childColumns = "articleTitle"),
                @ForeignKey(
                        entity = NewsArticleRoomModel.class,
                        parentColumns = "articleType",
                        childColumns = "articleType"),
                @ForeignKey(
                        entity = LanguageCombinationRoomModel.class,
                        parentColumns = "id",
                        childColumns = "languageCombination")
        })
public class ArticleLanguageLinkRoomModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String articleTitle;
    public int articleType;
    public int languageCombination;
}
