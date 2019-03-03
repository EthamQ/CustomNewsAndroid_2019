package com.raphael.rapha.myNews.roomDatabase.topics;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ITopicDao {

    @Query("SELECT * FROM topicRoomModel")
    LiveData<List<TopicRoomModel>> getAllKeyWords();

    @Query("SELECT * FROM topicRoomModel WHERE usedInArticleOfTheDay = :usedInArticleOfTheDay")
    LiveData<List<TopicRoomModel>> getAllKeyWords(boolean usedInArticleOfTheDay);

    @Query("SELECT * FROM topicRoomModel WHERE status = :status")
    LiveData<List<TopicRoomModel>> getKeyWordsByStatus(int status);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOneKeyWord(TopicRoomModel keyWordRoomModel);

    @Query("DELETE FROM topicRoomModel")
    void deleteAllKeyWords();

    @Update
    void updateKeyWord(TopicRoomModel keyWordRoomModel);
}
