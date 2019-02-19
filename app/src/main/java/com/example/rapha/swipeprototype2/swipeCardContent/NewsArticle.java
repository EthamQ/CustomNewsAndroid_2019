package com.example.rapha.swipeprototype2.swipeCardContent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.readArticle.ArticleDetailScrollingActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.api.ApiUtils;
import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.roomDatabase.ArticleLanguageLinkDbService;
import com.example.rapha.swipeprototype2.roomDatabase.KeyWordDbService;
import com.example.rapha.swipeprototype2.roomDatabase.LanguageCombinationDbService;
import com.example.rapha.swipeprototype2.roomDatabase.NewsArticleDbService;
import com.example.rapha.swipeprototype2.roomDatabase.OffsetDbService;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.IInsertsLanguageCombination;
import com.example.rapha.swipeprototype2.roomDatabase.languageCombination.LanguageCombinationData;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.utils.DateUtils;
import com.example.rapha.swipeprototype2.utils.JSONUtils;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    }

    public void updateValuesAfterSwipe(SwipeFragment swipeFragment) {
        userReadArticle(swipeFragment.getActivity());
        setLanguageCombination(swipeFragment);
    }

    public void userReadArticle(Activity activity){
	    if(!(activity == null)){
            NewsArticleDbService newsArticleDbService = NewsArticleDbService.getInstance(activity.getApplication());
            NewsArticleRoomModel readArticle =
                    newsArticleDbService.createNewsArticleRoomModelToUpdate(this);
            readArticle.hasBeenRead = true;
            newsArticleDbService.update(readArticle);
        }
        }

        private void setLanguageCombination(SwipeFragment swipeFragment){
	    if(!(swipeFragment.getActivity() == null)){
            LanguageCombinationData data = new LanguageCombinationData(this);
            data.data = swipeFragment;

            boolean[] currentLanguages = LanguageSettingsService.loadChecked(swipeFragment.mainActivity);
            LanguageCombinationDbService comboDbService = LanguageCombinationDbService.getInstance(swipeFragment.getActivity().getApplication());
            comboDbService.getAll().observe(swipeFragment.getActivity(), entries ->{
                if(entries.isEmpty()){
                    LanguageCombinationDbService languageCombinationDbService = LanguageCombinationDbService.getInstance(swipeFragment.getActivity().getApplication());
                    languageCombinationDbService.insertLanguageCombination(data, currentLanguages);
                }
                for(int i = 0; i < entries.size(); i++){
                    boolean alreadyExists = entries.get(i).german == currentLanguages[LanguageSettingsService.INDEX_GERMAN]
                            && entries.get(i).french == currentLanguages[LanguageSettingsService.INDEX_FRENCH]
                            && entries.get(i).russian == currentLanguages[LanguageSettingsService.INDEX_RUSSIAN]
                            && entries.get(i).english == currentLanguages[LanguageSettingsService.INDEX_ENGLISH];
                    if(alreadyExists){
                        Log.d("offsets", "alreadyExists");
                        data.insertedId = entries.get(i).id;
                        onLanguageCombinationInsertFinished(data);
                        break;
                    }
                    if(i == entries.size() - 1){
                        Log.d("offsets", "end of loop");
                        LanguageCombinationDbService languageCombinationDbService = LanguageCombinationDbService.getInstance(swipeFragment.getActivity().getApplication());
                        languageCombinationDbService.insertLanguageCombination(data, currentLanguages);
                    }
                }
            });
        }
        }

    @Override
    public void onLanguageCombinationInsertFinished(LanguageCombinationData data) {
	    Log.d("offsets", "onLanguageCombinationInsertFinished");
	    SwipeFragment swipeFragment = (SwipeFragment) data.data;
	    if(!(swipeFragment == null)){
            long insertedId = data.insertedId;
            if(!(swipeFragment.getActivity() == null)){
                OffsetDbService offsetDbService = OffsetDbService.getInstance(swipeFragment.getActivity().getApplication());
                if(!this.publishedAt.isEmpty()){
                    DateTime temp = new DateTime(this.publishedAt);
                    temp = temp.minusMinutes(1);

                    DecimalFormat df = new DecimalFormat("00");
                    String year = temp.getYear() + "";
                    String month = df.format(temp.getMonthOfYear());
                    String day = df.format(temp.getDayOfMonth());
                    String hour = df.format(temp.getHourOfDay());
                    String minute = df.format(temp.getMinuteOfHour());
                    String second = df.format(temp.getSecondOfMinute());
                    String s = DateUtils.dateToISO8601(year, month, day, hour, minute, second);

                    offsetDbService.saveRequestOffset(
                            s,
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
	public void setArticleProperties(JSONObject articleJson, int newsCategory){
		this.author = JSONUtils.getStringErrorHandled(articleJson, "author");
		this.title = JSONUtils.getStringErrorHandled(articleJson, "title");
		this.description = JSONUtils.getStringErrorHandled(articleJson, "description");
		this.url = JSONUtils.getStringErrorHandled(articleJson, "url");
		this.urlToImage = JSONUtils.getStringErrorHandled(articleJson, "urlToImage");
		this.publishedAt = JSONUtils.getStringErrorHandled(articleJson, "publishedAt");
		this.content = JSONUtils.getStringErrorHandled(articleJson, "content");
		this.newsCategory = newsCategory;
	}

	public void setTotalAmountInThisQuery(int totalAmount){ this.totalAmountInThisQuery = totalAmount; }
	public int getTotalAmountInThisQuery(){ return this.totalAmountInThisQuery; }

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
        String[] data= new String[6];
        in.readStringArray(data);
        this.title= data[0];
        this.author= data[1];
        this.url= data[2];
        this.urlToImage= data[3];
        this.publishedAt= data[4];
        this.content= data[5];
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
                this.content
        });
    }

    public static final Parcelable.Creator<NewsArticle> CREATOR= new Parcelable.Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel source) { return new NewsArticle(source); }

        @Override
        public NewsArticle[] newArray(int size) { return new NewsArticle[size]; }
    };

}
