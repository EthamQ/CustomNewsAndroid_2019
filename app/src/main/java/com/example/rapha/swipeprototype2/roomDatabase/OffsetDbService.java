package com.example.rapha.swipeprototype2.roomDatabase;

import android.app.Application;

import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.queryWords.QueryWordService;
import com.example.rapha.swipeprototype2.roomDatabase.articleLanguageLink.ArticleLanguageLinkRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRepository;
import com.example.rapha.swipeprototype2.roomDatabase.requestOffset.RequestOffsetRoomModel;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;

import java.util.LinkedList;

public class OffsetDbService {

    private static OffsetDbService instance;
    RequestOffsetRepository repository;

    public OffsetDbService(Application application){
        this.repository = new RequestOffsetRepository(application);
    }

    public static synchronized OffsetDbService getInstance(Application application){
        if(instance == null){
            instance = new OffsetDbService(application);
        }
        return instance;
    }

    public int saveRequestOffset(int requestOffset, int categoryId, int languageCombinationId)
    {
        RequestOffsetRoomModel offsetModel = new RequestOffsetRoomModel();
        offsetModel.requestOffset = requestOffset;
        offsetModel.categoryId = categoryId;
        offsetModel.languageCombination = languageCombinationId;

        repository.insert(offsetModel);
        return languageCombinationId;
    }

}
