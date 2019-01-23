package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates2;

import com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates.IMainActivityState;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class SwipeFragmentState {

    public SwipeFragment swipeFragment;

    public SwipeFragmentState(SwipeFragment swipeFragment){
        this.swipeFragment = swipeFragment;
    }

    public void changeStateTo(ISwipeFragmentState swipeFragmentState){
        swipeFragment.swipeFragmentState = swipeFragmentState;
    }
}
