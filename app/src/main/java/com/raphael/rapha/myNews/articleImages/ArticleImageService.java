package com.raphael.rapha.myNews.articleImages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.raphael.rapha.myNews.swipeCardContent.NewsArticle;

import java.io.InputStream;
import java.util.LinkedList;

public class ArticleImageService {

    /**
     * Takes an array of NewsArticle objects and sets the
     * imageForTextView property of every news article to a Bitmap object
     * that can be used as an image in a TextView.
     * @param newsArticle
     */
    public static void setImagesForTextView(LinkedList<NewsArticle> newsArticle, int offset){
        int endOfLoop = offset + 5 > newsArticle.size() ? newsArticle.size() : offset + 5;
        for(int i = offset; i < endOfLoop; i++){
            new DownloadImageTask(newsArticle.get(i)).execute();
        }
    }

    /**
     * Async task that takes a NewsArticle object in the constructor.
     * Gets a Bitmap object of an image with the help of an image url.
     * Sets imageForTextView property of the news article to this Bitmap object.
     */
    private static class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
        NewsArticle newsArticle;
        public DownloadImageTask(NewsArticle newsArticle) {
            this.newsArticle = newsArticle;
        }

        protected Bitmap doInBackground(Void... voids) {
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(newsArticle.urlToImage).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            newsArticle.imageForTextView = result;
        }
    }
}
