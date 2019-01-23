package com.example.rapha.swipeprototype2;

import android.support.test.runner.AndroidJUnit4;

import com.example.rapha.swipeprototype2.categoryDistribution.DistributionContainer;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.categoryDistribution.FilterNewsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class CategoryDistributionTest {

    //TODO: move input and output to extra mock file

    LinkedList<UserPreferenceRoomModel> userPreferenceRoomModels;

    public CategoryDistributionTest() {
    }

    @Before
    public void init(){
        userPreferenceRoomModels = new LinkedList<>();
        userPreferenceRoomModels.add(new UserPreferenceRoomModel(0, 10));
        userPreferenceRoomModels.add(new UserPreferenceRoomModel(1, 30));
        userPreferenceRoomModels.add(new UserPreferenceRoomModel(2, 10));
        userPreferenceRoomModels.add(new UserPreferenceRoomModel(3, 20));
        userPreferenceRoomModels.add(new UserPreferenceRoomModel(4, 10));
    }

    @Test
    public void testDistribution(){
        DistributionContainer distributionWrapper =
                FilterNewsService.getCategoryDistribution(userPreferenceRoomModels);
        HashMap<Integer, Integer> distribution = distributionWrapper.getDistributionAsHashMap();
        assertEquals(new Integer(37), distribution.get(1));
        assertEquals(new Integer(12), distribution.get(2));
        assertEquals(new Integer(25), distribution.get(3));
        assertEquals(new Integer(12), distribution.get(4));
    }
}
