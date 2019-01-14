package com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityStates;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;

public class MainActivityState {

    public MainActivity mainActivity;

    public MainActivityState(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void changeStateTo(IMainActivityState newMainActivityState){
        mainActivity.mainActivityState = newMainActivityState;
    }

}
