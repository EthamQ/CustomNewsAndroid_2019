package com.raphael.rapha.myNews.categoryDistribution;


import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.NewsCategoryRatingRoomModel;
import com.raphael.rapha.myNews.swipeCardContent.ISwipeCard;

public class CategoryRatingService {

    // The rating mustn't go lower than this value.
    public static final int MIN_RATING = 0;
    private static final int MAX_RATING = 40;

    /**
     * Increments the rating value of the category of swipedArticle by 1 in the database.
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsInteresting(SwipeFragment swipeFragment, final ISwipeCard swipedArticle){
            rate(swipeFragment, swipedArticle, true);
    }

    /**
     * Decrements the rating value of the category of swipedArticle by 1 in the database.
     * @param swipedArticle The article the user swiped to the left or right in MainActivity.
     */
    public static void rateAsNotInteresting(SwipeFragment swipeFragment, final ISwipeCard swipedArticle){
            rate(swipeFragment, swipedArticle, false);
    }

    /**
     * Common logic of rateAsInteresting() and rateAsNotInteresting().
     * Retrieve the old rating value from the database and increase or decrease
     * it based on the boolean "interesting".
     * @param swipedArticle
     * @param interesting
     */
    private static void rate(SwipeFragment swipeFragment, final ISwipeCard swipedArticle, final boolean interesting){
        // Get previous ratings to calculate the new one.
                for(int i = 0; i < swipeFragment.liveCategoryRatings.size(); i++){
                    if(swipeFragment.liveCategoryRatings.get(i).getNewsCategoryId() == swipedArticle.getNewsCategory()){
                        int newRating = swipeFragment.liveCategoryRatings.get(i).getRating();
                        if(interesting && (newRating < MAX_RATING)){
                            newRating++;
                        }
                        else if(!interesting && (newRating > MIN_RATING)){
                            newRating--;
                        }
                        // Update in database.
                        swipeFragment.ratingDbService.updateUserPreference(new NewsCategoryRatingRoomModel(
                                swipedArticle.getNewsCategory(),
                                newRating
                        ));
                    }
                }
    }
}
