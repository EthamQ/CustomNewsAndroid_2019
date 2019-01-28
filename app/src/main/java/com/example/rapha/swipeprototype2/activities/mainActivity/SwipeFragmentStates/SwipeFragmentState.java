package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;
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
