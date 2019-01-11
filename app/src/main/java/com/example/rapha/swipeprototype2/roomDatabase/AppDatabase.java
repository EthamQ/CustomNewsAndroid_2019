package com.example.rapha.swipeprototype2.roomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {UserPreferenceTable.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

        private static AppDatabase instance;
        public abstract IUserPreferenceDao dao();

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

        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
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
//                        dao.insertOneNewsCategory(new UserPreferenceTable(0, 1));
//                        dao.insertOneNewsCategory(new UserPreferenceTable(1, 3));
//                        dao.insertOneNewsCategory(new UserPreferenceTable(2, 2));
                        return null;
                }
        }

}
