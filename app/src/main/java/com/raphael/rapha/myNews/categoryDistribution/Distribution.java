package com.raphael.rapha.myNews.categoryDistribution;

public class Distribution {

    public int categoryId;
    public int amountToFetchFromApi;

    public Distribution(int categoryId) {
        this.categoryId = categoryId;
    }

    public void balanceWithLanguageDistribution(int numberOfLanguages){
        if(numberOfLanguages > 0) {
            this.amountToFetchFromApi /= numberOfLanguages;
        }
    }
}
