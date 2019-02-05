package com.example.rapha.swipeprototype2.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomService {

    public static int getRandomNumber(int start, int end){
        return ThreadLocalRandom.current().nextInt(start, end);
    }
}
