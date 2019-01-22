package com.example.rapha.swipeprototype2.categoryDistribution;

public class Distribution {

    public int categoryId;
    public int amountToFetchFromApi;

    public Distribution(int categoryId) {
        this.categoryId = categoryId;
    }

    public void balanceWithLanguageDistribution(int numberOfLanguages){
        this.amountToFetchFromApi /= numberOfLanguages;
    }
}