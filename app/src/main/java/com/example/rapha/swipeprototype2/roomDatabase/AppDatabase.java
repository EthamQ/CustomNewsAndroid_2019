package com.example.rapha.swipeprototype2.roomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.IUserPreferenceDao;
import com.example.rapha.swipeprototype2.roomDatabase.categoryRating.UserPreferenceRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.IKeyWordDao;
import com.example.rapha.swipeprototype2.roomDatabase.keyWordPreference.KeyWordRoomModel;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.INewsArticleDao;
import com.example.rapha.swipeprototype2.roomDatabase.newsArticles.NewsArticleRoomModel;

@Database(entities = {
        UserPreferenceRoomModel.class,
        NewsArticleRoomModel.class,
        KeyWordRoomModel.class
}, version = 10, exportSchema = false)
@TypeConverters({RoomConverters.class})
public abstract class AppDatabase extends RoomDatabase{

        private static AppDatabase instance;
        public abstract IUserPreferenceDao dao();
        public abstract INewsArticleDao newsArticleDao();
        public abstract IKeyWordDao keyWordDaoDao();

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
