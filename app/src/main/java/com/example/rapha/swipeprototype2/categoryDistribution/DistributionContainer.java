package com.example.rapha.swipeprototype2.categoryDistribution;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A wrapper class to store the distribution for each news category
 * calculated by NewsCategoryContainer.
 * (Distribution tells you the amount of articles you should request from a news category)
 */
public class DistributionContainer {

    // Contains a Distribution object for every news category.
    private LinkedList<Distribution> distribution;

    public DistributionContainer(){}

    public void setDistribution(LinkedList<Distribution> distribution) {
        this.distribution = distribution;
    }

    public LinkedList<Distribution> getDistributionAsLinkedList() {
        return distribution;
    }

    /**
     * Just makes a HashMap<categoryId, amountToFetchFromApi> out of the LinkedList "distribution" and
     * returns it because it sometimes is much easier to use.
     * @return
     */
    public HashMap<Integer, Integer> getDistributionAsHashMap(){
        HashMap<Integer, Integer> distribution = new HashMap<>();
        for(int i = 0; i < this.distribution.size(); i++){
            Distribution currentDistribution = this.distribution.get(i);
            distribution.put(currentDistribution.categoryId, currentDistribution.amountToFetchFromApi);
        }
        return distribution;
    }
}
