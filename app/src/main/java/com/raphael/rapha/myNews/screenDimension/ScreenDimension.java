package com.raphael.rapha.myNews.screenDimension;

import android.app.Activity;
import android.util.DisplayMetrics;

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
