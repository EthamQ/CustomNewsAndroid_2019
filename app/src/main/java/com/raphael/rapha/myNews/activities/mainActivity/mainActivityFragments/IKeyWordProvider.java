package com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments;

import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.List;

public interface IKeyWordProvider {

    List<KeyWordRoomModel> getCurrentKeyWords();
}
