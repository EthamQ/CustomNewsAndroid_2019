package com.example.rapha.swipeprototype2.activities.mainActivity.SwipeFragmentStates;


import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;

public class MainActivityState {

    public SwipeFragment swipeFragment;

    public MainActivityState(SwipeFragment swipeFragment){
        this.swipeFragment = swipeFragment;
    }

    public void changeStateTo(IMainActivityState newMainActivityState){
        swipeFragment.swipeActivityState = newMainActivityState;
    }

}
