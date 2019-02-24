package com.raphael.rapha.myNews.activities.viewElements;

import android.view.ViewGroup;

import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.StatisticFragment;
import com.raphael.rapha.myNews.screenDimension.ScreenDimension;
import com.jjoe64.graphview.GraphView;

public class StatisticsFragmentDimensions{

    StatisticFragment statisticFragment;
    ScreenDimension screenDimension;

    public StatisticsFragmentDimensions(StatisticFragment statisticFragment){
        this.statisticFragment = statisticFragment;
        screenDimension = new ScreenDimension(statisticFragment.getActivity());
    }

    public void setGraphWidth(GraphView graphView){
        double withFactor = 0.95;
        ViewGroup.LayoutParams params = graphView.getLayoutParams();
        params.width = (int)(screenDimension.getScreenWidthPixel() * withFactor);
        graphView.setLayoutParams(params);
        graphView.requestLayout();
    }

}
