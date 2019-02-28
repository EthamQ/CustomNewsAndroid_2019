package com.raphael.rapha.myNews.roomDatabase.newsHistory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;

import java.util.List;

@Dao
public interface INewsHistoryDao {

    @Query("SELECT * FROM news_history_article ORDER BY timeLiked DESC")
    LiveData<List<NewsHistoryRoomModel>> getAll();

    @Query("DELETE FROM news_history_article")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOne(NewsHistoryRoomModel newsArticleRoomModel);

    @Update
    void update(NewsHistoryRoomModel newsArticleRoomModel);
}
