package com.raphael.rapha.myNews.categoryDistribution;

import com.raphael.rapha.myNews.newsCategories.NewsCategory;

public class Distribution {

    public int categoryId;
    public int amountToFetchFromApi;

    public Distribution(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * If you request articles for several languages you spread the total amount
     * of articles to request for one category among different requests.
     * This function returns the amount for each request for each individual language.
     * For example you either request 30 articles for category x only in english or
     * 15 for german and 15 for english and not suddenly 30 in german and 30 in english.
     * @param numberOfLanguages
     */
    public void balanceWithLanguageDistribution(int numberOfLanguages){
        if(numberOfLanguages > 0) {
            this.amountToFetchFromApi /= numberOfLanguages;
        }
    }

    public void calculateAmountToFetchFromApi(NewsCategory category, int totalRating){
        amountToFetchFromApi = DistributionService.calculateDistribution(category, totalRating);
    }
}
