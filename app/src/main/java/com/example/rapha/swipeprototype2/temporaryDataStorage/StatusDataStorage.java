package com.example.rapha.swipeprototype2.temporaryDataStorage;

public class StatusDataStorage {

    private static boolean mainActivityWasActive = false;

    public static void mainActivityStarted(){
        mainActivityWasActive = true;
    }

    public static  boolean getMainActivityWasActive(){
        return mainActivityWasActive;
    }
}
