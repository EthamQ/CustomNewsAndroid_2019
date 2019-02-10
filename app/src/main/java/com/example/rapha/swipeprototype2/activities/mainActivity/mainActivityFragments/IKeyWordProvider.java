package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments;

import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;

import java.util.List;

public interface IKeyWordProvider {

    List<KeyWordRoomModel> getCurrentKeyWords();
}
