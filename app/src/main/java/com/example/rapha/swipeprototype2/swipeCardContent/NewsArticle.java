package com.example.rapha.swipeprototype2.swipeCardContent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.activities.readArticle.ArticleDetailScrollingActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.MainActivity;
import com.example.rapha.swipeprototype2.activities.mainActivity.mainActivityFragments.SwipeFragment;
import com.example.rapha.swipeprototype2.categoryDistribution.CategoryRatingService;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.example.rapha.swipeprototype2.utils.JSONUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class NewsArticle implements Parcelable, ISwipeCard {

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
		this.articleType = NewsArticleRoomModel.SWIPE_CARDS;
	}

    /**
     * Open the entire article in ArticleDetailScrollingActivity.
     * @param mainActivity
     */
	@Override
    public void onClick(MainActivity mainActivity){
        Intent intent = new Intent(mainActivity, ArticleDetailScrollingActivity.class);
        intent.putExtra("clickedArticle", this);
        mainActivity.startActivity(intent);
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

    @Override
    public void like(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsInteresting(swipeFragment, this);
        userReadArticle(swipeFragment);
    }
    @Override
    public void dislike(SwipeFragment swipeFragment) {
        CategoryRatingService.rateAsNotInteresting(swipeFragment, this);
        userReadArticle(swipeFragment);
    }

    private void userReadArticle(SwipeFragment swipeFragment){
            NewsArticleRoomModel readArticle =
                    swipeFragment.newsArticleDbService.createNewsArticleRoomModelToUpdate(this);
            readArticle.hasBeenRead = true;
            swipeFragment.newsArticleDbService.update(readArticle);
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
        ret += "Title: " + this.title;
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
