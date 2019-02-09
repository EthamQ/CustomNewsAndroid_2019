package com.example.rapha.swipeprototype2.activities.viewDimension;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.StatisticFragment;
import com.example.rapha.swipeprototype2.screenDimension.ScreenDimension;
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

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}
