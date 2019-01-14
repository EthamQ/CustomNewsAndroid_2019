package com.example.rapha.swipeprototype2.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rapha.swipeprototype2.utils.JSONUtils;

import org.json.JSONObject;

public class NewsArticle implements Parcelable {

	String sourceId;
	String sourceName;
	String author;
	public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;
	public int newsCategory;

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
        ret += "Hello i'm a news article with the category " + newsCategory;
        ret += "\n";
        ret += "My title is: " + this.title;
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
