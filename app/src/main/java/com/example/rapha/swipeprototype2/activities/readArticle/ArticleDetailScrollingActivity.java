package com.example.rapha.swipeprototype2.activities.readArticle;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.languages.LanguageSettingsService;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticleUtils;
import com.squareup.picasso.Picasso;

public class ArticleDetailScrollingActivity extends AppCompatActivity {

    NewsArticle newsArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_scrolling);
        getArticle();
        setToolbar();
        setArticleTitle();
        setArticleContent();
        setArticleUrl();
        setArticleImage();
    }

    private void getArticle(){
        newsArticle = getIntent().getParcelableExtra("clickedArticle");
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle("Read the article");
    }

    private void setArticleTitle(){
        ((TextView)findViewById(R.id.articleTitle)).setText(newsArticle.title);
    }

    private void setArticleContent(){
        Log.d("displayy", "id: " + newsArticle.languageId);
        TextView contentTextView = findViewById(R.id.articleContent);
        if(newsArticle.languageId.equals(LanguageSettingsService.RUSSIAN)){
            contentTextView.setText("Sorry, article previews for russian articles aren't supported.");
        }
        else {
            String previewContent = NewsArticleUtils.removeCharInformation(newsArticle.content);
            String defaultText = "Sorry, the article preview isn't available.";
            boolean contentValid = previewContent != null && !previewContent.isEmpty();
            contentTextView.setText(contentValid ? previewContent : defaultText);
        }
    }

    private void setArticleUrl(){
        ((TextView)findViewById(R.id.articleLink)).setText(newsArticle.url);
    }

    private void setArticleImage(){
        ImageView imageView = findViewById(R.id.articleImage);
        try{
            Picasso.get()
                    .load(newsArticle.urlToImage)
                    .error(R.drawable.newsdefault)
                    .into(imageView);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
