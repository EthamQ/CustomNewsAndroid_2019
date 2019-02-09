package com.example.rapha.swipeprototype2.screenDimension;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenDimension {

    Activity activity;
    DisplayMetrics metrics;

    public ScreenDimension(Activity activity){
        this.activity = activity;
        this.metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    public int getScreenWidthPixel(){
        return metrics.widthPixels;
    }
    public int getScreenHeightPixel(){
        return metrics.heightPixels;
    }
}
