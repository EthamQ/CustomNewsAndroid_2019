package com.raphael.rapha.myNews.roomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.raphael.rapha.myNews.roomDatabase.newsHistory.NewsHistoryRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsHistory.INewsHistoryDao;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.IUserPreferenceDao;
import com.raphael.rapha.myNews.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.IKeyWordDao;
import com.raphael.rapha.myNews.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.ILanguageCombinationDao;
import com.raphael.rapha.myNews.roomDatabase.languageCombination.LanguageCombinationRoomModel;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.INewsArticleDao;
import com.raphael.rapha.myNews.roomDatabase.newsArticles.NewsArticleRoomModel;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.IRequestOffsetDao;
import com.raphael.rapha.myNews.roomDatabase.requestOffset.RequestOffsetRoomModel;

@Database(entities = {
        UserPreferenceRoomModel.class,
        NewsArticleRoomModel.class,
        KeyWordRoomModel.class,
        LanguageCombinationRoomModel.class,
        NewsHistoryRoomModel.class,
        RequestOffsetRoomModel.class
}, version = 108, exportSchema = false)
@TypeConverters({RoomConverters.class})
public abstract class AppDatabase extends RoomDatabase{

        private static AppDatabase instance;
        public abstract IUserPreferenceDao dao();
        public abstract INewsArticleDao newsArticleDao();
        public abstract IKeyWordDao keyWordDao();
        public abstract ILanguageCombinationDao languageCombinationDao();
        public abstract IRequestOffsetDao requestOffsetDao();
        public abstract INewsHistoryDao newsHistoryDao();

        public static synchronized AppDatabase getInstance(Context context){
                if(instance == null){
                        instance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AppDatabase.class,
                                "news_article_db").fallbackToDestructiveMigration()
                                .addCallback(roomCallback)
                                .build();
                }
                return instance;
        }

        private static Callback roomCallback = new Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        new PopulateDbAsyncTask(instance).execute();
                }
        };

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

                private IUserPreferenceDao dao;

                private PopulateDbAsyncTask(AppDatabase db){
                        dao = db.dao();
                }
                @Override
                protected Void doInBackground(Void... voids) {
                        return null;
                }
        }

}
