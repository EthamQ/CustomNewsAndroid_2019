package com.raphael.rapha.myNews.roomDatabase;

import android.util.Log;

import com.raphael.rapha.myNews.categoryDistribution.CategoryRatingService;
import com.raphael.rapha.myNews.newsCategories.NewsCategory;
import com.raphael.rapha.myNews.newsCategories.NewsCategoryContainer;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRoomModel;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRepository;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRepository;
import com.raphael.rapha.myNews.roomDatabase.topics.TopicRoomModel;

import java.util.LinkedList;


public class FillDatabase {

    /**
     * Fill the table that contains the categories with its corresponding ratings
     * if it is empty.
     * @param repository
     */
    public static void fillCategories(NewsCategoryRatingRepository repository){

        NewsCategoryContainer newsNewsCategoryContainer = new NewsCategoryContainer();
        for(NewsCategory category : newsNewsCategoryContainer.allCategories){
            Log.d("datapoint: ","fill in db: " + category.getNewsCategoryID());
            repository.insert(new NewsCategoryRatingRoomModel(
                    category.getNewsCategoryID(),
                    CategoryRatingService.MIN_RATING));
        }

        repository.getAllUserPreferences().observeForever(cats ->{
            for(NewsCategoryRatingRoomModel category : cats){
                Log.d("datapoint: ","observed after fill: " + category.getNewsCategoryId());
            }
            });
    }

    public static void fillKeyWords(TopicRepository repository){
        NewsCategoryContainer categoryContainer = new NewsCategoryContainer();
        LinkedList<NewsCategory> allCategories = categoryContainer.allCategories;
        for(int i = 0; i < allCategories.size(); i++){
            for(int j = 0; j < allCategories.get(i).USER_DETERMINED_QUERY_STRINGS_EN.length; j++){
                repository.insert(new TopicRoomModel(
                        allCategories.get(i).USER_DETERMINED_QUERY_STRINGS_EN[j],
                        allCategories.get(i).getNewsCategoryID())
                );
            }
        }
    }
}
