package com.example.rapha.swipeprototype2;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rapha.swipeprototype2.utils.JSONUtils;

import org.json.JSONObject;

public class NewsArticle implements Parcelable {

	String sourceId;
	String sourceName;
	String author = "";
	public String title = "";
	String description = "";
	String url = "";
	String urlToImage = "";
	String publishedAt = "";
	String content = "";
	int newsCategory = 0;
	// We send a query to the api. We get a JSON with news articles.
    // This number doesn't say how many news articles are in the JSON, but how many
    // articles the api has stored that match this query. It sends only up to 100 results
    // even if there are more matches.
	private int totalAmountInThisQuery;
	
	public NewsArticle() {
		
	}
	
	public void setArticleProperties(JSONObject articleJson){
		this.author = JSONUtils.getStringErrorHandled(articleJson, "author");
		this.title = JSONUtils.getStringErrorHandled(articleJson, "title");
		this.description = JSONUtils.getStringErrorHandled(articleJson, "description");
		this.url = JSONUtils.getStringErrorHandled(articleJson, "url");
		this.urlToImage = JSONUtils.getStringErrorHandled(articleJson, "urlToImage");
		this.publishedAt = JSONUtils.getStringErrorHandled(articleJson, "publishedAt");
		this.content = JSONUtils.getStringErrorHandled(articleJson, "content");
	}

	public void setTotalAmountInThisQuery(int totalAmount){
		this.totalAmountInThisQuery = totalAmount;
	}

	public int getTotalAmountInThisQuery(){
		return this.totalAmountInThisQuery;
	}


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
