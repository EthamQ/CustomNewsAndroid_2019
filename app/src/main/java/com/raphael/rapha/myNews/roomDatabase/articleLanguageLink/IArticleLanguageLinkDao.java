package com.raphael.rapha.myNews.roomDatabase.articleLanguageLink;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IArticleLanguageLinkDao {

    @Query("SELECT * FROM article_language_link")
    LiveData<List<ArticleLanguageLinkRoomModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOne(ArticleLanguageLinkRoomModel articleLanguageLinkRoomModel);

    @Update
    void update(ArticleLanguageLinkRoomModel articleLanguageLinkRoomModel);
}
