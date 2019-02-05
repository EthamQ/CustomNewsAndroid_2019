package com.example.rapha.swipeprototype2.activities.articleDetailActivity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rapha.swipeprototype2.R;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticle;
import com.example.rapha.swipeprototype2.swipeCardContent.NewsArticleUtils;
import com.squareup.picasso.Picasso;

public class ArticleDetailScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NewsArticle article = getIntent().getParcelableExtra("clickedArticle");
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle("Read the Article");
        ((TextView)findViewById(R.id.articleTitle)).setText(article.title);
        ((TextView)findViewById(R.id.articleContent)).setText(
                NewsArticleUtils.removeCharInformation(article.content)
        );
        ((TextView)findViewById(R.id.articleLink)).setText(article.url);
        ImageView imageView = findViewById(R.id.articleImage);
        try{
            Picasso.get()
                    .load(article.urlToImage)
                    .error(R.drawable.newsdefault)
                    .into(imageView);
        } catch(Exception e){
            //convertView.findViewById(R.id.imageBackground).setVisibility(TextView.INVISIBLE);
        }

    }
}
