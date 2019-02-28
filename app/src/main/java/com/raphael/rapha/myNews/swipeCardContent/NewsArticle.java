package com.raphael.rapha.myNews.swipeCardContent;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raphael.rapha.myNews.R;
import com.raphael.rapha.myNews.activities.readArticle.ArticleDetailScrollingActivity;
import com.raphael.rapha.myNews.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.raphael.rapha.myNews.categoryDistribution.CategoryRatingService;
import com.raphael.rapha.myNews.languages.LanguageSettingsService;
import com.raphael.rapha.myNews.roomDatabase.LanguageCombinationDbService;
import com.raphael.rapha.myNews.roomDatabase.NewsArticleDbService;
import com.raphael.rapha.myNews.roomDatabase.OffsetDbService;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.IInsertsLanguageCombination;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationData;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.utils.DateService;
import com.raphael.rapha.myNews.utils.JSONUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class NewsArticle implements Parcelable, ISwipeCard, IInsertsLanguageCombination {

    public String sourceId;
    public String sourceName;
    public String author;
	public String title;
    public String description;
    public String url;
    public String urlToImage;
    public Bitmap imageForTextView;
    public String publishedAt;
    public String content;
    public String foundWithKeyWord;
    public boolean hasBeenRead;
    public boolean archived;
	public int newsCategory;
    public int articleType;
    public String languageId;

	// We send a query to the api. We get a JSON with news articles.
    // This number doesn't say how many news articles are in the JSON, but how many
    // articles the api has stored that match this query. It sends only up to 100 results
    // even if there are more matches.
	private int totalAmountInThisQuery;
	
	public NewsArticle() {
	    this.sourceId = "";
	    this.sourceName = "";
		this.title = "";
		this.description = "";
		this.url = "";
		this.urlToImage = "";
		this.publishedAt = "";
		this.content = "";
		this.newsCategory = 0;
		this.totalAmountInThisQuery = 0;
		foundWithKeyWord = "";
		this.articleType = NewsArticleRoomModel.SWIPE_CARDS;
	}

    /**
     * Open the entire article in ArticleDetailScrollingActivity.
     * @param activity
     */
	@Override
    public void onClick(Activity activity){
        Intent intent = new Intent(activity, ArticleDetailScrollingActivity.class);
        intent.putExtra("clickedArticle", this);
        activity.startActivity(intent);
    }

    @Override
    public void setSwipeCardView(View convertView){
        TextView mainText = convertView.findViewById(R.id.card_main_text);
        mainText.setText(this.title);
        ImageView imageView = convertView.findViewById(R.id.news_card_image);
        try{
            Picasso.get()
                    .load(this.urlToImage)
                    .error(R.drawable.newsdefault)
                    .into(imageView);
        } catch(Exception e){
            convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
        }
    }

    public void setNewsOfTheDayView(View convertView){
        TextView title = convertView.findViewById(R.id.news_of_the_day_title);
        title.setText(this.title);
        TextView topic = convertView.findViewById(R.id.news_of_the_day_topic);
        topic.setText("Because you liked: " + this.foundWithKeyWord);

        ImageView imageView = convertView.findViewById(R.id.news_of_the_day_image);
        try{
            Picasso.get()
                    .load(this.urlToImage)
                    .resize(200, 120)
                    .error(R.drawable.newsdefault)
                    .into(imageView);
        } catch(Exception e){
            //convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
        }
    }

    @Override
    public void like(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsInteresting(swipeFragment, this);
        swipeFragment.newsHistoryDbService.insert(this);
        updateValuesAfterSwipe(swipeFragment);
    }
    @Override
    public void dislike(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsNotInteresting(swipeFragment, this);
        updateValuesAfterSwipe(swipeFragment);
    }
    @Override
    public void onSwipe(SwipeFragment swipeFragment, float scrollProgressPercent) {
	    TextView leftIndicator = swipeFragment.leftIndicator;
        TextView rightIndicator = swipeFragment.rightIndicator;
        leftIndicator.setText("Dislike");
        rightIndicator.setText("Like");
        leftIndicator.setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
        rightIndicator.setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);

        Button skip = swipeFragment.skip;
        skip.setAlpha(scrollProgressPercent == 0 ? 1 : 0);
    }

    @Override
    public void initAlphaSkipButton(Button skipButton) {
        skipButton.setAlpha(1);
    }

    public void updateValuesAfterSwipe(SwipeFragment swipeFragment) {
        setArticleAsRead(swipeFragment.getActivity());
        saveDateOffset(swipeFragment);
    }

    public void saveDateOffset(SwipeFragment swipeFragment){
	    // After it has finished it will call onLanguageCombinationInsertFinished
        // where the date offset is set
        setLanguageCombination(swipeFragment);
    }

    public void setArticleAsRead(Activity activity){
	    if(!(activity == null)){
            NewsArticleDbService newsArticleDbService = NewsArticleDbService.getInstance(activity.getApplication());
            NewsArticleRoomModel readArticle =
                    newsArticleDbService.createNewsArticleRoomModelToUpdate(this);
            readArticle.hasBeenRead = true;
            newsArticleDbService.update(readArticle);
	    }
	}

        private void setLanguageCombination(SwipeFragment swipeFragment){
	    if(swipeFragment.mainActivity != null){
            LanguageCombinationData dataToPassToOnFinished = new LanguageCombinationData(this);
            dataToPassToOnFinished.data = swipeFragment;
            boolean[] currentLanguages = LanguageSettingsService.loadChecked(swipeFragment.mainActivity);
            LiveData<List<LanguageCombinationRoomModel>> allLanguageCombinationsLiveData = swipeFragment.languageComboDbService.getAll();
            allLanguageCombinationsLiveData.observe(swipeFragment.mainActivity, new Observer<List<LanguageCombinationRoomModel>>() {
                        @Override
                        public void onChanged(@Nullable List<LanguageCombinationRoomModel> languageCombinations) {
                            if(languageCombinations.isEmpty()){
                                swipeFragment.languageComboDbService.insertLanguageCombination(dataToPassToOnFinished, currentLanguages);
                            }
                            for(int i = 0; i < languageCombinations.size(); i++){
                                LanguageCombinationRoomModel currentCombination = languageCombinations.get(i);
                                boolean alreadyExists = LanguageCombinationDbService.languageSelectionIsEqual(currentLanguages, currentCombination);
                                if(alreadyExists){
                                    // The currently active language combination exists in database.
                                    // Just pass its id to on insert finished without inserting it again.
                                    dataToPassToOnFinished.insertedId = currentCombination.id;
                                    onLanguageCombinationInsertFinished(dataToPassToOnFinished);
                                    break;
                                }
                                if(i == languageCombinations.size() - 1){
                                    swipeFragment.languageComboDbService.insertLanguageCombination(dataToPassToOnFinished, currentLanguages);
                                }
                            }
                            allLanguageCombinationsLiveData.removeObserver(this);
                        }
                    }
            );
        }
	}

    @Override
    public void onLanguageCombinationInsertFinished(LanguageCombinationData dataToPassToOnFinished) {
        SwipeFragment swipeFragment = (SwipeFragment) dataToPassToOnFinished.data;
        long insertedId = dataToPassToOnFinished.insertedId;
	    if(swipeFragment != null){
            if(swipeFragment.getActivity() != null){
                OffsetDbService offsetDbService = OffsetDbService.getInstance(swipeFragment.getActivity().getApplication());
                if(!this.publishedAt.isEmpty()){
                    // Because the api results will include articles published at
                    // exactly the offset, we subtract a minute to exclude them in the next call
                    Log.d("newswipe3", "############");
                    Log.d("newswipe3", "Store offset in database: category: " + this.newsCategory + "offset: " + this.publishedAt);
                    String dateOffset = DateService.subtractSecond(this.publishedAt, 1);
                    Log.d("newswipe3", "After minute added: category: " + this.newsCategory + "offset: " + dateOffset);
                    Log.d("newswipe3", "############");
                    offsetDbService.saveRequestOffset(
                            dateOffset,
                            this.newsCategory,
                            insertedId
                    );
                }
            }
        }

    }

    @Override
    public int getNewsCategory() {
        return newsCategory;
    }

    /**
     * Reads the values whose keys correspond to the properties of this
     * class from the JSON object and assigns them to this NewsArticle object.
     * It also sets the news category.
     * @param articleJson
     * @param newsCategory
     */
	public void setArticleProperties(JSONObject articleJson, int newsCategory, String languageId){
		this.author = JSONUtils.getStringErrorHandled(articleJson, "author");
		this.title = JSONUtils.getStringErrorHandled(articleJson, "title");
		this.description = JSONUtils.getStringErrorHandled(articleJson, "description");
		this.url = JSONUtils.getStringErrorHandled(articleJson, "url");
		this.urlToImage = JSONUtils.getStringErrorHandled(articleJson, "urlToImage");
		this.publishedAt = JSONUtils.getStringErrorHandled(articleJson, "publishedAt");
		this.content = JSONUtils.getStringErrorHandled(articleJson, "content");
		this.newsCategory = newsCategory;
		this.languageId = languageId;
	}

    @Override
    public String toString(){
        String ret = "";
        ret += "category: : " + this.newsCategory;
        ret += ", published at: " + this.publishedAt;
        ret += ", Title: " + this.title;
        return ret;
    }


	// Below obligatory functions to make this object parcelable.
    // Needed to pass a news Article to another Activity.
    // Uses only the necessary data and not every property (properties can be added though)
    public NewsArticle(Parcel in){
        String[] data= new String[7];
        in.readStringArray(data);
        this.title= data[0];
        this.author= data[1];
        this.url= data[2];
        this.urlToImage= data[3];
        this.publishedAt= data[4];
        this.content= data[5];
        this.languageId = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeStringArray(new String[]{
                this.title,
                this.author,
                this.url,
                this.urlToImage,
                this.publishedAt,
                this.content,
                this.languageId
        });
    }

    public static final Parcelable.Creator<NewsArticle> CREATOR= new Parcelable.Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel source) { return new NewsArticle(source); }

        @Override
        public NewsArticle[] newArray(int size) { return new NewsArticle[size]; }
    };

}
