package com.example.rapha.swipeprototype2.roomDatabase.newsArticles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;

import java.util.LinkedList;
import java.util.List;

@Dao
public interface INewsArticleDao {

    @Query("SELECT * FROM NewsArticles")
    LiveData<List<NewsArticleRoomModel>> getAllNewsArticles();

    @Query("SELECT * FROM NewsArticles WHERE hasBeenRead = :hasBeenRead")
    LiveData<List<NewsArticleRoomModel>> getAllUnreadNewsArticles(boolean hasBeenRead);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOneNewsArticle (NewsArticleRoomModel newsArticleRoomModel);

    @Query("DELETE FROM UserPreference")
    void deleteAllNewsArticles();

    @Update
    void updateNewsArticle(NewsArticleRoomModel newsArticleRoomModel);
}
