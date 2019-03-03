package com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments;

import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;

import java.util.List;

public interface IKeyWordProvider {

    List<TopicRoomModel> getCurrentKeyWords();
}
