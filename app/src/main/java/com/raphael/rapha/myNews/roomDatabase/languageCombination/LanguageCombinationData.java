package com.raphael.rapha.myNews.roomDatabase.languageCombination;

public class LanguageCombinationData {

    public IInsertsLanguageCombination inserter;
    public Object data;
    public long insertedId;

    public LanguageCombinationData(IInsertsLanguageCombination inserter){
        this.inserter = inserter;
    }
}
