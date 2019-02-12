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

    @Query("SELECT * FROM NewsArticles WHERE hasBeenRead = :hasBeenRead AND articleType = :articleType")
    LiveData<List<NewsArticleRoomModel>> getAllUnreadNewsArticles(boolean hasBeenRead, int articleType);

    @Query("SELECT * FROM NewsArticles WHERE articleType = :articleType")
    LiveData<List<NewsArticleRoomModel>> getAllNewsArticlesByType(int articleType);

    @Query("SELECT * FROM NewsArticles WHERE title = :title AND articleType = :articleType")
    LiveData<List<NewsArticleRoomModel>> getOneNewsArticle(String title, int articleType);

    @Query("SELECT * FROM NewsArticles WHERE archived = :archived AND articleType = :articleType")
    LiveData<List<NewsArticleRoomModel>> getAllNewsArticles(int articleType, boolean archived);

    @Query("SELECT * FROM NewsArticles WHERE foundWithKeyWord = :foundWithKeyWord AND articleType = :articleType AND archived = :archived")
    LiveData<List<NewsArticleRoomModel>> getAllNewsArticlesByKeyWord(String foundWithKeyWord, int articleType, boolean archived);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOneNewsArticle(NewsArticleRoomModel newsArticleRoomModel);

    @Query("DELETE FROM NewsArticles WHERE articleType = :articleType")
    void deleteAllNewsArticles(int articleType);

    @Update
    void updateNewsArticle(NewsArticleRoomModel newsArticleRoomModel);
}
